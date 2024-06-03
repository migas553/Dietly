package com.rungroup.Dietly.services;

import com.rungroup.Dietly.DTO.RegistrationDTO;
import com.rungroup.Dietly.models.UserEntity;

public interface UserService {
    void saveUser(RegistrationDTO registrationDTO);
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);
}
