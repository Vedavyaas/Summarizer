/*      Run your Spring Boot app locally on port 8001.
        Download and install ngrok from https://ngrok.com/download or use this in terminal brew install ngrok/ngrok/ngrok.
        Open your terminal and run:
        ##ngrok http 8001##
        Copy the https URL that ngrok gives you (like https://abc123.ngrok.io).
        Go to your Twilio dashboard and set the webhook URL to:
        https://80b337899e4e.ngrok-free.app/twilio/webhook
        Now Twilio will send WhatsApp messages to your local app through ngrok!
        Keep ngrok running while you test.    */

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

    }

    @GetMapping("/messages")
    public List<WhatsappMessageEntity> getAllMessages() {
        return whatsappMessageRepository.findAll();
    }
}
