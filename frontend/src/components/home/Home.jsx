import React, { useState, useEffect, useContext, useRef } from "react";
import * as friendshipService from "../../services/friendshipService";
import * as channelService from "../../services/channelService";
import AuthContext from "../../contexts/authContext";
import Sidebar from "./sidebar/Sidebar";
import { useToast } from "@chakra-ui/react";

export default function Home() {
    const { id: currentUserId } = useContext(AuthContext);
    const [channels, setChannels] = useState([]);
    const [friends, setFriends] = useState([]);
    const [hasMoreChannels, setHasMoreChannels] = useState(true);
    const [hasMoreFriends, setHasMoreFriends] = useState(true);
    const [isChannelsLoading, setIsChannelsLoading] = useState(false);
    const [isFriendsLoading, setIsFriendsLoading] = useState(false);
    const channelPageRef = useRef(1); 
    const friendPageRef = useRef(1); 
    const toast = useToast();

    useEffect(() => {
        fetchChannels();
        fetchFriends();
    }, []);

    const fetchChannels = async () => {
        if (!hasMoreChannels || isChannelsLoading) return;

        setIsChannelsLoading(true);
        try {
            const { data, totalPages, currentPage } =
                await channelService.getAll(currentUserId, channelPageRef.current);

            setChannels((state) => [...state, ...data]);
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
        } finally {
            setIsChannelsLoading(false);
        }
    };

    const fetchFriends = async () => {
        if (!hasMoreFriends || isFriendsLoading) return;

        setIsFriendsLoading(true);
        try {
            const { data, totalPages, currentPage } =
                await friendshipService.getAll(currentUserId, friendPageRef.current);

            setFriends((state) => [...state, ...data]);
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
        } finally {
            setIsFriendsLoading(false);
        }
    };

    return (
        <Sidebar
            channels={channels}
            friends={friends}
            fetchMoreChannels={fetchChannels}
            fetchMoreFriends={fetchFriends}
            hasMoreChannels={hasMoreChannels}
            hasMoreFriends={hasMoreFriends}
        />
    );
}
