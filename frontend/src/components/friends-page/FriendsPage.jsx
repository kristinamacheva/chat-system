import { Box, Tab, TabList, TabPanel, TabPanels, Tabs, Text } from "@chakra-ui/react";
import FriendsList from "./friends-list/FriendsList";
import styles from "./friends-page.module.css";

export default function FriendsPage() {
    return (
        <>
            <Tabs
                isLazy
                colorScheme="themePurple"
                mx="1"
                mt="4"
                className={styles.scroll}
            >
                <Box width="100%" overflowX="auto" p={2}>
                    <TabList>
                        <Tab>Friends</Tab>
                        <Tab>Friend Requests</Tab>
                        <Tab>Users</Tab>
                    </TabList>
                </Box>

                <TabPanels>
                    <TabPanel>
                        <FriendsList/>
                    </TabPanel>
                </TabPanels>
            </Tabs>
        </>
    );
}
