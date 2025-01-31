package com.example.backend.repositories;

import com.example.backend.entities.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("SELECT m FROM Message m " +
            "WHERE ((m.sender.id = :userId AND m.recipient.id = :friendId) " +
            "   OR (m.sender.id = :friendId AND m.recipient.id = :userId)) " +
            "   AND m.isActive = 1 " +
            "   AND (:lastMessageId IS NULL OR m.id < :lastMessageId) " +
            "ORDER BY m.createdAt DESC")
    List<Message> findAllFriendMessagesWithCursor(Integer userId, Integer friendId, Integer lastMessageId, Pageable pageable);

    @Query("SELECT m FROM Message m " +
            "WHERE m.channel.id = :channelId " +
            "   AND m.isActive = 1 " +
            "   AND (:lastMessageId IS NULL OR m.id < :lastMessageId) " +
            "ORDER BY m.createdAt DESC")
    List<Message> findAllChannelMessagesWithCursor(Integer channelId, Integer lastMessageId, Pageable pageable);
}
