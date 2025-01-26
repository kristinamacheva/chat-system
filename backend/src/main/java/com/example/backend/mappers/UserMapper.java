package com.example.backend.mappers;

import com.example.backend.dto.BasicUserDTO;
import com.example.backend.dto.CreateUserDTO;
import com.example.backend.dto.ResponseUserDTO;
import com.example.backend.dto.UserWithFriendshipStatusDTO;
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

    public static BasicUserDTO toBasicUserDTO(User user) {
        BasicUserDTO basicUserDTO = new BasicUserDTO();
        basicUserDTO.setId(user.getId());
        basicUserDTO.setFullName(user.getFullName());
        return basicUserDTO;
    }

    public static UserWithFriendshipStatusDTO toUserWithFriendshipStatusDTO(User user, boolean isFriend) {
        UserWithFriendshipStatusDTO dto = new UserWithFriendshipStatusDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setFriend(isFriend);
        return dto;
    }
}
