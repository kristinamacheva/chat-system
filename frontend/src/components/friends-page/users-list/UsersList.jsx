import { useCallback, useContext, useEffect, useRef, useState } from "react";
import AuthContext from "../../../contexts/authContext";
import * as userService from "../../../services/userService";
import {
    Button,
    Flex,
    FormControl,
    FormLabel,
    Input,
    Spinner,
    Stack,
    Text,
    useToast,
} from "@chakra-ui/react";
import UsersListItem from "./users-list-item/UsersListItem";

export default function UsersList() {
    const [users, setUsers] = useState([]);
    const [emailSearchValue, setEmailSearchValue] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const [index, setIndex] = useState(2); // Page index starts at 2
    const [hasMore, setHasMore] = useState(false);
    const toast = useToast();
    const { id } = useContext(AuthContext);
    const loaderRef = useRef(null);

    useEffect(() => {
        fetchUsers();
    }, [id]);

    const fetchUsers = useCallback(
        async (reset = false) => {
            setIsLoading(true);
            try {
                const { data, totalPages, currentPage } =
                    await userService.getAll(1, { email: emailSearchValue });

                setUsers(data);
                setHasMore(totalPages > currentPage);
                setIndex(2);
            } catch (error) {
                toast({
                    title: "Error.",
                    description: error.message || "Error loading users.",
                    status: "error",
                    duration: 5000,
                    isClosable: true,
                });
            }

            setIsLoading(false);
        },
        [id, emailSearchValue]
    );

    const fetchMoreUsers = useCallback(async () => {
        if (isLoading || !hasMore) return;
        setIsLoading(true);

        try {
            const { data, totalPages, currentPage } =
                await friendInvitationService.getAll(index, {
                    email: emailSearchValue,
                });
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
    }, [isLoading, hasMore, index, emailSearchValue]);

    useEffect(() => {
        const observer = new IntersectionObserver((entries) => {
            const target = entries[0];
            if (target.isIntersecting) {
                fetchMoreUsers();
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
    }, [fetchMoreUsers]);

    const onChange = (e) => {
        setEmailSearchValue(e.target.value);
    };

    const onSubmit = (e) => {
        e.preventDefault();
        fetchUsers();
    };

    return (
        <>
            <form onSubmit={onSubmit}>
                <Text fontWeight="bold" fontSize="lg">
                    Search
                </Text>
                <Stack spacing="2" direction={{ base: "column", lg: "row" }}>
                    <Stack
                        direction={{ base: "column", md: "row" }}
                        width={{ lg: "49%" }}
                    >
                        <FormControl id="title">
                            <FormLabel>Email</FormLabel>
                            <Input
                                size="md"
                                type="search"
                                name="title"
                                value={emailSearchValue}
                                onChange={onChange}
                                placeholder="Enter email"
                            />
                        </FormControl>
                    </Stack>
                </Stack>
                <Flex justify="flex-end" my="3" mx="1">
                    <Button variant="primary" type="submit">
                        Search
                    </Button>
                </Flex>
            </form>

            <Stack>
                {Array.isArray(users) && users.length > 0 ? (
                    users.map((user) => (
                        <UsersListItem key={user.id} {...user} />
                    ))
                ) : (
                    <Flex justifyContent="center" alignItems="center">
                        <Text>No users found</Text>
                    </Flex>
                )}
            </Stack>

            <Stack ref={loaderRef} p="2">
                {isLoading && <Spinner />}
            </Stack>
        </>
    );
}
