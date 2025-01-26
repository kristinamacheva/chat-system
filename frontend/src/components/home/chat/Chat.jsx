import {
    Flex,
    Stack,
    Skeleton,
    SkeletonCircle,
    useToast,
    Button,
    Heading,
    Card,
    HStack,
    Avatar,
} from "@chakra-ui/react";
import { useContext, useEffect, useRef, useState, useCallback } from "react";
import AuthContext from "../../../contexts/authContext.jsx";
import styles from "./chat.module.css";
import MessageInput from "./message-input/MessageInput";

export default function Chat({ selectedChannel, selectedFriend }) {
    const messageEndRef = useRef(null);
    const { id: senderId } = useContext(AuthContext);
    const toast = useToast();
    const isChannelChat = Boolean(selectedChannel);
    return (
        <Flex
            flex="70"
            bg={"gray.200"}
            borderRadius={"md"}
            p={2}
            flexDirection={"column"}
            className={styles.chat}
        >
            <Card background="gray.100" p="2" boxShadow="xs">
                <HStack mx={4} my={2} alignItems="center" flexWrap="wrap">
                    {!isChannelChat && <Avatar size="sm" name={selectedFriend.fullName} />}
                    <Heading as="h3" size="md" color="themePurple.900">
                        {isChannelChat
                            ? selectedChannel.name
                            : selectedFriend.fullName}
                    </Heading>
                </HStack>
            </Card>
            <Flex
                flexDir={"column"}
                gap={4}
                my={4}
                p={2}
                height={"400px"}
                overflowY={"auto"}
            >
                <div ref={messageEndRef}></div>
            </Flex>
            <MessageInput
                selectedChannel={selectedChannel}
                selectedFriend={selectedFriend}
            />
        </Flex>
    );
}
