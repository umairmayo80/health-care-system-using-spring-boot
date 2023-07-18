package server.service.impl.database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.domain.Slot;
import server.domain.User;
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
    public void viewAppointments(int userID) {
        slotsServiceDB.viewSlotsById(userID);
    }

    @Override
    public boolean addSlotsEntry(Slot slot,User currentUser) {
        return slotsServiceDB.addSlot(slot,currentUser);
    }

    @Override
    public void viewSlots(int userID) {
        slotsServiceDB.getSlotsById(userID);
    }
}
