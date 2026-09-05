import React, { createContext, useContext, useEffect, useState } from 'react';
import { jwtDecode } from 'jwt-decode';
import Cookies from 'js-cookie';

const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

const COOKIE_OPTS = {
  sameSite: 'strict',
  // `secure` only works over HTTPS; keep it off for http://localhost dev
  secure: window.location.protocol === 'https:',
};

export function AuthProvider({ children }) {
  const [authState, setAuthState] = useState(null);

  const setToken = (token) => {
    if (typeof token !== 'string' || !token.trim()) return;
    try {
      setAuthState(jwtDecode(token));
    } catch {
      setAuthState(null);
    }
  };

  useEffect(() => {
    const token = Cookies.get('access_token');
    if (token) setToken(token);
  }, []);

  const login = (token) => {
    const expiry = new Date(Date.now() + 10 * 60 * 60 * 1000); // 10 h
    Cookies.set('access_token', String(token), { ...COOKIE_OPTS, expires: expiry });
    setToken(token);
  };

  const logout = () => {
    Cookies.remove('access_token');
    setAuthState(null);
  };

  const isAuthenticated = !!Cookies.get('access_token') && Cookies.get('access_token') !== 'null';

  return (
    <AuthContext.Provider value={{ authState, isAuthenticated, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}
