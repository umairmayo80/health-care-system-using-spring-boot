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
    private final SessionFactory sessionFactory;


    @Autowired
    public SlotRepoHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Slot> getAll() {
        Session session = sessionFactory.openSession();
        List<Slot> slotList = null;
        // HQL
        slotList = session.createQuery("from Slot", Slot.class).list();
        session.close();
        return slotList;
    }

    @Override
    public Slot getById(int slotID) {
        Session session = sessionFactory.openSession();
        Slot slot = session.createQuery("from Slot where slotId="+slotID,Slot.class).uniqueResult();
        session.close();
        return slot;
    }

    @Override
    public boolean add(Slot slot) {
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
    public List<Slot> getAllByUserId(int userId) {
        Session session = sessionFactory.openSession();
        List<Slot> slotList = null;
        // HQL
        slotList = session.createQuery("from Slot as s where s.doctor.userId="+userId,Slot.class).list();
        session.close();
        return slotList;
    }

    @Override
    public List<Slot> getBookedSlotsById(int userId) {
        Session session = sessionFactory.openSession();
        List<Slot> slotList = null;
        // HQL
        slotList = session.createQuery("from Slot as s where s.doctor.userId="+userId
                +" AND s.occupied=true", Slot.class).list();
        session.close();
        return slotList;
    }

    @Override
    public List<Slot> getFreeSlots() {
        Session session = sessionFactory.openSession();
        List<Slot> slotList = null;
        // HQL
        slotList = session.createQuery("from Slot as s where s.occupied=false",Slot.class).list();
        session.close();
        return slotList;
    }

    @Override
    public List<Slot> getFreeSlotsById(int userId) {
        Session session = sessionFactory.openSession();
        List<Slot> slotList = null;
        slotList = session.createQuery("from Slot as s where s.doctor.userId="+userId
                +" AND s.occupied=false", Slot.class).list();
        session.close();
        return slotList;
    }
}
