package com.example.backend.repositories;

import com.example.backend.entities.FriendInvitation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendInvitationRepository extends JpaRepository<FriendInvitation, Integer> {

    boolean existsBySenderIdAndRecipientIdAndIsActive(int senderId, int recipientId, int isActive);
    Optional<FriendInvitation> findByIdAndIsActive(int id, int isActive);
    Page<FriendInvitation> findByRecipientIdAndIsActive(int recipientId, int isActive, Pageable pageable);
}
