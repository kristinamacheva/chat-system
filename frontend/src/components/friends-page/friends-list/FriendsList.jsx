import { useCallback, useContext, useEffect, useRef, useState } from "react";
import AuthContext from "../../../contexts/authContext";
import * as friendshipService from "../../../services/friendshipService";
import { Card, Flex, Heading, HStack, Spinner, Stack, Text, useToast } from "@chakra-ui/react";
import FriendsListItem from "./friends-list-item/FriendsListItem";

export default function FriendsList() {
    const [friendItems, setFriendItems] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [index, setIndex] = useState(2); // Page index starts at 2
    const [hasMore, setHasMore] = useState(false);
    const toast = useToast();
    const { id } = useContext(AuthContext);
    const loaderRef = useRef(null);

    useEffect(() => {
        fetchFriendItems();
    }, []);

    const fetchFriendItems = useCallback(
        async (reset = false) => {
            setIsLoading(true);

            try {
                const { data, totalPages, currentPage } =
                    await friendshipService.getAll(id, 1);

                setFriendItems(data);
                setHasMore(totalPages > currentPage);
                setIndex(2);
            } catch (error) {
                toast({
                    title: "Error.",
                    description: error.message || "Error loading friends.",
                    status: "error",
                    duration: 5000,
                    isClosable: true,
                });
            }
            setIsLoading(false);
        },
        [id]
    );

    const fetchMoreFriendItems = useCallback(async () => {
        if (isLoading || !hasMore) return;
        setIsLoading(true);

        try {
            const { data, totalPages, currentPage } =
                await friendshipService.getAll(id, index);
            setFriendItems((state) => [...state, ...data]);
            setHasMore(totalPages > currentPage);
        } catch (error) {
            toast({
                title: "Error.",
                description: error.message || "Error loading friends.",
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
                fetchMoreFriendItems();
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
    }, [fetchMoreFriendItems]);

    return (
        <>
            <Card background="white" p="2" boxShadow="xs">
                <HStack mx={4} my={2} alignItems="center" flexWrap="wrap">
                    <Heading as="h1" size="lg" color="themePurple.800">
                        Friends
                    </Heading>
                </HStack>
            </Card>
            <Stack mt="4">
                {Array.isArray(friendItems) && friendItems.length > 0 ? (
                    friendItems.map((friendItem) => (
                        <FriendsListItem key={friendItem.id} {...friendItem} />
                    ))
                ) : (
                    <Flex justifyContent="center" alignItems="center">
                        <Text>No friends available</Text>
                    </Flex>
                )}
            </Stack>

            <Stack ref={loaderRef} p="2">
                {isLoading && <Spinner />}
            </Stack>
        </>
    );
}
