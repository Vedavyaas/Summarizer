package com.Summarizer.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

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
