package com.ticketease.ticket_booking_api.mapper;

import com.ticketease.ticket_booking_api.dto.TicketResponseDto;
import com.ticketease.ticket_booking_api.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    // Helper method to convert Entity -> DTO
    public TicketResponseDto mapToDto(Ticket ticket) {
        TicketResponseDto dto = new TicketResponseDto();
        dto.setId(ticket.getId());
        dto.setSeatNumber(ticket.getSeatNumber());
        dto.setPurchaseDate(ticket.getPurchaseDate());

        dto.setEventId(ticket.getEvent().getId());
        dto.setEventName(ticket.getEvent().getName());
        dto.setVenueName(ticket.getEvent().getVenue().getName());

        return dto;
    }
}
