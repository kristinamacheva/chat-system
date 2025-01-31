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

    /**
     * Creates a direct message between two friends.
     *
     * @param createFriendMessageDTO the data transfer object containing message details
     * @param senderId the ID of the sender
     * @param recipientId the ID of the recipient
     * @return the created message as a ResponseFriendMessageDTO
     */
    public ResponseFriendMessageDTO createFriendMessage(CreateFriendMessageDTO createFriendMessageDTO, Integer senderId, Integer recipientId) {
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

    /**
     * Retrieves a list of direct messages between a user and a friend.
     *
     * @param userId the ID of the user
     * @param friendId the ID of the friend
     * @param lastMessageId the ID of the last message retrieved (for pagination)
     * @param size the number of messages to retrieve
     * @return a list of messages as ResponseFriendMessageDTO
     */
    public List<ResponseFriendMessageDTO> getAllFriendMessages(Integer userId, Integer friendId, Integer lastMessageId, Integer size) {
        Pageable pageable = PageRequest.of(0, size);
        List<Message> messages =  messageRepository.findAllFriendMessagesWithCursor(userId, friendId, lastMessageId, pageable);
        Collections.reverse(messages);
        return messages.stream()
                .map(MessageMapper::toResponseFriendMessageDTO)
                .toList();
    }

    /**
     * Creates a message within a channel.
     *
     * @param createChannelMessageDTO the data transfer object containing message details
     * @param senderId the ID of the sender
     * @param channelId the ID of the channel
     * @return the created message as a ResponseChannelMessageDTO
     */
    public ResponseChannelMessageDTO createChannelMessage(CreateChannelMessageDTO createChannelMessageDTO, Integer senderId, Integer channelId) {
        User sender = getActiveUserById(senderId);
        Channel senderChannel = getActiveChannelById(channelId);
        if (!channelMembershipRepository.existsByChannelIdAndUserIdAndIsActive(senderChannel.getId(), sender.getId(), ACTIVE)) {
            throw new MembershipNotFoundException(senderChannel.getId(), sender.getId());
        }
        Message message = MessageMapper.toEntity(createChannelMessageDTO, sender, senderChannel);
        return MessageMapper.toResponseChannelMessageDTO(messageRepository.save(message));
    }

    /**
     * Retrieves a list of messages from a channel.
     *
     * @param userId the ID of the user requesting messages
     * @param channelId the ID of the channel
     * @param lastMessageId the ID of the last message retrieved (for pagination)
     * @param size the number of messages to retrieve
     * @return a list of messages as ResponseChannelMessageDTO
     */
    public List<ResponseChannelMessageDTO> getAllChannelMessages(Integer userId, Integer channelId, Integer lastMessageId, Integer size) {
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

    private User getActiveUserById(Integer userId) {
        return userRepository.findByIdAndIsActive(userId, ACTIVE)
                .orElseThrow(UserNotFoundException::new);
    }

    private Channel getActiveChannelById(Integer channelId) {
        return channelRepository.findByIdAndIsActive(channelId, ACTIVE)
                .orElseThrow(() -> new ChannelNotFoundException(channelId));
    }
}
