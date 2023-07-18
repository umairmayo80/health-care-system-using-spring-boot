package server.dao.impl.database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.dao.AppointmentV1Repository;
import server.domain.Slot;
import server.domain.Appointment;
import server.domain.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class AppointmentV1RepoDbImpl implements AppointmentV1Repository {
    private final Connection dbConnection;
    private final SlotRepoDbImpl slotRepoDb;
    private final UserRepoDbImpl userRepoDb;



    @Autowired
    public AppointmentV1RepoDbImpl(Connection dbConnection, SlotRepoDbImpl slotRepoDb, UserRepoDbImpl userRepoDb) {
        this.dbConnection = dbConnection;
        this.slotRepoDb = slotRepoDb;
        this.userRepoDb = userRepoDb;
    }


    @Override
    public void saveAppointmentsToStorage(List<Appointment> appointmentList) {
        for(Appointment appointment: appointmentList){
            add(appointment);
        }
    }

    @Override
    public List<Appointment> getAll() {
        String query = "select * from appointment_table;";
        return getAppointmentsByQuery(query);
    }


    @Override
    public List<Appointment> getAppointmentsByPatientId(int patientId) {
        String query = "SELECT a.appointmentId, a.patientId, p.name AS patientName, a.slotId, s.doctorId, d.name AS doctorName, " +
                "s.date, s.startTime, s.endTime, s.occupied " +
                "FROM appointment_table a " +
                "JOIN slot_table s ON a.slotId = s.slotId " +
                "JOIN user_table p ON a.patientId = p.userid " +
                "JOIN user_table d ON s.doctorId = d.userid " +
                "WHERE a.patientId = " + patientId;
        return getAppointmentsByQuery(query);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorId(int doctorId) {
        String query = "SELECT a.appointmentId, a.patientId, p.name AS patientName, a.slotId, s.doctorId, d.name AS doctorName, " +
                "s.date, s.startTime, s.endTime, s.occupied " +
                "FROM appointment_table a " +
                "JOIN slot_table s ON a.slotId = s.slotId " +
                "JOIN user_table p ON a.patientId = p.userid " +
                "JOIN user_table d ON s.doctorId = d.userid " +
                "WHERE s.doctorId = " + doctorId;
        return getAppointmentsByQuery(query);
    }

    private List<Appointment> getAppointmentsByQuery(String query) {
        List<Appointment> appointmentList = new ArrayList<>();
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                appointmentList.add(parseAppointmentData(resultSet));
            }

        } catch (SQLException e) {
            System.out.println("Error: Unable to retrieve data from the database: " + e.getMessage());
        }
        return appointmentList;
    }


    public Appointment parseAppointmentData(ResultSet resultSet) throws SQLException {

            Appointment appointment = new Appointment();
            int appointmentId = resultSet.getInt("appointmentId");
            int patientId = resultSet.getInt("patientId");
            int slotId = resultSet.getInt("slotId");


            appointment.setAppointmentId(appointmentId);
            User patient = userRepoDb.getById(patientId);
            appointment.setPatient(patient);
            Slot slot = slotRepoDb.getById(slotId);
            User doctor = userRepoDb.getById(slot.getDoctorId());
            slot.setDoctor(doctor);
            appointment.setSlot(slot);

            return appointment;



    }



    @Override
    public boolean add(Appointment appointment) {
        Slot slot = slotRepoDb.getById(appointment.getDoctorSlotId());
        if(slot == null){
            System.out.println("No slot found against the provided id: "+appointment.getDoctorSlotId());
            return false;
        }
        if(slot.getOccupied()){
            System.out.println("Error, slot is already occupied. Pick a different slot");
            return false;
        }
        // add the appointment into database
        String query = "Insert into appointment_table (patientId, slotId)\n"+
                "Values ("+appointment.getPatientId()+", "+appointment.getDoctorSlotId()+" )";
        try {
            Statement statement = dbConnection.createStatement();
            statement.executeUpdate(query);

            // update the selected slot occupied status to true
            String updateSlotOccupiedStatusQuery = "UPDATE slot_table\n" +
                    "SET occupied = TRUE\n" +
                    "WHERE slotId = "+appointment.getDoctorSlotId();
            statement.executeUpdate(updateSlotOccupiedStatusQuery);

        } catch (SQLException e){
            System.out.println("Error, unable to add appointment entry in database, "+e);
            return false;
        }
        return true;
    }
}
