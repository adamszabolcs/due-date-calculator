package com.emarsys.calculator;

import java.time.LocalTime;

public class DueDateCalculator {

    private LocalTime startHour;
    private LocalTime endHour;

    public DueDateCalculator(LocalTime startHour, LocalTime endHour) {
        if (startHour.isAfter(endHour)) {
            throw new IllegalArgumentException("Start hour is after end hour!");
        }
        this.startHour = startHour;
        this.endHour = endHour;
    }

}
