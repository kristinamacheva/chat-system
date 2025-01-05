package com.example.backend.mappers;

import com.example.backend.dto.CreateUserDTO;
import com.example.backend.dto.ResponseUserDTO;
import com.example.backend.entities.User;

public class UserMapper {

    public static User toEntity(CreateUserDTO createUserDTO) {
        User user = new User();
        user.setFullName(createUserDTO.getFullName());
        user.setEmail(createUserDTO.getEmail());
        user.setPassword(createUserDTO.getPassword());
        return user;
    }

    public static ResponseUserDTO toResponseDTO(User user) {
        ResponseUserDTO responseUserDTO = new ResponseUserDTO();
        responseUserDTO.setId(user.getId());
        responseUserDTO.setFullName(user.getFullName());
        responseUserDTO.setEmail(user.getEmail());
        return responseUserDTO;
    }
}
