package com.Summarizer.Controller;

import com.Summarizer.Config.TwilioConfig;
import com.Summarizer.Repository.WhatsappMessageEntity;
import com.Summarizer.Repository.WhatsappMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/twilio")
public class TwilioController {

    @Autowired
    private AiController aiController;

    @Autowired
    private WhatsappMessageRepository whatsappMessageRepository;

    @Autowired
    private TwilioConfig twilioConfig;

    @PostMapping("/webhook")
    public void receiveMessage(@RequestParam("From") String from, @RequestParam("Body") String body) {
        String summary = aiController.whatsAppResponse(body);

        WhatsappMessageEntity summarized = new WhatsappMessageEntity(summary, from);
        whatsappMessageRepository.save(summarized);

        System.out.println("SID: " + twilioConfig.getSid());
        System.out.println("TOKEN: " + twilioConfig.getAuthToken());

    }

    @GetMapping("/messages")
    public List<WhatsappMessageEntity> getAllMessages() {
        return whatsappMessageRepository.findAll();
    }
}
