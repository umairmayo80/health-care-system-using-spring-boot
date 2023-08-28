package server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@Controller
@RestController // rest is for json response and Controller is for redirecting jsp/html pages
@SpringBootApplication(scanBasePackages = {"server"})
public class AppServer {
    public static void main(String[] args){
        SpringApplication.run(AppServer.class,args);
    }

    @GetMapping("/")
    public String hello(){
//        return "home";
        return "<h1>Spring 2.7 Home</h1>";
    }

}
