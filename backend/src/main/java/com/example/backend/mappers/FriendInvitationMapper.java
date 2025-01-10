package com.example.backend.mappers;

import com.example.backend.dto.CreateFriendInvitationDTO;
import com.example.backend.dto.CreateUserDTO;
import com.example.backend.dto.ResponseUserDTO;
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
}
