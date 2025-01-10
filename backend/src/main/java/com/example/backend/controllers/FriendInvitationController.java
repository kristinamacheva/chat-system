package com.example.backend.controllers;

import com.example.backend.dto.CreateFriendInvitationDTO;
import com.example.backend.http.AppResponse;
import com.example.backend.services.FriendInvitationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friend-invitations")
public class FriendInvitationController {

    private final FriendInvitationService friendInvitationService;

    public FriendInvitationController(FriendInvitationService friendInvitationService) {
        this.friendInvitationService = friendInvitationService;
    }

    @PostMapping
    public ResponseEntity<?> createFriendInvitation(@RequestBody @Valid CreateFriendInvitationDTO createFriendInvitationDTO) {
        friendInvitationService.sendFriendInvitation(createFriendInvitationDTO);
        return AppResponse.success()
                .withMessage("Invitation sent successfully")
                .build();
    }
}
