package com.ticketease.ticket_booking_api.service;

import com.ticketease.ticket_booking_api.dto.RegisterRequest;
import com.ticketease.ticket_booking_api.entity.Role;
import com.ticketease.ticket_booking_api.entity.User;
import com.ticketease.ticket_booking_api.repository.RoleRepository;
import com.ticketease.ticket_booking_api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(RegisterRequest request) {
        // Check if user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }

        // Create the User entity
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // ENCRYPT the password!
        // We use the BCrypt bean we defined in SecurityConfig
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // 4. Assign the default Role (CUSTOMER)
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_CUSTOMER")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        user.setRoles(roles);

        // 5. Save to DB
        userRepository.save(user);

        return "User registered successfully!";
    }
}
