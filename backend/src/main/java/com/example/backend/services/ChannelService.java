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

/**
 * Service class for managing channels and their memberships.
 */
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

    /**
     * Creates a new channel and assigns the creator as OWNER.
     *
     * @param createChannelDTO The data for the new channel.
     * @param userId The ID of the user creating the channel.
     * @return The created channel as a DTO.
     */
    @Transactional
    public ResponseChannelDTO create(CreateChannelDTO createChannelDTO, int userId) {
        Channel channel = ChannelMapper.toEntity(createChannelDTO);
        Channel savedChannel = channelRepository.save(channel);
        User user = userRepository.findByIdAndIsActive(userId, ACTIVE)
                .orElseThrow(UserNotFoundException::new);
        Role ownerRole = roleRepository.findByName("OWNER")
                .orElseThrow(() -> new RoleNotFoundException("OWNER"));
        ChannelMembership membership = ChannelMembershipMapper.toEntity(savedChannel, user, ownerRole);
        channelMembershipRepository.save(membership);
        return ChannelMapper.toResponseDTO(savedChannel);
    }

    /**
     * Retrieves all active channels for a user with pagination.
     *
     * @param userId The ID of the user.
     * @param page The page number.
     * @param size The number of items per page.
     * @return A paginated list of channels.
     */
    public Page<ResponseChannelDTO> getAll(int userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Channel> channels = channelRepository.findActiveChannelsForUser(userId, pageable);
        return channels.map(ChannelMapper::toResponseDTO);
    }

    /**
     * Retrieves details of a single channel including its owner and admins.
     *
     * @param channelId The ID of the channel.
     * @return The channel details as a DTO.
     */
    public ResponseChannelDetailsDTO getOne(int channelId) {
        Channel channel = getActiveChannelById(channelId);
        ChannelMembership ownerMembership = channelMembershipRepository.findOwnerByChannelId(channelId)
                .orElseThrow(() -> new OwnerNotFoundException(channelId));
        ResponseUserDTO owner = UserMapper.toResponseDTO(ownerMembership.getUser());
        Set<ChannelMembership> adminsMemberships = channelMembershipRepository.findAdminsByChannelId(channelId);
        Set<ResponseUserDTO> admins = adminsMemberships.stream()
                .map(membership -> UserMapper.toResponseDTO(membership.getUser()))
                .collect(Collectors.toSet());
        return ChannelMapper.toResponseDetailsDTO(channel, owner, admins);
    }

    /**
     * Updates the name of a channel if the user has admin or owner privileges.
     *
     * @param channelId The ID of the channel to update.
     * @param updateChannelDTO The new channel data.
     * @param userId The ID of the user performing the update.
     * @return The updated channel as a DTO.
     */
    public ResponseChannelDTO update(int channelId, UpdateChannelDTO updateChannelDTO, int userId) {
        Channel channel = getActiveChannelById(channelId);
        ChannelMembership membership  = getActiveMembershipByChannelAndUser(channelId, userId);
        if (!(membership.getRole().getName().equals("ADMIN") || membership.getRole().getName().equals("OWNER"))) {
            throw new UnauthorizedAccessException();
        }
        channel.setName(updateChannelDTO.getName());
        return ChannelMapper.toResponseDTO(channelRepository.save(channel));
    }

    /**
     * Adds a new member to the specified channel.
     *
     * @param channelId the ID of the channel
     * @param addChannelMemberDTO the data for the new member
     * @param userId the ID of the requesting user
     * @return the added channel member as a DTO
     */
    public ChannelMemberDTO addMember(int channelId, AddChannelMemberDTO addChannelMemberDTO, int userId) {
        Channel channel = getActiveChannelById(channelId);
        ChannelMembership requesterMembership = getActiveMembershipByChannelAndUser(channelId, userId);
        Role requesterRole = requesterMembership.getRole();
        Role userToAddRole = roleRepository.findByName(addChannelMemberDTO.getRole())
                .orElseThrow(() -> new RoleNotFoundException(addChannelMemberDTO.getRole()));
        if (!requesterRole.getName().equals("OWNER") && !(requesterRole.getName().equals("ADMIN") && userToAddRole.getName().equals("GUEST"))) {
            throw new UnauthorizedAccessException();
        }
        User userToAdd = userRepository.findByEmailAndIsActive(addChannelMemberDTO.getEmail(), ACTIVE)
                .orElseThrow(UserNotFoundException::new);
        boolean isAlreadyMember = channelMembershipRepository.existsByChannelIdAndUserIdAndIsActive(channelId, userToAdd.getId(), ACTIVE);
        if (isAlreadyMember) {
            throw new MembershipAlreadyExistsException();
        }
        ChannelMembership newMembership = ChannelMembershipMapper.toEntity(channel, userToAdd, userToAddRole);
        channelMembershipRepository.save(newMembership);
        return ChannelMapper.toChannelMemberDTO(userToAdd, userToAddRole);
    }

    /**
     * Updates the role of a channel member.
     *
     * @param channelId the ID of the channel
     * @param memberId the ID of the member to update
     * @param userId the ID of the requesting user
     * @param updateChannelMemberDTO the data for updating the member's role
     * @return the updated channel member as a DTO
     */
    public ChannelMemberDTO updateMember(int channelId, int memberId, int userId, UpdateChannelMemberDTO updateChannelMemberDTO) {
        validateOwnerRole(channelId, userId);
        validateOwnerAction(memberId, userId, "change their own role");
        ChannelMembership userMembership = getActiveMembershipByChannelAndUser(channelId, memberId);
        Role userToUpdateRole = roleRepository.findByName(updateChannelMemberDTO.getRole())
                .orElseThrow(() -> new RoleNotFoundException(updateChannelMemberDTO.getRole()));
        userMembership.setRole(userToUpdateRole);
        ChannelMembership updatedMembership = channelMembershipRepository.save(userMembership);
        return ChannelMapper.toChannelMemberDTO(updatedMembership.getUser(), updatedMembership.getRole());
    }

    /**
     * Retrieves all active members of a channel with optional email filtering.
     *
     * @param channelId the ID of the channel
     * @param email an optional email filter
     * @param page the page number for pagination
     * @param size the number of results per page
     * @return a paginated list of channel members as DTOs
     */
    public Page<ChannelMemberDTO> getAllMembers(int channelId, String email, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ChannelMembership> memberships = channelMembershipRepository.findActiveMembershipsByChannelIdAndEmail(channelId, email, pageable);
        return memberships.map(cm -> ChannelMapper.toChannelMemberDTO(cm.getUser(), cm.getRole()));
    }

    /**
     * Removes a member from the channel.
     *
     * @param channelId the ID of the channel
     * @param memberId the ID of the member to remove
     * @param userId the ID of the requesting user
     */
    public void deleteMember(int channelId, int memberId, int userId) {
        validateOwnerRole(channelId, userId);
        validateOwnerAction(memberId, userId, "delete");
        ChannelMembership userMembership = getActiveMembershipByChannelAndUser(channelId, memberId);
        validateGuestRole(userMembership);
        userMembership.setIsActive(INACTIVE);
        channelMembershipRepository.save(userMembership);
    }

    /**
     * Deactivates a channel, marking it as inactive.
     *
     * @param channelId the ID of the channel to delete
     * @param userId the ID of the requesting user
     */
    public void delete(int channelId,  int userId) {
        Channel channel = getActiveChannelById(channelId);
        validateOwnerRole(channelId, userId);
        channel.setIsActive(INACTIVE);
        channelRepository.save(channel);
    }

    private Channel getActiveChannelById(int channelId) {
        return channelRepository.findByIdAndIsActive(channelId, ACTIVE)
                .orElseThrow(() -> new ChannelNotFoundException(channelId));
    }

    private ChannelMembership getActiveMembershipByChannelAndUser(int channelId, int userId) {
        return channelMembershipRepository.findByChannelIdAndUserIdAndIsActive(channelId, userId, ACTIVE)
                .orElseThrow(() -> new MembershipNotFoundException(channelId, userId));
    }

    private void validateOwnerRole(int channelId, int userId) {
        ChannelMembership requesterMembership = getActiveMembershipByChannelAndUser(channelId, userId);
        Role requesterRole = requesterMembership.getRole();
        if (!requesterRole.getName().equals("OWNER")) {
            throw new UnauthorizedAccessException();
        }
    }

    private void validateOwnerAction(int memberId, int userId, String action) {
        if (memberId == userId) {
            throw new InvalidActionException("Owner cannot " + action + " themselves.");
        }
    }

    private void validateGuestRole(ChannelMembership membership) {
        Role memberRole = membership.getRole();
        if (!memberRole.getName().equals("GUEST")) {
            throw new InvalidActionException("Only users with the GUEST role can be deleted.");
        }
    }
}
