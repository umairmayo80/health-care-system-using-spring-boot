package server.service.impl.fileSystem;

import server.context.RepositoryContext;
import server.domain.version1.AppointmentV1;
import server.dao.impl.fileSystem.AppointmentV1RepoFileImpl;
import server.service.version1.AppointmentServiceV1;

import java.util.List;

public class AppointServiceV1FileImpl implements AppointmentServiceV1 {
    private AppointmentV1RepoFileImpl appointmentV1RepoFile;
    public AppointServiceV1FileImpl(){
        appointmentV1RepoFile = RepositoryContext.getAppointmentV1RepoFile();
    }
    @Override
    public void saveAppointmentsToStorage(List<AppointmentV1> appointmentList) {
        appointmentV1RepoFile.saveAppointmentsToStorage(appointmentList);
    }

    @Override
    public List<AppointmentV1> getAppointments() {
        return appointmentV1RepoFile.getAppointments();
    }

    @Override
    public void viewAllAppointments() {
        System.out.println("FileSystem is pending");
    }

    @Override
    public void viewAppointmentsByPatientId(int patientId) {
        appointmentV1RepoFile.viewAppointmentsByPatientId(patientId);
    }

    @Override
    public void viewAppointmentsByDoctorId(int doctorId) {
        appointmentV1RepoFile.viewAppointmentsByDoctorId(doctorId);
    }

    @Override
    public boolean addAppointmentEntry(AppointmentV1 appointment) {
        return appointmentV1RepoFile.addAppointmentEntry(appointment);
    }
}
