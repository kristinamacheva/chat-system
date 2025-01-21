package com.example.backend.controllers;

import com.example.backend.dto.CreateChannelDTO;
import com.example.backend.http.AppResponse;
import com.example.backend.services.ChannelService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/channels")
public class ChannelController {

    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @PostMapping
    public ResponseEntity<?> createChannel(
            @RequestParam(name = "ownerId") int ownerId,
            @RequestBody @Valid CreateChannelDTO createChannelDTO) {
        var result = channelService.create(createChannelDTO, ownerId);
        return AppResponse.success()
                .withMessage("Channel created successfully")
                .withData(result)
                .build();
    }
}