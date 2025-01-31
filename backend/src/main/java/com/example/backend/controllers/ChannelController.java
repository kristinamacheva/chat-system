package com.example.backend.controllers;

import com.example.backend.dto.AddChannelMemberDTO;
import com.example.backend.dto.CreateChannelDTO;
import com.example.backend.dto.UpdateChannelDTO;
import com.example.backend.dto.UpdateChannelMemberDTO;
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
            @RequestParam(name = "userId") Integer userId,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "20") Integer size
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
            @RequestParam(name = "userId") Integer userId,
            @RequestBody @Valid CreateChannelDTO createChannelDTO) {
        var result = channelService.create(createChannelDTO, userId);
        return AppResponse.success()
                .withMessage("Channel created successfully")
                .withData(result)
                .build();
    }

    @GetMapping("/{channelId}")
    public ResponseEntity<?> getChannel(@PathVariable Integer channelId) {
        var result = channelService.getOne(channelId);
        return AppResponse.success()
                .withMessage("Channel found successfully")
                .withData(result)
                .build();
    }

    @PutMapping("/{channelId}")
    public ResponseEntity<?> update(
            @PathVariable Integer channelId,
            @RequestParam(name = "userId") Integer userId,
            @RequestBody @Valid UpdateChannelDTO updateChannelDTO
    ) {
        var result = channelService.update(channelId, updateChannelDTO, userId);
        return AppResponse.success()
                .withMessage("Channel updated successfully")
                .withData(result)
                .build();
    }

    @GetMapping("/{channelId}/members")
    public ResponseEntity<?> getAllMembers(
            @PathVariable Integer channelId,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "20") Integer size
    ) {
        int pageIndex = page > 0 ? page - 1 : 0;
        var result = channelService.getAllMembers(channelId, email, pageIndex, size);
        return AppResponse.success()
                .withMessage("Members found successfully")
                .withData(result.getContent())
                .withPagination(result)
                .build();
    }

    @PostMapping("/{channelId}/members")
    public ResponseEntity<?> addMember(
            @PathVariable Integer channelId,
            @RequestBody @Valid AddChannelMemberDTO addChannelMemberDTO,
            @RequestParam Integer userId
    ) {
        var result = channelService.addMember(channelId, addChannelMemberDTO, userId);
        return AppResponse.success()
                .withMessage("Channel updated successfully")
                .withData(result)
                .build();
    }

    @PutMapping("/{channelId}/members/{memberId}")
    public ResponseEntity<?> updateMember(
            @PathVariable Integer channelId,
            @PathVariable Integer memberId,
            @RequestParam Integer userId,
            @RequestBody @Valid UpdateChannelMemberDTO updateChannelMemberDTO
    ) {
        var result = channelService.updateMember(channelId, memberId, userId, updateChannelMemberDTO);
        return AppResponse.success()
                .withMessage("Member updated successfully")
                .withData(result)
                .build();
    }

    @DeleteMapping("/{channelId}/members/{memberId}")
    public ResponseEntity<?> deleteMember(
            @PathVariable Integer channelId,
            @PathVariable Integer memberId,
            @RequestParam Integer userId
    ) {
        channelService.deleteMember(channelId, memberId, userId);
        return AppResponse.success()
                .withMessage("Member deleted successfully")
                .build();
    }

    @DeleteMapping("/{channelId}")
    public ResponseEntity<?> delete(
            @PathVariable Integer channelId,
            @RequestParam(name = "userId") Integer userId
    ) {
        channelService.delete(channelId, userId);
        return AppResponse.success()
                .withMessage("Channel deleted successfully")
                .build();
    }
}
