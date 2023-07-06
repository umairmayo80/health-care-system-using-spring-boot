package server.service.impl.hibernate;

import server.context.ServiceContext;
import server.domain.Appointment;
import server.domain.version1.AppointmentV1;
import server.service.PatientService;

public class PatientServiceHibernateImpl implements PatientService {
    @Override
    public void viewAppointments(int patient_id) {
        ServiceContext.getAppointmentServiceV1Hibernate().viewAppointmentsByPatientId(patient_id);
    }

    @Override
    public boolean addAppointment(Appointment appointment) {
        return false;
    }

    @Override
    public boolean addAppointment(AppointmentV1 appointment) {
        return ServiceContext.getAppointmentServiceV1Hibernate().addAppointmentEntry(appointment);
    }
}
