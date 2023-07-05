package server.service.impl.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import server.context.ServiceContext;
import server.domain.Slot;
import server.service.SlotService;
import server.utilities.DisplayFormatting;

import java.util.List;

public class SlotServiceHibernateImpl implements SlotService {

    public SlotServiceHibernateImpl(){
    }
    @Override
    public List<Slot> getSlots() {
        Session session = ServiceContext.getSessionFactory().openSession();
        List<Slot> slotList = null;
        // HQL
        slotList = session.createQuery("from Slot").list();
        session.close();
        return slotList;
    }

    @Override
    public Slot getSlotBySlotId(int slotID) {
        Session session = ServiceContext.getSessionFactory().openSession();
        Slot slot = null;
        // HQL
        slot = (Slot) session.createQuery("from Slot where slotId="+slotID).uniqueResult();
        session.close();
        return slot;
    }

    @Override
    public boolean addSlotEntry(Slot slot) {
        Session session = ServiceContext.getSessionFactory().openSession();
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
        DisplayFormatting.displaySlots(slotList);
    }

    @Override
    public void viewSlotsById(int userId) {
        DisplayFormatting.displaySlots(getSlotsById(userId));
    }


    @Override
    public List<Slot> getSlotsById(int userId) {
        Session session = ServiceContext.getSessionFactory().openSession();
        List<Slot> slotList = null;
        // HQL
        slotList = (List<Slot>) session.createQuery("from Slot as s where s.doctor.userId="+userId).list();
        session.close();
        return slotList;
    }

    @Override
    public void viewBookedSlotsById(int userId) {
        Session session = ServiceContext.getSessionFactory().openSession();
        List<Slot> slotList = null;
        // HQL
        slotList = (List<Slot>) session.createQuery("from Slot as s where s.doctor.userId="+userId
                            +" AND s.occupied=true").list();
        session.close();
        DisplayFormatting.displaySlots(slotList);

    }

    @Override
    public void viewFreeSlots() {
        Session session = ServiceContext.getSessionFactory().openSession();
        List<Slot> slotList = null;
        // HQL
        slotList = (List<Slot>) session.createQuery("from Slot as s where s.occupied=false").list();
        session.close();
        DisplayFormatting.displaySlots(slotList);
    }

    @Override
    public void viewFreeSlotsById(int userId) {
        Session session = ServiceContext.getSessionFactory().openSession();
        List<Slot> slotList = null;
        // HQL
        slotList = (List<Slot>) session.createQuery("from Slot as s where s.doctor.userId="+userId
                +" AND s.occupied=false").list();
        session.close();
        DisplayFormatting.displaySlots(slotList);
    }
}
