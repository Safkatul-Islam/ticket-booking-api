package com.ticketease.ticket_booking_api.repository;

import com.ticketease.ticket_booking_api.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // Find all tickets bought by a specific user
    List<Ticket> findByUserId(Long userId);
}
