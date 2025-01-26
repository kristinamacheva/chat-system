package com.example.backend.mappers;

import com.example.backend.dto.*;
import com.example.backend.entities.Channel;
import com.example.backend.entities.Message;
import com.example.backend.entities.User;

import java.time.LocalDateTime;

public class MessageMapper {

    public static Message toEntity(CreateChannelMessageDTO createChannelMessageDTO, User sender, Channel channel) {
        Message message = new Message();
        message.setSender(sender);
        message.setChannel(channel);
        message.setContent(createChannelMessageDTO.getContent());
        message.setCreatedAt(LocalDateTime.now());
        return message;
    }

    public static Message toEntity(CreateFriendMessageDTO createFriendMessageDTO, User sender, User recipient) {
        Message message = new Message();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setContent(createFriendMessageDTO.getContent());
        message.setCreatedAt(LocalDateTime.now());
        return message;
    }

    public static ResponseChannelMessageDTO toResponseChannelMessageDTO(Message message) {
        ResponseChannelMessageDTO responseDTO = new ResponseChannelMessageDTO();
        responseDTO.setId(message.getId());
        responseDTO.setContent(message.getContent());
        responseDTO.setCreatedAt(message.getCreatedAt());
        responseDTO.setSender(UserMapper.toBasicUserDTO(message.getSender()));
        responseDTO.setChannel(ChannelMapper.toBasicChannelDTO(message.getChannel()));
        return responseDTO;
    }

    public static ResponseFriendMessageDTO toResponseFriendMessageDTO(Message message) {
        ResponseFriendMessageDTO responseDTO = new ResponseFriendMessageDTO();
        responseDTO.setId(message.getId());
        responseDTO.setContent(message.getContent());
        responseDTO.setCreatedAt(message.getCreatedAt());
        responseDTO.setSender(UserMapper.toBasicUserDTO(message.getSender()));
        responseDTO.setRecipient(UserMapper.toBasicUserDTO(message.getRecipient()));
        return responseDTO;
    }
}
