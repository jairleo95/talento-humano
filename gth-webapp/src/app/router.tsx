import { Routes, Route, Navigate } from 'react-router-dom';
import { LoginPage } from '../features/auth/LoginPage';
import { RequirementsPage } from '../features/requirements/RequirementsPage';
import { ProcessListPage } from '../features/processes/ProcessListPage';
import { ProcessDetailPage } from '../features/processes/ProcessDetailPage';
import { InboxPage } from '../features/inbox/InboxPage';
import { ContractListPage } from '../features/contracts/ContractListPage';
import { ContractDetailPage } from '../features/contracts/ContractDetailPage';
import { TemplatePage } from '../features/contracts/TemplatePage';
import { UserManagementPage } from '../features/users/UserManagementPage';
import { WorkerListPage } from '../features/workers/WorkerListPage';
import { WorkerDetailPage } from '../features/workers/WorkerDetailPage';
import { OrgStructurePage } from '../features/organization/OrgStructurePage';
import { AppShell } from '../core/layout/AppShell';
import { RequireAuth } from './RequireAuth';

export function AppRouter() {
  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route element={<RequireAuth />}>
        <Route element={<AppShell />}>
          <Route path="/" element={<Navigate to="/requirements" replace />} />
          <Route path="/requirements" element={<RequirementsPage />} />
          <Route path="/processes" element={<ProcessListPage />} />
          <Route path="/processes/:id" element={<ProcessDetailPage />} />
          <Route path="/inbox" element={<InboxPage />} />
          <Route path="/contracts" element={<ContractListPage />} />
          <Route path="/contracts/:id" element={<ContractDetailPage />} />
          <Route path="/contracts/templates" element={<TemplatePage />} />
          <Route path="/users" element={<UserManagementPage />} />
          <Route path="/workers" element={<WorkerListPage />} />
          <Route path="/workers/:id" element={<WorkerDetailPage />} />
          <Route path="/organization" element={<OrgStructurePage />} />
        </Route>
      </Route>
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}
