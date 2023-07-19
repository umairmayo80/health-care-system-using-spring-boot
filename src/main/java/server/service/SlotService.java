package server.service;

import server.domain.Slot;
import server.domain.User;
import server.utilities.ScheduleCreationException;

import java.util.List;

public interface SlotService {
    List<Slot> getSlots();
    Slot getSlotBySlotId(int slotID);
    void viewAllSlots();
    void viewSlotsById(int userId);
    List<Slot> getSlotsById(int userId);
    void viewBookedSlotsById(int userId);

    void viewFreeSlots();

    void viewFreeSlotsById(int userId);

    boolean addSlot(Slot newSlot, User currentUser);

    boolean createNewSlot(int doctorId, String date, String startTime, String endTime ) throws ScheduleCreationException;
}
