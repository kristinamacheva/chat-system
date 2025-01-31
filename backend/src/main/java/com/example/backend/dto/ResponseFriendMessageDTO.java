package com.example.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ResponseFriendMessageDTO {

    private Integer id;
    private BasicUserDTO sender;
    private String content;
    private LocalDateTime createdAt;
}
