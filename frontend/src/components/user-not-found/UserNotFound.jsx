import { Flex, Box, Heading, Text, Button } from "@chakra-ui/react";

export default function UserNotFound() {
    return (
        <Flex
            alignItems="center"
            justifyContent="center"
            minHeight="100vh"
        >
            <Box textAlign="center" py={10} px={6}>
                <Heading
                    display="inline-block"
                    as="h2"
                    size="2xl"
                    backgroundClip="text"
                    color="themePurple.700"
                >
                    404
                </Heading>
                <Text fontSize="18px" mt={3} mb={2}>
                    User Not Found
                </Text>
            </Box>
        </Flex>
    );
}
