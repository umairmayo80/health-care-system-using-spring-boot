package server.dao;

import server.domain.Appointment;

import java.util.List;

public interface AppointmentV1Repository {
    // Save appointments to storage
    void saveAppointmentsToStorage(List<Appointment> appointmentList);

    List<Appointment> getAll();

    List<Appointment> getAppointmentsByPatientId(int patientId);

    List<Appointment> getAppointmentsByDoctorId(int doctorId);


    boolean add(Appointment appointment);
}
