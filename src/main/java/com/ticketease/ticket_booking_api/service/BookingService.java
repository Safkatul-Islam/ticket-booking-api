package com.ticketease.ticket_booking_api.service;

import com.ticketease.ticket_booking_api.entity.Event;
import com.ticketease.ticket_booking_api.entity.Ticket;
import com.ticketease.ticket_booking_api.entity.User;
import com.ticketease.ticket_booking_api.repository.EventRepository;
import com.ticketease.ticket_booking_api.repository.TicketRepository;
import com.ticketease.ticket_booking_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public BookingService(TicketRepository ticketRepository,
                          UserRepository userRepository,
                          EventRepository eventRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional  // Important: Either everything happens, or nothing happens.
    public Ticket bookTicket(Long eventId, String userEmail) {
        // Find the Event
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Find the User (who is logged in?)
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // CHECK AVAILABILITY
        if (event.getSoldTickets() >= event.getTotalCapacity()) {
            throw new RuntimeException("Event is sold out");
        }

        // Create the Ticket
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setEvent(event);
        ticket.setPurchaseDate(LocalDateTime.now());
        ticket.setSeatNumber("GEN-" + (event.getSoldTickets() + 1));

        // UPDATE STOCK
        event.setSoldTickets(event.getSoldTickets() + 1);
        eventRepository.save(event);

        // Save Ticket
        return ticketRepository.save((ticket));
    }

    public List<Ticket> getAllTicketsForUser(Long userId) {

        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User Not found"));
        } catch (Exception e) {

        }
        return ticketRepository.findByUserId(userId);
    }
}
