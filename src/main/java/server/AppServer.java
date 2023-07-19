package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@SpringBootApplication(scanBasePackages = {"server"})
public class AppServer {
    public static void main(String[] args){
        SpringApplication.run(AppServer.class,args);
    }


    @GetMapping("/")
    public String hello(){
        return "Spring 2.7 Home";
    }
}
