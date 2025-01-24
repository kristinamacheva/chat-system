package com.example.backend.mappers;

import com.example.backend.dto.*;
import com.example.backend.entities.Channel;
import com.example.backend.entities.Role;
import com.example.backend.entities.User;

import java.util.Set;

public class ChannelMapper {

    public static Channel toEntity(CreateChannelDTO createChannelDTO) {
        Channel channel = new Channel();
        channel.setName(createChannelDTO.getName());
        return channel;
    }

    public static ResponseChannelDTO toResponseDTO(Channel channel) {
        ResponseChannelDTO responseChannelDTO = new ResponseChannelDTO();
        responseChannelDTO.setId(channel.getId());
        responseChannelDTO.setName(channel.getName());
        return responseChannelDTO;
    }

    public static ResponseChannelDetailsDTO toResponseDetailsDTO(Channel channel, ResponseUserDTO owner, Set<ResponseUserDTO> admins) {
        ResponseChannelDetailsDTO responseChannelDetailsDTO = new ResponseChannelDetailsDTO();
        responseChannelDetailsDTO.setId(channel.getId());
        responseChannelDetailsDTO.setName(channel.getName());
        responseChannelDetailsDTO.setOwner(owner);
        responseChannelDetailsDTO.setAdmins(admins);
        return responseChannelDetailsDTO;
    }

    public static ChannelMemberDTO toChannelMemberDTO(User member, Role role) {
        ChannelMemberDTO channelMemberDTO = new ChannelMemberDTO();
        channelMemberDTO.setId(member.getId());
        channelMemberDTO.setEmail(member.getEmail());
        channelMemberDTO.setFullName(member.getFullName());
        channelMemberDTO.setRole(role.getName());
        return channelMemberDTO;
    }
}
