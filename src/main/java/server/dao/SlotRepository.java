package server.dao;

import server.domain.Slot;

import java.util.List;

public interface SlotRepository {
    List<Slot> getAll();
    List<Slot> getAllByUserId(int userId);
    Slot getById (int slotID);
    boolean add (Slot slot);

    void viewAllSlots();
    void viewSlotsById(int userId);
    void viewBookedSlotsById(int userId);

    void viewFreeSlots();

    void viewFreeSlotsById(int userId);
}
