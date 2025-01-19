package com.example.backend.mappers;

import com.example.backend.dto.ResponseFriendInvitationDTO;
import com.example.backend.entities.FriendInvitation;
import com.example.backend.entities.User;

import java.time.LocalDateTime;

public class FriendInvitationMapper {

    public static FriendInvitation toEntity(User sender, User recipient) {
        FriendInvitation invitation = new FriendInvitation();
        invitation.setSender(sender);
        invitation.setRecipient(recipient);
        invitation.setCreatedAt(LocalDateTime.now());
        return invitation;
    }

    public static ResponseFriendInvitationDTO toResponseDTO(FriendInvitation invitation) {
        ResponseFriendInvitationDTO dto = new ResponseFriendInvitationDTO();
        dto.setId(invitation.getId());
        dto.setUserFullName(invitation.getSender().getFullName());
        dto.setUserEmail(invitation.getSender().getEmail());
        dto.setInvitationDate(invitation.getCreatedAt().toLocalDate());
        return dto;
    }
}
