package com.Summarizer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WhatsappMessageRepository extends JpaRepository<WhatsappMessageEntity, Long> {
    List<WhatsappMessageEntity> findByUser_Username(String username);

    @Modifying
    @Query("DELETE FROM WhatsappMessageEntity u WHERE u.sender = :phoneNumber")
    void delete(String phoneNumber);
}