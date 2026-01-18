package com.ticketease.ticket_booking_api.config;

import com.ticketease.ticket_booking_api.entity.Role;
import com.ticketease.ticket_booking_api.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            // Check if ROLE_CUSTOMER exists; if not, create it
            if (roleRepository.findByName("ROLE_CUSTOMER").isEmpty()) {
                roleRepository.save(new Role(null, "ROLE_CUSTOMER"));
            }

            // Check if ROLE_ADMIN exists; if not, create it
            if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
                roleRepository.save(new Role(null, "ROLE_ADMIN"));
            }
        };
    }
}
