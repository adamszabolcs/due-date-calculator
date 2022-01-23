package com.emarsys.calculator;

import com.emarsys.calculator.exception.InvalidSubmitDateException;
import com.emarsys.calculator.exception.InvalidTurnAroundTimeException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class DueDateCalculatorTest {

    private final DueDateCalculator calculator = new DueDateCalculator(LocalTime.of(9, 0), LocalTime.of(17, 0));

    @Test
    void smoke() {
        assertTrue(true);
    }

    @Test
    void constructorShouldThrowExceptionIfStartTimeIsAfterEndTime() {
        assertThrows(IllegalArgumentException.class, () -> new DueDateCalculator(LocalTime.of(12, 0), LocalTime.of(9, 0)));
    }

    @Test
    void constructorShouldThrowIllegalArgumentExceptionIfStartTimeIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new DueDateCalculator(null, LocalTime.of(17, 0)));
    }

    @Test
    void constructorShouldThrowIllegalArgumentExceptionIfEndTimeIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new DueDateCalculator(LocalTime.of(9, 0), null));
    }

    @Test
    void constructorShouldThrowExceptionIfStartTimeEqualsEndTime() {
        assertThrows(IllegalArgumentException.class, () -> new DueDateCalculator(LocalTime.of(9, 12), LocalTime.of(9, 12)));
    }

    @Test
    void invalidSubmitDateShouldThrownIfSubmitDateIsWeekendDay() {
        assertThrows(InvalidSubmitDateException.class, () -> calculator.calculateDueDate(LocalDateTime.of(2022, 1, 22, 9, 1), 2));
    }

    @Test
    void invalidSubmitDateShouldThrownIfSubmitDateTimeIsNotInWorkingHours() {
        assertThrows(InvalidSubmitDateException.class, () -> calculator.calculateDueDate(LocalDateTime.of(2022, 1, 21, 8, 59), 2));
    }

    @Test
    void invalidTurnAroundTimeExceptionShouldThrownIfTurnAroundTimeIsNegative() {
        assertThrows(InvalidTurnAroundTimeException.class, () -> calculator.calculateDueDate(LocalDateTime.of(2022, 1, 21, 9, 1), -4));
    }

    @Test
    void invalidSubmitDateShouldThrownIfSubmitDateIsNull() {
        assertThrows(InvalidSubmitDateException.class, () -> calculator.calculateDueDate(null, 2));
    }

    @Test
    void calculateDueDateShouldReturnSameDayWhenTurnAroundTimeIsLessThanEightHours() throws InvalidSubmitDateException, InvalidTurnAroundTimeException {
        LocalDateTime expected = LocalDateTime.of(2022, 1, 21, 11, 0);
        assertEquals(expected, calculator.calculateDueDate(LocalDateTime.of(2022, 1, 21, 9, 0), 2));
    }

    @Test
    void calculateDueDateShouldReturnSameDayWhenTurnAroundTimeIsEqualsToEndTime() throws InvalidSubmitDateException, InvalidTurnAroundTimeException {
        LocalDateTime expected = LocalDateTime.of(2022, 1, 21, 17, 0);
        assertEquals(expected, calculator.calculateDueDate(LocalDateTime.of(2022, 1, 21, 16, 0), 1));
    }

    @Test
    void calculateDueDateShouldReturnNextDayIfResultIsMoreThanTheEndTime() throws InvalidSubmitDateException, InvalidTurnAroundTimeException {
        LocalDateTime expected = LocalDateTime.of(2022, 1, 21, 9, 59);
        assertEquals(expected, calculator.calculateDueDate(LocalDateTime.of(2022, 1, 20, 16, 59), 1));
    }

    @Test
    void calculateDueDateShouldSkipWeekend() throws InvalidSubmitDateException, InvalidTurnAroundTimeException {
        LocalDateTime expected = LocalDateTime.of(2022, 1, 24, 10, 0);
        assertEquals(expected, calculator.calculateDueDate(LocalDateTime.of(2022, 1, 21, 16, 0), 2));
    }

    @Test
    void calculateDueDateShouldReturnTheSameIfTurnAroundTimeIsZero() throws InvalidSubmitDateException, InvalidTurnAroundTimeException {
        LocalDateTime submitAndDueDate = LocalDateTime.of(2022, 1, 21, 12, 12);
        assertEquals(submitAndDueDate, calculator.calculateDueDate(submitAndDueDate, 0));
    }

    @Test
    void calculateDueDateShouldSkipMultipleWeekends() throws InvalidSubmitDateException, InvalidTurnAroundTimeException {
        LocalDateTime expected = LocalDateTime.of(2022, 1, 31, 11, 0);
        assertEquals(expected, calculator.calculateDueDate(LocalDateTime.of(2022, 1, 21, 10, 0), 49));
    }

}