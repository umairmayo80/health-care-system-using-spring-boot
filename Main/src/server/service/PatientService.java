package server.service;

import server.domain.Appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public interface PatientService {

    public void viewAppointments(int patient_id);

    public void addAppointment(Appointment appointment);
}