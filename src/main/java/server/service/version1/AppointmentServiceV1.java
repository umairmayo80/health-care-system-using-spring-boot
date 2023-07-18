package server.service.version1;
//import server.domain.Appointment;
import server.domain.User;
import server.domain.Appointment;

import java.util.List;

public interface AppointmentServiceV1 {


    // Save appointments to storage
    void saveAppointmentsToStorage(List<Appointment> appointmentList);

    List<Appointment> getAppointments();

    void viewAllAppointments();

    void viewAppointmentsByPatientId(int patientId);

    void viewAppointmentsByDoctorId(int doctorId);

    boolean addAppointmentEntry(Appointment appointment);

    boolean addAppointment(Appointment appointmentV1, User currentUser);


}
