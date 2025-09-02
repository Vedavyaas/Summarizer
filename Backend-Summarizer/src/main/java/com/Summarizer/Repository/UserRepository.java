package com.Summarizer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByPhoneNumber(String phoneNumber);

    @Query("SELECT u.sid FROM User u WHERE u.phoneNumber = :phoneNumber")
    String getSidByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query("SELECT u.auth FROM User u WHERE u.phoneNumber = :phoneNumber")
    String getAuthByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Modifying
    @Query("DELETE FROM User u WHERE u.phoneNumber = :phoneNumber")
    void deleteByPhoneNumber(String phoneNumber);

    @Modifying
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.phoneNumber = :phoneNumber")
    void changePasswordByPhoneNumber(@Param("phoneNumber") String phoneNumber, @Param("newPassword") String newPassword);

    @Query("SELECT u.username FROM User u WHERE u.phoneNumber = :phoneNumber")
    String getUsernameByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}