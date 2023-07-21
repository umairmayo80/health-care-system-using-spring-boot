package server.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class SlotDTO {
    private int slotId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean occupied;
    private int doctorId; // Additional field for doctorId


    public SlotDTO() {
    }

    public SlotDTO(int slotId, LocalDate date, LocalTime startTime, LocalTime endTime, boolean occupied, int doctorId) {
        this.slotId = slotId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.occupied = occupied;
        this.doctorId = doctorId;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
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

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    @Override
    public String toString() {
        return "SlotDTO{" +
                "slotId=" + slotId +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", occupied=" + occupied +
                ", doctorId=" + doctorId +
                '}';
    }
}