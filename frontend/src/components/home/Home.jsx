import React, { useState, useEffect, useContext, useRef } from "react";
import * as friendshipService from "../../services/friendshipService";
import * as channelService from "../../services/channelService";
import AuthContext from "../../contexts/authContext";
import Sidebar from "./sidebar/Sidebar";
import { HStack, useToast } from "@chakra-ui/react";
import Chat from "./chat/Chat";

export default function Home() {
    const { id: currentUserId } = useContext(AuthContext);
    const [channels, setChannels] = useState([]);
    const [friends, setFriends] = useState([]);
    const [hasMoreChannels, setHasMoreChannels] = useState(true);
    const [hasMoreFriends, setHasMoreFriends] = useState(true);
    const [isChannelsLoading, setIsChannelsLoading] = useState(false);
    const [isFriendsLoading, setIsFriendsLoading] = useState(false);
    const channelPageRef = useRef(2); // Infinite scroll starts from page 2
    const friendPageRef = useRef(2);  // Infinite scroll starts from page 2
    const [selectedChannel, setSelectedChannel] = useState(null);
    const [selectedFriend, setSelectedFriend] = useState(null);
    const toast = useToast();
    const isFetching = useRef(false); // Prevents duplicate calls

    // Fetches first page of channels and friends (INITIAL LOAD ONLY)
    useEffect(() => {
        if (!currentUserId || isFetching.current) return;

        isFetching.current = true;

        const fetchInitialData = async () => {
            await fetchChannels();
            await fetchFriends();
            isFetching.current = false;

        };

        fetchInitialData();
    }, [currentUserId]);

    const fetchChannels = async () => {
        setIsChannelsLoading(true);
        try {
            const channelsResponse = await channelService.getAll(currentUserId, 1);
            setChannels(channelsResponse.data);
            setHasMoreChannels(channelsResponse.currentPage < channelsResponse.totalPages);
        } catch (error) {
            toast({
                title: "Error.",
                description: error.message || "Error loading channels.",
                status: "error",
                duration: 5000,
                isClosable: true,
            });
        }
        setIsChannelsLoading(false);
    }

    // Loads more channels for infinite scroll
    const fetchMoreChannels = async () => {
        if (!hasMoreChannels || isChannelsLoading) return;

        setIsChannelsLoading(true);
        try {
            const { data, totalPages, currentPage } = await channelService.getAll(
                currentUserId,
                channelPageRef.current
            );

            setChannels((prev) => [...prev, ...data]); // Append new data
            setHasMoreChannels(currentPage < totalPages);
            channelPageRef.current += 1;
        } catch (error) {
            toast({
                title: "Error.",
                description: error.message || "Error loading channels.",
                status: "error",
                duration: 5000,
                isClosable: true,
            });
        }
        setIsChannelsLoading(false);
    };

    const fetchFriends = async() => {
        setIsFriendsLoading(true);
        try {
            const friendsResponse = await friendshipService.getAll(currentUserId, 1);
            setFriends(friendsResponse.data);
            setHasMoreFriends(friendsResponse.currentPage < friendsResponse.totalPages);
        } catch (error) {
            toast({
                title: "Error.",
                description: error.message || "Error loading friends.",
                status: "error",
                duration: 5000,
                isClosable: true,
            });
        }
        setIsFriendsLoading(false);

    }

    // Loads more friends for infinite scroll
    const fetchMoreFriends = async () => {
        if (!hasMoreFriends || isFriendsLoading) return;

        setIsFriendsLoading(true);
        try {
            const { data, totalPages, currentPage } = await friendshipService.getAll(
                currentUserId,
                friendPageRef.current
            );

            setFriends((prev) => [...prev, ...data]); // Append new data
            setHasMoreFriends(currentPage < totalPages);
            friendPageRef.current += 1;
        } catch (error) {
            toast({
                title: "Error.",
                description: error.message || "Error loading friends.",
                status: "error",
                duration: 5000,
                isClosable: true,
            });
        }
        setIsFriendsLoading(false);
    };

    const handleChannelClick = (channel) => {
        setSelectedChannel(channel);
        setSelectedFriend(null);
    };

    const handleFriendClick = (friend) => {
        setSelectedFriend(friend);
        setSelectedChannel(null);
    };

    return (
        <HStack>
            <Sidebar
                channels={channels}
                friends={friends}
                fetchMoreChannels={fetchMoreChannels} // Infinite scroll
                fetchMoreFriends={fetchMoreFriends} // Infinite scroll
                hasMoreChannels={hasMoreChannels}
                hasMoreFriends={hasMoreFriends}
                onChannelClick={handleChannelClick}
                onFriendClick={handleFriendClick}
            />
            {(selectedChannel || selectedFriend) && (
                <Chat selectedChannel={selectedChannel} selectedFriend={selectedFriend} />
            )}
        </HStack>
    );
}
