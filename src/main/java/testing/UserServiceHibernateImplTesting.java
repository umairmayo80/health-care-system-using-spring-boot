package testing;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import server.context.ServiceContext;
import server.domain.User;
import server.service.UserService;
import server.utilities.InitializeHibernateDb;

import java.util.List;


public class UserServiceHibernateImplTesting {
    public static void main(String[] args){


//
//        Session session = ServiceContext.getSessionFactory().openSession();
//
//        User user1  = new User("Ali","patient","user123","user123");
//        System.out.println(user1);
//
        UserService userService = ServiceContext.getUserServiceHibernate();
//        userService.addUserEntry(user1);
//
//        // now the Id will be updated according to the database
//        System.out.println(user1);
//
//
//
//        User user2  = new User("Umer","doctor","user2","user123");
//        System.out.println(user2);
//
//        System.out.println(userService.addUserEntry(user2));
//
//        User userRetrieved = session.get(User.class,user1.getUserId());
//        System.out.println(userRetrieved);
//
//
//        System.out.println(userService.getUsers());
//
//        System.out.println(userService.validateUserLogin("user123","user123","patient"));
//
//
//        System.out.println(userService.getPatients());
//
//        System.out.println(userService.getDoctors());
//
//        userService.viewUsers();
//        userService.viewDoctors();
//
//        session.close();


        InitializeHibernateDb.initializeHibernateDb();
        userService.viewUsers();


        List<User> userList = userService.getUsers();

        User user = userList.get(1);
//        System.out.println(user.toString());

//        System.out.println(user.getAppointmentV1List());
//        System.out.println(user.getSlots());

        Session session = ServiceContext.getSessionFactory().openSession();
        Transaction transaction1 =  session.beginTransaction();
//        session.delete(user);
        userService.deleteUser("patient2");
        transaction1.commit();


        userService.viewUsers();




    }
}
