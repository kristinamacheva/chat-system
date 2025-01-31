package com.example.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ResponseFriendInvitationDTO {

    private Integer id;
    private String userFullName;
    private String userEmail;
    private LocalDate invitationDate;
}
