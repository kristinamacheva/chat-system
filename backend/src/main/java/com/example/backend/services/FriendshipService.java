package com.example.backend.services;

import com.example.backend.dto.ResponseUserDTO;
import com.example.backend.entities.User;
import com.example.backend.mappers.UserMapper;
import com.example.backend.repositories.FriendshipRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;

    public FriendshipService(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    public Page<ResponseUserDTO> getAllFriends(int userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = friendshipRepository.findFriendsByUserId(userId, pageable);
        return users.map(UserMapper::toResponseDTO);
    }
}
