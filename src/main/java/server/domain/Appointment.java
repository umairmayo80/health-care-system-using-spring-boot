package server.domain;

import javax.persistence.*;

@Entity
@Table(name = "appointment_table")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointmentId")
    private int appointmentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patientId", referencedColumnName = "userid")
    private User patient;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctorSlotId", referencedColumnName = "slotId")
    private Slot slot;



    // default constructor
    public Appointment(){

    }

    // Constructor
    public Appointment(int appointmentId, User patient, Slot slot) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.slot = slot;
    }

    public Appointment(User patient, Slot slot) {
        this.patient = patient;
        this.slot = slot;
    }

    // Getter for appointmentId
    public int getAppointmentId() {
        return appointmentId;
    }

    // Setter for appointmentId
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }


    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    // toString method to represent the Appointment object as a string

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", patient=" + patient +
                ", slot=" + slot +
                '}';
    }
}
