package testing;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import server.AppConfig;
import server.domain.User;
import server.utilities.DatabaseConnection;
import javax.persistence.PersistenceException;

public class HibernateConnectionTesting {
 public static void main(String[] args){
//     Session session = ServiceContext.getSessionFactory().openSession();
     Session session = new AppConfig().getSessionFactory(new DatabaseConnection()).openSession();
     Transaction transaction = session.beginTransaction();

     User user1  = new User("Ali","patient","user123","user123");
     System.out.println(user1);

     //save the user
     session.save(user1);

     // now the Id will be updated according to the database
     System.out.println(user1);



     User user2  = new User("Umer","patient","user2","user123");
     System.out.println(user2);

     try {
         session.save(user2);

     transaction.commit();
     } catch (PersistenceException e){
         System.out.println("asd");
         System.out.println(e.getCause() instanceof ConstraintViolationException);
         e.printStackTrace();
         System.out.println("---asd");
     }

     User userRetrieved = session.get(User.class,user1.getUserId());
     System.out.println(userRetrieved);


//     UserServiceHibernateImpl userServiceHibernate = ServiceContext.getUserServiceHibernate();
//     System.out.println(userServiceHibernate.getUsers());

     session.close();

 }
}
