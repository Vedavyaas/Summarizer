package com.Summarizer.Controller;

import com.Summarizer.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reset")
public class ResetController {

    @Autowired
    private OTPController otpController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @Transactional
    @PostMapping("/password")
    public String resetPassword(@RequestParam String phoneNumber, @RequestParam String otp, @RequestParam String newPassword) {
        boolean verified = otpController.otpChecker(phoneNumber, otp);
        if (!verified) {
            return "OTP verification failed. Password not changed.";
        }
        String encodedPassword = passwordEncoder.encode(newPassword);
        userRepository.changePasswordByPhoneNumber(phoneNumber, encodedPassword);
        return "Password changed successfully.";
    }

    @GetMapping("/username")
    public String showUsername(@RequestParam String phoneNumber, @RequestParam String otp) {
        boolean verified = otpController.otpChecker(phoneNumber, otp);
        if (!verified) {
            return "OTP verification failed. Password not changed.";
        }
        return "Username: " + userRepository.getUsernameByPhoneNumber(phoneNumber);
    }
}