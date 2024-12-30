package com.example.backend.services;

import com.example.backend.dto.UserDTO;
import com.example.backend.entities.User;
import com.example.backend.errors.UserWithThisEmailAlreadyExistsException;
import com.example.backend.repositories.UserRepository;
import com.example.backend.utils.PasswordUtils;
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

    private UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFullName(user.getFullName());
        userDTO.setEmail(user.getEmail());
        userDTO.setIsActive(user.getIsActive());
        return userDTO;
    }
}
