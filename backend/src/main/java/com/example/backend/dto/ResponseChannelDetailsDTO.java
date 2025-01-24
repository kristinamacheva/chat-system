package com.example.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ResponseChannelDetailsDTO {

    private int id;
    private String name;
    private ResponseUserDTO owner;
    private Set<ResponseUserDTO> admins;
}
