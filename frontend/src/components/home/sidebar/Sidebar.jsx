import React, { useEffect, useRef } from "react";
import { Box, Flex, VStack, Text, Button, Spinner } from "@chakra-ui/react";
import styles from "./sidebar.module.css";

export default function Sidebar({
    channels,
    friends,
    fetchMoreChannels,
    fetchMoreFriends,
    hasMoreChannels,
    hasMoreFriends,
    isChannelsLoading,
    isFriendsLoading,
    onChannelClick,
    onFriendClick,
}) {
    const channelLoaderRef = useRef(null);
    const friendLoaderRef = useRef(null);

    useEffect(() => {
        const channelObserver = new IntersectionObserver((entries) => {
            if (entries[0].isIntersecting && hasMoreChannels) {
                fetchMoreChannels();
            }
        });
        if (channelLoaderRef.current) {
            channelObserver.observe(channelLoaderRef.current);
        }
        return () => {
            if (channelLoaderRef.current) {
                channelObserver.unobserve(channelLoaderRef.current);
            }
        };
    }, [hasMoreChannels, fetchMoreChannels]);

    useEffect(() => {
        const friendObserver = new IntersectionObserver((entries) => {
            if (entries[0].isIntersecting && hasMoreFriends) {
                fetchMoreFriends();
            }
        });
        if (friendLoaderRef.current) {
            friendObserver.observe(friendLoaderRef.current);
        }
        return () => {
            if (friendLoaderRef.current) {
                friendObserver.unobserve(friendLoaderRef.current);
            }
        };
    }, [hasMoreFriends, fetchMoreFriends]);

    return (
        <Box
            as="aside"
            w="64" 
            bg="themePurple.200"
            color="white"
            h="86vh"
            maxH="86vh"
            p={4}
            className={styles.scroll}
        >
            <Flex direction="column" h="full">
                <VStack
                    align="start"
                    spacing={4}
                    flex="1"
                    overflow="auto"
                    mb={4}
                    maxH="40vh"
                >
                    <Text
                        fontSize="lg"
                        fontWeight="bold"
                        borderBottom="1px solid white"
                        color="themePurple.900"
                    >
                        Channels
                    </Text>
                    {channels.map((channel) => (
                        <Button
                            key={channel.id}
                            w="full"
                            variant="ghost"
                            justifyContent="flex-start"
                            onClick={() => onChannelClick(channel)}
                        >
                            {channel.name}
                        </Button>
                    ))}
                        <Box
                            ref={channelLoaderRef}
                            w="full"
                            textAlign="center"
                            py={2}
                        >
                            {isChannelsLoading && <Spinner />}
                        </Box>
                </VStack>
                <VStack
                    align="start"
                    spacing={4}
                    flex="1"
                    overflow="auto"
                    maxH="40vh"
                >
                    <Text
                        fontSize="lg"
                        fontWeight="bold"
                        borderBottom="1px solid white"
                        color="themePurple.900"
                    >
                        Friends
                    </Text>
                    {friends.map((friend) => (
                        <Button
                            key={friend.id}
                            w="full"
                            variant="ghost"
                            justifyContent="flex-start"
                            onClick={() => onFriendClick(friend)}
                        >
                            {friend.fullName}
                        </Button>
                    ))}
                        <Box
                            ref={friendLoaderRef}
                            w="full"
                            textAlign="center"
                            py={2}
                        >
                            {isFriendsLoading && <Spinner />}
                        </Box>
                </VStack>
            </Flex>
        </Box>
    );
}
