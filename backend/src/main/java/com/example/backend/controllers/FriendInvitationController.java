package com.example.backend.controllers;

import com.example.backend.dto.CreateFriendInvitationDTO;
import com.example.backend.http.AppResponse;
import com.example.backend.services.FriendInvitationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friend-invitations")
public class FriendInvitationController {

    private final FriendInvitationService friendInvitationService;

    public FriendInvitationController(FriendInvitationService friendInvitationService) {
        this.friendInvitationService = friendInvitationService;
    }

    @PostMapping
    public ResponseEntity<?> createFriendInvitation(
            @RequestParam(name = "userId") Integer senderId,
            @RequestBody @Valid CreateFriendInvitationDTO createFriendInvitationDTO
    ) {
        friendInvitationService.createFriendInvitation(senderId, createFriendInvitationDTO);
        return AppResponse.success()
                .withMessage("Invitation sent successfully")
                .build();
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(name = "userId") Integer recipientId,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "20") Integer size
    ) {
        int pageIndex = page > 0 ? page - 1 : 0;
        var result = friendInvitationService.getAll(recipientId, pageIndex, size);
        return AppResponse.success()
                .withMessage("Invitations fetched successfully")
                .withData(result.getContent())
                .withPagination(result)
                .build();
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<?> acceptFriendInvitation(
            @PathVariable Integer id,
            @RequestParam(name = "userId") Integer userId
    ) {
        friendInvitationService.acceptFriendInvitation(userId, id);
        return AppResponse.success()
                .withMessage("Invitation accepted successfully")
                .build();
    }

    @PutMapping("/{id}/decline")
    public ResponseEntity<?> declineFriendInvitation(
            @PathVariable Integer id,
            @RequestParam(name = "userId") Integer userId
    ) {
        friendInvitationService.declineFriendInvitation(userId, id);
        return AppResponse.success()
                .withMessage("Invitation declined successfully")
                .build();
    }
}
