package com.punnawit.auth.service;

import com.punnawit.auth.dto.request.RegisterRequest;
import com.punnawit.auth.entity.Role;
import com.punnawit.auth.entity.Roles;
import com.punnawit.auth.entity.Users;
import com.punnawit.auth.repository.RoleRepository;
import com.punnawit.auth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ============ REGISTER ============
    public Users register(RegisterRequest request) {

        if (Objects.isNull(request)) {
            throw new IllegalArgumentException("Request is null");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new IllegalArgumentException("Phone already exists");
        }

        Users user = new Users();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        Role userRole = roleRepository.findByRole(Roles.USER)
                .orElseThrow(() -> new IllegalArgumentException("Role USER not found"));
        user.setRole(userRole);


        return userRepository.save(user);

    }


    // ============ LOGIN ============
    public Optional<Users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
