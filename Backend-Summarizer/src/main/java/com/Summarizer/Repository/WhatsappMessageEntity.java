package com.Summarizer.Repository;

import jakarta.persistence.*;

@Entity
@Table(name = "message")
public class WhatsappMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender")
    private String sender;

    @Lob
    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public WhatsappMessageEntity() {
    }

    public WhatsappMessageEntity(String message, String sender, User user) {
        this.message = message;
        this.sender = sender;
        this.user = user;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
