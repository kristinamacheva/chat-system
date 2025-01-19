package com.example.backend.controllers;

import com.example.backend.http.AppResponse;
import com.example.backend.services.FriendshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friendships")
public class FriendshipController {

    private final FriendshipService friendshipService;

    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(name = "userId") int userId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        int pageIndex = page > 0 ? page - 1 : 0;
        var result = friendshipService.getAllFriends(userId, pageIndex, size);
        return AppResponse.success()
                .withMessage("Friends fetched successfully")
                .withData(result.getContent())
                .withPagination(result)
                .build();
    }
}
