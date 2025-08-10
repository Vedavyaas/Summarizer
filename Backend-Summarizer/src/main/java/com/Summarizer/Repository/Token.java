package com.Summarizer.Repository;

import jakarta.persistence.*;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Entity
@Table(name = "confirmation_token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "token",nullable = false)
    private String token;

    @Column(name = "expiresAt",nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(name ="user_id",nullable = false)
    private User user;

    public Token() {
    }

    public Token(String token, LocalDateTime expiresAt,LocalDateTime createdAt,  User user) {
        this.token = token;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(LocalDateTime confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}