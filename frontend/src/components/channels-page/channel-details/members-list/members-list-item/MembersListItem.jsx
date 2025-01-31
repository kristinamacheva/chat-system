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
    IconButton,
    useDisclosure,
    Box,
    Badge,
} from "@chakra-ui/react";
import { FaEnvelope, FaPen } from "react-icons/fa";
import * as channelService from "../../../../../services/channelService";
import { useContext } from "react";
import { useParams } from "react-router-dom";
import ChannelUpdateMember from "./channel-update-member/ChannelUpdateMember";
import { FaTrashCan } from "react-icons/fa6";
import AuthContext from "../../../../../contexts/authContext";

export default function MembersListItem({ member, isOwner, fetchMembers }) {
    const { id: currentUserId } = useContext(AuthContext);
    const { channelId } = useParams();
    const toast = useToast();
    const {
        isOpen: isUpdateMemberModalOpen,
        onOpen: onOpenUpdateMemberModal,
        onClose: onCloseUpdateMemberModal,
    } = useDisclosure();

    const deleteMemberHandler = async (memberId) => {
        try {
            await channelService.removeMember(
                currentUserId,
                memberId,
                channelId
            );
            toast({
                title: "Member deleted successfully",
                status: "success",
                duration: 6000,
                isClosable: true,
                position: "bottom",
            });
            fetchMembers();
        } catch (error) {
            toast({
                title: error.message || "Error deleting member",
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
                boxShadow="md"
                background="white"
                spacing="4"
                justifyContent="center"
                width="642px"
                height="100px"
            >
                <Stack
                    direction="row"
                    alignItems="center"
                    justifyContent="space-between"
                >
                    <Stack direction="row" spacing="5">
                        <Avatar name={member.fullName} />
                        <Stack spacing="0.5" alignItems="center">
                            <HStack>
                                <Heading as="h4" size="sm">
                                    {member.fullName}
                                </Heading>
                                <Box display="inline-block">
                                    <Badge
                                        variant="subtle"
                                        colorScheme="themePurple"
                                    >
                                        {member.role}
                                    </Badge>
                                </Box>
                            </HStack>
                            <HStack>
                                <Icon as={FaEnvelope} color="themePurple.800" />
                                <Text>{member.email}</Text>
                            </HStack>
                        </Stack>
                    </Stack>
                    {isOwner && member.id !== currentUserId && (
                        <HStack>
                            <IconButton
                                aria-label="Edit"
                                title="Edit"
                                icon={<FaPen fontSize="20px" />}
                                variant="ghost"
                                color="themePurple.800"
                                onClick={onOpenUpdateMemberModal}
                            />
                            <IconButton
                                aria-label="Delete"
                                title="Delete"
                                icon={<FaTrashCan fontSize="22px" />}
                                variant="ghost"
                                color="themePurple.800"
                                onClick={() => deleteMemberHandler(member.id)}
                            />
                        </HStack>
                    )}
                </Stack>
            </Card>
            {isUpdateMemberModalOpen && (
                <ChannelUpdateMember
                    isOpen={isUpdateMemberModalOpen}
                    onClose={onCloseUpdateMemberModal}
                    fetchMembers={fetchMembers}
                    member={member}
                />
            )}
        </>
    );
}
