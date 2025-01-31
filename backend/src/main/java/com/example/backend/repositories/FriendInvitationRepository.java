package com.example.backend.repositories;

import com.example.backend.entities.FriendInvitation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendInvitationRepository extends JpaRepository<FriendInvitation, Integer> {

    boolean existsBySenderIdAndRecipientIdAndIsActive(Integer senderId, Integer recipientId, Integer isActive);

    Optional<FriendInvitation> findByIdAndIsActive(Integer id, Integer isActive);

    Page<FriendInvitation> findByRecipientIdAndIsActive(Integer recipientId, Integer isActive, Pageable pageable);
}
