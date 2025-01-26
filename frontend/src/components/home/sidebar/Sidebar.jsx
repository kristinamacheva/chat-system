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
}) {
    const channelLoaderRef = useRef(null);
    const friendLoaderRef = useRef(null);

    useEffect(() => {
        const channelObserver = new IntersectionObserver((entries) => {
            if (entries[0].isIntersecting && hasMoreChannels) {
                fetchMoreChannels();
            }
        });

        const friendObserver = new IntersectionObserver((entries) => {
            if (entries[0].isIntersecting && hasMoreFriends) {
                fetchMoreFriends();
            }
        });

        if (channelLoaderRef.current) {
            channelObserver.observe(channelLoaderRef.current);
        }
        if (friendLoaderRef.current) {
            friendObserver.observe(friendLoaderRef.current);
        }

        return () => {
            if (channelLoaderRef.current) {
                channelObserver.unobserve(channelLoaderRef.current);
            }
            if (friendLoaderRef.current) {
                friendObserver.unobserve(friendLoaderRef.current);
            }
        };
    }, [hasMoreChannels, hasMoreFriends, fetchMoreChannels, fetchMoreFriends]);

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
                        >
                            {channel.name}
                        </Button>
                    ))}
                    {hasMoreChannels && (
                        <Box
                            ref={channelLoaderRef}
                            w="full"
                            textAlign="center"
                            py={2}
                        >
                            <Spinner size="sm" />
                        </Box>
                    )}
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
                        >
                            {friend.fullName}
                        </Button>
                    ))}
                    {hasMoreFriends && (
                        <Box
                            ref={friendLoaderRef}
                            w="full"
                            textAlign="center"
                            py={2}
                        >
                            <Spinner size="sm" />
                        </Box>
                    )}
                </VStack>
            </Flex>
        </Box>
    );
}
