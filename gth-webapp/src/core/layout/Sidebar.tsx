import { useAuth } from '../auth/useAuth';
import { useNavigate, useLocation } from 'react-router-dom';

export function Sidebar({ collapsed }: { collapsed: boolean }) {
  const { user } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();

  const privileges = user?.privileges ?? [];

  const getInitials = (name: string) => {
    const parts = name.split(' ');
    return (parts[0]?.[0] || '') + (parts[1]?.[0] || parts[0]?.[1] || '').toUpperCase();
  };

  return (
    <aside className={`gth-sidebar ${collapsed ? 'collapsed' : ''}`}>
      <div className="user-info">
        <div className="avatar">{user ? getInitials(user.username) : '?'}</div>
        <div className="info">
          <div className="name">{user?.username}</div>
          <div className="role">{user?.roles?.join(', ') || 'Sin rol'}</div>
        </div>
      </div>

      <ul className="menu-list">
        {privileges.map((item) => {
          const isActive = location.pathname === item.linkUrl || location.pathname.startsWith(item.linkUrl + '/');
          return (
            <li key={item.code}>
              <a
                className={isActive ? 'active' : ''}
                onClick={(e) => { e.preventDefault(); navigate(item.linkUrl); }}
                href={item.linkUrl}
              >
                <i className={item.icon} />
                <span>{item.description}</span>
              </a>
            </li>
          );
        })}
      </ul>
    </aside>
  );
}
