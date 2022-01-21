package com.emarsys.calculator;

import java.time.LocalTime;

public class DueDateCalculator {

    private LocalTime startTime;
    private LocalTime endTime;

    public DueDateCalculator(LocalTime startTime, LocalTime endTime) {
        validateArguments(startTime, endTime);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private void validateArguments(LocalTime startTime, LocalTime endTime) {
        validateNullCheck(startTime, endTime);
        validateStartTimeIsBeforeEndTime(startTime, endTime);
    }

    private void validateNullCheck(LocalTime startTime, LocalTime endTime) {
        if (startTime == null) {
            throw new IllegalArgumentException("Start time is required!");
        } else if (endTime == null) {
            throw new IllegalArgumentException("End time is required!");
        }
    }

    private void validateStartTimeIsBeforeEndTime(LocalTime startTime, LocalTime endTime) {
        if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time!");
        }
    }

}
