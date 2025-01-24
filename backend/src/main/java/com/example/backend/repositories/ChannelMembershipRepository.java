package com.example.backend.repositories;

import com.example.backend.entities.ChannelMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ChannelMembershipRepository extends JpaRepository<ChannelMembership, Integer> {
    @Query("""
       SELECT m FROM ChannelMembership m
       JOIN m.user u
       JOIN m.channel c
       JOIN m.role r
       WHERE c.id = :channelId
         AND c.isActive = 1
         AND m.isActive = 1
         AND r.name = 'OWNER'
       """)
    Optional<ChannelMembership> findOwnerByChannelId(@Param("channelId") int channelId);

    @Query("""
       SELECT m FROM ChannelMembership m
       JOIN m.user u
       JOIN m.channel c
       JOIN m.role r
       WHERE c.id = :channelId
         AND c.isActive = 1
         AND m.isActive = 1
         AND r.name = 'ADMIN'
       """)
    Set<ChannelMembership> findAdminsByChannelId(@Param("channelId") int channelId);
    Optional<ChannelMembership> findByChannelIdAndUserIdAndIsActive(int channelId, int userId, int isActive);
}
