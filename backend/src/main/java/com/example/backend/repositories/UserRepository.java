package com.example.backend.repositories;

import com.example.backend.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);
    Optional<User> findByIdAndIsActive(int id, int isActive);
    Page<User> findByIsActiveAndIdNot(int isActive, int id, Pageable pageable);
    Page<User> findByEmailContainingIgnoreCaseAndIsActiveAndIdNot(String email, int isActive, int id, Pageable pageable);
}
