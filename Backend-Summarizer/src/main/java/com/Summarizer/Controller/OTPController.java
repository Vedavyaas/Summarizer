package com.Summarizer.Controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.time.Instant;

@RestController
@RequestMapping("/otp")
@CrossOrigin("*")
public class OTPController {

    private String finalOtp;
    private Instant otpCreationTime;
    private final long OTP_VALID_DURATION = 2 * 60 * 1000; // 2 minutes


    @GetMapping("/generate")
    public String otpGenerator(@RequestParam String phoneNumber,
                               @RequestParam String sid,
                               @RequestParam String auth) {
        finalOtp = String.format("%06d", new SecureRandom().nextInt(1000000));
        otpCreationTime = Instant.now();
        Twilio.init(sid,auth);
        try {
            Message message = Message.creator(
                            new com.twilio.type.PhoneNumber("whatsapp:" + phoneNumber),
                            new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                    (String) null
                    )
                    .setContentSid("HX229f5a04fd0510ce1b071852155d3e75")
                    .setContentVariables("{\"1\":\"" + finalOtp + "\"}")
                    .create();
            return "OTP sent successfully to WhatsApp: " + phoneNumber;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send OTP: " + e.getMessage();
        }
    }

    @PostMapping("/checker")
    public boolean otpChecker(@RequestParam String otp) {
        return otp != null &&
                otp.equals(finalOtp) &&
                Instant.now().isBefore(otpCreationTime.plusMillis(OTP_VALID_DURATION));
    }
}