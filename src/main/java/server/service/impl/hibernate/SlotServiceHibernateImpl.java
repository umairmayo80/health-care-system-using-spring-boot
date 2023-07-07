package server.service.impl.hibernate;
import server.context.RepositoryContext;
import server.dao.impl.hibernate.SlotRepoHibernateImpl;
import server.domain.Slot;
import server.service.SlotService;

import java.util.List;

public class SlotServiceHibernateImpl implements SlotService {
    private SlotRepoHibernateImpl slotRepoHibernate;
    public SlotServiceHibernateImpl(){
        slotRepoHibernate = RepositoryContext.getSlotRepoHibernate();
    }

    @Override
    public List<Slot> getSlots() {
        return slotRepoHibernate.getSlots();
    }

    @Override
    public Slot getSlotBySlotId(int slotID) {
       return slotRepoHibernate.getSlotBySlotId(slotID);
    }

    @Override
    public boolean addSlotEntry(Slot slot) {
        return slotRepoHibernate.addSlotEntry(slot);
    }

    @Override
    public void viewAllSlots() {
        List<Slot> slotList = getSlots();
        displaySlots(slotList);
    }

    public static void displaySlots(List<Slot> slotList){
        // Display the data in a table format
        System.out.println("+--------+----------+------------+-----------+----------+----------+");
        System.out.println("| slotId | doctorId |    date    | startTime |  endTime | occupied |");
        System.out.println("+--------+----------+------------+-----------+----------+----------+");

        for(Slot slot : slotList){
            System.out.format("|%-7d |%9d |%11s |%10s |%9s |%9s |\n", slot.getSlotId(), slot.getDoctor().getUserId(), slot.getDate(), slot.getStartTime(), slot.getEndTime(), slot.getOccupied());
        }
        System.out.println("+--------+----------+------------+-----------+----------+----------+");

    }

    @Override
    public void viewSlotsById(int userId) {
        displaySlots(getSlotsById(userId));
    }


    @Override
    public List<Slot> getSlotsById(int userId) {
       return slotRepoHibernate.getSlotsById(userId);
    }

    @Override
    public void viewBookedSlotsById(int userId) {
        slotRepoHibernate.viewBookedSlotsById(userId);
    }

    @Override
    public void viewFreeSlots() {
        slotRepoHibernate.viewFreeSlots();
    }

    @Override
    public void viewFreeSlotsById(int userId) {
       slotRepoHibernate.viewFreeSlotsById(userId);
    }
}
