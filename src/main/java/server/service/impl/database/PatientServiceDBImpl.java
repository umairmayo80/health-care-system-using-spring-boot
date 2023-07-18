package server.service.impl.database;

import org.springframework.stereotype.Component;
import server.domain.Appointment;
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
}
