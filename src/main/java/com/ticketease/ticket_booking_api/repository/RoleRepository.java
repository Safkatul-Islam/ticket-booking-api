package com.ticketease.ticket_booking_api.repository;

import com.ticketease.ticket_booking_api.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Derived Query Method
    // SELECT * FROM roles WHERE name = ?
    Optional<Role> findByName(String name);
}
