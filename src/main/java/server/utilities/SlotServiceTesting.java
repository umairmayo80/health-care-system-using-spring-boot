//package server.utilities;
//
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import server.AppConfig;
//import server.service.SlotService;
//
//@SpringBootApplication(scanBasePackages = {"server"})
//public class SlotServiceTesting {
//    public static void main(String [] args){
//        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//        SlotService slotService = context.getBean(SlotService.class);
//        System.out.println(slotService.getSlots());
//    }
//
//}
