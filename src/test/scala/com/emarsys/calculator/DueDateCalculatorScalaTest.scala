package com.emarsys.calculator

import com.emarsys.calculator.exception.{InvalidSubmitDateException, InvalidTurnAroundTimeException}
import org.scalatest.FunSuite

import java.time.{LocalDateTime, LocalTime}

class DueDateCalculatorScalaTest extends FunSuite {

  val calculator: DueDateCalculatorScala = new DueDateCalculatorScala(Some(LocalTime.of(9, 0)), Some(LocalTime.of(17, 0)))

  test("DueDateCalculatorScala.constructorShouldThrowExceptionIfStartTimeIsAfterEndTime") {
    assertThrows[IllegalArgumentException] {
      new DueDateCalculatorScala(Some(LocalTime.of(12, 0)), Some(LocalTime.of(9, 0)))
    }
  }

  test("DueDateCalculatorScala.constructorShouldThrowIllegalArgumentExceptionIfStartTimeIsNull") {
    assertThrows[IllegalArgumentException] {
      new DueDateCalculatorScala(None, Some(LocalTime.of(17, 0)))
    }
  }

  test("DueDateCalculatorScala.constructorShouldThrowIllegalArgumentExceptionIfEndTimeIsNull") {
    assertThrows[IllegalArgumentException] {
      new DueDateCalculatorScala(Some(LocalTime.of(9, 0)), None)
    }
  }

  test("DueDateCalculatorScala.constructorShouldThrowExceptionIfStartTimeEqualsEndTime") {
    assertThrows[IllegalArgumentException] {
      new DueDateCalculatorScala(Some(LocalTime.of(9, 0)), Some(LocalTime.of(9, 0)))
    }
  }

  test("DueDateCalculatorScala.invalidSubmitDateShouldThrownIfSubmitDateIsWeekendDay") {
    assertThrows[InvalidSubmitDateException] {
      calculator.calculateDueDate(LocalDateTime.of(2022, 1, 22, 9, 1), 2)
    }
  }

  test("DueDateCalculatorScala.invalidSubmitDateShouldThrownIfSubmitDateTimeIsNotInWorkingHours") {
    assertThrows[InvalidSubmitDateException] {
      calculator.calculateDueDate(LocalDateTime.of(2022, 1, 21, 8, 59), 2)
    }
  }

  test("DueDateCalculatorScala.invalidTurnAroundTimeExceptionShouldThrownIfTurnAroundTimeIsNegative") {
    assertThrows[InvalidTurnAroundTimeException] {
      calculator.calculateDueDate(LocalDateTime.of(2022, 1, 21, 9, 1), -4)
    }
  }

  test("DueDateCalculatorScala.invalidSubmitDateShouldThrownIfSubmitDateIsNull") {
    assertThrows[InvalidSubmitDateException] {
      calculator.calculateDueDate(null, 2)
    }
  }

  test("DueDateCalculatorScala.calculateDueDateShouldReturnSameDayWhenTurnAroundTimeIsLessThanEightHours") {
    assert(LocalDateTime.of(2022, 1, 21, 11, 0).equals(calculator.calculateDueDate(LocalDateTime.of(2022, 1, 21, 9, 0), 2)))
  }

  test("DueDateCalculatorScala.calculateDueDateShouldReturnSameDayWhenTurnAroundTimeIsEqualsToEndTime") {
    assertResult(LocalDateTime.of(2022, 1, 21, 17, 0)) {
      calculator.calculateDueDate(LocalDateTime.of(2022, 1, 21, 16, 0), 1)
    }
  }

  test("DueDateCalculatorScala.calculateDueDateShouldReturnNextDayIfResultIsMoreThanTheEndTime") {
    assertResult(LocalDateTime.of(2022, 1, 21, 9, 59)) {
      calculator.calculateDueDate(LocalDateTime.of(2022, 1, 20, 16, 59), 1)
    }
  }

  test("DueDateCalculatorScala.calculateDueDateShouldSkipWeekend") {
    assertResult(LocalDateTime.of(2022, 1, 24, 10, 0)) {
      calculator.calculateDueDate(LocalDateTime.of(2022, 1, 21, 16, 0), 2)
    }
  }

  test("DueDateCalculatorScala.calculateDueDateShouldReturnTheSameIfTurnAroundTimeIsZero") {
    assertResult(LocalDateTime.of(2022, 1, 21, 12, 12)) {
      calculator.calculateDueDate(LocalDateTime.of(2022, 1, 21, 12, 12), 0)
    }
  }

  test("DueDateCalculatorScala.calculateDueDateShouldSkipMultipleWeekends") {
    assertResult(LocalDateTime.of(2022, 1, 31, 11, 0)) {
      calculator.calculateDueDate(LocalDateTime.of(2022, 1, 21, 10, 0), 49)
    }
  }



}
