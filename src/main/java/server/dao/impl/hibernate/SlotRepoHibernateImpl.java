package server.dao.impl.hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.dao.SlotRepository;
import server.domain.Slot;
import java.util.List;


@Component
public class SlotRepoHibernateImpl implements SlotRepository {
    private SessionFactory sessionFactory;

    public SlotRepoHibernateImpl() {
    }

    @Autowired
    public SlotRepoHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Slot> getSlots() {
        Session session = sessionFactory.openSession();
        List<Slot> slotList = null;
        // HQL
        slotList = session.createQuery("from Slot").list();
        session.close();
        return slotList;
    }

    @Override
    public Slot getSlotBySlotId(int slotID) {
        Session session = sessionFactory.openSession();
        Slot slot = null;
        // HQL
        slot = (Slot) session.createQuery("from Slot where slotId="+slotID).uniqueResult();
        session.close();
        return slot;
    }

    @Override
    public boolean addSlotEntry(Slot slot) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            session.save(slot);

            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.out.println("Error: Unable to add slot entry to the database using hibernate: " + e.getMessage());
            e.printStackTrace();
            if(transaction != null){
                transaction.rollback();
                session.close();
            }
            return false;
        }
        return true;
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
        Session session = sessionFactory.openSession();
        List<Slot> slotList = null;
        // HQL
        slotList = (List<Slot>) session.createQuery("from Slot as s where s.doctor.userId="+userId).list();
        session.close();
        return slotList;
    }

    @Override
    public void viewBookedSlotsById(int userId) {
        Session session = sessionFactory.openSession();
        List<Slot> slotList = null;
        // HQL
        slotList = (List<Slot>) session.createQuery("from Slot as s where s.doctor.userId="+userId
                +" AND s.occupied=true").list();
        session.close();
        displaySlots(slotList);

    }

    @Override
    public void viewFreeSlots() {
        Session session = sessionFactory.openSession();
        List<Slot> slotList = null;
        // HQL
        slotList = (List<Slot>) session.createQuery("from Slot as s where s.occupied=false").list();
        session.close();
        displaySlots(slotList);
    }

    @Override
    public void viewFreeSlotsById(int userId) {
        Session session = sessionFactory.openSession();
        List<Slot> slotList = null;
        // HQL
        slotList = (List<Slot>) session.createQuery("from Slot as s where s.doctor.userId="+userId
                +" AND s.occupied=false").list();
        session.close();
        displaySlots(slotList);
    }
}
