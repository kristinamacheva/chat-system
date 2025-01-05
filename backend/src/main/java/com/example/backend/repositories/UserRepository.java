package com.example.backend.repositories;

import com.example.backend.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);
    Optional<User> findByIdAndIsActive(int id, int isActive);
    Page<User> findByIsActive(int isActive, Pageable pageable);
    Page<User> findByEmailContainingIgnoreCaseAndIsActive(String email, int isActive, Pageable pageable);
}
