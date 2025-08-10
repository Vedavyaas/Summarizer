package com.Summarizer.Controller;

import com.Summarizer.Service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/control")
public class AiController {

    private final String prompting = "Summarize the paragraph into one professional sentence. Then list up to 10 single-word bullets for core topic, purpose, and key takeaway. Output only the sentence and bullets. No extra text or comments.\n";
    @Autowired
    private AiService aiService;
    //http://localhost:8001/control/api
    @GetMapping("/api")
    public String aiResponse(@RequestParam String messages){
            String promptMessage = prompting + messages;
            return (aiService.getResult(promptMessage));
    }

    public String whatsAppResponse(String whatsAppMessage){
          String promptMessage = prompting + whatsAppMessage;
          return (aiService.getResult(promptMessage));
    }

}
