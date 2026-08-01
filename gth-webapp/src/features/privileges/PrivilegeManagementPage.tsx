import { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { InputNumber } from 'primereact/inputnumber';
import { TabView, TabPanel } from 'primereact/tabview';
import { PickList } from 'primereact/picklist';
import { Toolbar } from 'primereact/toolbar';
import { useForm, Controller } from 'react-hook-form';
import { apiGet, apiPost, apiPatch } from '../../core/api/client';

interface PrivilegeResponse { id: string; code: string; description: string; linkUrl: string; icon: string; moduleName: string; sortOrder: number; }
interface RoleResponse { id: string; name: string; description: string; }

export function PrivilegeManagementPage() {
  const queryClient = useQueryClient();
  const [activeTab, setActiveTab] = useState(0);
  const [showPriv, setShowPriv] = useState(false);
  const [showRole, setShowRole] = useState(false);
  const [selectedRole, setSelectedRole] = useState<string | null>(null);
  const [target, setTarget] = useState<string[]>([]);
  const [source, setSource] = useState<string[]>([]);

  const { data: privileges = [], isLoading: l1 } = useQuery({
    queryKey: ['privileges-all'],
    queryFn: () => apiGet<PrivilegeResponse[]>('/identity/api/v1/privileges'),
  });

  const { data: roles = [], isLoading: l2 } = useQuery({
    queryKey: ['roles-all'],
    queryFn: () => apiGet<RoleResponse[]>('/identity/api/v1/roles'),
  });

  const privMutate = useMutation({
    mutationFn: (b: Record<string, unknown>) => apiPost('/identity/api/v1/privileges', b),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['privileges-all'] }); setShowPriv(false); },
  });

  const roleMutate = useMutation({
    mutationFn: (b: Record<string, unknown>) => apiPost('/identity/api/v1/roles', b),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['roles-all'] }); setShowRole(false); },
  });

  const { control: c1, handleSubmit: h1, reset: r1 } = useForm({
    defaultValues: { code: '', description: '', linkUrl: '', icon: '', moduleName: '', sortOrder: 0 },
  });
  const { control: c2, handleSubmit: h2, reset: r2 } = useForm({
    defaultValues: { name: '', description: '' },
  });

  const loadRolePrivs = async (roleId: string) => {
    setSelectedRole(roleId);
    const rolePrivs: PrivilegeResponse[] = await apiGet(`/identity/api/v1/roles/${roleId}/privileges`);
    const assignedIds = rolePrivs.map(p => p.id);
    setTarget(assignedIds);
    setSource(privileges.filter((p: PrivilegeResponse) => !assignedIds.includes(p.id)).map((p: PrivilegeResponse) => p.id));
  };

  const saveRolePrivs = async () => {
    if (!selectedRole) return;
    await apiPatch(`/identity/api/v1/roles/${selectedRole}/privileges`, { privilegeIds: target });
    queryClient.invalidateQueries({ queryKey: ['roles-all'] });
  };

  return (
    <div className="flex flex-column gap-3">
      <h3 className="gth-page-title">Roles y Privilegios</h3>

      <TabView activeIndex={activeTab} onTabChange={e => setActiveTab(e.index)}>
        <TabPanel header="Privilegios">
          <Toolbar className="surface-card border-round my-2"
            end={<Button label="Nuevo" icon="pi pi-plus" onClick={() => { r1({ code: '', description: '', linkUrl: '', icon: '', moduleName: '', sortOrder: 0 }); setShowPriv(true); }} />} />
          <DataTable value={privileges} loading={l1} paginator rows={15} size="small" stripedRows className="surface-card border-round" emptyMessage="No hay privilegios">
            <Column field="code" header="Código" sortable />
            <Column field="description" header="Descripción" sortable />
            <Column field="linkUrl" header="URL" sortable />
            <Column field="icon" header="Ícono" sortable />
            <Column field="moduleName" header="Módulo" sortable />
            <Column field="sortOrder" header="Orden" sortable style={{ width: '80px' }} />
          </DataTable>
        </TabPanel>

        <TabPanel header="Roles">
          <Toolbar className="surface-card border-round my-2"
            end={<Button label="Nuevo" icon="pi pi-plus" onClick={() => { r2({ name: '', description: '' }); setShowRole(true); }} />} />
          <DataTable value={roles} loading={l2} paginator rows={10} size="small" stripedRows className="surface-card border-round" emptyMessage="No hay roles">
            <Column field="name" header="Rol" sortable />
            <Column field="description" header="Descripción" sortable />
            <Column header="Acción" body={(r: RoleResponse) => (
              <Button label="Privilegios" icon="pi pi-key" size="small" outlined onClick={() => loadRolePrivs(r.id)} />
            )} />
          </DataTable>

          {selectedRole && (
            <div className="mt-3 surface-card border-round p-3">
              <h4 className="mt-0 mb-2">Asignar privilegios a: {roles.find((r: RoleResponse) => r.id === selectedRole)?.name}</h4>
              <PickList
                dataKey="id"
                source={source.map((id: string) => privileges.find((p: PrivilegeResponse) => p.id === id)).filter(Boolean)}
                target={target.map((id: string) => privileges.find((p: PrivilegeResponse) => p.id === id)).filter(Boolean)}
                onChange={e => { setSource(e.source.map((x: PrivilegeResponse) => x.id)); setTarget(e.target.map((x: PrivilegeResponse) => x.id)); }}
                itemTemplate={(p: PrivilegeResponse) => <span>{p.code} — {p.description}</span>}
                sourceHeader="Disponibles" targetHeader="Asignados"
                style={{ height: '300px' }}
              />
              <div className="flex justify-content-end mt-2">
                <Button label="Guardar" icon="pi pi-check" onClick={saveRolePrivs} />
              </div>
            </div>
          )}
        </TabPanel>
      </TabView>

      <Dialog header="Nuevo Privilegio" visible={showPriv} onHide={() => setShowPriv(false)} style={{ width: '500px' }}
        footer={<div className="flex justify-content-end gap-2"><Button label="Cancelar" outlined onClick={() => setShowPriv(false)} /><Button label="Crear" icon="pi pi-check" onClick={h1(d => privMutate.mutate(d))} /></div>}>
        <form className="flex flex-column gap-3">
          <div className="grid">
            <div className="col-6"><div className="flex flex-column gap-1"><label className="text-sm font-semibold">Código *</label><Controller name="code" control={c1} rules={{ required: true }} render={({ field }) => <InputText {...field} className="w-full" />} /></div></div>
            <div className="col-6"><div className="flex flex-column gap-1"><label className="text-sm font-semibold">Descripción *</label><Controller name="description" control={c1} rules={{ required: true }} render={({ field }) => <InputText {...field} className="w-full" />} /></div></div>
            <div className="col-6"><div className="flex flex-column gap-1"><label className="text-sm font-semibold">URL</label><Controller name="linkUrl" control={c1} render={({ field }) => <InputText {...field} className="w-full" />} /></div></div>
            <div className="col-6"><div className="flex flex-column gap-1"><label className="text-sm font-semibold">Ícono</label><Controller name="icon" control={c1} render={({ field }) => <InputText {...field} className="w-full" />} /></div></div>
            <div className="col-6"><div className="flex flex-column gap-1"><label className="text-sm font-semibold">Módulo</label><Controller name="moduleName" control={c1} render={({ field }) => <InputText {...field} className="w-full" />} /></div></div>
            <div className="col-6"><div className="flex flex-column gap-1"><label className="text-sm font-semibold">Orden</label><Controller name="sortOrder" control={c1} render={({ field }) => <InputNumber value={field.value} onValueChange={e => field.onChange(e.value ?? 0)} min={0} className="w-full" />} /></div></div>
          </div>
        </form>
      </Dialog>

      <Dialog header="Nuevo Rol" visible={showRole} onHide={() => setShowRole(false)} style={{ width: '400px' }}
        footer={<div className="flex justify-content-end gap-2"><Button label="Cancelar" outlined onClick={() => setShowRole(false)} /><Button label="Crear" icon="pi pi-check" onClick={h2(d => roleMutate.mutate(d))} /></div>}>
        <form className="flex flex-column gap-3">
          <div className="flex flex-column gap-1"><label className="text-sm font-semibold">Nombre *</label><Controller name="name" control={c2} rules={{ required: true }} render={({ field }) => <InputText {...field} className="w-full" />} /></div>
          <div className="flex flex-column gap-1"><label className="text-sm font-semibold">Descripción</label><Controller name="description" control={c2} render={({ field }) => <InputText {...field} className="w-full" />} /></div>
        </form>
      </Dialog>
    </div>
  );
}
