package server.dao.impl.database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.domain.Slot;
import server.dao.SlotRepository;
import server.utilities.DisplayFormatting;
import server.utilities.ScheduleCreationException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class SlotRepoDbImpl implements SlotRepository {

    private final Connection dbConnection;

    @Autowired
    public SlotRepoDbImpl(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    private Slot parseSlotDataFromResultSet(ResultSet resultSet) throws SQLException, ScheduleCreationException {
        int slotId = resultSet.getInt("slotId");
        int doctorId = resultSet.getInt("doctorId");
        String date = resultSet.getDate("date").toString();
        String startTime = resultSet.getTime("startTime").toString();
        String endTime = resultSet.getTime("endTime").toString();
        boolean occupied = resultSet.getBoolean("occupied");
        return new Slot(slotId,doctorId,date,startTime,endTime,occupied);
    }

    @Override
    public List<Slot> getAll(){
        String query = "SELECT * FROM slot_table";
        return getSlotsByQuery(query);
    }

    @Override
    public Slot getById(int slotID) {
        String query = "Select * from slot_table where slotId= "+slotID;
        List<Slot> slotList = getSlotsByQuery(query);
        try {
            return slotList.get(0);
        } catch (IndexOutOfBoundsException e){
            return  null;
        }
    }

    public List<Slot> getSlotsByQuery(String query) {
        List<Slot> slotList = new ArrayList<>();
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                slotList.add(parseSlotDataFromResultSet(resultSet));
            }
        } catch (SQLException | ScheduleCreationException e) {
            System.out.println("Error: Unable to retrieve data from the database: " + e.getMessage());
        }
        return slotList;
    }


    @Override
    public void viewAllSlots() {
        List<Slot> slotList = getAll();
        DisplayFormatting.displaySlots(slotList);
    }

    @Override
    public void viewSlotsById(int userId) {
        DisplayFormatting.displaySlots(getAllByUserId(userId));
    }


    public List<Slot> getAllByUserId(int userId){
        String query = "SELECT * FROM slot_table where doctorId = "+userId+";";
        return getSlotsByQuery(query);
    }

    @Override
    public void viewBookedSlotsById(int userId) {
        String query = "SELECT * FROM slot_table where doctorId = "+userId
                +" AND occupied=true;";
        List<Slot> slotList = getSlotsByQuery(query);
        DisplayFormatting.displaySlots(slotList);
    }

    @Override
    public void viewFreeSlots() {
        // Execute the query to select free slots and join with the user_table to get the doctor's name
        String query = "SELECT slot_table.slotId, user_table.name AS doctorName, slot_table.date, " +
                "slot_table.startTime, slot_table.endTime " +
                "FROM slot_table " +
                "JOIN user_table ON slot_table.doctorId = user_table.userid " +
                "WHERE slot_table.occupied = false";

        try{
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Print the table header
            System.out.println("+--------+----------------+------------+-----------+----------+");
            System.out.println("| slotId | doctorName     | date       | startTime | endTime  |");
            System.out.println("+--------+----------------+------------+-----------+----------+");

            // Process the result set and display the free slots with all slot details
            while (resultSet.next()) {
                int slotId = resultSet.getInt("slotId");
                String doctorName = resultSet.getString("doctorName");
                String date = resultSet.getString("date");
                String startTime = resultSet.getString("startTime");
                String endTime = resultSet.getString("endTime");

                // Print each row in the specified format
                System.out.printf("| %6d | %-14s | %10s | %9s | %8s |%n", slotId, doctorName, date, startTime, endTime);
            }

            // Print the table footer
            System.out.println("+--------+----------------+------------+-----------+----------+");

            // Close the result set
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void viewFreeSlotsById(int userId) {
        String query = "SELECT * FROM slot_table where doctorId = "+userId
                +" AND occupied=false;";
        List<Slot> slotList = getSlotsByQuery(query);
        DisplayFormatting.displaySlots(slotList);
    }

    @Override
    public boolean add(Slot slot) {
        try {
            String query = "INSERT INTO slot_table (doctorId, date, startTime, endTime, occupied) " +
                    "VALUES (" + slot.getDoctorId()
                    + ", '" + slot.getDate()
                    + "', '" + slot.getStartTime()
                    + "', '" + slot.getEndTime()
                    + "', " + slot.getOccupied() + ")";
            Statement statement = dbConnection.createStatement();

            int rowsAffected = statement.executeUpdate(query);
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error: Unable to add slot entry to the database: " + e.getMessage());
            return false;
        }
    }
}
