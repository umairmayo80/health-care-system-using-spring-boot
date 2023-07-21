package server.service;
import server.domain.Appointment;
import server.domain.User;

import java.util.List;


public interface PatientService {

    List<Appointment> viewAppointments(int patient_id);
    boolean addAppointment(int doctorSlotId, int patientId);
}