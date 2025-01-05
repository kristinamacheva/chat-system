package com.example.backend.services;

import com.example.backend.dto.CreateUserDTO;
import com.example.backend.dto.ResponseUserDTO;
import com.example.backend.entities.User;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.exceptions.UserWithThisEmailAlreadyExistsException;
import com.example.backend.mappers.UserMapper;
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

    public ResponseUserDTO register(CreateUserDTO createUserDTO) {
        if (userRepository.existsByEmail(createUserDTO.getEmail())) {
            throw new UserWithThisEmailAlreadyExistsException(createUserDTO.getEmail());
        }
        User user = UserMapper.toEntity(createUserDTO);
        user.setPassword(PasswordUtils.hashPassword(user.getPassword()));
        return UserMapper.toResponseDTO(userRepository.save(user));
    }

    public Page<ResponseUserDTO> getAll(String email, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = (email != null && !email.isBlank())
                ? userRepository.findByEmailContainingIgnoreCaseAndIsActive(email, Status.ACTIVE, pageable)
                : userRepository.findByIsActive(Status.ACTIVE, pageable);
        return users.map(UserMapper::toResponseDTO);
    }

    public ResponseUserDTO getOne(int id) {
        User user = userRepository.findByIdAndIsActive(id, Status.ACTIVE)
                .orElseThrow(() -> new UserNotFoundException(id));
        return UserMapper.toResponseDTO(user);
    }
}
