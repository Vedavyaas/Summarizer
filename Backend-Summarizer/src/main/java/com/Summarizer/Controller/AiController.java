package com.Summarizer.Controller;

import com.Summarizer.Service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/control")
public class AiController {

    @Autowired
    private AiService aiService;
    //http://localhost:8001/control/api
    @GetMapping("/api")
    public String aiResponse(@RequestParam String messages){
            String promptMessage = "Summarize the following paragraph into a clear, professional one-liner, highlighting the core topic, purpose, and key takeaway in bullets upto 10 words." + messages;
            return (aiService.getResult(promptMessage));
    }

    public String whatsAppResponse(String whatsAppMessage){
          String promptMessage = "Summarize the following paragraph into a clear, professional one-liner, highlighting the core topic, purpose, and key takeaway in bullets upto 10 words." + whatsAppMessage;
          return (aiService.getResult(promptMessage));
    }

}
