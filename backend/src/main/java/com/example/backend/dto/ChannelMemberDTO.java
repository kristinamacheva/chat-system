package com.example.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChannelMemberDTO {

    private Integer id;
    private String fullName;
    private String email;
    private String role;
}
