package com.example.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateChannelMemberDTO {

    @NotBlank(message = "Role is required")
    @Pattern(regexp = "^(ADMIN|GUEST)$", message = "Role must be either ADMIN or GUEST")
    private String role;
}
