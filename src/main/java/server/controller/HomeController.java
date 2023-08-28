package server.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
public class HomeController {
    @GetMapping("jsp")
    public String helloFromJspPage(){
        return "home";
//        return "<h1>Spring 2.7 Home</h1>";
    }

    @GetMapping("/thymeLeaf")
    public String helloFromThyMeLeafPage(Model model) {
        // You can add data to the model to use in the template
        model.addAttribute("message", "Hello from Spring Boot and Thymeleaf!");
        return "home"; // This refers to the template name without the extension
    }
}
