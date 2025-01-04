import {
    Box,
    Flex,
    Avatar,
    HStack,
    IconButton,
    useDisclosure,
    useColorModeValue,
    Stack,
    Text,
} from "@chakra-ui/react";
import { MdClose, MdMenu } from "react-icons/md";
import AuthContext from "../../contexts/authContext";
import { useContext } from "react";
import { Link, Outlet } from "react-router-dom";
import Path from "../../paths"; 

const LinkItems = [
    { name: "Home", to: Path.Home },
    { name: "Friends", to: Path.FriendsPage },
    { name: "Channels", to: Path.ChannelsPage },
];

const NavLink = ({ children, to }) => {
    return (
        <Link to={to}>
            <Box
                px={2}
                py={1}
                rounded={"md"}
                _hover={{
                    textDecoration: "none",
                    bg: useColorModeValue("gray.200", "gray.700"),
                }}
            >
                {children}
            </Box>
        </Link>
    );
};

export default function Navbar() {
    const { isOpen, onOpen, onClose } = useDisclosure();
    const { fullName } = useContext(AuthContext);

    return (
        <>
            <Box bg={useColorModeValue("gray.100")} px={4}>
                <Flex
                    h={16}
                    alignItems={"center"}
                    justifyContent={"space-between"}
                >
                    <IconButton
                        size={"md"}
                        icon={isOpen ? <MdClose /> : <MdMenu />}
                        aria-label={"Open Menu"}
                        display={{ md: "none" }}
                        onClick={isOpen ? onClose : onOpen}
                    />
                    <HStack spacing={8} alignItems={"center"}>
                        <Box>
                            <Text
                                fontFamily="monospace"
                                fontWeight="bold"
                                fontSize="md"
                            >
                                Chat App
                            </Text>
                        </Box>
                        <HStack
                            as={"nav"}
                            spacing={4}
                            display={{ base: "none", md: "flex" }}
                        >
                            {LinkItems.map((link) => (
                                <NavLink key={link.name} to={link.to}>
                                    {link.name}
                                </NavLink>
                            ))}
                        </HStack>
                    </HStack>
                    <Avatar size={"sm"} name={fullName} />
                </Flex>

                {isOpen ? (
                    <Box pb={4} display={{ md: "none" }}>
                        <Stack as={"nav"} spacing={4}>
                            {LinkItems.map((link) => (
                                <NavLink key={link.name} to={link.to}>
                                    {link.name}
                                </NavLink>
                            ))}
                        </Stack>
                    </Box>
                ) : null}
            </Box>

            <Box p={4}>
                <Outlet/>
            </Box>
        </>
    );
}
