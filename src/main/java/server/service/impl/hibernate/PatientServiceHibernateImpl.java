package server.service.impl.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.domain.Appointment;
import server.service.PatientService;

import java.util.List;

@Component
public class PatientServiceHibernateImpl implements PatientService {

    private final AppointmentServiceV1HibernateImpl appointmentServiceV1Hibernate;

    @Autowired
    public PatientServiceHibernateImpl(AppointmentServiceV1HibernateImpl appointmentServiceV1Hibernate) {
        this.appointmentServiceV1Hibernate = appointmentServiceV1Hibernate;
    }

    @Override
    public List<Appointment> viewAppointments(int patient_id) {
        return appointmentServiceV1Hibernate.getAppointmentsByPatientId(patient_id);
    }


    @Override
    public boolean addAppointment(int doctorSlotId, int patientId) {
        return appointmentServiceV1Hibernate.addAppointment(doctorSlotId,patientId);
    }
}
