import { createContext, useState, useEffect } from "react";
import * as userService from "../services/userService";
import UserNotFound from "../components/user-not-found/UserNotFound";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [userId, setUserId] = useState(11);
    const [user, setUser] = useState(null);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        setIsLoading(true);
        userService
            .getOne(userId)
            .then((result) => {
                setUser(result.data);
            })
            .catch((error) => {
                setUser(null);
            });
        setIsLoading(false);
    }, []);

    if (!isLoading && user === null) {
        return <UserNotFound />;
    }

    return (
        <AuthContext.Provider value={user}>
            {!isLoading && children}
        </AuthContext.Provider>
    );
};

AuthContext.displayName = "AuthContext";

export default AuthContext;
