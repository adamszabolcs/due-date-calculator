package com.emarsys.calculator;

import java.time.LocalTime;

public class DueDateCalculator {

    private LocalTime startHour;
    private LocalTime endHour;

    public DueDateCalculator(LocalTime startHour, LocalTime endHour) {
        validateArguments(startHour, endHour);
        this.startHour = startHour;
        this.endHour = endHour;
    }

    private void validateArguments(LocalTime startHour, LocalTime endHour) {
        if (startHour == null) {
            throw new IllegalArgumentException("Start hour is required!");
        } else if (endHour == null) {
            throw new IllegalArgumentException("End hour is required!");
        }
        if (startHour.isAfter(endHour)) {
            throw new IllegalArgumentException("Start hour is after end hour!");
        }
    }

}
