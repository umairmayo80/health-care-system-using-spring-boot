package server.service.impl.Database;
import server.context.ServiceContext;
import server.domain.Slot;
import server.utilities.ScheduleCreationException;
import server.service.SlotService;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SlotsServiceDBImpl implements SlotService {
    private Connection dbConnection;
    public SlotsServiceDBImpl()
    {
        this.dbConnection= ServiceContext.getDatabaseConnection();
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
    public List<Slot> getSlots(){
        String query = "SELECT * FROM slot_table";
        return getSlotsByQuery(query);
    }

    @Override
    public Slot getSlotBySlotId(int slotID) {
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

    private void displaySlots(List<Slot> slotList){
        // Display the data in a table format
        System.out.println("+--------+----------+------------+-----------+----------+----------+");
        System.out.println("| slotId | doctorId |    date    | startTime |  endTime | occupied |");
        System.out.println("+--------+----------+------------+-----------+----------+----------+");

        for(Slot slot : slotList){
            System.out.format("|%-7d |%9d |%11s |%10s |%9s |%9s |\n", slot.getSlotId(), slot.getDoctorId(), slot.getDate(), slot.getStartTime(), slot.getEndTime(), slot.getOccupied());
        }
        System.out.println("+--------+----------+------------+-----------+----------+----------+");


    }
    @Override
    public void viewAllSlots() {
        List<Slot> slotList = getSlots();
        displaySlots(slotList);
    }

    @Override
    public void viewSlotsById(int userId) {
        displaySlots(getSlotsById(userId));
    }


    public List<Slot> getSlotsById(int userId){
        String query = "SELECT * FROM slot_table where doctorId = "+userId+";";
        return getSlotsByQuery(query);
    }

    @Override
    public void viewBookedSlotsById(int userId) {
        String query = "SELECT * FROM slot_table where doctorId = "+userId
                +" AND occupied=true;";
        List<Slot> slotList = getSlotsByQuery(query);
        displaySlots(slotList);
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
        displaySlots(slotList);
    }

    @Override
    public boolean addSlotEntry(Slot slot) {
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

    public static void main(String[] args) throws ScheduleCreationException {
        SlotsServiceDBImpl slotsServiceDB = new SlotsServiceDBImpl();
        slotsServiceDB.viewAllSlots();
        slotsServiceDB.viewSlotsById(3);
        slotsServiceDB.viewBookedSlotsById(3);
        slotsServiceDB.viewFreeSlotsById(3);

        Slot slot = new Slot(4,3,"2023-12-12","09:30:00","10:30",false);
        String query = "INSERT INTO slot_table (doctorId, date, startTime, endTime, occupied) " +
                "VALUES (" + slot.getDoctorId()
                + ", '" + slot.getDate()
                + "', '" + slot.getStartTime()
                + "', '" + slot.getEndTime()
                + "', " + slot.getOccupied() + ")";
        System.out.println(query);
        System.out.println(slotsServiceDB.addSlotEntry(slot));
        slotsServiceDB.viewAllSlots();


//        System.out.println("//");
        slotsServiceDB.viewFreeSlots();
    }
}
