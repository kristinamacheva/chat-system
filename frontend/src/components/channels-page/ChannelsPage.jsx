import { useCallback, useContext, useEffect, useRef, useState } from "react";
import {
    Button,
    HStack,
    Heading,
    Spacer,
    Stack,
    useDisclosure,
    Card,
    useToast,
} from "@chakra-ui/react";
import ChannelCreate from "./channel-create/ChannelCreate";
import * as channelService from "../../services/channelService";
import AuthContext from "../../contexts/authContext";
import ChannelListItem from "./household-list-item/ChannelListItem";

export default function ChannelsPage() {
    const [channels, setChannels] = useState([]);
    const { id } = useContext(AuthContext);
    const toast = useToast();
    const [isLoading, setIsLoading] = useState(false);
    const [index, setIndex] = useState(2); // Page index starts at 2
    const [hasMore, setHasMore] = useState(false);
    const loaderRef = useRef(null);

    useEffect(() => {
        fetchChannels();
    }, [id]);

    const fetchChannels = useCallback(
        async (reset = false) => {
            setIsLoading(true);
            try {
                const { data, totalPages, currentPage } =
                    await channelService.getAll(id, 1);
                setChannels(data);
                setHasMore(totalPages > currentPage);
                setIndex(2);
            } catch (error) {
                toast({
                    title: "Error.",
                    description: error.message || "Error loading channels.",
                    status: "error",
                    duration: 5000,
                    isClosable: true,
                });
            }
            setIsLoading(false);
        },
        [id]
    );

    const fetchMoreChannels = useCallback(async () => {
        if (isLoading || !hasMore) return;
        setIsLoading(true);

        try {
            const { data, totalPages, currentPage } =
                await channelService.getAll(id, index);
            setChannels((state) => [...state, ...data]);
            setHasMore(totalPages > currentPage);
        } catch (error) {
            toast({
                title: "Error.",
                description: error.message || "Error loading channels.",
                status: "error",
                duration: 5000,
                isClosable: true,
            });
        }

        setIndex((prevIndex) => prevIndex + 1);
        setIsLoading(false);
    }, [isLoading, hasMore, index]);

    useEffect(() => {
        const observer = new IntersectionObserver((entries) => {
            const target = entries[0];
            if (target.isIntersecting) {
                fetchMoreChannels();
            }
        });

        if (loaderRef.current) {
            observer.observe(loaderRef.current);
        }

        return () => {
            if (loaderRef.current) {
                observer.unobserve(loaderRef.current);
            }
        };
    }, [fetchMoreChannels]);

    const {
        isOpen: isCreateModalOpen,
        onOpen: onOpenCreateModal,
        onClose: onCloseCreateModal,
    } = useDisclosure();

    return (
        <>
            <Stack>
                <Card background="white" p="2" boxShadow="xs">
                    <HStack mx={4} my={2} alignItems="center" flexWrap="wrap">
                        <Heading as="h1" size="lg" color="themePurple.800">
                            Channels
                        </Heading>
                        <Spacer />
                        <Button variant="primary" onClick={onOpenCreateModal}>
                            + Create
                        </Button>
                    </HStack>
                </Card>
                <Stack mt="4">
                    {channels.map((channel) => (
                        <ChannelListItem
                            key={channel.id}
                            channel={channel}
                        />
                    ))}
                </Stack>
            </Stack>
            {isCreateModalOpen && (
                <ChannelCreate
                    isOpen={isCreateModalOpen}
                    onClose={onCloseCreateModal}
                    fetchChannels={fetchChannels}
                />
            )}
        </>
    );
}
