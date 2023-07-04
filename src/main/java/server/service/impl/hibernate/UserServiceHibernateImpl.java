package server.service.impl.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import server.context.ServiceContext;
import server.domain.User;
import server.service.UserService;
import server.utilities.DisplayFormatting;
import javax.persistence.PersistenceException;
import java.util.List;

public class UserServiceHibernateImpl implements UserService {
    private final Session session;
    public UserServiceHibernateImpl(){
        session = ServiceContext.getSessionFactory().openSession();
    }


    @Override
    public List<User> getUsers() {
        List<User> userList = null;
        // HQL
        userList = session.createQuery("from User").list();
        return userList;
    }


    @Override
    public boolean addUserEntry(User user) {
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
            return false;

        }
        return true;
    }

    @Override
    public void addUsersListToStorage(List<User> userList) {
        userList.forEach(this::addUserEntry);
    }

    @Override
    public User validateUserLogin(String username, String password, String userRole) {
        User user = null;
        //HQL Query
        user = (User) session.createQuery("from User where username=:username AND password=:password " +
                                    " AND role=:role")
                .setParameter("username",username)
                .setParameter("password",password)
                .setParameter("role",userRole)
                .uniqueResult();
        return user;
    }

    @Override
    public List<User> getPatients() {
        List<User> patientList;
        patientList = (List<User>) session.createQuery("from User where role=:role")
                .setParameter("role","patient")
                .list();
        return patientList;
    }

    @Override
    public List<User> getDoctors() {
        List<User> doctorList;
        doctorList = (List<User>) session.createQuery("from User where role=:role")
                .setParameter("role","doctor")
                .list();
        return doctorList;
    }

    public void viewUsers() {
        List<User> users = getUsers();
        DisplayFormatting.displayUsers(users);
    }

    @Override
    public void viewPatients() {
        List<User> patients = getPatients();
        DisplayFormatting.displayUsers(patients);
    }

    @Override
    public void viewDoctors() {
        List<User> doctors = getDoctors();
        DisplayFormatting.displayUsers(doctors);
    }
}
