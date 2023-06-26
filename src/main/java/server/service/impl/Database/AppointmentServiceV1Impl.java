package server.service.impl.Database;

import server.context.ServiceContext;
import server.domain.Slot;
import server.domain.version1.Appointment;
import server.service.AppointmentServiceV1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AppointmentServiceV1Impl implements AppointmentServiceV1 {
    private Connection dbConnection;
    public AppointmentServiceV1Impl()
    {
        this.dbConnection= ServiceContext.getDatabaseConnection();
    }


    public List<Appointment> getAppointmentsByQuery(String query) {
        List<Appointment> appointmentList = new ArrayList<>();
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

    private Appointment parseSlotDataFromResultSet(ResultSet resultSet) throws SQLException {
        int appointmentId = resultSet.getInt("appointmentId");
        int patientId = resultSet.getInt("patientId");
        int doctorSlotId = resultSet.getInt("slotId");
        return new Appointment(appointmentId,patientId,doctorSlotId);
    }


    @Override
    public void saveAppointmentsToStorage(List<Appointment> appointmentList) {
        for(Appointment appointment: appointmentList){
            addAppointmentEntry(appointment);
        }
    }

    @Override
    public List<Appointment> getAppointments() {
        String query = "select * from appointment_table;";
        return getAppointmentsByQuery(query);
    }


    public void viewAppointmentByUserId(int userId){
        String query = "SELECT a.appointmentId, a.patientId, p.name AS patientName, a.slotId, s.doctorId, d.name AS doctorName, " +
                "s.date, s.startTime, s.endTime, s.occupied " +
                "FROM appointment_table a " +
                "JOIN slot_table s ON a.slotId = s.slotId " +
                "JOIN user_table p ON a.patientId = p.userid " +
                "JOIN user_table d ON s.doctorId = d.userid " +
                "WHERE a.patientId = " + userId;
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
        viewAppointmentByUserId(patientId);
    }

    @Override
    public void viewAppointmentsByDoctorId(int doctorId) {
        viewAppointmentByUserId(doctorId);
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
    public boolean addAppointmentEntry(Appointment appointment) {
        Slot slot = ServiceContext.getSlotService().getSlotBySlotId(appointment.getDoctorSlotId());
        if(slot == null){
            System.out.println("Error,unable to add appointment entry");
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

    public static void main(String[] args){
        AppointmentServiceV1Impl appointmentServiceV1 = new AppointmentServiceV1Impl();
        System.out.println(appointmentServiceV1.getAppointments());

        appointmentServiceV1.viewAppointmentsByPatientId(2);

        System.out.println(appointmentServiceV1.addAppointmentEntry(new Appointment(2,3)));
    }
}
