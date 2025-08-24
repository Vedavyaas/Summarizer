package com.Summarizer.Controller;

import com.Summarizer.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reset")
public class ResetController {

    @Autowired
    private OTPController otpController;

    @Autowired
    private UserRepository userRepository;

    // Endpoint to send OTP for account reset
    @GetMapping("/account")
    public String reset(@RequestParam String phoneNumber) {
        String sid = userRepository.getSidByPhoneNumber(phoneNumber);
        String auth = userRepository.getAuthByPhoneNumber(phoneNumber);
        otpController.otpGenerator(phoneNumber, sid, auth);
        return "OTP sent to your WhatsApp number.";
    }

    @DeleteMapping("/account")
    public String deleteAccount(@RequestParam String phoneNumber, @RequestParam String otp) {
        boolean verified = otpController.otpChecker(phoneNumber, otp);
        if (!verified) {
            return "OTP verification failed. Account not deleted.";
        }
        userRepository.deleteByPhoneNumber(phoneNumber);
        return "Account deleted successfully.";
    }
}