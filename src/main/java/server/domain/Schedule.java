package server.domain;

import server.utilities.ScheduleCreationException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Schedule {
    private int userId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public Schedule(int userId, String date, String startTime, String endTime) throws ScheduleCreationException {
        this.userId = userId;
        try {
            this.date = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new ScheduleCreationException("Invalid date format. Please enter date in the format YYYY-MM-DD.", e);
        }

        try {
            this.startTime = LocalTime.parse(startTime);
        } catch (DateTimeParseException e) {
            throw new ScheduleCreationException("Invalid start time format. Please enter time in the format HH:MM:SS.", e);
        }

        try {
            this.endTime = LocalTime.parse(endTime);
        } catch (DateTimeParseException e) {
            throw new ScheduleCreationException("Invalid end time format. Please enter time in the format HH:MM:SS.", e);
        }
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "server.domain.Schedule{" +
                "userId=" + userId +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
