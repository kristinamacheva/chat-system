package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Tracks pending friend requests between users.
 */
@Entity
@Table(name = "tc_friend_invitations")
@Getter
@Setter
@NoArgsConstructor
public class FriendInvitation {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_active")
    private Integer isActive = 1;
}
