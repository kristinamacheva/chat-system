package com.example.backend.services;

import com.example.backend.dto.CreateFriendInvitationDTO;
import com.example.backend.entities.FriendInvitation;
import com.example.backend.entities.Friendship;
import com.example.backend.entities.User;
import com.example.backend.exceptions.FriendInvitationAlreadyExistsException;
import com.example.backend.exceptions.FriendInvitationNotFoundException;
import com.example.backend.exceptions.FriendshipAlreadyExistsException;
import com.example.backend.mappers.FriendInvitationMapper;
import com.example.backend.mappers.FriendshipMapper;
import com.example.backend.repositories.FriendInvitationRepository;
import com.example.backend.repositories.FriendshipRepository;
import com.example.backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import static com.example.backend.utils.Status.ACTIVE;
import static com.example.backend.utils.Status.INACTIVE;

@Service
public class FriendInvitationService {

    private final FriendInvitationRepository friendInvitationRepository;
    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;

    public FriendInvitationService(FriendInvitationRepository friendInvitationRepository, UserRepository userRepository, FriendshipRepository friendshipRepository) {
        this.friendInvitationRepository = friendInvitationRepository;
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
    }

    public FriendInvitation createFriendInvitation(CreateFriendInvitationDTO createFriendInvitationDTO) {
        User sender = validateUserExistence(createFriendInvitationDTO.getSenderId());
        User recipient = validateUserExistence(createFriendInvitationDTO.getRecipientId());
        checkActiveFriendship(sender, recipient);
        checkExistingActiveInvitation(sender, recipient);
        FriendInvitation invitation = FriendInvitationMapper.toEntity(sender, recipient);
        return friendInvitationRepository.save(invitation);
    }

    @Transactional
    public Friendship acceptFriendInvitation(int id) {
        FriendInvitation invitation = deleteInvitation(id);
        User sender = invitation.getSender();
        User recipient = invitation.getRecipient();
        Friendship friendship = FriendshipMapper.toEntity(sender, recipient);
        return friendshipRepository.save(friendship);
    }

    public FriendInvitation declineFriendInvitation(int id) {
        return deleteInvitation(id);
    }

    private FriendInvitation deleteInvitation(int id) {
        FriendInvitation invitation = friendInvitationRepository.findByIdAndIsActive(id, ACTIVE)
                .orElseThrow(() -> new FriendInvitationNotFoundException(id));
        invitation.setIsActive(INACTIVE);
        return friendInvitationRepository.save(invitation);
    }

    /**
     * Helper method to validate if user exists
     **/
    private User validateUserExistence(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
    }

    /**
     * Helper method to check if active friendship already exists
     **/
    private void checkActiveFriendship(User sender, User recipient) {
        if (friendshipRepository.existsActiveFriendship(sender.getId(), recipient.getId())) {
            throw new FriendshipAlreadyExistsException();
        }
    }

    /**
     * Helper method to check if an active friend invitation already exists
     **/
    private void checkExistingActiveInvitation(User sender, User recipient) {
        if (friendInvitationRepository.existsBySenderIdAndRecipientIdAndIsActive(sender.getId(), recipient.getId(), ACTIVE)) {
            throw new FriendInvitationAlreadyExistsException();
        }
    }
}
