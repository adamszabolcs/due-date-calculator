package com.emarsys.calculator;

import com.emarsys.calculator.exception.InvalidSubmitDateException;
import com.emarsys.calculator.exception.InvalidTurnAroundTimeException;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DueDateCalculator {

    private final LocalTime startTime;
    private final LocalTime endTime;
    private final int workingHours;

    private static final int NUMBER_OF_WEEKEND_DAYS = 2;

    public DueDateCalculator(LocalTime startTime, LocalTime endTime) {
        validateConstructorArguments(startTime, endTime);
        this.startTime = startTime;
        this.endTime = endTime;
        this.workingHours = endTime.getHour() - startTime.getHour();
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

        if (isDueDateIsOnTheSameDay(submitDate, turnAroundTime)) {
            return submitDate.plusHours(turnAroundTime);
        } else {
            LocalDateTime dueDate = submitDate;
            int plusDays = turnAroundTime / workingHours;
            int remainingHours = turnAroundTime % workingHours;

            if (submitDate.plusHours(remainingHours).toLocalTime().isAfter(endTime)) {
                plusDays++;
                remainingHours -= endTime.minusHours(submitDate.getHour()).getHour();
                dueDate = dueDate.withHour(startTime.getHour());
            }
            dueDate = setDueDateToAppropriateWorkDay(dueDate, plusDays);
            return dueDate.plusHours(remainingHours);
        }
    }

    private void validateCalculateDueDateArguments(LocalDateTime submitDate, int turnAroundTime) throws InvalidSubmitDateException, InvalidTurnAroundTimeException {
        validateSubmitDateIsNotNull(submitDate);
        validateSubmitDateIsNotOnWeekend(submitDate.getDayOfWeek());
        validateSubmitDateTimeIsDuringWorkingHours(submitDate.toLocalTime());
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

    private boolean isDueDateIsOnTheSameDay(LocalDateTime submitDate, int turnAroundTime) {
        LocalDateTime endOfSubmitDate = submitDate.withHour(endTime.getHour()).withMinute(endTime.getMinute());

        return submitDate.plusHours(turnAroundTime).isBefore(endOfSubmitDate) ||
                submitDate.plusHours(turnAroundTime).toLocalTime().equals(endTime);
    }

    private LocalDateTime setDueDateToAppropriateWorkDay(LocalDateTime dueDate, int days) {
        for (int i = days; i > 0; i--) {
            dueDate = dueDate.plusDays(1);
            if (dueDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                dueDate = dueDate.plusDays(NUMBER_OF_WEEKEND_DAYS);
            }
        }
        return dueDate;
    }
}
