package com.Summarizer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WhatsappMessageRepository extends JpaRepository<WhatsappMessageEntity,Long>{
}
