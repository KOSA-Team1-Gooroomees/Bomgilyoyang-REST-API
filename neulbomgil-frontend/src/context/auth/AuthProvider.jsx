import { AuthContext } from "./AuthContext.jsx";
import { useState, useEffect } from "react";
import api from "../../api/axios.js";

export const AuthProvider = ({ children }) => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        const checkAuthStatus = async () => {
            try {
                await api.get("/api/auth/me");
                setIsLoggedIn(true);
            } catch (e) {
                setIsLoggedIn(false);
            } finally {
                setIsLoading(false);
            }
        };

        checkAuthStatus();
    }, []);

    const login = () => setIsLoggedIn(true);
    const logout = () => setIsLoggedIn(false);

    if (isLoading) {
        return (
            <div className="min-h-screen flex items-center justify-center text-gray-500 bg-gray-50">
                인증 상태 확인 중...
            </div>
        );
    }

    return (
        <AuthContext.Provider value={{ isLoggedIn, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};