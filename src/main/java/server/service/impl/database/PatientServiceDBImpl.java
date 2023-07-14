package server.service.impl.database;

import org.springframework.stereotype.Component;
import server.domain.Appointment;
import server.domain.version1.AppointmentV1;
import server.service.PatientService;

@Component
public class PatientServiceDBImpl implements PatientService {
    @Override
    public void viewAppointments(int patient_id) {

    }

    @Override
    public boolean addAppointment(Appointment appointment) {
        return false;
    }

    @Override
    public boolean addAppointment(AppointmentV1 appointment) {
        return false;
    }
}
