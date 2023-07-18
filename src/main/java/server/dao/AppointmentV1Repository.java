package server.dao;

import server.domain.Appointment;

import java.util.List;

public interface AppointmentV1Repository {
    // Save appointments to storage
    void saveAppointmentsToStorage(List<Appointment> appointmentList);

    List<Appointment> getAll();

    void viewAppointmentsByPatientId(int patientId);

    void viewAppointmentsByDoctorId(int doctorId);

    void viewAllAppointments();

    boolean add(Appointment appointment);
}
