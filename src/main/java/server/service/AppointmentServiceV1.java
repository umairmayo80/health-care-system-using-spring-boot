package server.service;
//import server.domain.Appointment;
import server.domain.version1.Appointment;
import java.util.List;

public interface AppointmentServiceV1 {


    // Save appointments to storage
    public void saveAppointmentsToStorage(List<Appointment> appointmentList);

    public List<Appointment> getAppointments();

    public void viewAppointmentsByPatientId(int patientId);

    public void viewAppointmentsByDoctorId(int doctorId);

    public boolean addAppointmentEntry(Appointment appointment);


}
