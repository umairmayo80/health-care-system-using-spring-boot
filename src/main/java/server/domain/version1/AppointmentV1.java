package server.domain.version1;

public class AppointmentV1 {
    private int patientId;
    private int doctorSlotId;
    private int appointmentId;

    // Constructor
    public AppointmentV1(int appointmentId, int patientId, int doctorSlotId) {
        this.patientId = patientId;
        this.doctorSlotId = doctorSlotId;
        this.appointmentId = appointmentId;
    }

    public AppointmentV1(int patientId, int doctorSlotId){
        this.appointmentId = 0;
        this.patientId = patientId;
        this. doctorSlotId = doctorSlotId;
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
