package com.example.backend.services;

import com.example.backend.dto.UserDTO;
import com.example.backend.entities.User;
import com.example.backend.exceptions.UserWithThisEmailAlreadyExistsException;
import com.example.backend.repositories.UserRepository;
import com.example.backend.utils.PasswordUtils;
import com.example.backend.utils.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserWithThisEmailAlreadyExistsException(user.getEmail());
        }
        String encodedPassword = PasswordUtils.hashPassword(user.getPassword());
        user.setPassword(encodedPassword);
        return mapToUserDTO(userRepository.save(user));
    }

    public Page<UserDTO> getAll(String email, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users;
        if (email != null && !email.isBlank()) {
            users = userRepository.findByEmailContainingIgnoreCaseAndIsActive(email, Status.ACTIVE, pageable);
        } else {
            users = userRepository.findByIsActive(Status.ACTIVE, pageable);
        }
        return users.map(this::mapToUserDTO);
    }

    private UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFullName(user.getFullName());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}
