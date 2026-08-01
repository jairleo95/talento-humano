import { useAuth } from '../auth/useAuth';
import { useNavigate, useLocation } from 'react-router-dom';

interface MenuItem {
  label: string;
  icon: string;
  path: string;
}

const ALL_MENU_ITEMS: MenuItem[] = [
  { label: 'Requerimientos', icon: 'pi pi-file', path: '/requirements' },
  { label: 'Procesos', icon: 'pi pi-sitemap', path: '/processes' },
  { label: 'Bandeja', icon: 'pi pi-inbox', path: '/inbox' },
  { label: 'Contratos', icon: 'pi pi-id-card', path: '/contracts' },
  { label: 'Trabajadores', icon: 'pi pi-users', path: '/workers' },
  { label: 'Organigrama', icon: 'pi pi-building', path: '/organization' },
  { label: 'Presupuesto', icon: 'pi pi-chart-bar', path: '/budget' },
  { label: 'Académico', icon: 'pi pi-book', path: '/academic' },
  { label: 'Funciones', icon: 'pi pi-list', path: '/functions' },
  { label: 'Reportes', icon: 'pi pi-chart-line', path: '/reports' },
  { label: 'Usuarios', icon: 'pi pi-shield', path: '/users' },
];

function getMenuItems(roles: string[]): MenuItem[] {
  if (!roles.includes('ADMIN')) {
    return ALL_MENU_ITEMS.filter((item) => item.label === 'Requerimientos');
  }
  return ALL_MENU_ITEMS;
}

function getInitials(name: string): string {
  const parts = name.split(' ');
  return (parts[0]?.[0] || '') + (parts[1]?.[0] || parts[0]?.[1] || '').toUpperCase();
}

export function Sidebar({ collapsed }: { collapsed: boolean }) {
  const { user } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();

  const menuItems = user ? getMenuItems(user.roles) : [];

  return (
    <aside className={`gth-sidebar ${collapsed ? 'collapsed' : ''}`}>
      <div className="user-info">
        <div className="avatar">
          {user ? getInitials(user.username) : '?'}
        </div>
        <div className="info">
          <div className="name">{user?.username}</div>
          <div className="role">{user?.roles?.join(', ') || 'Sin rol'}</div>
        </div>
      </div>

      <ul className="menu-list">
        {menuItems.map((item) => {
          const isActive = location.pathname.startsWith(item.path);
          return (
            <li key={item.path}>
              <a
                className={isActive ? 'active' : ''}
                onClick={(e) => {
                  e.preventDefault();
                  navigate(item.path);
                }}
                href={item.path}
              >
                <i className={item.icon} />
                <span>{item.label}</span>
              </a>
            </li>
          );
        })}
      </ul>
    </aside>
  );
}
