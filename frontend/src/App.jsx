import { Route, Routes } from "react-router-dom";
import { AuthProvider } from "./contexts/authContext";
import Path from "./paths";
import Home from "./components/home/Home";
import FriendsPage from "./components/friends-page/FriendsPage";
import ChannelsPage from "./components/channels-page/ChannelsPage";
import Navbar from "./components/navbar/Navbar";
import NotFound from "./components/not-found/NotFound";
import UserNotFound from "./components/user-not-found/UserNotFound";

function App() {
    return (
        <AuthProvider>
            <Routes>
                <Route element={<Navbar />}>
                    <Route path={Path.Home} element={<Home />} />
                    <Route path={Path.FriendsPage} element={<FriendsPage />} />
                    <Route path={Path.ChannelsPage} element={<ChannelsPage />} />
                </Route>
                {/* <Route path="/user-not-found" element={<UserNotFound />} /> */}
                <Route path="*" element={<NotFound />} />
            </Routes>
        </AuthProvider>
    );
}

export default App;
