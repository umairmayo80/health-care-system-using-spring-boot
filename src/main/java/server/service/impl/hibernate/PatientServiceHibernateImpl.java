package server.service.impl.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.service.PatientService;

@Component
public class PatientServiceHibernateImpl implements PatientService {

    private final AppointmentServiceV1HibernateImpl appointmentServiceV1Hibernate;

    @Autowired
    public PatientServiceHibernateImpl(AppointmentServiceV1HibernateImpl appointmentServiceV1Hibernate) {
        this.appointmentServiceV1Hibernate = appointmentServiceV1Hibernate;
    }

    @Override
    public void viewAppointments(int patient_id) {
        appointmentServiceV1Hibernate.viewAppointmentsByPatientId(patient_id);
    }


    @Override
    public boolean addAppointment(int doctorSlotId, int patientId) {
        return appointmentServiceV1Hibernate.addAppointment(doctorSlotId,patientId);
    }
}
