import React, { useState, useEffect, useContext, useRef, useCallback } from "react";
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
    const [channelsPageIndex, setChannelsPageIndex] = useState(2);
    const [friendsPageIndex, setFriendsPageIndex] = useState(2);
    const [selectedChannel, setSelectedChannel] = useState(null);
    const [selectedFriend, setSelectedFriend] = useState(null);
    const toast = useToast();
    const isFetching = useRef(false);

    const fetchChannels = useCallback(async () => {
        setIsChannelsLoading(true);
        try {
            const { data, totalPages, currentPage } = await channelService.getAll(currentUserId, 1);
            setChannels(data);
            setHasMoreChannels(currentPage < totalPages);
            setChannelsPageIndex(2);
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
    }, [currentUserId, toast]);

    const fetchMoreChannels = useCallback(async () => {
        if (!hasMoreChannels || isChannelsLoading) return;

        setIsChannelsLoading(true);
        try {
            const { data, totalPages, currentPage } = await channelService.getAll(
                currentUserId,
                channelsPageIndex
            );
            setChannels((state) => [...state, ...data]); 
            setHasMoreChannels(currentPage < totalPages);
            setChannelsPageIndex((prevIndex) => prevIndex + 1);
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
    }, [currentUserId, hasMoreChannels, isChannelsLoading, channelsPageIndex, toast]);

    const fetchFriends = useCallback(async () => {
        setIsFriendsLoading(true);
        try {
            const { data, totalPages, currentPage } = await friendshipService.getAll(currentUserId, 1);
            setFriends(data);
            setHasMoreFriends(currentPage < totalPages);
            setFriendsPageIndex(2);
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
    }, [currentUserId, toast]);

    const fetchMoreFriends = useCallback(async () => {
        if (!hasMoreFriends || isFriendsLoading) return;

        setIsFriendsLoading(true);
        try {
            const { data, totalPages, currentPage } = await friendshipService.getAll(
                currentUserId,
                friendsPageIndex
            );
            setFriends((state) => [...state, ...data]); 
            setHasMoreFriends(currentPage < totalPages);
            setFriendsPageIndex((prevIndex) => prevIndex + 1);
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
    }, [currentUserId, hasMoreFriends, isFriendsLoading, friendsPageIndex, toast]);

    useEffect(() => {
        if (!currentUserId || isFetching.current) return;
        isFetching.current = true;

        const fetchInitialData = async () => {
            await fetchChannels();
            await fetchFriends();
            isFetching.current = false;
        };
        fetchInitialData();
    }, [currentUserId, fetchChannels, fetchFriends]);

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
                fetchMoreChannels={fetchMoreChannels}
                fetchMoreFriends={fetchMoreFriends}
                hasMoreChannels={hasMoreChannels}
                hasMoreFriends={hasMoreFriends}
                isChannelsLoading={isChannelsLoading}
                isFriendsLoading={isFriendsLoading}
                onChannelClick={handleChannelClick}
                onFriendClick={handleFriendClick}
            />
            {(selectedChannel || selectedFriend) && (
                <Chat selectedChannel={selectedChannel} selectedFriend={selectedFriend} />
            )}
        </HStack>
    );
}
