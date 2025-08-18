package com.Summarizer.Controller;

import com.Summarizer.Repository.User;
import com.Summarizer.Repository.UserRepository;
import com.Summarizer.Repository.WhatsappMessageEntity;
import com.Summarizer.Repository.WhatsappMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/twilio")
public class TwilioController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WhatsappMessageRepository whatsappMessageRepository;

    @Autowired
    private AiController aiController;

    @PostMapping("/webhook")
    public void receiveMessage(@RequestParam("From") String from,
                               @RequestParam("Body") String body,
                               Authentication authentication) {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(
                from.startsWith("whatsapp:") ? from.substring(9) : from
        );
        System.out.println("Done");
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String summary = aiController.whatsAppResponse(body);
            WhatsappMessageEntity message = new WhatsappMessageEntity(summary, from, user);
            whatsappMessageRepository.save(message);
        }
    }
    @GetMapping("/messages")
    public List<WhatsappMessageEntity> getMessagesForUser(Authentication authentication) {
        return whatsappMessageRepository.findByUser_Username(authentication.getName());
    }
}
