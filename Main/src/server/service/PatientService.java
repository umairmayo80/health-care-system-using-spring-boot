package server.service;

import server.domain.Appointment;


public interface PatientService {

    public void viewAppointments(int patient_id);

    public void addAppointment(Appointment appointment);
}