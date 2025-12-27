package com.ticketease.ticket_booking_api.repository;

import com.ticketease.ticket_booking_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Finds a user by email. Used for Login.
    Optional<User> findByEmail(String email);

    // Checks if a user exists. Used for Registration (to prevent duplicates).
    Boolean existsByEmail(String email);
}
