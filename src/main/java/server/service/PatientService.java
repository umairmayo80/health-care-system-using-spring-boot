package server.service;

import server.domain.Appointment;
import server.domain.version1.AppointmentV1;


public interface PatientService {

    public void viewAppointments(int patient_id);

    public boolean addAppointment(Appointment appointment);
    public boolean addAppointment(AppointmentV1 appointment);
}