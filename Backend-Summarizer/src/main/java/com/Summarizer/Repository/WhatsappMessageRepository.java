package com.Summarizer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WhatsappMessageRepository extends JpaRepository<WhatsappMessageEntity, Long> {
    List<WhatsappMessageEntity> findByUser_Username(String username);
}
