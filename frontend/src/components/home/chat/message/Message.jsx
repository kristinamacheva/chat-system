import { Avatar, Flex, Stack, Text } from "@chakra-ui/react";
import { useState } from "react";

export default function Message({ ownMessage, message }) {
    const [showCreatedAt, setShowCreatedAt] = useState(false);
    const toggleCreatedAt = () => setShowCreatedAt((prev) => !prev);

    return (
        <>
            {ownMessage ? (
                <Flex gap={2} alignSelf={"flex-end"}>
                    <Stack direction="column">
                        <div
                            onClick={toggleCreatedAt}
                            style={{ cursor: "pointer" }}
                        >
                            <Text
                                fontSize={14}
                                color={"white"}
                                bg={"themePurple.800"}
                                maxW={{ base: "350px", md: "450px" }}
                                px={3}
                                py={1}
                                borderRadius={"md"}
                            >
                                {message.content}
                            </Text>
                            {showCreatedAt && (
                                <Text fontSize={12} color={"gray.500"} mt={1}>
                                    {new Date(message.createdAt).toLocaleString(
                                        "en-US",
                                        {
                                            hour: "numeric",
                                            minute: "2-digit",
                                            hour12: true,
                                            day: "numeric",
                                            month: "short",
                                        }
                                    )}
                                </Text>
                            )}
                        </div>
                    </Stack>
                    <Avatar name={message.sender.fullName} size="sm" />
                </Flex>
            ) : (
                <Flex gap={2}>
                    <Avatar name={message.sender.fullName} size="sm" />
                    <Stack direction="column">
                        <div
                            onClick={toggleCreatedAt}
                            style={{ cursor: "pointer" }}
                        >
                            <Text
                                maxW={{ base: "350px", md: "450px" }}
                                bg={"gray.300"}
                                px={3}
                                py={1}
                                fontSize={14}
                                borderRadius={"md"}
                                color={"black"}
                            >
                                {message.content}
                            </Text>
                            {showCreatedAt && (
                                <Text fontSize={12} color={"gray.500"} mt={1}>
                                    {new Date(message.createdAt).toLocaleString(
                                        "en-US",
                                        {
                                            hour: "numeric",
                                            minute: "2-digit",
                                            hour12: true,
                                            day: "numeric",
                                            month: "short",
                                        }
                                    )}
                                </Text>
                            )}
                        </div>
                    </Stack>
                </Flex>
            )}
        </>
    );
}
