package com.ticketease.ticket_booking_api.service;

import com.ticketease.ticket_booking_api.dto.TicketResponseDto;
import com.ticketease.ticket_booking_api.entity.Event;
import com.ticketease.ticket_booking_api.entity.Ticket;
import com.ticketease.ticket_booking_api.entity.User;
import com.ticketease.ticket_booking_api.mapper.TicketMapper;
import com.ticketease.ticket_booking_api.repository.EventRepository;
import com.ticketease.ticket_booking_api.repository.TicketRepository;
import com.ticketease.ticket_booking_api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // Using Mockito
class BookingServiceTest {

    // The Fakes (Mocks)
    // Create fake versions of everything the Service needs.
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private TicketMapper ticketMapper;

    // The Real Object (Subject Under Test)
    // Mockito creates a real instance of BookingService and injects the mocks above into it.
    @InjectMocks
    private BookingService bookingService;

    @Test
    void testBookTicket_Success() {
        // --- ARRANGE (Prepare the data) ---
        Long eventId = 1L;
        String userEmail = "john@example.com";

        // Create a dummy Event (Capacity 100, Sold 0 - So it's available)
        Event mockEvent = new Event();
        mockEvent.setId(eventId);
        mockEvent.setTotalCapacity(100);
        mockEvent.setSoldTickets(0);

        // Create a dummy User
        User mockUser = new User();
        mockUser.setEmail(userEmail);

        // Create the Ticket that 'save' would return
        Ticket mockTicket = new Ticket();
        mockTicket.setId(10L);

        // Create the DTO mapper would return
        TicketResponseDto mockDto = new TicketResponseDto();
        mockDto.setId(10L);


        // PROGRAM THE MOCKS (The Stubbing)
        // "When the service asks for event #1, give it mockEvent"
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(mockEvent));

        // "When the service asks for this email, give it mockUser"
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(mockUser));

        // "When save is called with ANY ticket, return mockTicket"
        when(ticketRepository.save(any(Ticket.class))).thenReturn(mockTicket);

        // "When mapper is called, return the DTO"
        when(ticketMapper.mapToDto(mockTicket)).thenReturn(mockDto);

        // --- ACT (Run the logic) ---
        TicketResponseDto result = bookingService.bookTicket(eventId, userEmail);

        // --- ASSERT (Verify the results) ---
        assertNotNull(result);
        assertEquals(10L, result.getId());

        // VERIFY INTERACTIONS
        // Did the service actually try to save the ticket?
        verify(ticketRepository).save(any(Ticket.class));

        // Did the service actually update the sold count?
        // Note: In the test, the event object is in memory, so soldTickets should be 1 now.
        assertEquals(1, mockEvent.getSoldTickets());
    }

    @Test
    void testBookTicket_SoldOut_ThrowsException() {
        // --- ARRANGE ---
        Long eventId = 1L;
        String userEmail = "john@example.com";

        // Create a FULL Event (Capacity 100, Sold 100)
        Event fullEvent = new Event();
        fullEvent.setId(eventId);
        fullEvent.setTotalCapacity(100);
        fullEvent.setSoldTickets(100);  // FULL!

        User mockUser = new User();
        mockUser.setEmail(userEmail);

        // Stub the repo
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(fullEvent));

        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(mockUser));

        // --- ACT & ASSERT ---
        // We expect a RuntimeException. If no exception is thrown, the test fails.
        Exception exception = assertThrows(RuntimeException.class, () -> {
            bookingService.bookTicket(eventId, userEmail);
        });

        // Verify the message is correct
        assertEquals("Event is sold out", exception.getMessage());

        // CRITICAL: Ensure we NEVER saved a ticket
        verify(ticketRepository, never()).save(any(Ticket.class));
    }
}