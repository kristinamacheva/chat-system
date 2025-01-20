package com.example.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserWithFriendshipStatusDTO {

    private int id;
    private String fullName;
    private String email;
    private boolean isFriend;
}
