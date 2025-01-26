import {
    Flex,
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
import Message from "./message/Message";
import * as messageService from "../../../services/messageService";

export default function Chat({ selectedChannel, selectedFriend }) {
    const [loadingMessages, setLoadingMessages] = useState(true);
    const [messages, setMessages] = useState([]);
    const [isFetchingOlderMessages, setIsFetchingOlderMessages] =
        useState(false);
    const [hasMoreMessages, setHasMoreMessages] = useState(true);
    const messageEndRef = useRef(null);
    const { id: userId } = useContext(AuthContext);
    const toast = useToast();
    const isChannelChat = Boolean(selectedChannel);

    useEffect(() => {
        setLoadingMessages(true);
        const fetchMessages = isChannelChat
            ? messageService.getAllChannelMessages(userId, selectedChannel.id)
            : messageService.getAllFriendMessages(userId, selectedFriend.id);

        fetchMessages
            .then((result) => {
                setMessages(result.data);
                setLoadingMessages(false);
                setHasMoreMessages(result.data.length > 0);
            })
            .catch((error) => {
                toast({
                    title: error.message || "Could not load messages",
                    status: "error",
                    duration: 6000,
                    isClosable: true,
                    position: "bottom",
                });
            });
        setLoadingMessages(false);
    }, [selectedChannel, selectedFriend, isChannelChat]);

    const fetchOlderMessages = useCallback(async () => {
        if (loadingMessages || isFetchingOlderMessages || !hasMoreMessages)
            return;
        setIsFetchingOlderMessages(true);

        const lastMessageId = messages[0]?.id;
        const fetchMessages = isChannelChat
            ? messageService.getAllChannelMessages(
                  userId,
                  selectedChannel.id,
                  lastMessageId
              )
            : messageService.getAllFriendMessages(
                  userId,
                  selectedFriend.id,
                  lastMessageId
              );
        fetchMessages
            .then((result) => {
                setMessages((prevMessages) => [
                    ...result.data,
                    ...prevMessages,
                ]);
                setHasMoreMessages(result.data.length > 0);
            })
            .catch((error) => {
                toast({
                    title: error.message || "Could not load older messages",
                    status: "error",
                    duration: 6000,
                    isClosable: true,
                    position: "bottom",
                });
            });
        setIsFetchingOlderMessages(false);
    }, [
        loadingMessages,
        isFetchingOlderMessages,
        hasMoreMessages,
        messages,
        isChannelChat,
    ]);

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
                    {!isChannelChat && (
                        <Avatar size="sm" name={selectedFriend.fullName} />
                    )}
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
                {loadingMessages &&
                    [...Array(5)].map((_, i) => (
                        <Flex
                            key={i}
                            gap={2}
                            alignItems={"center"}
                            p={1}
                            borderRadius={"md"}
                            alignSelf={i % 2 === 0 ? "flex-start" : "flex-end"}
                        >
                            {i % 2 === 0 && <SkeletonCircle size={7} />}
                            <Flex flexDir={"column"} gap={2}>
                                <Skeleton h="8px" w="250px" />
                                <Skeleton h="8px" w="250px" />
                                <Skeleton h="8px" w="250px" />
                            </Flex>
                            {i % 2 !== 0 && <SkeletonCircle size={7} />}
                        </Flex>
                    ))}

                {!loadingMessages && (
                    <>
                        {hasMoreMessages && (
                            <Button
                                onClick={fetchOlderMessages}
                                isLoading={isFetchingOlderMessages}
                                mb={4}
                                py={1}
                                px={3}
                                alignSelf="center"
                            >
                                Load More
                            </Button>
                        )}
                        {messages.map((message) => (
                            <Flex key={message.id} direction={"column"}>
                                <Message
                                    message={message}
                                    ownMessage={userId === message.sender.id}
                                />
                            </Flex>
                        ))}
                    </>
                )}
                <div ref={messageEndRef}></div>
            </Flex>
            <MessageInput
                selectedChannel={selectedChannel}
                selectedFriend={selectedFriend}
            />
        </Flex>
    );
}
