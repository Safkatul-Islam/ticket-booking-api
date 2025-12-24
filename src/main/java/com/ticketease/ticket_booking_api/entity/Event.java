package com.ticketease.ticket_booking_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    // Use LocalDateTime for precise event timing
    @Column(nullable = false)
    private LocalDateTime eventDate;

    @Column(nullable = false)
    private BigDecimal price;

    //  Relationship Magic Begins Here

    // @ManyToOne: This tells JPA that "Many Events can belong to One Venue".
    // @JoinColumn: This creates a column in the 'events' table called 'venue_id'.
    // This column will store the ID of the venue.
    // nullable = false: An event MUST have a venue.
    @ManyToOne
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;


}
