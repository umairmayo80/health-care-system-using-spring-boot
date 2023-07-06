package server.domain;
import server.domain.version1.AppointmentV1;
import server.utilities.ScheduleCreationException;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "slot_table",
                uniqueConstraints = @UniqueConstraint(name = "unique_slot", columnNames = {"doctorId","date","startTime","endTime"}))
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slotId")
    private int slotId;

    @Transient
    private int doctorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorId", referencedColumnName = "userid")
    private User doctor;


    private LocalDate date;

    @Column(name = "startTime")
    private LocalTime startTime;

    @Column(name = "endTime")
    private LocalTime endTime;
    private boolean occupied;


    @OneToOne(
            mappedBy = "slot",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private AppointmentV1 appointmentV1;

    public Slot()
    {
    }
    public Slot(int doctorId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.slotId = 0;
        this.doctorId = doctorId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.occupied = false;
    }

    public Slot(int doctorId, String date, String startTime, String endTime) throws ScheduleCreationException {
        this.slotId = 0;
        this.occupied = false;
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

    }

    public Slot(int slotId, int doctorId, String date, String startTime, String endTime, boolean occupied) throws ScheduleCreationException {
        this.slotId = slotId;
        this.occupied = occupied;
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

    }

    public Slot(int slotId, int doctorId, User doctor, LocalDate date, LocalTime startTime, LocalTime endTime, boolean occupied) {
        this.slotId = slotId;
        this.doctorId = doctorId;
        this.doctor = doctor;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.occupied = occupied;
    }


    public Slot(int slotId, int doctorId, User doctor, LocalDate date, LocalTime startTime, LocalTime endTime, boolean occupied, AppointmentV1 appointmentV1) {
        this.slotId = slotId;
        this.doctorId = doctorId;
        this.doctor = doctor;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.occupied = occupied;
        this.appointmentV1 = appointmentV1;
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

    public boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }


    public AppointmentV1 getAppointmentV1() {
        return appointmentV1;
    }

    public void setAppointmentV1(AppointmentV1 appointmentV1) {
        this.appointmentV1 = appointmentV1;
        appointmentV1.setSlot(this);
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
}
