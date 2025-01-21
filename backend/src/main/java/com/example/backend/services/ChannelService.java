package com.example.backend.services;

import com.example.backend.dto.CreateChannelDTO;
import com.example.backend.dto.ResponseChannelDTO;
import com.example.backend.entities.Channel;
import com.example.backend.entities.ChannelMembership;
import com.example.backend.entities.Role;
import com.example.backend.entities.User;
import com.example.backend.exceptions.RoleNotFoundException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.mappers.ChannelMapper;
import com.example.backend.mappers.ChannelMembershipMapper;
import com.example.backend.repositories.ChannelMembershipRepository;
import com.example.backend.repositories.ChannelRepository;
import com.example.backend.repositories.RoleRepository;
import com.example.backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import static com.example.backend.utils.Status.ACTIVE;

@Service
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final ChannelMembershipRepository channelMembershipRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public ChannelService(ChannelRepository channelRepository,
                          ChannelMembershipRepository channelMembershipRepository,
                          RoleRepository roleRepository,
                          UserRepository userRepository) {
        this.channelRepository = channelRepository;
        this.channelMembershipRepository = channelMembershipRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseChannelDTO create(CreateChannelDTO createChannelDTO, int userId) {
        Channel channel = ChannelMapper.toEntity(createChannelDTO);
        Channel savedChannel = channelRepository.save(channel);
        User user = userRepository.findByIdAndIsActive(userId, ACTIVE)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Role ownerRole = roleRepository.findByName("OWNER")
                .orElseThrow(() -> new RoleNotFoundException("OWNER"));
        ChannelMembership membership = ChannelMembershipMapper.toEntity(savedChannel, user, ownerRole);
        channelMembershipRepository.save(membership);
        return ChannelMapper.toResponseDTO(savedChannel, user);
    }
}
