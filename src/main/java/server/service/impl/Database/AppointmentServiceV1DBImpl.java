package server.service.impl.Database;

import server.context.RepositoryContext;
import server.context.ServiceContext;
import server.dao.impl.database.AppointmentV1RepoDbImpl;
import server.domain.Slot;
import server.domain.version1.AppointmentV1;
import server.service.version1.AppointmentServiceV1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

}
