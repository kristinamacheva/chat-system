package com.example.backend.services;

import com.example.backend.dto.*;
import com.example.backend.entities.Channel;
import com.example.backend.entities.Message;
import com.example.backend.entities.User;
import com.example.backend.exceptions.*;
import com.example.backend.mappers.MessageMapper;
import com.example.backend.repositories.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.example.backend.utils.Status.ACTIVE;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final FriendshipRepository friendshipRepository;
    private final ChannelMembershipRepository channelMembershipRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository, ChannelRepository channelRepository, FriendshipRepository friendshipRepository, ChannelMembershipRepository channelMembershipRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.channelRepository = channelRepository;
        this.friendshipRepository = friendshipRepository;
        this.channelMembershipRepository = channelMembershipRepository;
    }

    public ResponseFriendMessageDTO createFriendMessage(CreateFriendMessageDTO createFriendMessageDTO, int senderId, int recipientId) {
        if (senderId == recipientId) {
            throw new InvalidActionException("You cannot send messages to yourself.");
        }
        User sender = getActiveUserById(senderId);
        User recipient = getActiveUserById(recipientId);
        if (!friendshipRepository.existsActiveFriendship(sender.getId(), recipient.getId())) {
            throw new FriendshipNotFoundException();
        }
        Message message = MessageMapper.toEntity(createFriendMessageDTO, sender, recipient);
        return MessageMapper.toResponseFriendMessageDTO(messageRepository.save(message));
    }

    public List<ResponseFriendMessageDTO> getAllFriendMessages(int userId, int friendId, Integer lastMessageId, int size) {
        Pageable pageable = PageRequest.of(0, size);
        List<Message> messages =  messageRepository.findAllFriendMessagesWithCursor(userId, friendId, lastMessageId, pageable);
        Collections.reverse(messages);
        return messages.stream()
                .map(MessageMapper::toResponseFriendMessageDTO)
                .toList();
    }

    public ResponseChannelMessageDTO createChannelMessage(CreateChannelMessageDTO createChannelMessageDTO, int senderId, int channelId) {
        User sender = getActiveUserById(senderId);
        Channel senderChannel = getActiveChannelById(channelId);
        if (!channelMembershipRepository.existsByChannelIdAndUserIdAndIsActive(senderChannel.getId(), sender.getId(), ACTIVE)) {
            throw new MembershipNotFoundException(senderChannel.getId(), sender.getId());
        }
        Message message = MessageMapper.toEntity(createChannelMessageDTO, sender, senderChannel);
        return MessageMapper.toResponseChannelMessageDTO(messageRepository.save(message));
    }

    public List<ResponseChannelMessageDTO> getAllChannelMessages(int userId, int channelId, Integer lastMessageId, int size) {
        if (!channelMembershipRepository.existsByChannelIdAndUserIdAndIsActive(channelId, userId, ACTIVE)) {
            throw new UnauthorizedAccessException();
        }
        Pageable pageable = PageRequest.of(0, size);
        List<Message> messages =  messageRepository.findAllChannelMessagesWithCursor(channelId, lastMessageId, pageable);
        Collections.reverse(messages);
        return messages.stream()
                .map(MessageMapper::toResponseChannelMessageDTO)
                .toList();
    }

    private User getActiveUserById(int userId) {
        return userRepository.findByIdAndIsActive(userId, ACTIVE)
                .orElseThrow(UserNotFoundException::new);
    }

    private Channel getActiveChannelById(int channelId) {
        return channelRepository.findByIdAndIsActive(channelId, ACTIVE)
                .orElseThrow(() -> new ChannelNotFoundException(channelId));
    }
}
