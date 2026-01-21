package com.ticketease.ticket_booking_api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketResponseDto {
    private Long id;
    private String seatNumber;
    private LocalDateTime purchaseDate;

    // Flattened Event Details (Easier for Frontend)
    private Long eventId;
    private String eventName;
    private String venueName;
}
