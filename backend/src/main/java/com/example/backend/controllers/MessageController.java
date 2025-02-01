package com.example.backend.controllers;

import com.example.backend.dto.CreateChannelMessageDTO;
import com.example.backend.dto.CreateFriendMessageDTO;
import com.example.backend.http.AppResponse;
import com.example.backend.services.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/friends/{friendId}")
    public ResponseEntity<?> createChannelMessage(
            @PathVariable Integer friendId,
            @RequestParam(name = "userId") Integer senderId,
            @RequestBody @Valid CreateFriendMessageDTO messageDTO) {
        var result = messageService.createFriendMessage(messageDTO, senderId, friendId);
        return AppResponse.success()
                .withMessage("Friend message created successfully")
                .withData(result)
                .build();
    }

    @GetMapping("/friends/{friendId}")
    public ResponseEntity<?> getAllFriendMessages(
            @PathVariable Integer friendId,
            @RequestParam(name = "userId") Integer userId,
            @RequestParam(name = "lastMessageId", required = false) Integer lastMessageId,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        var result = messageService.getAllFriendMessages(userId, friendId, lastMessageId, size);
        return AppResponse.success()
                .withMessage("Friend messages fetched successfully")
                .withData(result)
                .build();
    }

    @PostMapping("/channels/{channelId}")
    public ResponseEntity<?> createChannelMessage(
            @PathVariable Integer channelId,
            @RequestParam(name = "userId") Integer senderId,
            @RequestBody @Valid CreateChannelMessageDTO messageDTO) {
        var result = messageService.createChannelMessage(messageDTO, senderId, channelId);
        return AppResponse.success()
                .withMessage("Channel message created successfully")
                .withData(result)
                .build();
    }

    @GetMapping("/channels/{channelId}")
    public ResponseEntity<?> getAllChannelMessages(
            @PathVariable Integer channelId,
            @RequestParam(name = "userId") Integer userId,
            @RequestParam(name = "lastMessageId", required = false) Integer lastMessageId,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        var result = messageService.getAllChannelMessages(userId, channelId, lastMessageId, size);
        return AppResponse.success()
                .withMessage("Channel messages fetched successfully")
                .withData(result)
                .build();
    }
}
