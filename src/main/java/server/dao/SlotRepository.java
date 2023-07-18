package server.dao;
import server.domain.Slot;
import java.util.List;

public interface SlotRepository {
    List<Slot> getAll();
    List<Slot> getAllByUserId(int userId);
    Slot getById (int slotID);
    boolean add (Slot slot);
    List<Slot> getBookedSlotsById(int userId);
    List<Slot> getFreeSlots();
    List<Slot> getFreeSlotsById(int userId);
}
