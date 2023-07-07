package server.service.impl.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import server.context.RepositoryContext;
import server.context.ServiceContext;
import server.dao.impl.hibernate.AppointmentRepoHibernate;
import server.domain.Slot;
import server.domain.version1.AppointmentV1;
import server.service.version1.AppointmentServiceV1;


import java.util.List;

public class AppointmentServiceV1HibernateImpl implements AppointmentServiceV1 {
    private final AppointmentRepoHibernate appointmentRepoHibernate;

    public AppointmentServiceV1HibernateImpl(){
        appointmentRepoHibernate = RepositoryContext.getAppointmentRepoHibernate();
    }

    @Override
    public void saveAppointmentsToStorage(List<AppointmentV1> appointmentList) {
        appointmentList.forEach(this::addAppointmentEntry);
    }

    @Override
    public List<AppointmentV1> getAppointments() {
        return appointmentRepoHibernate.getAppointments();
    }

    @Override
    public void viewAllAppointments() {
        appointmentRepoHibernate.viewAllAppointments();
    }

    @Override
    public void viewAppointmentsByPatientId(int patientId) {
       appointmentRepoHibernate.viewAppointmentsByPatientId(patientId);
    }

    @Override
    public void viewAppointmentsByDoctorId(int doctorId) {
       appointmentRepoHibernate.viewAppointmentsByDoctorId(doctorId);
    }

    @Override
    public boolean addAppointmentEntry(AppointmentV1 appointment) {
        return appointmentRepoHibernate.addAppointmentEntry(appointment);
    }
}
