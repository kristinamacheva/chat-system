import {
    Stack,
    Heading,
    Card,
    IconButton,
    HStack,
} from "@chakra-ui/react";
import { FaEye } from "react-icons/fa6";
import { Link } from "react-router-dom";

export default function ChannelListItem({ channel }) {
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
                        <Heading as="h3" size="md">
                            {channel.name}
                        </Heading>
                    </Stack>
                </Stack>
                <HStack
                    spacing="0"
                    w={["auto", "auto", "90px"]}
                    justifyContent="flex-end"
                >
                    <IconButton
                        as={Link}
                        to={`/channels/${channel.id}`}
                        aria-label="Details"
                        title="Details"
                        icon={<FaEye fontSize="20px" />}
                        variant="ghost"
                        color="themePurple.800"
                    />
                </HStack>
            </Card>
        </>
    );
}
