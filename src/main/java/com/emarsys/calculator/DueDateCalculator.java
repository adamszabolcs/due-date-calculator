package com.emarsys.calculator;

import java.time.LocalTime;

public class DueDateCalculator {

    private LocalTime startHour;
    private LocalTime endHour;

    public DueDateCalculator(LocalTime startHour, LocalTime endHour) {
        this.startHour = startHour;
        this.endHour = endHour;
    }

}
