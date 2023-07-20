package server.utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.domain.Slot;
import server.domain.User;
import server.domain.Appointment;
import server.service.impl.hibernate.AppointmentServiceV1HibernateImpl;
import server.service.impl.hibernate.SlotServiceHibernateImpl;
import server.service.impl.hibernate.UserServiceHibernateImpl;

@Component
public class InitializeHibernateDb {
    private UserServiceHibernateImpl userService;
    private SlotServiceHibernateImpl slotService;
    private AppointmentServiceV1HibernateImpl appointmentServiceV1;

    @Autowired
    public void setUserService(UserServiceHibernateImpl userService) {
        this.userService = userService;
    }

    @Autowired
    public void setSlotService(SlotServiceHibernateImpl slotService) {
        this.slotService = slotService;
    }


    @Autowired
    public void setAppointmentServiceV1(AppointmentServiceV1HibernateImpl appointmentServiceV1) {
        this.appointmentServiceV1 = appointmentServiceV1;
    }

    public void initializeHibernateDb() {
        try {
            User user1 = new User("John Doe", "admin", "admin123", "password123",false);
            User user2 = new User("Jane Smith", "patient", "patient123", "patient123",false);
            User user3 = new User("Robert Johnson", "doctor", "doctor123", "doctor123",false);

            User user4 = new User("Alex", "patient", "patient2", "patient123",true);

//            UserService userService = ServiceContext.getUserServiceHibernate();
//            userService = ServiceContext.getUserServiceHibernate();

            // persist the users
            userService.addUserEntry(user1);
            userService.addUserEntry(user2);
            userService.addUserEntry(user3);
            userService.addUserEntry(user4);


            // Now the users are saved, now we can add slot while making relation to the persisted users
//            Slot slot1 = new Slot(4, 1, "2023-12-12", "09:30:00", "10:30", false);
//            Slot slot2 = new Slot(4, 1, "2023-12-13", "09:30:00", "10:30", false);
//            Slot slot3 = new Slot(4, 1, "2023-12-14", "09:30:00", "10:30", false);


            // associate the slots with the users
            // right way is, it will be bidirectional
//            user3.addSlot(slot1);
//            user3.addSlot(slot2);
//            user3.addSlot(slot3);


            // save the slots
//            slotService.addSlotEntry(slot1);
//            slotService.addSlotEntry(slot2);
//            slotService.addSlotEntry(slot3);


            slotService.createNewSlot(user3.getUserId(),"2023-12-12","09:30:00","10:30:00");
            slotService.createNewSlot(user3.getUserId(),"2023-13-12","09:30:00","10:30:00");
            slotService.createNewSlot(user3.getUserId(),"2023-14-12","09:30:00","10:30:00");

            // insert slot data
//            Appointment appointmentV1 = new Appointment(2, 3);
//            Appointment appointment2 = new Appointment(4, 2);

            //associate the appointment with the target slot otherwise the relation will not be made with parent and foreign key will be null
//            slot3.setAppointmentV1(appointmentV1);
//            slot2.setAppointmentV1(appointment2);


            // associate the appointment with the target patient
//            user2.addAppointmentV1(appointmentV1); // this will make bidirectional and will work in cascading
//            user4.addAppointmentV1(appointment2);


//            appointmentServiceV1.addAppointmentEntry(appointmentV1);
//            appointmentServiceV1.addAppointmentEntry(appointment2);

            // it will fetch the slot, the user, create association and
            appointmentServiceV1.addAppointment(2,user2.getUserId());
            appointmentServiceV1.addAppointment(3,user2.getUserId());

            System.out.println("Hibernate System Success initialized");
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Failed to Initialize hibernate System.\n Exiting");
            System.exit(1);
        }

    }
}
