package com.Summarizer.Controller;

import com.Summarizer.Repository.User;
import com.Summarizer.Repository.UserRepository;
import com.Summarizer.Repository.WhatsappMessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/reset")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ResetController {

    @Autowired
    private OTPController otpController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private WhatsappMessageRepository whatsappMessageRepository;

    /**
     * Get user account info by phone number (requires OTP verification)
     */
    @GetMapping("/account-info")
    public ResponseEntity<Map<String, Object>> getAccountInfo(
            @RequestParam String phoneNumber, 
            @RequestParam String otp) {
        try {
            // OTP verification
            boolean verified = otpController.otpChecker(phoneNumber, otp);
            if (!verified) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "OTP verification failed.", "success", false));
            }

            // Get user info
            Optional<User> userOpt = userRepository.findByPhoneNumber(phoneNumber);
            if (!userOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found", "success", false));
            }

            User user = userOpt.get();
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("username", user.getUsername());
            userInfo.put("phoneNumber", user.getPhoneNumber());
            userInfo.put("hasTwilioCredentials", user.getSid() != null && user.getAuth() != null);
            
            return ResponseEntity.ok(Map.of(
                "userInfo", userInfo,
                "success", true
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Internal server error", "success", false));
        }
    }

    /**
     * Reset account - send OTP using stored Twilio credentials
     */
    @GetMapping("/account")
    public ResponseEntity<Map<String, Object>> resetAccount(@RequestParam String phoneNumber) {
        try {
            // Check if user exists
            Optional<User> userOpt = userRepository.findByPhoneNumber(phoneNumber);
            if (!userOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found", "success", false));
            }

            User user = userOpt.get();
            String sid = user.getSid();
            String auth = user.getAuth();
            
            if (sid == null || auth == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Twilio credentials not configured for this account", "success", false));
            }

            // Use stored Twilio credentials to send OTP
            otpController.otpGenerator(phoneNumber, sid, auth);
            
            return ResponseEntity.ok(Map.of(
                "message", "OTP sent to your WhatsApp number using stored credentials.",
                "success", true,
                "phoneNumber", phoneNumber
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Internal server error", "success", false));
        }
    }

    /**
     * Update Twilio credentials for an account
     */
    @PostMapping("/update-twilio")
    public ResponseEntity<Map<String, Object>> updateTwilioCredentials(
            @RequestParam String phoneNumber,
            @RequestParam String otp,
            @RequestParam String newSid,
            @RequestParam String newAuth) {
        try {
            // OTP verification
            boolean verified = otpController.otpChecker(phoneNumber, otp);
            if (!verified) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "OTP verification failed.", "success", false));
            }

            // Get user and update Twilio credentials
            Optional<User> userOpt = userRepository.findByPhoneNumber(phoneNumber);
            if (!userOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found", "success", false));
            }

            User user = userOpt.get();
            user.setSid(newSid);
            user.setAuth(newAuth);
            userRepository.save(user);

            return ResponseEntity.ok(Map.of(
                "message", "Twilio credentials updated successfully.",
                "success", true
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Internal server error", "success", false));
        }
    }

    /**
     * Delete account
     */
    @Transactional
    @DeleteMapping("/account")
    public ResponseEntity<Map<String, Object>> deleteAccount(
            @RequestParam String phoneNumber, 
            @RequestParam String otp) {
        try {
            // OTP verification
            boolean verified = otpController.otpChecker(phoneNumber, otp);
            if (!verified) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "OTP verification failed. Account not deleted.", "success", false));
            }

            // Get user info before deletion
            Optional<User> userOpt = userRepository.findByPhoneNumber(phoneNumber);
            if (!userOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found", "success", false));
            }

            User user = userOpt.get();
            String username = user.getUsername();

            // Delete related data first
            whatsappMessageRepository.delete(phoneNumber);
            userRepository.deleteByPhoneNumber(phoneNumber);

            return ResponseEntity.ok(Map.of(
                "message", "Account deleted successfully.",
                "success", true,
                "deletedUsername", username,
                "deletedPhoneNumber", phoneNumber
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Internal server error", "success", false));
        }
    }

    /**
     * Reset password
     */
    @Transactional
    @PostMapping("/password")
    public ResponseEntity<Map<String, Object>> resetPassword(
            @RequestParam String phoneNumber, 
            @RequestParam String otp, 
            @RequestParam String newPassword) {
        try {
            // OTP verification
            boolean verified = otpController.otpChecker(phoneNumber, otp);
            if (!verified) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "OTP verification failed. Password not changed.", "success", false));
            }

            // Check if user exists
            Optional<User> userOpt = userRepository.findByPhoneNumber(phoneNumber);
            if (!userOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found", "success", false));
            }

            User user = userOpt.get();
            String oldUsername = user.getUsername();
            
            // Update password
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
            userRepository.save(user);

            return ResponseEntity.ok(Map.of(
                "message", "Password changed successfully.",
                "success", true,
                "username", oldUsername,
                "phoneNumber", phoneNumber
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Internal server error", "success", false));
        }
    }

    /**
     * Show username
     */
    @GetMapping("/username")
    public ResponseEntity<Map<String, Object>> showUsername(
            @RequestParam String phoneNumber, 
            @RequestParam String otp) {
        try {
            // OTP verification
            boolean verified = otpController.otpChecker(phoneNumber, otp);
            if (!verified) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "OTP verification failed.", "success", false));
            }

            Optional<User> userOpt = userRepository.findByPhoneNumber(phoneNumber);
            if (!userOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Username not found", "success", false));
            }

            User user = userOpt.get();
            return ResponseEntity.ok(Map.of(
                "username", user.getUsername(),
                "phoneNumber", user.getPhoneNumber(),
                "success", true
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Internal server error", "success", false));
        }
    }

    /**
     * Check if account exists and has Twilio credentials
     */
    @GetMapping("/check-account")
    public ResponseEntity<Map<String, Object>> checkAccount(@RequestParam String phoneNumber) {
        try {
            Optional<User> userOpt = userRepository.findByPhoneNumber(phoneNumber);
            if (!userOpt.isPresent()) {
                return ResponseEntity.ok(Map.of(
                    "exists", false,
                    "message", "Account not found",
                    "success", true
                ));
            }

            User user = userOpt.get();
            boolean hasTwilioCredentials = user.getSid() != null && user.getAuth() != null;
            
            return ResponseEntity.ok(Map.of(
                "exists", true,
                "hasTwilioCredentials", hasTwilioCredentials,
                "username", user.getUsername(),
                "phoneNumber", user.getPhoneNumber(),
                "success", true
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Internal server error", "success", false));
        }
    }
}