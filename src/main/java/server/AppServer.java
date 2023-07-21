package server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import server.domain.Appointment;
import server.domain.Slot;
import server.service.AppointmentServiceV1;
import server.service.SlotService;
import server.service.UserService;

import java.util.List;

@RestController
@SpringBootApplication(scanBasePackages = {"server"})
public class AppServer {

    private final UserService userService;
    private final AppointmentServiceV1 appointmentServiceV1;
    private final SlotService slotService;

    public AppServer(UserService userService, AppointmentServiceV1 appointmentServiceV1, SlotService slotService) {
        this.userService = userService;
        this.appointmentServiceV1 = appointmentServiceV1;
        this.slotService = slotService;
    }

    public static void main(String[] args){
        SpringApplication.run(AppServer.class,args);
    }


    @GetMapping("/")
    public String hello(){
        return "Spring 2.7 Home";
    }

    @GetMapping("/appointments")
    public List<Appointment> getAppointments(){
        return appointmentServiceV1.getAppointments();
    }

    @GetMapping("/slots")
    public List<Slot> getSlots(){
        return slotService.getSlots();
    }
}
