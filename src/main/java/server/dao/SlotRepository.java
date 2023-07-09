package server.dao;

import server.domain.Slot;

import java.util.List;

public interface SlotRepository {
    List<Slot> getSlots();
    Slot getSlotBySlotId(int slotID);
    boolean addSlotEntry(Slot slot);
    void viewAllSlots();
    void viewSlotsById(int userId);
    List<Slot> getSlotsById(int userId);
    void viewBookedSlotsById(int userId);

    void viewFreeSlots();

    void viewFreeSlotsById(int userId);
}
