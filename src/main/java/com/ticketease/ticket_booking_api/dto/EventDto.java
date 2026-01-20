package com.ticketease.ticket_booking_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EventDto {
    private Long id;

    @NotBlank(message = "Event name is required")
    private String name;

    @NotBlank(message = "Event description is required")
    private String description;

    // Use specific format for dates: "2026-12-25 18:00:00"
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "Event Date is required")
    @Future(message = "Event date must be in the future")
    private LocalDateTime eventDate;

    @NotNull(message = "Event price is required")
    private BigDecimal price;

    @NotNull(message = "Venue ID is required for events")
    private Long venueId;   // Only send the ID, not the whole Venue object
}
