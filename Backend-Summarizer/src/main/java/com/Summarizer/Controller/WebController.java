package com.Summarizer.Controller;

import com.Summarizer.Repository.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    /*@GetMapping("/register")
    public String register() {
        return "register";
    }*/

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/docs")
    public String docs() {
        return "docs";
    }

    @GetMapping("/whatsapp")
    public String whatsapp() {
        return "whatsapp";
    }
}
