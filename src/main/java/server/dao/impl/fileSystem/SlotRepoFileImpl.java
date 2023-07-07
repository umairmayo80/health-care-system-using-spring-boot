package server.dao.impl.fileSystem;

import server.domain.Slot;
import server.dao.SlotRepository;

import java.util.List;

public class SlotRepoFileImpl implements SlotRepository {
    @Override
    public List<Slot> getSlots() {

        System.out.println("Filesystem is pending");
        return null;
    }

    @Override
    public Slot getSlotBySlotId(int slotID) {
        System.out.println("Filesystem is pending");
        return null;
    }

    @Override
    public boolean addSlotEntry(Slot slot) {
        System.out.println("Filesystem is pending");
        return false;
    }

    @Override
    public void viewAllSlots() {
        System.out.println("Filesystem is pending");

    }

    @Override
    public void viewSlotsById(int userId) {
        System.out.println("Filesystem is pending");

    }

    @Override
    public List<Slot> getSlotsById(int userId) {
        System.out.println("Filesystem is pending");
        return null;
    }

    @Override
    public void viewBookedSlotsById(int userId) {
        System.out.println("Filesystem is pending");

    }

    @Override
    public void viewFreeSlots() {
        System.out.println("Filesystem is pending");
    }

    @Override
    public void viewFreeSlotsById(int userId) {
        System.out.println("Filesystem is pending");

    }
}
