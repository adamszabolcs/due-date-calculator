package com.emarsys.calculator;

import com.emarsys.calculator.exception.InvalidSubmitDateException;
import com.emarsys.calculator.exception.InvalidTurnAroundTimeException;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DueDateCalculator {

    private LocalTime startTime;
    private LocalTime endTime;


    public DueDateCalculator(LocalTime startTime, LocalTime endTime) {
        validateConstructorArguments(startTime, endTime);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private void validateConstructorArguments(LocalTime startTime, LocalTime endTime) {
        validateConstructorArgumentNullCheck(startTime, endTime);
        validateStartTimeIsBeforeEndTime(startTime, endTime);
    }

    private void validateConstructorArgumentNullCheck(LocalTime startTime, LocalTime endTime) {
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

    public LocalDateTime calculateDueDate(LocalDateTime submitDate, int turnAroundTime) throws InvalidSubmitDateException, InvalidTurnAroundTimeException {
        validateCalculateDueDateArguments(submitDate, turnAroundTime);
        if (turnAroundTime < 8) {
            return submitDate.plusHours(turnAroundTime);
        }
        return null;
    }

    private void validateCalculateDueDateArguments(LocalDateTime submitDate, int turnAroundTime) throws InvalidSubmitDateException, InvalidTurnAroundTimeException {
        validateSubmitDateIsNotNull(submitDate);

        DayOfWeek dayOfWeek = submitDate.getDayOfWeek();
        LocalTime submitTime = submitDate.toLocalTime();

        validateSubmitDateIsNotOnWeekend(dayOfWeek);
        validateSubmitDateTimeIsDuringWorkingHours(submitTime);
        validateTurnAroundTimeIsNotNegative(turnAroundTime);
    }

    private void validateSubmitDateIsNotNull(LocalDateTime submitDate) throws InvalidSubmitDateException {
        if (submitDate == null) {
            throw new InvalidSubmitDateException("Submit date is required!");
        }
    }

    private void validateSubmitDateIsNotOnWeekend(DayOfWeek dayOfWeek) throws InvalidSubmitDateException {
        if (dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY)) {
            throw new InvalidSubmitDateException("Submit date is on weekend!");
        }
    }

    private void validateSubmitDateTimeIsDuringWorkingHours(LocalTime submitTime) throws InvalidSubmitDateException {
        if (submitTime.isBefore(startTime) || submitTime.isAfter(endTime)) {
            throw new InvalidSubmitDateException("Submit time is not during working hours!");
        }
    }

    private void validateTurnAroundTimeIsNotNegative(int turnAroundTime) throws InvalidTurnAroundTimeException {
        if (turnAroundTime < 0) {
            throw new InvalidTurnAroundTimeException("Turnaround time should be a positive integer!");
        }
    }

}
