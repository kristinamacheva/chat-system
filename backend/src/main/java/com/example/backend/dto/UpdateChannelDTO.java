package com.example.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateChannelDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 256, message = "Name must not exceed 256 characters")
    private String name;
}
