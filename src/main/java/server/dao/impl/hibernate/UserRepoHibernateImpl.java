package server.dao.impl.hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import server.dao.UserRepository;
import server.domain.Slot;
import server.domain.User;
import server.domain.version1.AppointmentV1;

import javax.persistence.PersistenceException;
import java.util.List;

@Repository
@Qualifier("userRepoHibernate")
public class UserRepoHibernateImpl implements UserRepository {
    private SessionFactory sessionFactory;


    @Autowired
    public UserRepoHibernateImpl(SessionFactory sessionFactory) {
        System.out.println("UserRepoHibernateImpl- autowrie-    public UserRepoHibernateImpl(SessionFactory sessionFactory)-"+sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> getUsers() {
//        Session session = ServiceContext.getSessionFactory().openSession();
        Session session = sessionFactory.openSession();
        List<User> userList = null;
        // HQL
        userList = session.createQuery("from User").list();
        session.close();
        return userList;

    }


    @Override
    public boolean addUserEntry(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try{
            // start the transaction
            transaction =  session.beginTransaction();

            // save student object
            session.save(user);

            //commit transaction
            transaction.commit();
        } catch (PersistenceException e){
            if(e.getCause() instanceof ConstraintViolationException)
                System.out.println("Username Already Exists:"+ e.getMessage());

            if(transaction != null){
                transaction.rollback();
            }
            session.close();
            return false;

        }
        session.close();
        return true;
    }

    @Override
    public void addUsersListToStorage(List<User> userList) {
        userList.forEach(this::addUserEntry);
    }



    @Override
    public List<User> getPatients() {
        Session session = sessionFactory.openSession();
        List<User> patientList;
        patientList = (List<User>) session.createQuery("from User where role=:role")
                .setParameter("role","patient")
                .list();
        session.close();
        return patientList;
    }

    @Override
    public List<User> getDoctors() {
        Session session = sessionFactory.openSession();
        List<User> doctorList;
        doctorList = (List<User>) session.createQuery("from User where role=:role")
                .setParameter("role","doctor")
                .list();
        session.close();
        return doctorList;
    }



    @Override
    public boolean deleteUser(String username) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String hql = "FROM User as u WHERE u.username = :username";
            User user = (User) session.createQuery(hql)
                    .setParameter("username", username)
                    .uniqueResult();
            if(user==null) {
                System.out.println("No User found against the provided username");
                return false;
            }

            // Remove the association between AppointmentV1 and Slot entities
            for (AppointmentV1 appointment : user.getAppointmentV1List()) {
                Slot slot = appointment.getSlot();
                slot.removeAppointmentV1(); // If we don`t remove the child appointment from parent slot, hibernate will through exception Exception in thread "main" javax.persistence.EntityNotFoundException: deleted object would be re-saved by cascade (remove deleted object from associations): [server.domain.version1.AppointmentV1#1]
            }

            // As we have already defined cascading to parent will automatically delete the children upon deletion, so no need
            user.getAppointmentV1List().clear();
            user.getSlots().clear();

            session.delete(user);
            transaction.commit();
            return true;
        } catch (Exception e){
            System.out.println("Error: Unable to write data to Database using hibernate: " + e.getMessage());
            e.printStackTrace();
            if(transaction!=null){
                transaction.rollback();
            }
        }
        return false;
    }

    @Override
    public User getUserByUsername(String username) {
        Session session = sessionFactory.openSession();
        User user = null;
        //HQL Query
        user = (User) session.createQuery("from User where username=:username")
                .setParameter("username",username)
                .uniqueResult();
        session.close();
        return user;
    }
}
