package com.example.backend.services;

import com.example.backend.dto.CreateFriendInvitationDTO;
import com.example.backend.entities.FriendInvitation;
import com.example.backend.entities.User;
import com.example.backend.exceptions.FriendInvitationAlreadyExistsException;
import com.example.backend.mappers.FriendInvitationMapper;
import com.example.backend.mappers.UserMapper;
import com.example.backend.repositories.FriendInvitationRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.utils.PasswordUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FriendInvitationService {

    private final FriendInvitationRepository friendInvitationRepository;
    private final UserRepository userRepository;

    public FriendInvitationService(FriendInvitationRepository friendInvitationRepository, UserRepository userRepository) {
        this.friendInvitationRepository = friendInvitationRepository;
        this.userRepository = userRepository;
    }

    public FriendInvitation sendFriendInvitation(CreateFriendInvitationDTO createFriendInvitationDTO) {
        User sender = userRepository.findById(createFriendInvitationDTO.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        User recipient = userRepository.findById(createFriendInvitationDTO.getRecipientId())
                .orElseThrow(() -> new IllegalArgumentException("Recipient not found"));
        if (friendInvitationRepository.existsBySenderIdAndRecipientId(
                createFriendInvitationDTO.getSenderId(),
                createFriendInvitationDTO.getRecipientId())) {
            throw new FriendInvitationAlreadyExistsException();
        }
        FriendInvitation invitation = FriendInvitationMapper.toEntity(sender, recipient);
        return friendInvitationRepository.save(invitation);
    }
}
