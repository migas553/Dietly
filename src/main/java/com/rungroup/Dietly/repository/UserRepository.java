package com.rungroup.Dietly.repository;

import com.rungroup.Dietly.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity , Long>{
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);
    UserEntity findFirstByUsername(String username);
    Optional<UserEntity> findById(Long id);


}
