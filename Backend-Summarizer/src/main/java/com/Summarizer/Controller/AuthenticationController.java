package com.Summarizer.Controller;


import com.Summarizer.Repository.User;
import com.Summarizer.Service.CustomUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("register")
public class AuthenticationController {


    private final CustomUserDetailsService customUserDetailsService;

    public AuthenticationController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping
    public String register(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "register";
    }

    @PostMapping
    public String postUser(@ModelAttribute("user") User user, Model model, RedirectAttributes redirectAttributes){
        customUserDetailsService.registerUser(user);
        redirectAttributes.addFlashAttribute("message","Please confirm your email address");
        return "redirect:/register";  // optionally redirect after POST
    }

    @GetMapping("/confirmToken")
    public String confirmToken(@RequestParam("token") String token,Model model){
        customUserDetailsService.confirmToken(token);
        return "confirmToken";
    }
}