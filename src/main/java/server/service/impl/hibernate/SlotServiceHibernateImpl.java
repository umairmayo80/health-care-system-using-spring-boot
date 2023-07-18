package server.service.impl.hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.dao.impl.hibernate.SlotRepoHibernateImpl;
import server.domain.Slot;
import server.domain.User;
import server.service.SlotService;

import java.util.List;

@Component
public class SlotServiceHibernateImpl implements SlotService {
    private final SlotRepoHibernateImpl slotRepoHibernate;

    @Autowired
    public SlotServiceHibernateImpl(SlotRepoHibernateImpl slotRepoHibernate) {
        this.slotRepoHibernate = slotRepoHibernate;
    }

    @Override
    public List<Slot> getSlots() {
        return slotRepoHibernate.getAll();
    }

    @Override
    public Slot getSlotBySlotId(int slotID) {
       return slotRepoHibernate.getById(slotID);
    }

    @Override
    public boolean addSlotEntry(Slot slot) {
        return slotRepoHibernate.add(slot);
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
       return slotRepoHibernate.getAllByUserId(userId);
    }

    @Override
    public void viewBookedSlotsById(int userId) {
        displaySlots(slotRepoHibernate.getBookedSlotsById(userId));
    }

    @Override
    public void viewFreeSlots() {
        displaySlots(slotRepoHibernate.getFreeSlots());
    }

    @Override
    public void viewFreeSlotsById(int userId) {
       displaySlots(slotRepoHibernate.getFreeSlotsById(userId));
    }

    @Override
    public boolean addSlot(Slot newSlot, User currentUser) {
        //associate the slot with the parent
        currentUser.addSlot(newSlot);
        return addSlotEntry(newSlot);
    }
}
