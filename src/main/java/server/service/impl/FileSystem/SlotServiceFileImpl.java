package server.service.impl.FileSystem;

import server.context.RepositoryContext;
import server.domain.Slot;
import server.service.SlotService;
import server.dao.impl.fileSystem.SlotRepoFileImpl;

import java.util.List;

public class SlotServiceFileImpl implements SlotService {
    private final SlotRepoFileImpl slotRepoFile;
    public SlotServiceFileImpl(){
        slotRepoFile = RepositoryContext.getSlotRepoFile();
    }
    @Override
    public List<Slot> getSlots() {
        return slotRepoFile.getSlots();
    }

    @Override
    public Slot getSlotBySlotId(int slotID) {
        return slotRepoFile.getSlotBySlotId(slotID);
    }

    @Override
    public boolean addSlotEntry(Slot slot) {
        return slotRepoFile.addSlotEntry(slot);
    }

    @Override
    public void viewAllSlots() {
        slotRepoFile.viewAllSlots();
    }

    @Override
    public void viewSlotsById(int userId) {
        slotRepoFile.viewSlotsById(userId);
    }

    @Override
    public List<Slot> getSlotsById(int userId) {
        return slotRepoFile.getSlotsById(userId);
    }

    @Override
    public void viewBookedSlotsById(int userId) {
        slotRepoFile.viewBookedSlotsById(userId);
    }

    @Override
    public void viewFreeSlots() {
        slotRepoFile.viewFreeSlots();
  }

    @Override
    public void viewFreeSlotsById(int userId) {
        slotRepoFile.viewFreeSlotsById(userId);
    }
}
