package server.domain;
import server.utilities.ScheduleCreationException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Slot {
    private int slotId;
    private int doctorId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean occupied;

    public Slot(int slotId, int doctorId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.slotId = slotId;
        this.doctorId = doctorId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.occupied = false;
    }

    public Slot(int slotId, int doctorId, String date, String startTime, String endTime, Boolean occupied) throws ScheduleCreationException {
        this.slotId = slotId;
        this.doctorId = doctorId;
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

        this.occupied = occupied;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
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

    public Boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }


    @Override
    public String toString() {
        return "Slot{" +
                "slotId=" + slotId +
                ", doctorId=" + doctorId +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", occupied=" + occupied +
                '}';
    }

    public static void main(String[] args){
        Slot slot = new Slot(1, 101, LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0));
        System.out.println(slot.getDate());        // Output: current date
        System.out.println(slot.getStartTime());   // Output: 09:00
        System.out.println(slot.getEndTime());     // Output: 10:00

        slot.setDoctorId(102);
        System.out.println(slot.getDoctorId());
        System.out.println(slot);
    }
}


