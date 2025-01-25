package com.example.backend.services;

import com.example.backend.dto.CreateUserDTO;
import com.example.backend.dto.ResponseUserDTO;
import com.example.backend.dto.UserWithFriendshipStatusDTO;
import com.example.backend.entities.User;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.exceptions.UserWithThisEmailAlreadyExistsException;
import com.example.backend.mappers.UserMapper;
import com.example.backend.repositories.FriendshipRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.utils.PasswordUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.backend.utils.Status.ACTIVE;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;

    public UserService(UserRepository userRepository, FriendshipRepository friendshipRepository) {
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
    }

    public ResponseUserDTO register(CreateUserDTO createUserDTO) {
        if (userRepository.existsByEmail(createUserDTO.getEmail())) {
            throw new UserWithThisEmailAlreadyExistsException(createUserDTO.getEmail());
        }
        User user = UserMapper.toEntity(createUserDTO);
        user.setPassword(PasswordUtils.hashPassword(user.getPassword()));
        return UserMapper.toResponseDTO(userRepository.save(user));
    }

    public Page<UserWithFriendshipStatusDTO> getAll(int id, String email, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = (email != null && !email.isBlank())
                ? userRepository.findByEmailContainingIgnoreCaseAndIsActiveAndIdNot(email, ACTIVE, id, pageable)
                : userRepository.findByIsActiveAndIdNot(ACTIVE, id, pageable);
        return users.map(user -> {
            boolean isFriend = friendshipRepository.existsActiveFriendship(id, user.getId());
            return UserMapper.toUserWithFriendshipStatusDTO(user, isFriend);
        });
    }

    public ResponseUserDTO getOne(int id) {
        User user = userRepository.findByIdAndIsActive(id, ACTIVE)
                .orElseThrow(UserNotFoundException::new);
        return UserMapper.toResponseDTO(user);
    }
}
