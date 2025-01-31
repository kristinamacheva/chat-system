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

    /**
     * Registers a new user by saving them to the database after validating the email.
     *
     * @param createUserDTO The DTO containing the user details for registration.
     * @return ResponseUserDTO containing the user details.
     */
    public ResponseUserDTO register(CreateUserDTO createUserDTO) {
        if (userRepository.existsByEmail(createUserDTO.getEmail())) {
            throw new UserWithThisEmailAlreadyExistsException(createUserDTO.getEmail());
        }
        User user = UserMapper.toEntity(createUserDTO);
        user.setPassword(PasswordUtils.hashPassword(user.getPassword()));
        return UserMapper.toResponseDTO(userRepository.save(user));
    }

    /**
     * Retrieves a paginated list of users, excluding the user with the specified ID and checks if they are friends.
     *
     * @param userId The ID of the requesting user to exclude from the result.
     * @param email Optional filter for users by email.
     * @param page The page number for pagination.
     * @param size The size of each page for pagination.
     * @return A page of users with their friendship status.
     */
    public Page<UserWithFriendshipStatusDTO> getAll(Integer userId, String email, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = (email != null && !email.isBlank())
                ? userRepository.findByEmailContainingIgnoreCaseAndIsActiveAndIdNot(email, ACTIVE, userId, pageable)
                : userRepository.findByIsActiveAndIdNot(ACTIVE, userId, pageable);
        return users.map(user -> {
            boolean isFriend = friendshipRepository.existsActiveFriendship(userId, user.getId());
            return UserMapper.toUserWithFriendshipStatusDTO(user, isFriend);
        });
    }

    /**
     * Retrieves the details of a specific user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return ResponseUserDTO containing the user details.
     */
    public ResponseUserDTO getOne(Integer id) {
        User user = userRepository.findByIdAndIsActive(id, ACTIVE)
                .orElseThrow(UserNotFoundException::new);
        return UserMapper.toResponseDTO(user);
    }
}
