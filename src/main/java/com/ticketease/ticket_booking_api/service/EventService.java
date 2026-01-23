package com.ticketease.ticket_booking_api.service;

import com.ticketease.ticket_booking_api.dto.EventDto;
import com.ticketease.ticket_booking_api.dto.VenueDto;
import com.ticketease.ticket_booking_api.entity.Event;
import com.ticketease.ticket_booking_api.entity.Venue;
import com.ticketease.ticket_booking_api.repository.EventRepository;
import com.ticketease.ticket_booking_api.repository.VenueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final VenueRepository venueRepository;
    private final EventRepository eventRepository;

    public EventService(VenueRepository venueRepository,
                        EventRepository eventRepository) {
        this.venueRepository = venueRepository;
        this.eventRepository = eventRepository;
    }

    // --- VENUE LOGIC ---
    public Venue createVenue(VenueDto venueDto) {
        Venue venue = new Venue();
        venue.setName(venueDto.getName());
        venue.setAddress(venueDto.getAddress());
        venue.setCapacity(venueDto.getCapacity());
        return venueRepository.save(venue);
    }

    // --- EVENT LOGIC ---
    public Event createEvent(EventDto eventDto) {

        // Find the Venue by ID
        Venue venue = venueRepository.findById(eventDto.getVenueId())
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        // Map DTO to Entity
        Event event = new Event();
        event.setName(eventDto.getName());
        event.setDescription(eventDto.getDescription());
        event.setEventDate(eventDto.getEventDate());
        event.setPrice(eventDto.getPrice());
        event.setTotalCapacity(venue.getCapacity());

        // Link the Relationship
        event.setVenue(venue);

        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }
}
