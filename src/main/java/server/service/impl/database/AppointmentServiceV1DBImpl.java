package server.service.impl.database;

import server.context.RepositoryContext;
import server.dao.impl.database.AppointmentV1RepoDbImpl;
import server.domain.User;
import server.domain.version1.AppointmentV1;
import server.service.version1.AppointmentServiceV1;

import java.util.List;

public class AppointmentServiceV1DBImpl implements AppointmentServiceV1 {
    private final AppointmentV1RepoDbImpl appointmentV1RepoDb;
    public AppointmentServiceV1DBImpl() {
        appointmentV1RepoDb = RepositoryContext.getAppointmentV1RepoDb();
    }


    @Override
    public void saveAppointmentsToStorage(List<AppointmentV1> appointmentList) {
        for(AppointmentV1 appointment: appointmentList){
            addAppointmentEntry(appointment);
        }
    }

    @Override
    public List<AppointmentV1> getAppointments() {
       return appointmentV1RepoDb.getAppointments();
    }

    @Override
    public void viewAllAppointments() {
        appointmentV1RepoDb.viewAllAppointments();
    }


    @Override
    public void viewAppointmentsByPatientId(int patientId) {
        appointmentV1RepoDb.viewAppointmentsByPatientId(patientId);
    }

    @Override
    public void viewAppointmentsByDoctorId(int doctorId) {
        appointmentV1RepoDb.viewAppointmentsByDoctorId(doctorId);
    }



    @Override
    public boolean addAppointmentEntry(AppointmentV1 appointment) {
        return appointmentV1RepoDb.addAppointmentEntry(appointment);
    }


    @Override
    public boolean addAppointment(AppointmentV1 appointmentV1, User currentUser) {
        // now associate the new appointment with the parents
        currentUser.addAppointmentV1(appointmentV1);
        return addAppointmentEntry(appointmentV1);
    }

}