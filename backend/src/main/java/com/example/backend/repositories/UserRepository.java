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

    Optional<User> findByIdAndIsActive(Integer id, Integer isActive);

    Optional<User> findByEmailAndIsActive(String email, Integer isActive);

    Page<User> findByIsActiveAndIdNot(Integer isActive, Integer id, Pageable pageable);

    Page<User> findByEmailContainingIgnoreCaseAndIsActiveAndIdNot(String email, Integer isActive, Integer userId, Pageable pageable);
}
