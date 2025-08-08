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

    public WhatsappMessageEntity() {
    }

    public WhatsappMessageEntity(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }
}
