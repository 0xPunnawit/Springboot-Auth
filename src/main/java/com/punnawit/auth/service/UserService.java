package com.punnawit.auth.service;

import com.punnawit.auth.dto.request.auth.RegisterRequest;
import com.punnawit.auth.dto.request.profile.UpdateProfileRequest;
import com.punnawit.auth.entity.Role;
import com.punnawit.auth.entity.Roles;
import com.punnawit.auth.entity.Users;
import com.punnawit.auth.exception.BaseException;
import com.punnawit.auth.exception.UserException;
import com.punnawit.auth.repository.RoleRepository;
import com.punnawit.auth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public Users register(RegisterRequest request) throws BaseException {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw UserException.emailExist();
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            throw UserException.phoneExist();
        }

        Role userRole = roleRepository.findByRole(Roles.USER)
                .orElseThrow(() -> UserException.roleNotFound());

        Users user = new Users();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setRole(userRole);

        return userRepository.save(user);

    }

    // ============ LOGIN ============
    public Optional<Users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // ============ UPDATE PROFILE ============
    public Users updateProfile(String userId, UpdateProfileRequest request) throws BaseException {
        Optional<Users> byId = userRepository.findById(userId);
        
        if (byId.isEmpty()) {
            throw UserException.notFound();
        }

        Users user = byId.get();
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());

        return userRepository.save(user);
    }





    // ****************** START ******************
    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public Optional<Users> findById(String id) {
        return userRepository.findById(id);
    }

    public void save(Users user) {
        userRepository.save(user);
    }
    // ****************** END ******************
}
