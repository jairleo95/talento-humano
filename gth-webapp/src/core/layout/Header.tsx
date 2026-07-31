import { useAuth } from '../auth/useAuth';

export function Header({ onMenuToggle }: { onMenuToggle: () => void }) {
  const { user, logout } = useAuth();

  return (
    <header className="gth-header">
      <div className="header-left">
        <button className="btn-icon" onClick={onMenuToggle}>
          <i className="pi pi-bars" />
        </button>
        <span className="logo-text">
          Talento<span>Humano</span>
        </span>
      </div>
      <div className="header-right">
        <span className="user-name">{user?.username}</span>
        <button className="btn-icon" onClick={logout} title="Cerrar sesión">
          <i className="pi pi-sign-out" />
        </button>
      </div>
    </header>
  );
}
