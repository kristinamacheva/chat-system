package com.example.backend.services;

import com.example.backend.dto.*;
import com.example.backend.entities.Channel;
import com.example.backend.entities.ChannelMembership;
import com.example.backend.entities.Role;
import com.example.backend.entities.User;
import com.example.backend.exceptions.*;
import com.example.backend.mappers.ChannelMapper;
import com.example.backend.mappers.ChannelMembershipMapper;
import com.example.backend.mappers.UserMapper;
import com.example.backend.repositories.ChannelMembershipRepository;
import com.example.backend.repositories.ChannelRepository;
import com.example.backend.repositories.RoleRepository;
import com.example.backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.backend.utils.Status.ACTIVE;
import static com.example.backend.utils.Status.INACTIVE;

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
        return ChannelMapper.toResponseDTO(savedChannel);
    }

    public Page<ResponseChannelDTO> getAll(int userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Channel> channels = channelRepository.findActiveChannelsForUser(userId, pageable);
        return channels.map(ChannelMapper::toResponseDTO);
    }

    public ResponseChannelDetailsDTO getOne(int channelId) {
        Channel channel = channelRepository.findByIdAndIsActive(channelId, ACTIVE)
                .orElseThrow(() -> new ChannelNotFoundException(channelId));
        ChannelMembership ownerMembership = channelMembershipRepository.findOwnerByChannelId(channelId)
                .orElseThrow(() -> new OwnerNotFoundException(channelId));
        ResponseUserDTO owner = UserMapper.toResponseDTO(ownerMembership.getUser());
        Set<ChannelMembership> adminsMemberships = channelMembershipRepository.findAdminsByChannelId(channelId);
        Set<ResponseUserDTO> admins = adminsMemberships.stream()
                .map(membership -> UserMapper.toResponseDTO(membership.getUser()))
                .collect(Collectors.toSet());
        return ChannelMapper.toResponseDetailsDTO(channel, owner, admins);
    }

    public ResponseChannelDTO update(int channelId, UpdateChannelDTO updateChannelDTO, int userId) {
        Channel channel = channelRepository.findByIdAndIsActive(channelId, ACTIVE)
                .orElseThrow(() -> new ChannelNotFoundException(channelId));
        ChannelMembership membership = channelMembershipRepository.findByChannelIdAndUserIdAndIsActive(channelId, userId, ACTIVE)
                .orElseThrow(() -> new MembershipNotFoundException(channelId, userId));
        if (!(membership.getRole().getName().equals("ADMIN") || membership.getRole().getName().equals("OWNER"))) {
            throw new UnauthorizedAccessException();
        }
        channel.setName(updateChannelDTO.getName());
        return ChannelMapper.toResponseDTO(channelRepository.save(channel));
    }

    public void delete(int channelId,  int userId) {
        Channel channel = channelRepository.findByIdAndIsActive(channelId, ACTIVE)
                .orElseThrow(() -> new ChannelNotFoundException(channelId));
        ChannelMembership membership = channelMembershipRepository.findByChannelIdAndUserIdAndIsActive(channelId, userId, ACTIVE)
                .orElseThrow(() -> new MembershipNotFoundException(channelId, userId));
        if (!membership.getRole().getName().equals("OWNER")) {
            throw new UnauthorizedAccessException();
        }
        channel.setIsActive(INACTIVE);
        channelRepository.save(channel);
    }
}
