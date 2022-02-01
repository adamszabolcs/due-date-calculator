package com.emarsys.calculator

import com.emarsys.calculator.exception.{InvalidSubmitDateException, InvalidTurnAroundTimeException}

import java.time.{DayOfWeek, LocalDateTime, LocalTime}

class DueDateCalculatorScala {

  var startTime: LocalTime = null
  var endTime: LocalTime = null
  var workingHours: Int = 0
  val numberOfWeekendDays: Int = 2

  def this(startTime: Option[LocalTime], endTime: Option[LocalTime]) {
    this()
    this.startTime = startTime match {
      case Some(value) => value
      case None => null
    }
    this.endTime = endTime match {
      case Some(value) => value
      case None => null
    }
    validateConstructorArguments(this.startTime, this.endTime)
    workingHours = this.endTime.getHour - this.startTime.getHour
  }

  private[this] def validateConstructorArguments(startTime: LocalTime, endTime: LocalTime): Unit = {
    validateConstructorArgumentNullCheck(startTime, endTime)
    validateStartTimeIsBeforeEndTime(startTime, endTime)
  }

  private[this] def validateConstructorArgumentNullCheck(startTime: LocalTime, endTime: LocalTime): Unit = {
    if (startTime == null) {
      throw new IllegalArgumentException("Start Time required!")
    } else if (endTime == null) {
      throw new IllegalArgumentException("End time is required!")
    }
  }

  private[this] def validateStartTimeIsBeforeEndTime(startTime: LocalTime, endTime: LocalTime): Unit = {
    if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
      throw new IllegalArgumentException("Start time must be before end time!")
    }
  }

  def calculateDueDate(submitDate: LocalDateTime, turnAroundTime: Int): LocalDateTime = {
    validateCalculateDueDateArguments(submitDate, turnAroundTime)
    if (isDueDateIsOnTheSameDay(submitDate, turnAroundTime)) {
      return submitDate.plusHours(turnAroundTime)
    }

    var dueDate: LocalDateTime = submitDate
    var plusDays: Int = turnAroundTime / workingHours
    var remainingHours: Int = turnAroundTime % workingHours

    if (submitDate.plusHours(remainingHours).toLocalTime.isAfter(endTime)) {
      plusDays += 1
      remainingHours -= endTime.minusHours(submitDate.getHour).getHour
      dueDate = dueDate.withHour(startTime.getHour)
    }
    dueDate = setDueDateToAppropriateWorkDay(dueDate, plusDays)
    dueDate.plusHours(remainingHours)
  }

  private[this] def validateCalculateDueDateArguments(submitDate: LocalDateTime, turnAroundTime: Int): Unit = {
    validateSubmitDateIsNotNull(submitDate)
    validateSubmitDateIsNotOnWeekend(submitDate.getDayOfWeek)
    validateSubmitDateTimeIsDuringWorkingHours(submitDate.toLocalTime)
    validateTurnAroundTimeIsNotNegative(turnAroundTime)
  }

  private[this] def validateSubmitDateIsNotNull(submitDate: LocalDateTime): Unit = {
    if (submitDate == null) {
      throw new InvalidSubmitDateException("Submit date is required!")
    }
  }

  private[this] def validateSubmitDateIsNotOnWeekend(dayOfWeek: DayOfWeek): Unit = {
    if (dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY)) {
      throw new InvalidSubmitDateException("Submit date is on weekend!")
    }
  }

  private[this] def validateSubmitDateTimeIsDuringWorkingHours(submitTime: LocalTime): Unit = {
    if (submitTime.isBefore(startTime) || submitTime.isAfter(endTime)) {
      throw new InvalidSubmitDateException("Submit time is not during working hours!")
    }
  }

  private[this] def validateTurnAroundTimeIsNotNegative(turnAroundTime: Int): Unit = {
    if (turnAroundTime < 0) {
      throw new InvalidTurnAroundTimeException("Turnaround time should be a positive integer!")
    }
  }

  private[this] def isDueDateIsOnTheSameDay(submitDate: LocalDateTime, turnAroundTime: Int): Boolean = {
    val endOfSubmitDate: LocalDateTime = submitDate.withHour(endTime.getHour).withMinute(endTime.getMinute)

    submitDate.plusHours(turnAroundTime).isBefore(endOfSubmitDate) || submitDate.plusHours(turnAroundTime).toLocalTime.equals(endTime)
  }

  private[this] def setDueDateToAppropriateWorkDay(dueDate: LocalDateTime, days: Int): LocalDateTime = {
    var varDueDate = dueDate
    for (_ <- 1 to days) {
      varDueDate = varDueDate.plusDays(1)
      if (varDueDate.getDayOfWeek.equals(DayOfWeek.SATURDAY)) {
        varDueDate = varDueDate.plusDays(numberOfWeekendDays)
      }
    }
    varDueDate
  }

}
