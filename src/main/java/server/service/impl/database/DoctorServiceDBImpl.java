package server.service.impl.database;

import server.context.ServiceContext;
import server.domain.Schedule;
import server.domain.Slot;
import server.service.DoctorService;

import java.sql.Connection;

public class DoctorServiceDBImpl implements DoctorService {
    private Connection dbConnection;
    public DoctorServiceDBImpl(){
        this.dbConnection = ServiceContext.getDatabaseConnection();
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
        ServiceContext.getSlotService().getSlotsById(userID);
    }
}
