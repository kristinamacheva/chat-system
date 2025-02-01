import {
    Flex,
    Input,
    InputGroup,
    InputRightElement,
    useToast,
} from "@chakra-ui/react";
import { useContext, useState } from "react";
import { IoSendSharp } from "react-icons/io5";
import AuthContext from "../../../../contexts/authContext";
import * as messageService from "../../../../services/messageService";

export default function MessageInput({
    selectedChannel,
    selectedFriend,
    sendMessageHandler,
}) {
    const [messageText, setMessageText] = useState("");
    const { id: senderId } = useContext(AuthContext);
    const toast = useToast();

    const handleSendMessage = async (e) => {
        e.preventDefault();
        if (!messageText.trim()) return;
        const messageData = {
            content: messageText,
        };

        let result;
        try {
            selectedChannel
                ? (result = await messageService.createChannelMessage(
                      senderId,
                      selectedChannel.id,
                      messageData
                  ))
                : (result = await messageService.createFriendMessage(
                      senderId,
                      selectedFriend.id,
                      messageData
                  ));
            sendMessageHandler(result.data);
            setMessageText("");
        } catch (error) {
            toast({
                title: error.message || "Could not send message",
                status: "error",
                duration: 6000,
                isClosable: true,
                position: "bottom",
            });
        }
    };

    return (
        <Flex gap={2} alignItems={"center"}>
            <form onSubmit={handleSendMessage} style={{ flex: 95 }}>
                <InputGroup>
                    <Input
                        w={"full"}
                        placeholder="Write message..."
                        onChange={(e) => setMessageText(e.target.value)}
                        value={messageText}
                    />
                    <InputRightElement
                        onClick={handleSendMessage}
                        cursor="pointer"
                    >
                        <IoSendSharp />
                    </InputRightElement>
                </InputGroup>
            </form>
        </Flex>
    );
}
