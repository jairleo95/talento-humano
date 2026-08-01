import { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { Toolbar } from 'primereact/toolbar';
import { Tag } from 'primereact/tag';
import { MultiSelect } from 'primereact/multiselect';
import { useForm, Controller } from 'react-hook-form';
import dayjs from 'dayjs';
import { apiGet, apiPost, apiPatch, apiDelete } from '../../core/api/client';
import type { UserResponse, RoleResponse } from './types';

const BASE = '/identity/api/v1/users';
const ROLES_PATH = '/identity/api/v1/roles';

function dateBody(row: UserResponse) {
  return dayjs(row.createdAt).format('DD/MM/YYYY HH:mm');
}

function enabledBody(row: UserResponse) {
  return <Tag severity={row.enabled ? 'success' : 'danger'} value={row.enabled ? 'Activo' : 'Inactivo'} />;
}

export function UserManagementPage() {
  const queryClient = useQueryClient();
  const [searchTerm, setSearchTerm] = useState('');
  const [showCreate, setShowCreate] = useState(false);
  const [editUser, setEditUser] = useState<UserResponse | null>(null);

  const { data: users = [], isLoading } = useQuery({
    queryKey: ['users', searchTerm],
    queryFn: () => {
      const param = searchTerm ? `?search=${encodeURIComponent(searchTerm)}` : '';
      return apiGet<UserResponse[]>(`${BASE}${param}`);
    },
  });

  const { data: roles = [] } = useQuery({
    queryKey: ['roles'],
    queryFn: () => apiGet<RoleResponse[]>(ROLES_PATH),
  });

  const createMutation = useMutation({
    mutationFn: (body: Record<string, unknown>) => apiPost<UserResponse>(BASE, body),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['users'] }); setShowCreate(false); },
  });

  const toggleMutation = useMutation({
    mutationFn: ({ id, enabled }: { id: string; enabled: boolean }) =>
      apiPatch<UserResponse>(`${BASE}/${id}`, { enabled }),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['users'] }),
  });

  const deleteMutation = useMutation({
    mutationFn: (id: string) => apiDelete(`${BASE}/${id}`),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['users'] }),
  });

  const updateRolesMutation = useMutation({
    mutationFn: ({ id, roleIds }: { id: string; roleIds: string[] }) =>
      apiPatch<UserResponse>(`${BASE}/${id}`, { roleIds }),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['users'] }); setEditUser(null); },
  });

  const CREATE_DEFAULTS = { username: '', email: '', password: '' };
  const { control, handleSubmit, reset, formState: { errors } } = useForm({
    defaultValues: CREATE_DEFAULTS,
  });

  const openCreate = () => { reset(CREATE_DEFAULTS); setShowCreate(true); };

  const roleOptions = roles.map((r) => ({ label: r.name, value: r.id }));

  const toolbarEnd = (
    <div className="flex gap-2">
      <span className="p-input-icon-left">
        <i className="pi pi-search" style={{ left: '10px', top: '45%' }} />
        <InputText placeholder="Buscar..." value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          style={{ paddingLeft: '2rem', width: '220px' }} />
      </span>
      <Button label="Nuevo" icon="pi pi-plus" onClick={openCreate} />
    </div>
  );

  return (
    <div className="flex flex-column gap-3">
      <h3 className="gth-page-title">Usuarios</h3>

      <Toolbar end={toolbarEnd} className="surface-card border-round" />

      <DataTable value={users} loading={isLoading} paginator rows={10} rowsPerPageOptions={[5, 10, 20]}
        emptyMessage="No hay usuarios" className="surface-card border-round" size="small" stripedRows
      >
        <Column field="username" header="Usuario" sortable />
        <Column field="email" header="Email" sortable />
        <Column field="enabled" header="Estado" body={enabledBody} sortable />
        <Column field="createdAt" header="Creado" body={dateBody} sortable />
        <Column header="Acciones" body={(row: UserResponse) => (
          <div className="flex gap-2">
            <Button icon={row.enabled ? 'pi pi-eye-slash' : 'pi pi-eye'} text rounded size="small"
              severity={row.enabled ? 'warning' : 'success'}
              onClick={() => toggleMutation.mutate({ id: row.id, enabled: !row.enabled })}
              tooltip={row.enabled ? 'Desactivar' : 'Activar'} tooltipOptions={{ position: 'top' }} />
            <Button icon="pi pi-pencil" text rounded size="small"
              onClick={() => setEditUser(row)} tooltip="Editar roles" tooltipOptions={{ position: 'top' }} />
            <Button icon="pi pi-trash" text rounded size="small" severity="danger"
              onClick={() => { if (confirm('¿Eliminar usuario?')) deleteMutation.mutate(row.id); }}
              tooltip="Eliminar" tooltipOptions={{ position: 'top' }} />
          </div>
        )} />
      </DataTable>

      {/* Create dialog */}
      <Dialog header="Nuevo Usuario" visible={showCreate} onHide={() => setShowCreate(false)}
        style={{ width: '400px' }}
        footer={
          <div className="flex justify-content-end gap-2">
            <Button label="Cancelar" outlined onClick={() => setShowCreate(false)} />
            <Button label="Crear" icon="pi pi-check" onClick={handleSubmit((d) => createMutation.mutate(d))} />
          </div>
        }
      >
        <form className="flex flex-column gap-3">
          <div className="flex flex-column gap-1">
            <label className="text-sm font-semibold">Usuario *</label>
            <Controller name="username" control={control} rules={{ required: 'Requerido', maxLength: { value: 120, message: 'Máx 120' } }}
              render={({ field }) => <InputText {...field} className={errors.username ? 'p-invalid' : ''} />} />
            {errors.username && <small className="p-error">{errors.username.message}</small>}
          </div>
          <div className="flex flex-column gap-1">
            <label className="text-sm font-semibold">Email *</label>
            <Controller name="email" control={control} rules={{ required: 'Requerido', pattern: { value: /^[^\s@]+@[^\s@]+\.[^\s@]+$/, message: 'Email inválido' } }}
              render={({ field }) => <InputText {...field} className={errors.email ? 'p-invalid' : ''} />} />
            {errors.email && <small className="p-error">{errors.email.message || 'Requerido'}</small>}
          </div>
          <div className="flex flex-column gap-1">
            <label className="text-sm font-semibold">Contraseña</label>
            <Controller name="password" control={control}
              render={({ field }) => <InputText {...field} type="password" />} />
          </div>
        </form>
      </Dialog>

      {/* Edit roles dialog */}
      <Dialog header={`Roles: ${editUser?.username}`} visible={!!editUser} onHide={() => setEditUser(null)}
        style={{ width: '440px' }}
        footer={
          <div className="flex justify-content-end gap-2">
            <Button label="Cancelar" outlined onClick={() => setEditUser(null)} />
            <Button label="Guardar" icon="pi pi-check"
              onClick={() => {
                if (editUser) {
                  updateRolesMutation.mutate({ id: editUser.id, roleIds: editUser.roleIds });
                }
              }} />
          </div>
        }
      >
        {editUser && (
          <div className="flex flex-column gap-3">
            <div>
              <label className="text-sm font-semibold mb-2 block">Roles asignados</label>
              <MultiSelect value={editUser.roleIds} options={roleOptions}
                onChange={(e) => setEditUser({ ...editUser, roleIds: e.value })}
                placeholder="Seleccione roles" className="w-full" display="chip" />
            </div>
          </div>
        )}
      </Dialog>
    </div>
  );
}
