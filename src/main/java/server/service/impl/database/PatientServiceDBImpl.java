package server.service.impl.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.domain.Appointment;
import server.domain.User;
import server.service.PatientService;

@Component
public class PatientServiceDBImpl implements PatientService {
    AppointmentServiceV1DBImpl appointmentServiceV1DB;

    @Autowired
    public PatientServiceDBImpl(AppointmentServiceV1DBImpl appointmentServiceV1DB) {
        this.appointmentServiceV1DB = appointmentServiceV1DB;
    }

    @Override
    public void viewAppointments(int patient_id) {
        appointmentServiceV1DB.viewAppointmentsByPatientId(patient_id);
    }

    @Override
    public boolean addAppointment(Appointment appointment, User patient) {
        return appointmentServiceV1DB.addAppointment(appointment,patient);
    }
}
