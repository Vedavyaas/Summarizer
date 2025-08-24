package com.Summarizer.Controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/otp")
@CrossOrigin("*")
public class OTPController {

    private static class OtpDetails {
        private final String otp;
        private final Instant creationTime;

        public OtpDetails(String otp, Instant creationTime) {
            this.otp = otp;
            this.creationTime = creationTime;
        }

        public String getOtp() {
            return otp;
        }

        public Instant getCreationTime() {
            return creationTime;
        }
    }

    private final Map<String, OtpDetails> otpStorage = new HashMap<>();
    private final long OTP_VALID_DURATION = 2 * 60 * 1000;

    @GetMapping("/generate")
    public String otpGenerator(@RequestParam String phoneNumber,
                               @RequestParam String sid,
                               @RequestParam String auth) {
        String generatedOtp = String.format("%06d", new SecureRandom().nextInt(1000000));
        otpStorage.put(phoneNumber, new OtpDetails(generatedOtp, Instant.now()));

        Twilio.init(sid, auth);
        try {
            Message message = Message.creator(
                            new com.twilio.type.PhoneNumber("whatsapp:" + phoneNumber),
                            new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                            (String) null
                    )
                    .setContentSid("HX229f5a04fd0510ce1b071852155d3e75")
                    .setContentVariables("{\"1\":\"" + generatedOtp + "\"}")
                    .create();
            return "OTP sent successfully to WhatsApp: " + phoneNumber;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send OTP: " + e.getMessage();
        }
    }

    public boolean otpChecker(String phoneNumber, String otp) {
        OtpDetails details = otpStorage.get(phoneNumber);
        if (details == null) return false;
        boolean valid = otp != null &&
                otp.equals(details.getOtp()) &&
                Instant.now().isBefore(details.getCreationTime().plusMillis(OTP_VALID_DURATION));
        if (valid) otpStorage.remove(phoneNumber);
        return valid;
    }

    @PostMapping("/checker")
    public boolean otpCheckerApi(@RequestParam String phoneNumber, @RequestParam String otp) {
        return otpChecker(phoneNumber, otp);
    }
}