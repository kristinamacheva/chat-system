import {
    Avatar,
    Heading,
    HStack,
    Icon,
    Stack,
    Text,
    Card,
    Button,
    useToast,
} from "@chakra-ui/react";
import { FaEnvelope } from "react-icons/fa";
import * as friendInvitationService from "../../../../services/friendInvitationService";
import { useContext } from "react";
import AuthContext from "../../../../contexts/authContext";

export default function UsersListItem({ user }) {
    const { id } = useContext(AuthContext);
    const toast = useToast();
    
    const sendRequestHandler = async (recipientId) => {
        try {
            await friendInvitationService.create(id, recipientId);
            toast({
                title: "Request send successfully",
                status: "success",
                duration: 6000,
                isClosable: true,
                position: "bottom",
            });
        } catch (error) {
            toast({
                title: error.message || "Error sending request",
                status: "error",
                duration: 6000,
                isClosable: true,
                position: "bottom",
            });
        }
    };

    return (
        <Card
            px="5"
            py="5"
            boxShadow="md"
            background="white"
            spacing="4"
            justifyContent="center"
            width="460px"
            height="100px"
        >
            <Stack
                direction="row"
                alignItems="center"
                justifyContent="space-between"
            >
                <Stack direction="row" spacing="5">
                    <Avatar name={user.fullName} />
                    <Stack spacing="0.5" alignItems="center">
                        <Heading as="h4" size="sm">
                            {user.fullName}
                        </Heading>
                        <HStack>
                            <Icon as={FaEnvelope} color="themePurple.800" />
                            <Text>{user.email}</Text>
                        </HStack>
                    </Stack>
                </Stack>
                {!user.friend && (
                    <Button variant="primary" type="submit" onClick={() => sendRequestHandler(user.id)}>
                        Send request
                    </Button>
                )}
            </Stack>
        </Card>
    );
}
