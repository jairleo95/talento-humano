import { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { InputNumber } from 'primereact/inputnumber';
import { Toolbar } from 'primereact/toolbar';
import { useForm, Controller } from 'react-hook-form';
import dayjs from 'dayjs';
import { apiGet, apiPost } from '../../core/api/client';
import type { CostCenterResponse } from './types';
import { VALIDATION_RULES } from '../../shared/validations';

const BASE = '/recruitment/api/v1/recruitment/cost-centers';
const VR = VALIDATION_RULES;

function dateBody(row: CostCenterResponse) {
  return dayjs(row.createdAt).format('DD/MM/YYYY');
}

export function CostCenterPage() {
  const queryClient = useQueryClient();
  const [showCreate, setShowCreate] = useState(false);

  const { data: centers = [], isLoading } = useQuery({
    queryKey: ['cost-centers'],
    queryFn: () => apiGet<CostCenterResponse[]>(BASE),
  });

  const createMutation = useMutation({
    mutationFn: (body: Record<string, unknown>) => apiPost<CostCenterResponse>(BASE, body),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['cost-centers'] }); setShowCreate(false); },
  });

  const DEFAULT = { code: '', name: '', departmentId: '', percentage: 100.0 };
  const { control, handleSubmit, reset, formState: { errors } } = useForm({ defaultValues: DEFAULT });

  const toolbarEnd = (
    <Button label="Nuevo" icon="pi pi-plus" onClick={() => { reset(DEFAULT); setShowCreate(true); }} />
  );

  return (
    <div className="flex flex-column gap-3">
      <h3 className="gth-page-title">Centros de Costo</h3>
      <Toolbar end={toolbarEnd} className="surface-card border-round" />

      <DataTable value={centers} loading={isLoading} paginator rows={10} rowsPerPageOptions={[5, 10, 20]}
        emptyMessage="No hay centros de costo" className="surface-card border-round" size="small" stripedRows
      >
        <Column field="code" header="Código" sortable />
        <Column field="name" header="Nombre" sortable />
        <Column field="departmentId" header="Departamento" sortable />
        <Column field="percentage" header="%" body={(r: CostCenterResponse) => `${r.percentage}%`} sortable />
        <Column field="createdAt" header="Creado" body={dateBody} sortable />
      </DataTable>

      <Dialog header="Nuevo Centro de Costo" visible={showCreate} onHide={() => setShowCreate(false)}
        style={{ width: '440px' }}
        footer={
          <div className="flex justify-content-end gap-2">
            <Button label="Cancelar" outlined onClick={() => setShowCreate(false)} />
            <Button label="Crear" icon="pi pi-check" onClick={handleSubmit((d) => createMutation.mutate(d))} />
          </div>
        }
      >
        <form className="flex flex-column gap-3">
          <div className="flex flex-column gap-1">
            <label className="text-sm font-semibold">Código *</label>
            <Controller name="code" control={control} rules={{ required: 'Requerido', maxLength: { value: VR.CODE_MAX, message: `Máx ${VR.CODE_MAX}` } }}
              render={({ field }) => <InputText {...field} className={errors.code ? 'p-invalid' : ''} />} />
            {errors.code && <small className="p-error">{errors.code.message}</small>}
          </div>
          <div className="flex flex-column gap-1">
            <label className="text-sm font-semibold">Nombre *</label>
            <Controller name="name" control={control} rules={{ required: 'Requerido', maxLength: { value: VR.NAME_MAX, message: `Máx ${VR.NAME_MAX}` } }}
              render={({ field }) => <InputText {...field} className={errors.name ? 'p-invalid' : ''} />} />
            {errors.name && <small className="p-error">{errors.name.message}</small>}
          </div>
          <div className="flex flex-column gap-1">
            <label className="text-sm font-semibold">Departamento</label>
            <Controller name="departmentId" control={control}
              render={({ field }) => <InputText {...field} />} />
          </div>
          <div className="flex flex-column gap-1">
            <label className="text-sm font-semibold">Porcentaje (%)</label>
            <Controller name="percentage" control={control}
              render={({ field }) => (
                <InputNumber value={field.value} onValueChange={(e) => field.onChange(e.value ?? 0)}
                  min={0} max={100} className="w-full" />
              )} />
          </div>
        </form>
      </Dialog>
    </div>
  );
}
