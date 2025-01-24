package com.example.backend.repositories;

import com.example.backend.entities.Channel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Integer> {
    @Query("""
    SELECT DISTINCT c FROM Channel c
    JOIN c.memberships m
    WHERE m.user.id = :userId
      AND c.isActive = 1
      AND m.isActive = 1
""")
    Page<Channel> findActiveChannelsForUser(@Param("userId") int userId, Pageable pageable);
    Optional<Channel> findByIdAndIsActive(int id, int isActive);
}
