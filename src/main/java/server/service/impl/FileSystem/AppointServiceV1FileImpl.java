package server.service.impl.FileSystem;

import server.domain.version1.AppointmentV1;
import server.service.version1.AppointmentServiceV1;

import java.util.List;

public class AppointServiceV1FileImpl implements AppointmentServiceV1 {
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
    public void viewAllAppointments() {
        System.out.println("FileSystem is pending");
    }

    @Override
    public void viewAppointmentsByPatientId(int patientId) {
        System.out.println("Filesystem is pending");

    }

    @Override
    public void viewAppointmentsByDoctorId(int doctorId) {
        System.out.println("Filesystem is pending");
    }

    @Override
    public boolean addAppointmentEntry(AppointmentV1 appointment) {
        System.out.println("Filesystem is pending");
        return false;
    }
}
