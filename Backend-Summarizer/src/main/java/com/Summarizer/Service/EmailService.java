package com.Summarizer.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    @Async
    public void send(String to , String token){
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setFrom("itsvyaas32@gmail.com");
            message.setSubject("Confirm your email - Summarizer");
            String messageBody = """
                    Thank you for registration. Please confirm your email.
                    
                    The confirmation token is
                    
                    http://localhost:8001/register/confirmToken?token=%s
                    """.formatted(token);
            message.setText(messageBody);
        } catch (Exception e){

        }
    }
}