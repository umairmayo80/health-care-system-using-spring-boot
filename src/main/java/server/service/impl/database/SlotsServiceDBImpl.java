package server.service.impl.database;
import server.context.RepositoryContext;
import server.domain.Slot;
import server.dao.impl.database.SlotRepoDbImpl;
import server.domain.User;
import server.utilities.DisplayFormatting;
import server.service.SlotService;

import java.util.List;

public class SlotsServiceDBImpl implements SlotService {
    private final SlotRepoDbImpl slotRepoDb;
    public SlotsServiceDBImpl()
    {
        slotRepoDb = RepositoryContext.getSlotRepoDb();
    }

    @Override
    public List<Slot> getSlots(){
        return slotRepoDb.getSlots();
    }

    @Override
    public Slot getSlotBySlotId(int slotID) {
        return slotRepoDb.getSlotBySlotId(slotID);
    }


    @Override
    public void viewAllSlots() {
        List<Slot> slotList = getSlots();
        DisplayFormatting.displaySlots(slotList);
    }

    @Override
    public void viewSlotsById(int userId) {
        DisplayFormatting.displaySlots(getSlotsById(userId));
    }


    public List<Slot> getSlotsById(int userId){
        return slotRepoDb.getSlotsById(userId);
    }

    @Override
    public void viewBookedSlotsById(int userId) {
        slotRepoDb.viewBookedSlotsById(userId);
    }

    @Override
    public void viewFreeSlots() {
        slotRepoDb.viewFreeSlots();
    }

    @Override
    public void viewFreeSlotsById(int userId) {
        slotRepoDb.viewFreeSlotsById(userId);
    }

    @Override
    public boolean addSlotEntry(Slot slot) {
       return slotRepoDb.addSlotEntry(slot);
    }

    @Override
    public boolean addSlot(Slot newSlot, User currentUser) {
        //associate the slot with the parent
        currentUser.addSlot(newSlot);
        return addSlotEntry(newSlot);
    }

}
