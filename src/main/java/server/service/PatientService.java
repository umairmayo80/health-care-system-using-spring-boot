package server.service;
import server.domain.Appointment;


public interface PatientService {

    void viewAppointments(int patient_id);
    boolean addAppointment(Appointment appointment);
}