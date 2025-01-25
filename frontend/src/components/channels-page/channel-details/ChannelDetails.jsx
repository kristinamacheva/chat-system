import {
    Card,
    HStack,
    Heading,
    IconButton,
    Spinner,
    Stack,
    useDisclosure,
    useToast,
} from "@chakra-ui/react";
import { useContext, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import * as channelService from "../../../services/channelService";
import AuthContext from "../../../contexts/authContext";
import ChannelNotFound from "../../channel-not-found/ChannelNotFound";
import { HiUserAdd } from "react-icons/hi";
import { FaPen, FaTrashCan } from "react-icons/fa6";
import ChannelEdit from "./channel-edit/ChannelEdit";
import ChannelAddUser from "./channel-add-user/ChannelAddUser";
import MembersList from "./members-list/MembersList";

export default function ChannelDetails() {
    const [channel, setChannel] = useState({});
    const [isLoading, setIsLoading] = useState(true);
    const [notFound, setNotFound] = useState(false);
    const { id: currentUserId } = useContext(AuthContext);
    const [isAdmin, setIsAdmin] = useState(false);
    const [isOwner, setIsOwner] = useState(false);
    const {
        isOpen: isEditModalOpen,
        onOpen: onOpenEditModal,
        onClose: onCloseEditModal,
    } = useDisclosure();
    const {
        isOpen: isAddUserModalOpen,
        onOpen: onOpenAddUserModal,
        onClose: onCloseAddUserModal,
    } = useDisclosure();
    const toast = useToast();
    const navigate = useNavigate();
    const { channelId } = useParams();

    useEffect(() => {
        fetchChannel();
    }, [currentUserId, channelId]);

    const fetchChannel = function () {
        setIsLoading(true);
        channelService
            .getOne(channelId)
            .then((result) => {
                setChannel(result.data);
                setIsOwner(result.data.owner?.id === currentUserId);
                setIsAdmin(
                    result.data.admins?.some(
                        (admin) => admin.id === currentUserId
                    )
                );
                setIsLoading(false);
            })
            .catch((error) => {
                if (error.status === 404) {
                    setIsLoading(false);
                    setNotFound(true);
                } else {
                    toast({
                        title: error.message || "Could not load the channel",
                        status: "error",
                        duration: 6000,
                        isClosable: true,
                        position: "bottom",
                    });
                    setIsLoading(false);
                    navigate("/channels");
                }
            });
    };

    if (isLoading) {
        return <Spinner size="lg" />;
    }
    if (notFound) {
        return <ChannelNotFound />;
    }

    const deleteChannelHandler = (channelId) => {
        channelService
            .remove(channelId, currentUserId)
            .then(() => {
                toast({
                    title: "Channel deleted successfully!",
                    status: "success",
                    duration: 6000,
                    isClosable: true,
                    position: "bottom",
                });
                navigate("/channels");
            })
            .catch((error) => {
                toast({
                    title: error.message || "Could not delete the channel",
                    status: "error",
                    duration: 6000,
                    isClosable: true,
                    position: "bottom",
                });
            });
    };

    return (
        <>
            <Card background="white" p="2" boxShadow="xs">
                <HStack
                    mx={4}
                    my={2}
                    alignItems="center"
                    flexWrap="wrap"
                    justifyContent="space-between"
                >
                    <Heading as="h1" size="lg" color="themePurple.800" mr="2">
                        {channel.name}
                    </Heading>
                    <HStack>
                        {(isAdmin || isOwner) && (
                            <>
                                <IconButton
                                    aria-label="Add user"
                                    title="Add user"
                                    icon={<HiUserAdd fontSize="22px" />}
                                    variant="ghost"
                                    color="themePurple.800"
                                    onClick={onOpenAddUserModal}
                                />
                                <IconButton
                                    aria-label="Edit"
                                    title="Edit"
                                    icon={<FaPen fontSize="20px" />}
                                    variant="ghost"
                                    color="themePurple.800"
                                    onClick={onOpenEditModal}
                                />
                            </>
                        )}
                        {isOwner && (
                            <IconButton
                                aria-label="Delete"
                                title="Delete"
                                icon={<FaTrashCan fontSize="22px" />}
                                variant="ghost"
                                color="themePurple.800"
                                onClick={() => deleteChannelHandler(channel.id)}
                            />
                        )}
                    </HStack>
                </HStack>
            </Card>
            <Stack m="2" pt="4">
                <MembersList
                    isOwner={isOwner}
                />
            </Stack>
            {isEditModalOpen && (
                <ChannelEdit
                    isOpen={isEditModalOpen}
                    onClose={onCloseEditModal}
                    channel={channel}
                    fetchChannel={fetchChannel}
                />
            )}
            {isAddUserModalOpen && (
                <ChannelAddUser
                    isOpen={isAddUserModalOpen}
                    onClose={onCloseAddUserModal}
                    isOwner={isOwner}
                    fetchChannel={fetchChannel}
                />
            )}
        </>
    );
}
