package com.emarsys.calculator;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class DueDateCalculatorTest {

    private DueDateCalculator calculator = new DueDateCalculator(LocalTime.of(9, 0), LocalTime.of(17, 0));

    @Test
    void smoke() {
        assertTrue(true);
    }

    @Test
    void constructorShouldThrowExceptionIfStartHourIsAfterEndHour() {
        assertThrows(IllegalArgumentException.class, () -> new DueDateCalculator(LocalTime.of(12, 0), LocalTime.of(9, 0)));
    }

    @Test
    void constructorShouldThrowIllegalArgumentExceptionIfStartHourIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new DueDateCalculator(null, LocalTime.of(17, 0)));
    }

    @Test
    void constructorShouldThrowIllegalArgumentExceptionIfEndHourIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new DueDateCalculator(LocalTime.of(9, 0), null));
    }

}