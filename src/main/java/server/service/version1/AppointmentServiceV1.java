package server.service.version1;
//import server.domain.Appointment;
import server.domain.version1.AppointmentV1;
import java.util.List;

public interface AppointmentServiceV1 {


    // Save appointments to storage
    public void saveAppointmentsToStorage(List<AppointmentV1> appointmentList);

    public List<AppointmentV1> getAppointments();

    void viewAllAppointments();

    public void viewAppointmentsByPatientId(int patientId);

    public void viewAppointmentsByDoctorId(int doctorId);

    public boolean addAppointmentEntry(AppointmentV1 appointment);


}
