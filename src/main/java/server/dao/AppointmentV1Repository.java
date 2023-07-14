package server.dao;

import server.domain.version1.AppointmentV1;

import java.util.List;

public interface AppointmentV1Repository {
    // Save appointments to storage
    void saveAppointmentsToStorage(List<AppointmentV1> appointmentList);

    List<AppointmentV1> getAll();

    void viewAppointmentsByPatientId(int patientId);

    void viewAppointmentsByDoctorId(int doctorId);

    void viewAllAppointments();

    boolean add(AppointmentV1 appointment);
}
