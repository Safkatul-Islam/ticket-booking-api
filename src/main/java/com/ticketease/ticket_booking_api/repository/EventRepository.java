package com.ticketease.ticket_booking_api.repository;

import com.ticketease.ticket_booking_api.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // Custom query to find all events happening at a specific venue
    List<Event> findByVenueId(Long venueId);
}
