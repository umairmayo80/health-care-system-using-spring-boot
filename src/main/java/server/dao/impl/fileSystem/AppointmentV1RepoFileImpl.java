package server.dao.impl.fileSystem;
import server.domain.version1.AppointmentV1;
import server.dao.AppointmentV1Repository;

import java.util.List;

public class AppointmentV1RepoFileImpl implements AppointmentV1Repository {
    @Override
    public void saveAppointmentsToStorage(List<AppointmentV1> appointmentList) {
        System.out.println("Filesystem is pending");
    }

    @Override
    public List<AppointmentV1> getAppointments() {
        System.out.println("FileSystem is pending");
        return null;
    }

    @Override
    public void viewAppointmentsByPatientId(int patientId) {
        System.out.println("FileSystem is pending");
    }

    @Override
    public void viewAppointmentsByDoctorId(int doctorId) {
        System.out.println("FileSystem is pending");
    }

    @Override
    public void viewAllAppointments() {
        System.out.println("FileSystem is pending");
    }

    @Override
    public boolean addAppointmentEntry(AppointmentV1 appointment) {
        System.out.println("Filesystem is pending");
        return false;
    }
}
