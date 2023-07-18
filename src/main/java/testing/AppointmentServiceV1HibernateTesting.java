//package testing;
//
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//import server.context.ServiceContext;
//import server.domain.Slot;
//import server.domain.User;
//import server.domain.AppointmentV1;
//import server.service.SlotService;
//import server.service.UserService;
//import server.service.impl.hibernate.AppointmentServiceV1HibernateImpl;
//import server.service.impl.hibernate.SlotServiceHibernateImpl;
//import server.service.impl.hibernate.UserServiceHibernateImpl;
//import server.service.AppointmentServiceV1;
//import server.utilities.ScheduleCreationException;
//
//public class AppointmentServiceV1HibernateTesting {
//    public static void main(String[] args) throws ScheduleCreationException {
//
//        // Inserting dummy data into the database
////        Session session = ServiceContext.getSessionFactory().openSession();
////        Transaction transaction = session.beginTransaction();
//
//
//        User user1  = new User("John Doe","admin","admin123","password123");
//        User user2  = new User("Jane Smith","patient","patient123","patient123");
//        User user3  = new User("Robert Johnson","doctor","doctor123","doctor123");
//
//        User user4  = new User("Alex","patient","patient2","patient123");
//
////        UserService userService = ServiceContext.getUserServiceHibernate();
//        UserService userService = new UserServiceHibernateImpl();
//
//        // Save the users individually first
////        session.save(user1);
////        session.save(user2);
////        session.save(user3);
////        transaction.commit();
//
//        userService.addUserEntry(user1);
//        userService.addUserEntry(user2);
//        userService.addUserEntry(user3);
//
//        userService.addUserEntry(user4);
//
//
//        userService.viewUsers();
//        System.out.println(user1);
//
//
//        System.out.println("-------------------------\n");
//
//
//        // Now the users are saved, now we can add slot while making relation to the persisted users
//        Slot slot1 = new Slot(4,1,"2023-12-12","09:30:00","10:30",false);
//        Slot slot2 = new Slot(4,1,"2023-12-13","09:30:00","10:30",false);
//        Slot slot3 = new Slot(4,1,"2023-12-14","09:30:00","10:30",false);
//
//
//        // associate the slots with the users
////        slot1.setDoctor(user3); // these are wrong way to make association
////        slot2.setDoctor(user3);
////        slot3.setDoctor(user3);
//
//
//        // right way is, it will be bidirectional
//        user3.addSlot(slot1);
//        user3.addSlot(slot2);
//        user3.addSlot(slot3);
//
//
//
//
//        // save the slots
//        SlotService slotService = new SlotServiceHibernateImpl();
//        slotService.addSlotEntry(slot1);
//        slotService.addSlotEntry(slot2);
//        slotService.addSlotEntry(slot3);
//
//
//        // check the stored data
//        slotService.viewAllSlots();
//
//
//        // insert slot data
//        AppointmentV1 appointmentV1 = new AppointmentV1(2,3);
//        AppointmentV1 appointment2 = new AppointmentV1(4,2);
//
//        //associate the appointment with the target slot otherwise the relation will not be made with parent and foreign key will be null
//        // we also need to add this in the
////        appointmentV1.setSlot(slot3);
//        slot3.setAppointmentV1(appointmentV1);
//        slot2.setAppointmentV1(appointment2);
//
//
//
//        // associate the appointment with the target patient
////        appointmentV1.setPatient(user2); this will be unidirectional, only the appointment will know
//        user2.addAppointmentV1(appointmentV1); // this will make bidirectional and will work in cascading
//        user4.addAppointmentV1(appointment2);
//
//        System.out.println("asd");
//
//
//        AppointmentServiceV1 appointmentServiceV1 = new AppointmentServiceV1HibernateImpl();
//        appointmentServiceV1.addAppointmentEntry(appointmentV1);
//        appointmentServiceV1.addAppointmentEntry(appointment2);
//
//
//        System.out.println("asd");
//        Session session = ServiceContext.getSessionFactory().openSession();
////        Transaction transaction = session.beginTransaction();
////
//////        session.delete(user2);
//////        session.delete(user3);
////        transaction.commit();
//
//
//
//        appointmentServiceV1.viewAllAppointments();
//
//
//        appointmentServiceV1.viewAppointmentsByPatientId(4);
//        appointmentServiceV1.viewAppointmentsByDoctorId(3);
//
//
//
//
//
//    }
//}
