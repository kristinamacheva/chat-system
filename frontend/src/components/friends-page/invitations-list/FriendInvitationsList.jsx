import { useCallback, useContext, useEffect, useRef, useState } from "react";
import AuthContext from "../../../contexts/authContext";
import * as friendInvitationService from "../../../services/friendInvitationService";
import {
    Card,
    Flex,
    Heading,
    HStack,
    Spinner,
    Stack,
    Text,
    useToast,
} from "@chakra-ui/react";
import InvitationsListItem from "./invitations-list-item/InvitationsListItem";

export default function FriendInvitationsList() {
    const [invitations, setInvitations] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [index, setIndex] = useState(2); // Page index starts at 2
    const [hasMore, setHasMore] = useState(false);
    const toast = useToast();
    const { id } = useContext(AuthContext);
    const loaderRef = useRef(null);

    useEffect(() => {
        fetchInvitations();
    }, [id]);

    const fetchInvitations = useCallback(
        async (reset = false) => {
            setIsLoading(true);

            try {
                const { data, totalPages, currentPage } =
                    await friendInvitationService.getAll(id, 1);

                setInvitations(data);
                setHasMore(totalPages > currentPage);
                setIndex(2);
            } catch (error) {
                toast({
                    title: "Error.",
                    description: error.message || "Error loading invitations.",
                    status: "error",
                    duration: 5000,
                    isClosable: true,
                });
            }
            setIsLoading(false);
        },
        [id]
    );

    const fetchMoreInvitations = useCallback(async () => {
        if (isLoading || !hasMore) return;
        setIsLoading(true);

        try {
            const { data, totalPages, currentPage } =
                await friendInvitationService.getAll(id, index);
            setInvitations((state) => [...state, ...data]);
            setHasMore(totalPages > currentPage);
        } catch (error) {
            toast({
                title: "Error.",
                description: error.message || "Error loading invitations.",
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
                fetchMoreInvitations();
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
    }, [fetchMoreInvitations]);

    return (
        <>
            <Card background="white" p="2" boxShadow="xs">
                <HStack mx={4} my={2} alignItems="center" flexWrap="wrap">
                    <Heading as="h1" size="lg" color="themePurple.800">
                        Friend Requests
                    </Heading>
                </HStack>
            </Card>
            <Stack mt="4">
                {Array.isArray(invitations) && invitations.length > 0 ? (
                    invitations.map((invitation) => (
                        <InvitationsListItem
                            key={invitation.id}
                            fetchInvitations={fetchInvitations}
                            {...invitation}
                        />
                    ))
                ) : (
                    <Flex justifyContent="center" alignItems="center">
                        <Text>No invitations available</Text>
                    </Flex>
                )}
            </Stack>

            <Stack ref={loaderRef} p="2">
                {isLoading && <Spinner />}
            </Stack>
        </>
    );
}
