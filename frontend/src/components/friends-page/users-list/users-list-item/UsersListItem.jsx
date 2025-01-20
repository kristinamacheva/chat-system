import {
    Avatar,
    Heading,
    HStack,
    Icon,
    Stack,
    Text,
    Card,
    Button,
} from "@chakra-ui/react";
import { FaEnvelope } from "react-icons/fa";

export default function UsersListItem({ id, fullName, email, friend }) {
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
                    <Avatar name={fullName} />
                    <Stack spacing="0.5" alignItems="center">
                        <Heading as="h4" size="sm">
                            {fullName}
                        </Heading>
                        <HStack>
                            <Icon as={FaEnvelope} color="themePurple.800" />
                            <Text>{email}</Text>
                        </HStack>
                    </Stack>
                </Stack>
                {!friend && (
                    <Button variant="primary" type="submit">
                        Send request
                    </Button>
                )}
            </Stack>
        </Card>
    );
}
