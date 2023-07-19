package server.service;
//import server.domain.Appointment;
import server.domain.User;
import server.domain.Appointment;

import java.util.List;

public interface AppointmentServiceV1 {

    List<Appointment> getAppointments();

    void viewAllAppointments();

    void viewAppointmentsByPatientId(int patientId);

    void viewAppointmentsByDoctorId(int doctorId);

    boolean addAppointment(int doctorSlotId, int patientId);


}
