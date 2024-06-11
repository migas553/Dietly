package com.rungroup.Dietly.services.impl;

import com.rungroup.Dietly.DTO.RegistrationDTO;
import com.rungroup.Dietly.models.Role;
import com.rungroup.Dietly.models.UserEntity;
import com.rungroup.Dietly.repository.RoleRepository;
import com.rungroup.Dietly.repository.UserRepository;
import com.rungroup.Dietly.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository  roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public boolean saveUser(RegistrationDTO registrationDTO) {
        UserEntity user = new UserEntity();
        user.setEmail(registrationDTO.getEmail());
        user.setUsername(registrationDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        Role role = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Collections.singletonList(role));
        UserEntity savedUser = userRepository.save(user);
        return savedUser != null;

    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getCurrentUser() {
        return (User) org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
