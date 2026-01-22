package com.ticketease.ticket_booking_api.controller;

import com.ticketease.ticket_booking_api.dto.TicketResponseDto;
import com.ticketease.ticket_booking_api.entity.Ticket;
import com.ticketease.ticket_booking_api.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> bookTicket(@RequestBody Map<String, Long> request, Authentication authentication) {
        Long eventId = request.get("eventId");
        // Extract the email securely from the JWT Token
        String userEmail = authentication.getName();

        try {
            // Call the service securely
            TicketResponseDto ticket = bookingService.bookTicket(eventId, userEmail);
            return new ResponseEntity<>(ticket, HttpStatus.CREATED);
        } catch (ObjectOptimisticLockingFailureException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Somebody else just booked the last ticket! Please try again.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<TicketResponseDto>> getMyTickets(Authentication authentication) {

        // Get the email from the Security Context (The JWT Token)
        String userEmail = authentication.getName();

        // Fetch the tickets
        List<TicketResponseDto> tickets = bookingService.getMyTickets(userEmail);

        return ResponseEntity.ok(tickets);
    }
}
