import { Flex, Box, Heading, Text, Button } from "@chakra-ui/react";
import { Link } from "react-router-dom";
import Path from "../../paths";

export default function NotFound() {
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
                    Page Not Found
                </Text>

                <Button as={Link} to={Path.Home} variant="primary">
                    Home
                </Button>
            </Box>
        </Flex>
    );
}
