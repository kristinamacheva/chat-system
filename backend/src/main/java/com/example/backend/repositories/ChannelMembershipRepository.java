package com.example.backend.repositories;

import com.example.backend.entities.ChannelMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelMembershipRepository extends JpaRepository<ChannelMembership, Integer> {
}
