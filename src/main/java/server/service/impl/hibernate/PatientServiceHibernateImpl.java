package server.service.impl.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.domain.Appointment;
import server.domain.User;
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
    public boolean addAppointment(Appointment appointment, User patient) {
        return appointmentServiceV1Hibernate.addAppointment(appointment, patient);
    }
}
