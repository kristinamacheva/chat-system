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

    @PostMapping("/friends/{recipientId}")
    public ResponseEntity<?> createChannelMessage(
            @PathVariable int recipientId,
            @RequestParam(name = "userId") int senderId,
            @RequestBody @Valid CreateFriendMessageDTO messageDTO) {
        var result = messageService.createFriendMessage(messageDTO, senderId, recipientId);
        return AppResponse.success()
                .withMessage("Friend message created successfully")
                .withData(result)
                .build();
    }

    @GetMapping("/friends/{friendId}")
    public ResponseEntity<?> getAllFriendMessages(
            @PathVariable int friendId,
            @RequestParam(name = "userId") int userId,
            @RequestParam(name = "lastMessageId", required = false) Integer lastMessageId,
            @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        var result = messageService.getAllFriendMessages(userId, friendId, lastMessageId, size);
        return AppResponse.success()
                .withMessage("Friend messages fetched successfully")
                .withData(result)
                .build();
    }

    @PostMapping("/channels/{channelId}")
    public ResponseEntity<?> createChannelMessage(
            @PathVariable int channelId,
            @RequestParam(name = "userId") int senderId,
            @RequestBody @Valid CreateChannelMessageDTO messageDTO) {
        var result = messageService.createChannelMessage(messageDTO, senderId, channelId);
        return AppResponse.success()
                .withMessage("Channel message created successfully")
                .withData(result)
                .build();
    }

    @GetMapping("/channels/{channelId}")
    public ResponseEntity<?> getAllChannelMessages(
            @PathVariable int channelId,
            @RequestParam(name = "userId") int userId,
            @RequestParam(name = "lastMessageId", required = false) Integer lastMessageId,
            @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        var result = messageService.getAllChannelMessages(userId, channelId, lastMessageId, size);
        return AppResponse.success()
                .withMessage("Channel messages fetched successfully")
                .withData(result)
                .build();
    }
}
