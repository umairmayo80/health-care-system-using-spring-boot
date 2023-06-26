package server.service;

import server.domain.Slot;
import server.domain.User;

import java.util.List;

public interface SlotService {
    List<Slot> getSlots();
    Slot getSlotBySlotId(int slotID);
    boolean addSlotEntry(Slot slot);
    void viewAllSlots();
    void viewSlotsById(int userId);
    List<Slot> getSlotsById(int userId);
    void viewBookedSlotsById(int userId);
    void viewFreeSlotsById(int userId);
}
