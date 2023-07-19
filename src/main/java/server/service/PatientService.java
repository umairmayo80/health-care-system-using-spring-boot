package server.service;
import server.domain.Appointment;
import server.domain.User;


public interface PatientService {

    void viewAppointments(int patient_id);
    boolean addAppointment(int doctorSlotId, int patientId);
}