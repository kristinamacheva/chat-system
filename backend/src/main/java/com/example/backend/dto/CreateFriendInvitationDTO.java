package com.example.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CreateFriendInvitationDTO {

    @NotNull(message = "Sender ID is required")
    private int senderId;

    @NotNull(message = "Recipient ID is required")
    private int recipientId;
}
