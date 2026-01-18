package com.ticketease.ticket_booking_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class DemoController {
    @GetMapping
    public String sayHello() {
        return "Hello from secured endpoint! You are authenticated.";
    }
}
