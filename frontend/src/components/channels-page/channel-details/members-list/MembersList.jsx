import { useCallback, useContext, useEffect, useRef, useState } from "react";
import * as channelService from "../../../../services/channelService";
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
import { useParams } from "react-router-dom";
import MembersListItem from "./members-list-item/MembersListItem";
import AuthContext from "../../../../contexts/authContext";

export default function MembersList({ isOwner }) {
    const [members, setMembers] = useState([]);
    const [emailSearchValue, setEmailSearchValue] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const [index, setIndex] = useState(2); // Page index starts at 2
    const [hasMore, setHasMore] = useState(false);
    const toast = useToast();
    const { id: currentUserId } = useContext(AuthContext);
    const loaderRef = useRef(null);
    const { channelId } = useParams();

    useEffect(() => {
        fetchMembers();
    }, [currentUserId, channelId]);

    const fetchMembers = useCallback(
        async (reset = false) => {
            setIsLoading(true);
            try {
                const { data, totalPages, currentPage } =
                    await channelService.getAllMembers(channelId, 1, {
                        email: emailSearchValue,
                    });
                setMembers(data);
                setHasMore(totalPages > currentPage);
                setIndex(2);
            } catch (error) {
                toast({
                    title: "Error.",
                    description: error.message || "Error loading members.",
                    status: "error",
                    duration: 5000,
                    isClosable: true,
                });
            }
            setIsLoading(false);
        },
        [currentUserId, channelId, emailSearchValue]
    );

    const fetchMoreMembers = useCallback(async () => {
        if (isLoading || !hasMore) return;
        setIsLoading(true);

        try {
            const { data, totalPages, currentPage } =
                await channelService.getAllMembers(channelId, index, {
                    email: emailSearchValue,
                });
            setMembers((state) => [...state, ...data]);
            setHasMore(totalPages > currentPage);
        } catch (error) {
            toast({
                title: "Error.",
                description: error.message || "Error loading members.",
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
                fetchMoreMembers();
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
    }, [fetchMoreMembers]);

    const onChange = (e) => {
        setEmailSearchValue(e.target.value);
    };

    const onSubmit = (e) => {
        e.preventDefault();
        fetchMembers();
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
                        <Button
                            variant="primary"
                            type="submit"
                            mt={{ base: "3", md: "8" }}
                            mb={{ base: "4" }}
                        >
                            Search
                        </Button>
                    </Stack>
                </Stack>
            </form>

            <Stack>
                {Array.isArray(members) && members.length > 0 ? (
                    members.map((member) => (
                        <MembersListItem
                            key={member.id}
                            member={member}
                            isOwner={isOwner}
                            fetchMembers={fetchMembers}
                        />
                    ))
                ) : (
                    <Flex justifyContent="center" alignItems="center">
                        <Text>No members found</Text>
                    </Flex>
                )}
            </Stack>

            <Stack ref={loaderRef} p="2">
                {isLoading && <Spinner />}
            </Stack>
        </>
    );
}
