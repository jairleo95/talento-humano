import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from '../core/auth/useAuth';

export function RequireAuth() {
  const { isAuthenticated, isLoading } = useAuth();

  if (isLoading) {
    return (
      <div className="login-wrapper align-items-center justify-content-center">
        <i className="pi pi-spin pi-spinner" style={{ fontSize: '2rem', color: '#4f8cff' }} />
      </div>
    );
  }

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  return <Outlet />;
}
