import { Outlet } from 'react-router-dom';
import { useState } from 'react';
import { Header } from './Header';
import { Sidebar } from './Sidebar';

export function AppShell() {
  const [sidebarCollapsed, setSidebarCollapsed] = useState(false);

  return (
    <div className="h-full">
      <Header onMenuToggle={() => setSidebarCollapsed(!sidebarCollapsed)} />
      <Sidebar collapsed={sidebarCollapsed} />
      <div className={`gth-main ${sidebarCollapsed ? 'expanded' : ''}`}>
        <div className="content">
          <Outlet />
        </div>
      </div>
    </div>
  );
}
