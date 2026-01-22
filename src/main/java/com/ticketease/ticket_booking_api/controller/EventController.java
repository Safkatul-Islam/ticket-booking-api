package com.ticketease.ticket_booking_api.controller;

import com.ticketease.ticket_booking_api.dto.EventDto;
import com.ticketease.ticket_booking_api.dto.VenueDto;
import com.ticketease.ticket_booking_api.entity.Event;
import com.ticketease.ticket_booking_api.entity.Venue;
import com.ticketease.ticket_booking_api.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/venues")
    public ResponseEntity<Venue> createVenue(@RequestBody @Valid VenueDto venueDto) {
        Venue newVenue = eventService.createVenue(venueDto);
        return new ResponseEntity<>(newVenue, HttpStatus.CREATED);
    }

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @PostMapping("/events")
    public ResponseEntity<Event> createEvent(@RequestBody @Valid EventDto eventDto) {
        Event newEvent = eventService.createEvent(eventDto);
        return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
    }

    @GetMapping("/venues")
    public ResponseEntity<List<Venue>> getAllVenues() {
        return ResponseEntity.ok(eventService.getAllVenues());
    }
}
