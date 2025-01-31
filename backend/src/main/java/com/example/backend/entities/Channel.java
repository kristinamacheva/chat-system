package com.example.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Defines a communication channel where users can send messages and have memberships.
 */
@Entity
@Table(name = "td_channels")
@Getter
@Setter
@NoArgsConstructor
public class Channel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 256)
    @NotBlank(message = "Name is required")
    @Size(max = 256, message = "Name must not exceed 256 characters")
    private String name;

    @OneToMany(mappedBy = "channel")
    private List<ChannelMembership> memberships;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;

    @Column(name = "is_active")
    private Integer isActive = 1;
}
