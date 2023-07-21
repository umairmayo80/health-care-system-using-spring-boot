package server.service;
//import server.domain.Appointment;
import server.domain.User;
import server.domain.Appointment;

import java.util.List;

public interface AppointmentServiceV1 {

    List<Appointment> getAppointments();

    List<Appointment> getAppointmentsByPatientId(int patientId);
    List<Appointment> getAppointmentsByDoctorId(int doctorId);



    boolean addAppointment(int doctorSlotId, int patientId);

    boolean deleteAppointment(int appointmentId);


}
