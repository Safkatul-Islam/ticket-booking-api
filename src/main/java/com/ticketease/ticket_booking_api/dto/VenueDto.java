package com.ticketease.ticket_booking_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VenueDto {
    private Long id;  // Optional, useful for responses

    @NotBlank(message = "Venue name is required")
    private String name;

    @NotBlank(message = "Venue address is required")
    private String address;

    private int capacity;
}
