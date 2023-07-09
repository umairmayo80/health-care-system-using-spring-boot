package server.dao.impl.database;

import server.context.ServiceContext;
import server.dao.AppointmentV1Repository;
import server.domain.Slot;
import server.domain.version1.AppointmentV1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AppointmentV1RepoDbImpl implements AppointmentV1Repository {
    private final Connection dbConnection;
    public AppointmentV1RepoDbImpl()
    {
        this.dbConnection= ServiceContext.getDatabaseConnection();
    }

    public List<AppointmentV1> getAppointmentsByQuery(String query) {
        List<AppointmentV1> appointmentList = new ArrayList<>();
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                appointmentList.add(parseSlotDataFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error: Unable to retrieve data from the database: " + e.getMessage());
        }
        return appointmentList;
    }

    private AppointmentV1 parseSlotDataFromResultSet(ResultSet resultSet) throws SQLException {
        int appointmentId = resultSet.getInt("appointmentId");
        int patientId = resultSet.getInt("patientId");
        int doctorSlotId = resultSet.getInt("slotId");
        return new AppointmentV1(appointmentId,patientId,doctorSlotId);
    }


    @Override
    public void saveAppointmentsToStorage(List<AppointmentV1> appointmentList) {
        for(AppointmentV1 appointment: appointmentList){
            addAppointmentEntry(appointment);
        }
    }

    @Override
    public List<AppointmentV1> getAppointments() {
        String query = "select * from appointment_table;";
        return getAppointmentsByQuery(query);
    }

    @Override
    public void viewAllAppointments() {
        String query = "SELECT a.appointmentId, a.patientId, p.name AS patientName, a.slotId, s.doctorId, d.name AS doctorName, " +
                "s.date, s.startTime, s.endTime, s.occupied " +
                "FROM appointment_table a " +
                "JOIN slot_table s ON a.slotId = s.slotId " +
                "JOIN user_table p ON a.patientId = p.userid " +
                "JOIN user_table d ON s.doctorId = d.userid ";
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            displayAppointmentData(resultSet);
        } catch (SQLException e) {
            System.out.println("Error: Unable to retrieve data from the database: " + e.getMessage());
        }
    }


    @Override
    public void viewAppointmentsByPatientId(int patientId) {
        String query = "SELECT a.appointmentId, a.patientId, p.name AS patientName, a.slotId, s.doctorId, d.name AS doctorName, " +
                "s.date, s.startTime, s.endTime, s.occupied " +
                "FROM appointment_table a " +
                "JOIN slot_table s ON a.slotId = s.slotId " +
                "JOIN user_table p ON a.patientId = p.userid " +
                "JOIN user_table d ON s.doctorId = d.userid " +
                "WHERE a.patientId = " + patientId;
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            displayAppointmentData(resultSet);
        } catch (SQLException e) {
            System.out.println("Error: Unable to retrieve data from the database: " + e.getMessage());
        }
    }

    @Override
    public void viewAppointmentsByDoctorId(int doctorId) {
        String query = "SELECT a.appointmentId, a.patientId, p.name AS patientName, a.slotId, s.doctorId, d.name AS doctorName, " +
                "s.date, s.startTime, s.endTime, s.occupied " +
                "FROM appointment_table a " +
                "JOIN slot_table s ON a.slotId = s.slotId " +
                "JOIN user_table p ON a.patientId = p.userid " +
                "JOIN user_table d ON s.doctorId = d.userid " +
                "WHERE s.doctorId = " + doctorId;
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            displayAppointmentData(resultSet);
        } catch (SQLException e) {
            System.out.println("Error: Unable to retrieve data from the database: " + e.getMessage());
        }
    }


    // Method to display appointment data with patient and doctor names
    public static void displayAppointmentData(ResultSet resultSet) throws SQLException {
        System.out.println("+---------------+-----------+--------------+--------+----------+-----------------+-----------+----------+----------+-----------+");
        System.out.println("| appointmentId | patientId | patientName  | slotId | doctorId | doctorName      | date      | startTime | endTime  | occupied |");
        System.out.println("+---------------+-----------+--------------+--------+----------+-----------------+-----------+----------+----------+-----------+");

        while (resultSet.next()) {
            int appointmentId = resultSet.getInt("appointmentId");
            int patientId = resultSet.getInt("patientId");
            String patientName = resultSet.getString("patientName");
            int slotId = resultSet.getInt("slotId");
            int doctorId = resultSet.getInt("doctorId");
            String doctorName = resultSet.getString("doctorName");
            String date = resultSet.getString("date");
            String startTime = resultSet.getString("startTime");
            String endTime = resultSet.getString("endTime");
            boolean occupied = resultSet.getBoolean("occupied");

            System.out.printf("|%14d |%10d | %12s |%7d |%9d | %15s |%10s |%9s |%9s |%10s |\n",
                    appointmentId, patientId, patientName, slotId, doctorId, doctorName, date, startTime, endTime, occupied);
        }

        System.out.println("+---------------+-----------+--------------+--------+----------+-----------------+-----------+----------+----------+-----------+");
    }



    @Override
    public boolean addAppointmentEntry(AppointmentV1 appointment) {
        Slot slot = ServiceContext.getSlotService().getSlotBySlotId(appointment.getDoctorSlotId());
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
