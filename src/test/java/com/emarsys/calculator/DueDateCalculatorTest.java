package com.emarsys.calculator;

import com.emarsys.calculator.exception.InvalidSubmitDateException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class DueDateCalculatorTest {

    private DueDateCalculator calculator = new DueDateCalculator(LocalTime.of(9, 0), LocalTime.of(17, 0));

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

}