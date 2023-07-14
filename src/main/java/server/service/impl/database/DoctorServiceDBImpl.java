package server.service.impl.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.context.ServiceContext;
import server.domain.Schedule;
import server.domain.Slot;
import server.service.DoctorService;

import java.sql.Connection;

@Component
public class DoctorServiceDBImpl implements DoctorService {
    private Connection dbConnection;
    private SlotsServiceDBImpl slotsServiceDB;

    @Autowired
    public DoctorServiceDBImpl(Connection dbConnection, SlotsServiceDBImpl slotsServiceDB) {
        this.dbConnection = dbConnection;
        this.slotsServiceDB = slotsServiceDB;
    }

    @Override
    public void addScheduleSlots(Schedule schedule) {
        // currently this function is for handling Schedule entry. Will remove schedule domain entirely and replace it with slot
    }

    @Override
    public void viewAppointments(int userID) {
        // covered by slotService
    }

    @Override
    public void addSlotsEntry(Slot slot) {
        // covered by slotService
    }

    @Override
    public void viewSlots(int userID) {
        slotsServiceDB.getSlotsById(userID);
    }
}
