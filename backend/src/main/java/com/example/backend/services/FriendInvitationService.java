package com.example.backend.services;

import com.example.backend.dto.CreateFriendInvitationDTO;
import com.example.backend.dto.ResponseFriendInvitationDTO;
import com.example.backend.entities.FriendInvitation;
import com.example.backend.entities.Friendship;
import com.example.backend.entities.User;
import com.example.backend.exceptions.FriendInvitationAlreadyExistsException;
import com.example.backend.exceptions.FriendInvitationNotFoundException;
import com.example.backend.exceptions.FriendshipAlreadyExistsException;
import com.example.backend.exceptions.InvalidActionException;
import com.example.backend.mappers.FriendInvitationMapper;
import com.example.backend.mappers.FriendshipMapper;
import com.example.backend.repositories.FriendInvitationRepository;
import com.example.backend.repositories.FriendshipRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.utils.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    /**
     * Creates a new friend invitation.
     *
     * @param createFriendInvitationDTO the data for creating the invitation
     * @return the created friend invitation
     */
    public FriendInvitation createFriendInvitation(CreateFriendInvitationDTO createFriendInvitationDTO) {
        if (createFriendInvitationDTO.getSenderId() == createFriendInvitationDTO.getRecipientId()) {
            throw new InvalidActionException("You cannot send an invitation to yourself.");
        }
        User sender = validateUserExistence(createFriendInvitationDTO.getSenderId());
        User recipient = validateUserExistence(createFriendInvitationDTO.getRecipientId());
        checkActiveFriendship(sender, recipient);
        checkExistingActiveInvitation(sender, recipient);
        FriendInvitation invitation = FriendInvitationMapper.toEntity(sender, recipient);
        return friendInvitationRepository.save(invitation);
    }

    /**
     * Retrieves all active friend invitations for a recipient.
     *
     * @param recipientId the ID of the recipient
     * @param page the page number for pagination
     * @param size the number of results per page
     * @return a paginated list of friend invitations
     */
    public Page<ResponseFriendInvitationDTO> getAll(Integer recipientId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FriendInvitation> invitations =  friendInvitationRepository.findByRecipientIdAndIsActive(recipientId, Status.ACTIVE, pageable);
        return invitations.map(FriendInvitationMapper::toResponseDTO);
    }

    /**
     * Accepts a friend invitation, creating a friendship and removing the invitation.
     *
     * @param id the ID of the invitation
     * @return the created friendship
     */
    @Transactional
    public Friendship acceptFriendInvitation(Integer id) {
        FriendInvitation invitation = deleteInvitation(id);
        User sender = invitation.getSender();
        User recipient = invitation.getRecipient();
        Friendship friendship = FriendshipMapper.toEntity(sender, recipient);
        return friendshipRepository.save(friendship);
    }

    /**
     * Declines a friend invitation, marking it as inactive.
     *
     * @param id the ID of the invitation
     * @return the updated friend invitation marked as inactive
     */
    public FriendInvitation declineFriendInvitation(Integer id) {
        return deleteInvitation(id);
    }

    /**
     * Marks a friend invitation as inactive.
     *
     * @param id the ID of the invitation
     * @return the updated friend invitation marked as inactive
     */
    private FriendInvitation deleteInvitation(Integer id) {
        FriendInvitation invitation = friendInvitationRepository.findByIdAndIsActive(id, ACTIVE)
                .orElseThrow(() -> new FriendInvitationNotFoundException(id));
        invitation.setIsActive(INACTIVE);
        return friendInvitationRepository.save(invitation);
    }

    private User validateUserExistence(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
    }

    private void checkActiveFriendship(User sender, User recipient) {
        if (friendshipRepository.existsActiveFriendship(sender.getId(), recipient.getId())) {
            throw new FriendshipAlreadyExistsException();
        }
    }

    private void checkExistingActiveInvitation(User sender, User recipient) {
        if (friendInvitationRepository.existsBySenderIdAndRecipientIdAndIsActive(sender.getId(), recipient.getId(), ACTIVE)) {
            throw new FriendInvitationAlreadyExistsException();
        }
    }
}
