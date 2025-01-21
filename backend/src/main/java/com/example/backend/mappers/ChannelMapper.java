package com.example.backend.mappers;

import com.example.backend.dto.*;
import com.example.backend.entities.Channel;
import com.example.backend.entities.User;

public class ChannelMapper {

    public static Channel toEntity(CreateChannelDTO createChannelDTO) {
        Channel channel = new Channel();
        channel.setName(createChannelDTO.getName());
        return channel;
    }

    public static ResponseChannelDTO toResponseDTO(Channel channel, User owner) {
        ResponseChannelDTO responseChannelDTO = new ResponseChannelDTO();
        responseChannelDTO.setId(channel.getId());
        responseChannelDTO.setName(channel.getName());
        responseChannelDTO.setOwnerId(owner.getId());
        return responseChannelDTO;
    }
}
