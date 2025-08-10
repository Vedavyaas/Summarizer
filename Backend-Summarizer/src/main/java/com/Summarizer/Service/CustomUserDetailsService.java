package com.Summarizer.Service;

import com.Summarizer.Repository.Token;
import com.Summarizer.Repository.User;
import com.Summarizer.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final EmailService emailService;

    public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.emailService = emailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

    }

    public void registerUser(User user) {
        userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail())
                .ifPresent(existingUser -> {
                    throw new IllegalStateException("User already exist");
                });
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        userRepository.save(user);

        Token confirmationToken = new Token(UUID.randomUUID().toString(), LocalDateTime.now(),LocalDateTime.now().plusMinutes(15),user);
        tokenService.save(confirmationToken);

        emailService.send(user.getEmail(),confirmationToken.getToken());
    }

    public void confirmToken(String token) {

        Token confirmedToken = tokenService
                .findByToken(token)
                .orElseThrow(() -> new IllegalStateException("Invalid Token"));

        if(confirmedToken.getConfirmedAt() != null){
            throw new IllegalStateException("User already verified");
        }

        LocalDateTime expiresAt = confirmedToken.getExpiresAt();
        if(expiresAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("Token expired");
        }

        confirmedToken.setConfirmedAt(LocalDateTime.now());
        tokenService.save(confirmedToken);

        enableUser(confirmedToken.getUser());

    }

    private void enableUser(User user) {
        user.setEnabled(true);
    }
}