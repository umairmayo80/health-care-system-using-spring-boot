package server.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name = "slot_table",
                uniqueConstraints = @UniqueConstraint(name = "unique_slot", columnNames = {"doctorId","date","startTime","endTime"}))
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slotId")
    private int slotId;

    @ManyToOne(fetch = FetchType.EAGER)
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
            fetch = FetchType.EAGER
    )
    private Appointment appointmentV1;

    public Slot()
    {
    }


    public Slot(User doctor, LocalDate date, LocalTime startTime, LocalTime endTime)  {
        this.slotId = 0;
        this.doctor = doctor;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.occupied = false;

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


    public Appointment getAppointmentV1() {
        return appointmentV1;
    }

    public void setAppointmentV1(Appointment appointmentV1) {
        this.appointmentV1 = appointmentV1;
        appointmentV1.setSlot(this);
    }
    public void removeAppointmentV1(){
        if(appointmentV1 !=null){
            appointmentV1.setSlot(null);
            this.appointmentV1 = null;
        }
    }

    @Override
    public String toString() {
        return "Slot{" +
                "slotId=" + slotId +
                ", doctor=" + doctor +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", occupied=" + occupied +
                ", appointmentV1=" + appointmentV1 +
                '}';
    }
}
