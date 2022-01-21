package com.emarsys.calculator;

import com.emarsys.calculator.exception.InvalidSubmitDateException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public LocalDateTime calculateDueDate(LocalDateTime submitDate, int turnAroundTime) throws InvalidSubmitDateException {
        if (submitDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) || submitDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            throw new InvalidSubmitDateException("Submit date is a weekend day!");
        } else if (submitDate.toLocalTime().isBefore(startTime) || submitDate.toLocalTime().isAfter(endTime)) {
            throw new InvalidSubmitDateException("Submit time is not in working hours!");
        }
        return null;
    }

}
