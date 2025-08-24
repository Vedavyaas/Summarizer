package com.Summarizer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByPhoneNumber(String phoneNumber);

    String getSidByPhoneNumber(String phoneNumber);
    String getAuthByPhoneNumber(String phoneNumber);

    void deleteByPhoneNumber(String phoneNumber);
}
