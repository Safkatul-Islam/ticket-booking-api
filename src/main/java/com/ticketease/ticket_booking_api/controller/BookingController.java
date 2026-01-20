package com.ticketease.ticket_booking_api.controller;

import com.ticketease.ticket_booking_api.dto.EventDto;
import com.ticketease.ticket_booking_api.entity.Event;
import com.ticketease.ticket_booking_api.entity.Ticket;
import com.ticketease.ticket_booking_api.entity.User;
import com.ticketease.ticket_booking_api.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    // Expect a JSON body like: { "eventId": 1 }
    // Inject 'Authentication' to get the logged-in user's details automatically
    public ResponseEntity<Ticket> bookTicket(@RequestBody Map<String, Long> request, Authentication authentication) {

        Long eventId = request.get("eventId");

        // Extract the email securely from the JWT Token
        String userEmail = authentication.getName();

        // Call the service securely
        Ticket ticket = bookingService.bookTicket(eventId, userEmail);

        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> getAllTicket() {
        return ResponseEntity.ok(bookingService.getAllTickets());
    }
}
