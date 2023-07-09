package server.service;

import server.domain.Appointment;
import server.domain.version1.AppointmentV1;


public interface PatientService {

    void viewAppointments(int patient_id);

    boolean addAppointment(Appointment appointment);
    boolean addAppointment(AppointmentV1 appointment);
}