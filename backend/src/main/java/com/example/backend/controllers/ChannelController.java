package com.example.backend.controllers;

import com.example.backend.dto.CreateChannelDTO;
import com.example.backend.dto.ResponseChannelDetailsDTO;
import com.example.backend.dto.UpdateChannelDTO;
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

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(name = "userId") int userId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        int pageIndex = page > 0 ? page - 1 : 0;
        var result = channelService.getAll(userId, pageIndex, size);
        return AppResponse.success()
                .withMessage("Channels fetched successfully")
                .withData(result.getContent())
                .withPagination(result)
                .build();
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestParam(name = "userId") int userId,
            @RequestBody @Valid CreateChannelDTO createChannelDTO) {
        var result = channelService.create(createChannelDTO, userId);
        return AppResponse.success()
                .withMessage("Channel created successfully")
                .withData(result)
                .build();
    }

    @GetMapping("/{channelId}")
    public ResponseEntity<?> getChannel(@PathVariable int channelId) {
        var result = channelService.getOne(channelId);
        return AppResponse.success()
                .withMessage("Channel found successfully")
                .withData(result)
                .build();
    }

    @PutMapping("/{channelId}")
    public ResponseEntity<?> update(
            @PathVariable int channelId,
            @RequestParam(name = "userId") int userId,
            @RequestBody @Valid UpdateChannelDTO updateChannelDTO
    ) {
        var result = channelService.update(channelId, updateChannelDTO, userId);
        return AppResponse.success()
                .withMessage("Channel updated successfully")
                .withData(result)
                .build();
    }

    @DeleteMapping("/{channelId}")
    public ResponseEntity<?> delete(
            @PathVariable int channelId,
            @RequestParam(name = "userId") int userId
    ) {
        channelService.delete(channelId, userId);
        return AppResponse.success()
                .withMessage("Channel deleted successfully")
                .build();
    }
}