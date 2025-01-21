package com.example.backend.mappers;

import com.example.backend.entities.Channel;
import com.example.backend.entities.ChannelMembership;
import com.example.backend.entities.Role;
import com.example.backend.entities.User;

public class ChannelMembershipMapper {

    public static ChannelMembership toEntity(Channel channel, User user, Role role) {
        ChannelMembership membership = new ChannelMembership();
        membership.setChannel(channel);
        membership.setUser(user);
        membership.setRole(role);
        return membership;
    }
}
