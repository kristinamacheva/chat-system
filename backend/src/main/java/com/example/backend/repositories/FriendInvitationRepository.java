package com.example.backend.repositories;

import com.example.backend.entities.FriendInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendInvitationRepository extends JpaRepository<FriendInvitation, Integer> {

    boolean existsBySenderIdAndRecipientId(int senderId, int recipientId);
}
