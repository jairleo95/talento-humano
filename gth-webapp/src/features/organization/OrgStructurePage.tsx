import { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { Dropdown } from 'primereact/dropdown';
import { Toolbar } from 'primereact/toolbar';
import { Tag } from 'primereact/tag';
import { useForm, Controller } from 'react-hook-form';
import { apiGet, apiPost, apiPatch } from '../../core/api/client';
import type { OrgUnitResponse } from './types';
import { UNIT_TYPES, TYPE_LABELS } from './types';

const BASE = '/recruitment/api/v1/recruitment/organizational-units';

export function OrgStructurePage() {
  const queryClient = useQueryClient();
  const [filterType, setFilterType] = useState<string | null>(null);
  const [showCreate, setShowCreate] = useState(false);

  const { data: units = [], isLoading } = useQuery({
    queryKey: ['org-units', filterType],
    queryFn: () => {
      const param = filterType ? `?type=${filterType}` : '';
      return apiGet<OrgUnitResponse[]>(`${BASE}${param}`);
    },
  });

  const createMutation = useMutation({
    mutationFn: (body: Record<string, unknown>) => apiPost<OrgUnitResponse>(BASE, body),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['org-units'] }); setShowCreate(false); },
  });

  const toggleMutation = useMutation({
    mutationFn: (id: string) => apiPatch(`${BASE}/${id}/toggle`),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['org-units'] }),
  });

  const DEFAULT = { name: '', shortName: '', unitType: 'DEPARTAMENTO', parentId: '', occupationGroupCode: '' };
  const { control, handleSubmit, reset } = useForm({ defaultValues: DEFAULT });

  const unitMap = Object.fromEntries(units.map((u) => [u.id, u]));

  const toolbarEnd = (
    <div className="flex gap-2">
      <Dropdown value={filterType}
        options={[{ label: 'Todos', value: null }, ...UNIT_TYPES.map((t) => ({ label: t.label, value: t.value }))]}
        onChange={(e) => setFilterType(e.value)} placeholder="Tipo" className="w-10rem" />
      <Button label="Nuevo" icon="pi pi-plus" onClick={() => { reset(DEFAULT); setShowCreate(true); }} />
    </div>
  );

  const parentOptions = units
    .map((u) => ({ label: `${TYPE_LABELS[u.unitType]}: ${u.name}`, value: u.id }));

  return (
    <div className="flex flex-column gap-3">
      <h3 className="gth-page-title">Estructura Organizacional</h3>
      <Toolbar end={toolbarEnd} className="surface-card border-round" />

      <DataTable value={units} loading={isLoading} paginator rows={15} rowsPerPageOptions={[10, 15, 25]}
        emptyMessage="No hay unidades" className="surface-card border-round" size="small" stripedRows
      >
        <Column field="shortName" header="Código" sortable style={{ width: '100px' }} />
        <Column field="name" header="Nombre" sortable />
        <Column body={(r: OrgUnitResponse) => TYPE_LABELS[r.unitType] || r.unitType} header="Tipo" sortable sortField="unitType" style={{ width: '130px' }} />
        <Column body={(r: OrgUnitResponse) => r.parentId ? unitMap[r.parentId]?.name || r.parentId.substring(0, 8)+'...' : '—'}
          header="Depende de" sortable sortField="parentId" />
        <Column body={(r: OrgUnitResponse) => r.occupationGroupCode || '—'}
          header="Grupo Ocup." style={{ width: '110px' }} />
        <Column body={(r: OrgUnitResponse) => (
          <Tag severity={r.isActive ? 'success' : 'danger'} value={r.isActive ? 'Activo' : 'Inactivo'} />
        )} header="Estado" sortable sortField="isActive" style={{ width: '100px' }} />
        <Column body={(r: OrgUnitResponse) => (
          <Button icon="pi pi-power-off" text rounded size="small"
            severity={r.isActive ? 'danger' : 'success'}
            onClick={() => toggleMutation.mutate(r.id)}
            tooltip={r.isActive ? 'Desactivar' : 'Activar'} tooltipOptions={{ position: 'top' }} />
        )} header="Acción" style={{ width: '70px' }} />
      </DataTable>

      <Dialog header="Nueva Unidad" visible={showCreate} onHide={() => setShowCreate(false)} style={{ width: '440px' }}
        footer={
          <div className="flex justify-content-end gap-2">
            <Button label="Cancelar" outlined onClick={() => setShowCreate(false)} />
            <Button label="Crear" icon="pi pi-check" onClick={handleSubmit((d) => createMutation.mutate({...d, parentId: d.parentId || null}))} />
          </div>
        }
      >
        <form className="flex flex-column gap-3">
          <div className="flex flex-column gap-1">
            <label className="text-sm font-semibold">Tipo *</label>
            <Controller name="unitType" control={control}
              render={({ field }) => (
                <Dropdown value={field.value} options={UNIT_TYPES.map((t) => ({ label: t.label, value: t.value }))}
                  onChange={(e) => field.onChange(e.value)} className="w-full" />
              )} />
          </div>
          <div className="flex flex-column gap-1">
            <label className="text-sm font-semibold">Nombre *</label>
            <Controller name="name" control={control} rules={{ required: true }}
              render={({ field }) => <InputText {...field} className="w-full" />} />
          </div>
          <div className="flex flex-column gap-1">
            <label className="text-sm font-semibold">Nombre Corto</label>
            <Controller name="shortName" control={control}
              render={({ field }) => <InputText {...field} className="w-full" />} />
          </div>
          <div className="flex flex-column gap-1">
            <label className="text-sm font-semibold">Depende de</label>
            <Controller name="parentId" control={control}
              render={({ field }) => (
                <Dropdown value={field.value || null} options={[{ label: '— Ninguno (raíz) —', value: '' }, ...parentOptions]}
                  onChange={(e) => field.onChange(e.value || '')} className="w-full" filter />
              )} />
          </div>
          <div className="flex flex-column gap-1">
            <label className="text-sm font-semibold">Grupo Ocupacional</label>
            <Controller name="occupationGroupCode" control={control}
              render={({ field }) => <InputText {...field} className="w-full" />} />
          </div>
        </form>
      </Dialog>
    </div>
  );
}
