import { Flex, Box, Heading, Text, Button } from "@chakra-ui/react";

export default function ChannelNotFound() {
    return (
        <Flex
            justifyContent="center"
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
                    Page not found
                </Text>
                <Text color={"gray.500"} mb={6}>
                    The channel you're looking for doesnt exist.
                </Text>
            </Box>
        </Flex>
    );
}