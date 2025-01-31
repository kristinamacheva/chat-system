package com.example.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Represents a registered user with authentication credentials and relationships such as friendships
 * and channel memberships.
 */
@Entity
@Table(name = "td_users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_name", nullable = false, length = 256)
    @NotBlank(message = "Full name is required")
    @Size(max = 256, message = "Full name must not exceed 256 characters")
    private String fullName;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 150, message = "Email must not exceed 150 characters")
    private String email;

    @Column(name = "password", nullable = false, length = 256)
    @NotBlank(message = "Password is required")
    private String password;

    @OneToMany(mappedBy = "recipient")
    private List<FriendInvitation> receivedFriendInvitations;

    @OneToMany(mappedBy = "user")
    private List<ChannelMembership> memberships;

    @OneToMany(mappedBy = "user1")
    private List<Friendship> friendshipsAsUser1;

    @OneToMany(mappedBy = "user2")
    private List<Friendship> friendshipsAsUser2;

    @Column(name = "is_active")
    private Integer isActive = 1;
}
