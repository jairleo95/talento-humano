import { createContext, useCallback, useEffect, useState, type ReactNode } from 'react';
import { configureApiClient, saveToken, clearToken, loadToken, apiGet, apiPost } from '../api/client';
import type { LoginRequest, LoginResponse, MeResponse } from './types';

interface AuthState {
  user: MeResponse | null;
  isLoading: boolean;
  isAuthenticated: boolean;
  login: (credentials: LoginRequest) => Promise<void>;
  logout: () => void;
}

export const AuthContext = createContext<AuthState>({
  user: null,
  isLoading: true,
  isAuthenticated: false,
  login: async () => {},
  logout: () => {},
});

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<MeResponse | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  const logout = useCallback(() => {
    clearToken();
    setUser(null);
  }, []);

  useEffect(() => {
    configureApiClient(logout);
  }, [logout]);

  useEffect(() => {
    const token = loadToken();
    if (!token) {
      setIsLoading(false);
      return;
    }
    apiGet<MeResponse>('/identity/api/v1/users/me')
      .then(setUser)
      .catch(() => {
        clearToken();
        setUser(null);
      })
      .finally(() => setIsLoading(false));
  }, []);

  const login = useCallback(async (credentials: LoginRequest) => {
    const result = await apiPost<LoginResponse>('/identity/api/v1/auth/login', credentials);
    saveToken(result.token);
    setUser(result.user);
  }, []);

  const value: AuthState = {
    user,
    isLoading,
    isAuthenticated: !!user,
    login,
    logout,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}
