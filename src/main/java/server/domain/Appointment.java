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

    @Transient
    private int patientId;
    @Transient
    private int doctorSlotId;

    // default constructor
    Appointment(){

    }

    // Constructor
    public Appointment(int appointmentId, int patientId, int doctorSlotId) {
        this.patientId = patientId;
        this.doctorSlotId = doctorSlotId;
        this.appointmentId = appointmentId;
    }

    public Appointment(int patientId, int doctorSlotId){
        this.appointmentId = 0;
        this.patientId = patientId;
        this. doctorSlotId = doctorSlotId;
    }

    public Appointment(int appointmentId, User patient, Slot slot, int patientId, int doctorSlotId) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.slot = slot;
        this.patientId = patientId;
        this.doctorSlotId = doctorSlotId;
    }



    // Getter for patientId
    public int getPatientId() {
        return patientId;
    }

    // Setter for patientId
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    // Getter for doctorSlotId
    public int getDoctorSlotId() {
        return doctorSlotId;
    }

    // Setter for doctorSlotId
    public void setDoctorSlotId(int doctorSlotId) {
        this.doctorSlotId = doctorSlotId;
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
                "patientId=" + patientId +
                ", doctorSlotId=" + doctorSlotId +
                ", appointmentId=" + appointmentId +
                '}';
    }
}
