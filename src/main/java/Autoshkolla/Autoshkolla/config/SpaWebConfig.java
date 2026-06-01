package Autoshkolla.Autoshkolla.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpaWebConfig {

    @GetMapping({"/login", "/kandidat", "/kandidat/**", "/admin", "/admin/**"})
    public String forwardSpa() {
        return "forward:/index.html";
    }
}
