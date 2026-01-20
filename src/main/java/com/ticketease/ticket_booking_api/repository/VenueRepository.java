package com.ticketease.ticket_booking_api.repository;

import com.ticketease.ticket_booking_api.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {

    // Check if a venue exists by name to prevent duplicates
    boolean existsByName(String name);
}
