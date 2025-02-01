package com.example.backend.repositories;

import com.example.backend.entities.Channel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    ORDER BY c.id
""")
    Page<Channel> findActiveChannelsForUser(Integer userId, Pageable pageable);

    Optional<Channel> findByIdAndIsActive(Integer id, Integer isActive);
}
