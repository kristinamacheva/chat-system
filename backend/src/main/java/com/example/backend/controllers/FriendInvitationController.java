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
    public ResponseEntity<?> createFriendInvitation(@RequestBody @Valid CreateFriendInvitationDTO createFriendInvitationDTO) {
        friendInvitationService.createFriendInvitation(createFriendInvitationDTO);
        return AppResponse.success()
                .withMessage("Invitation sent successfully")
                .build();
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(name = "recipientId") int recipientId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size
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
    public ResponseEntity<?> acceptFriendInvitation(@PathVariable int id) {
        friendInvitationService.acceptFriendInvitation(id);
        return AppResponse.success()
                .withMessage("Invitation accepted successfully")
                .build();
    }

    @PutMapping("/{id}/decline")
    public ResponseEntity<?> declineFriendInvitation(@PathVariable int id) {
        friendInvitationService.declineFriendInvitation(id);
        return AppResponse.success()
                .withMessage("Invitation declined successfully")
                .build();
    }
}
