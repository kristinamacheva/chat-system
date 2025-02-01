package com.example.backend.repositories;

import com.example.backend.entities.ChannelMembership;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    Optional<ChannelMembership> findOwnerByChannelId(@Param("channelId") Integer channelId);

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
    Set<ChannelMembership> findAdminsByChannelId(@Param("channelId") Integer channelId);

    @Query("SELECT cm FROM ChannelMembership cm " +
            "JOIN FETCH cm.user u " +
            "JOIN FETCH cm.role r " +
            "WHERE cm.channel.id = :channelId " +
            "AND cm.isActive = 1 " +
            "AND (:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%')))" +
            "ORDER BY u.email ASC"
    )
    Page<ChannelMembership> findActiveMembershipsByChannelIdAndEmail(
            Integer channelId,
            String email,
            Pageable pageable
    );

    Optional<ChannelMembership> findByChannelIdAndUserIdAndIsActive(Integer channelId, Integer userId, Integer isActive);

    boolean existsByChannelIdAndUserIdAndIsActive(Integer channelId, Integer userId, Integer isActive);
}
