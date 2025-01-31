import {
    Heading,
    HStack,
    Icon,
    Stack,
    Text,
    Card,
    IconButton,
    useToast,
} from "@chakra-ui/react";
import { FaEnvelope } from "react-icons/fa";
import * as friendInvitationService from "../../../../services/friendInvitationService";
import { FaCircleCheck, FaCircleXmark } from "react-icons/fa6";
import { BsCalendar2DateFill } from "react-icons/bs";
import AuthContext from "../../../../contexts/authContext";
import { useContext } from "react";

export default function InvitationsListItem({
    id,
    fetchInvitations,
    userFullName,
    userEmail,
    invitationDate,
}) {
    const { id: userId } = useContext(AuthContext);
    const toast = useToast();

    const invitationAcceptHandler = async (id) => {
        try {
            await friendInvitationService.accept(userId, id);
            toast({
                title: "Invitation accepted successfully",
                status: "success",
                duration: 6000,
                isClosable: true,
                position: "bottom",
            });
            fetchInvitations();
        } catch (error) {
            toast({
                title: error.message || "Could not accept invitation",
                status: "error",
                duration: 6000,
                isClosable: true,
                position: "bottom",
            });
        }
    };

    const invitationDeclineHandler = async (id) => {
        try {
            await friendInvitationService.decline(userId, id);
            toast({
                title: "Invitation declined successfully",
                status: "success",
                duration: 6000,
                isClosable: true,
                position: "bottom",
            });
            fetchInvitations();
        } catch (error) {
            toast({
                title: error.message || "Could not decline invitation",
                status: "error",
                duration: 6000,
                isClosable: true,
                position: "bottom",
            });
        }
    };

    return (
        <>
            <Card
                px="5"
                py="5"
                mx="4"
                my="1"
                boxShadow="md"
                borderTop="4px solid #676F9D"
                background="white"
                spacing="4"
                direction={{ base: "column", md: "row" }}
                justifyContent="space-between"
                alignItems={{ md: "center" }}
            >
                <Stack
                    direction={{ base: "column", md: "row" }}
                    alignItems={{ md: "center" }}
                    spacing={{ base: "2", md: "4" }}
                >
                    <Stack direction="column" spacing={{ base: "1", md: "0" }}>
                        <Heading as="h3" size="md" mb="1">
                            {userFullName}
                        </Heading>
                        <HStack>
                            <Icon as={FaEnvelope} color="themePurple.800" />
                            <Text>{userEmail}</Text>
                        </HStack>
                        <HStack>
                            <Icon
                                as={BsCalendar2DateFill}
                                color="themePurple.800"
                                size="md"
                            />
                            <Text>
                                {new Intl.DateTimeFormat("bg-BG").format(
                                    new Date(invitationDate)
                                )}
                            </Text>
                        </HStack>
                    </Stack>
                </Stack>
                <HStack
                    spacing="0"
                    w={["auto", "auto", "90px"]}
                    justifyContent="flex-end"
                >
                    <IconButton
                        aria-label="Accept"
                        title="Accept"
                        icon={<FaCircleCheck fontSize="20px" />}
                        variant="ghost"
                        color="themePurple.800"
                        onClick={() => invitationAcceptHandler(id)}
                    />
                    <IconButton
                        aria-label="Decline"
                        title="Decline"
                        icon={<FaCircleXmark fontSize="20px" />}
                        variant="ghost"
                        color="themePurple.800"
                        onClick={() => invitationDeclineHandler(id)}
                    />
                </HStack>
            </Card>
        </>
    );
}
