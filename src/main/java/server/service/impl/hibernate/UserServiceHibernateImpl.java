package server.service.impl.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import server.context.ServiceContext;
import server.domain.User;
import server.domain.version1.AppointmentV1;
import server.service.UserService;
import server.utilities.DisplayFormatting;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

public class UserServiceHibernateImpl implements UserService {
    public UserServiceHibernateImpl(){
    }


    @Override
    public List<User> getUsers() {
        Session session = ServiceContext.getSessionFactory().openSession();
        List<User> userList = null;
        // HQL
        userList = session.createQuery("from User").list();
        session.close();
        return userList;

    }


    @Override
    public boolean addUserEntry(User user) {
        Session session = ServiceContext.getSessionFactory().openSession();
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
    public User validateUserLogin(String username, String password, String userRole) {
        Session session = ServiceContext.getSessionFactory().openSession();
        User user = null;
        //HQL Query
        user = (User) session.createQuery("from User where username=:username AND password=:password " +
                                    " AND role=:role")
                .setParameter("username",username)
                .setParameter("password",password)
                .setParameter("role",userRole)
                .uniqueResult();
        session.close();
        return user;
    }

    @Override
    public List<User> getPatients() {
        Session session = ServiceContext.getSessionFactory().openSession();
        List<User> patientList;
        patientList = (List<User>) session.createQuery("from User where role=:role")
                .setParameter("role","patient")
                .list();
        session.close();
        return patientList;
    }

    @Override
    public List<User> getDoctors() {
        Session session = ServiceContext.getSessionFactory().openSession();
        List<User> doctorList;
        doctorList = (List<User>) session.createQuery("from User where role=:role")
                .setParameter("role","doctor")
                .list();
        session.close();
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

    @Override
    public boolean deleteUser(String username) {
        Session session = ServiceContext.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
//            transaction = session.beginTransaction();
//            String hql = "FROM User as u WHERE u.username = :username";
//            User user = (User) session.createQuery(hql)
//                    .setParameter("username", username)
//                    .uniqueResult();
//            if(user==null) {
//                System.out.println("No User found against the provided username");
//                return false;
//            }

//            System.out.println(user);
//            System.out.println(user.getAppointmentV1List());
//            System.out.println(user.getSlots());



//
//            // Delete associated AppointmentV1 objects
//            List<AppointmentV1> appointmentV1List = user.getAppointmentV1List();
//            for (AppointmentV1 appointmentV1 : appointmentV1List) {
//                session.delete(appointmentV1);
//            }
//
//            transaction.commit();
//
//            user.getAppointmentV1List().clear();
//            user.getSlots().clear();
//            session.delete(user);

            // poor solution but working
            List<User> userList = getUsers();

            Optional<User> userOptional = userList.stream().
                    filter(user1 -> user1.getUsername().equals(username)).
                    findFirst();
            if(userOptional.isEmpty()) {
                System.out.println("No User found against the provided username");
                return false;
            }

            transaction =  session.beginTransaction();
            session.delete(userOptional.get());
            transaction.commit();
            session.close();

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
}
