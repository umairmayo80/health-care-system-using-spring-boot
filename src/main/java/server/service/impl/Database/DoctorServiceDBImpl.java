package server.service.impl.Database;

import server.context.ServiceContext;
import server.domain.Schedule;
import server.domain.Slot;
import server.service.DoctorService;

import javax.print.Doc;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DoctorServiceDBImpl implements DoctorService {
    private Connection dbConnection;
    DoctorServiceDBImpl(){
        this.dbConnection = ServiceContext.getDatabaseConnection();
    }

    @Override
    public void addScheduleSlots(Schedule schedule) {
        // currently this function is for handling Schedule entry. Will remove schedule domain entirely and replace it with slot
    }

    @Override
    public void viewAppointments(int userID) {
        // Prints the slots that are booked/ occupied

    }

    @Override
    public void addSlotsEntry(Slot slot) {

    }

    @Override
    public void viewSlots(int userID) {

    }
}
