package server;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.spi.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;
import server.dao.UserRepository;
import server.domain.Slot;
import server.domain.User;
import server.domain.Appointment;
import server.service.AppointmentServiceV1;
import server.service.SlotService;
import server.service.UserService;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    //To Initialize the Database
    @Bean
    CommandLineRunner commandLineRunner(UserService userService, SlotService slotService, AppointmentServiceV1 appointmentServiceV1) {
        return args -> {
            try {
                User user1 = new User("John Doe", "admin", "admin123", "password123",false);
                User user2 = new User("Jane Smith", "patient", "patient123", "patient123",false);
                User user3 = new User("Robert Johnson", "doctor", "doctor123", "doctor123",false);

                User user4 = new User("Alex", "patient", "patient2", "patient123",true);

                // persist the users
                userService.addUserEntry(user1);
                userService.addUserEntry(user2);
                userService.addUserEntry(user3);
                userService.addUserEntry(user4);


                slotService.createNewSlot(user3.getUserId(), "2023-12-12", "09:30:00", "10:30:00");
                slotService.createNewSlot(user3.getUserId(), "2023-12-13", "09:30:00", "10:30:00");
                slotService.createNewSlot(user3.getUserId(), "2023-12-14", "09:30:00", "10:30:00");


                // it will fetch the slot, the user, create association and
                appointmentServiceV1.addAppointment(2, user2.getUserId());
                appointmentServiceV1.addAppointment(3, user2.getUserId());

                System.out.println("Hibernate System Success initialized");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Failed to Initialize hibernate System.\n Exiting");
                System.exit(1);
            }
        };
    }

}
