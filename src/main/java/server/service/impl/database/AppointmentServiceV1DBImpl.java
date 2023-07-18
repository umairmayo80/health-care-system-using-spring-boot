package server.service.impl.database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.dao.impl.database.AppointmentV1RepoDbImpl;
import server.domain.User;
import server.domain.Appointment;
import server.service.AppointmentServiceV1;
import static server.utilities.DisplayFormatting.displayAppointmentData;

import java.util.List;

@Component
public class AppointmentServiceV1DBImpl implements AppointmentServiceV1 {
    private final AppointmentV1RepoDbImpl appointmentV1RepoDb;

    @Autowired
    public AppointmentServiceV1DBImpl(AppointmentV1RepoDbImpl appointmentV1RepoDb) {
        this.appointmentV1RepoDb = appointmentV1RepoDb;
    }

    @Override
    public void saveAppointmentsToStorage(List<Appointment> appointmentList) {
        for(Appointment appointment: appointmentList){
            addAppointmentEntry(appointment);
        }
    }

    @Override
    public List<Appointment> getAppointments() {
       return appointmentV1RepoDb.getAll();
    }

    @Override
    public void viewAllAppointments() {
        displayAppointmentData(getAppointments());
    }


    @Override
    public void viewAppointmentsByPatientId(int patientId) {
        displayAppointmentData(appointmentV1RepoDb.getAppointmentsByPatientId(patientId));
    }

    @Override
    public void viewAppointmentsByDoctorId(int doctorId) {
        displayAppointmentData(appointmentV1RepoDb.getAppointmentsByDoctorId(doctorId));
    }



    @Override
    public boolean addAppointmentEntry(Appointment appointment) {
        return appointmentV1RepoDb.add(appointment);
    }


    @Override
    public boolean addAppointment(Appointment appointmentV1, User currentUser) {
        // now associate the new appointment with the parents
        currentUser.addAppointmentV1(appointmentV1);
        return addAppointmentEntry(appointmentV1);
    }

}
