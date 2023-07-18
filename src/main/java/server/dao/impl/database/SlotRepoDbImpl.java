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
import java.util.stream.LongStream;

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

    public List<Slot> getAllByUserId(int userId){
        String query = "SELECT * FROM slot_table where doctorId = "+userId+";";
        return getSlotsByQuery(query);
    }

    @Override
    public List<Slot> getBookedSlotsById(int userId) {
        String query = "SELECT * FROM slot_table where doctorId = "+userId
                +" AND occupied=true;";
        return getSlotsByQuery(query);
    }

    @Override
    public List<Slot> getFreeSlots() {
        // Execute the query to select free slots and join with the user_table to get the doctor's name
        String query = "SELECT * FROM slot_table where occupied=false";
        return getSlotsByQuery(query);
    }

    @Override
    public List<Slot> getFreeSlotsById(int userId) {
        String query = "SELECT * FROM slot_table where doctorId = "+userId
                +" AND occupied=false;";
        return getSlotsByQuery(query);
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
