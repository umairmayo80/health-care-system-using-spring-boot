//package testing;
//
//import com.mysql.cj.jdbc.SuspendableXAConnection;
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//import server.context.ServiceContext;
//import server.domain.Slot;
//import server.domain.User;
//import server.service.UserService;
//import server.service.impl.hibernate.SlotServiceHibernateImpl;
//import server.utilities.ScheduleCreationException;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class SlotServiceHibernateTesting {
//    public static void main(String[] args) throws ScheduleCreationException {
//
//        Session session = ServiceContext.getSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//
//
//        User user1  = new User("Ali","doctor","user123","user123");
//        User user2  = new User("umer","doctor","user2","user123");
//
//        System.out.println(user1);
//        Slot slot = new Slot(4,1,"2023-12-12","09:30:00","10:30",false);
//
////        List<Slot> slotsList = new ArrayList<Slot>();
////        slotsList.add(slot);
////        user1.setSlots(slotsList);
//
////        UserService userService = ServiceContext.getUserServiceHibernate();
////
////        userService.addUserEntry(user1);
////        System.out.println(user1);
//
//
//
//
//        SlotServiceHibernateImpl slotServiceHibernate = ServiceContext.getSlotServiceHibernate();
//
//
////        slot.setDoctor(user1);
//
////        System.out.println(slotServiceHibernate.addSlotEntry(slot));
//////        System.out.println(slotServiceHibernate.addSlotEntry(slot));
////        Session session = ServiceContext.getSessionFactory().openSession();
////        Transaction transaction = session.beginTransaction();
////        System.out.println(user1.getSlots());
//
//
//        System.out.println(session.get(User.class,user1.getUserId()));
////        transaction.commit();
//
//        Slot slot2 = new Slot(4,1,"2023-12-13","09:30:00","10:30",true);
////
//
//        // Save the slot individually
//        session.save(slot);
//        session.save(slot2);
//
//
//
//        // Associate the slot with the user
//        user1.addSlot(slot);
////        user2.addSlot(slot2);
//        slot2.setDoctor(user2);
//
//        // Save the user with the associated slot
//        session.save(user1);
//        session.save(user2);
//
//
//        transaction.commit();
//
//
//        System.out.println(user1);
//        System.out.println(session.get(User.class, user1.getUserId()));
//
//
//
//        Slot slot3 = new Slot(4,1,"2023-12-14","09:30:00","10:30",false);
//        slot3.setDoctor(user2);
////
////        session.save(slot3);
//        slotServiceHibernate.addSlotEntry(slot3);
////        Transaction transaction1 =  session.beginTransaction();
////        session.delete(user1);
////        transaction1.commit();
//
//
//        System.out.println(slotServiceHibernate.getSlots());
//
//
//        slotServiceHibernate.viewAllSlots();
//
//        slotServiceHibernate.viewSlotsById(2);
//
//        slotServiceHibernate.viewFreeSlots();
//        slotServiceHibernate.viewBookedSlotsById(2);
//
//
//
//    }
//}
