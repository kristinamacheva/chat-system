package com.example.backend.mappers;

import com.example.backend.entities.Friendship;
import com.example.backend.entities.User;

public class FriendshipMapper {

    public static Friendship toEntity(User sender, User recipient) {
        Friendship friendship = new Friendship();
        friendship.setUser1(sender);
        friendship.setUser2(recipient);
        return friendship;
    }
}
