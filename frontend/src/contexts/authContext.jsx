import { createContext, useState, useEffect } from "react";
import * as authService from "../services/authService";
import UserNotFound from "../components/user-not-found/UserNotFound";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [userId, setUserId] = useState(1);
    const [user, setUser] = useState(null);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        setIsLoading(true);
        authService
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
