import { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { InputTextarea } from 'primereact/inputtextarea';
import { InputNumber } from 'primereact/inputnumber';
import { Tag } from 'primereact/tag';
import { useForm, Controller } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import dayjs from 'dayjs';
import { apiGet, apiPost } from '../../core/api/client';
import type { ProcessResponse, ProcessStepResponse, ProcessStepRequest } from './types';

const BASE_PATH = '/recruitment/api/v1/recruitment/processes';

const stepSchema = z.object({
  name: z.string().min(1, 'Requerido'),
  code: z.string().min(1, 'Requerido'),
  description: z.string().optional().default(''),
  orderIndex: z.number().min(0, '>= 0'),
  slaHours: z.number().min(0).optional().default(0),
});

type StepForm = z.infer<typeof stepSchema>;

const STEP_STATUS_TAGS: Record<string, { severity: 'info' | 'success' | 'warn' | 'danger'; label: string }> = {
  PENDING: { severity: 'warn', label: 'Pendiente' },
  IN_PROGRESS: { severity: 'info', label: 'En curso' },
  COMPLETED: { severity: 'success', label: 'Completado' },
  SKIPPED: { severity: 'danger', label: 'Omitido' },
};

function stepStatusBody(row: ProcessStepResponse) {
  const tag = STEP_STATUS_TAGS[row.status] ?? { severity: 'info', label: row.status };
  return <Tag severity={tag.severity as never} value={tag.label} />;
}

function stepDateBody(row: ProcessStepResponse) {
  return dayjs(row.createdAt).format('DD/MM/YYYY HH:mm');
}

export function ProcessDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const [showAddStep, setShowAddStep] = useState(false);

  const { data: process, isLoading: loadingProcess } = useQuery({
    queryKey: ['process', id],
    queryFn: () => apiGet<ProcessResponse>(`${BASE_PATH}/${id}`),
    enabled: !!id,
  });

  const { data: steps = [], isLoading: loadingSteps } = useQuery({
    queryKey: ['process-steps', id],
    queryFn: () => apiGet<ProcessStepResponse[]>(`${BASE_PATH}/${id}/steps`),
    enabled: !!id,
  });

  const addStepMutation = useMutation({
    mutationFn: (req: ProcessStepRequest) =>
      apiPost<ProcessStepResponse>(`${BASE_PATH}/${id}/steps`, req),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['process-steps', id] });
      setShowAddStep(false);
    },
  });

  const { control, handleSubmit, reset, formState: { errors, isSubmitting } } = useForm<StepForm>({
    resolver: zodResolver(stepSchema),
    defaultValues: { name: '', code: '', description: '', orderIndex: 0, slaHours: 0 },
  });

  const openAddStep = () => {
    reset({ name: '', code: '', description: '', orderIndex: steps.length, slaHours: 0 });
    setShowAddStep(true);
  };

  if (loadingProcess) {
    return (
      <div className="flex align-items-center justify-content-center p-5">
        <i className="pi pi-spin pi-spinner" style={{ fontSize: '2rem' }} />
      </div>
    );
  }

  return (
    <div className="flex flex-column gap-3">
      <div className="flex align-items-center gap-2">
        <Button icon="pi pi-arrow-left" text rounded onClick={() => navigate('/processes')} />
        <h3 className="m-0">{process?.name}</h3>
      </div>

      <div className="surface-card border-round p-3 flex flex-wrap gap-3">
        <div>
          <label className="text-xs text-color-secondary">Código</label>
          <p className="m-0 font-medium">{process?.code}</p>
        </div>
        <div>
          <label className="text-xs text-color-secondary">Descripción</label>
          <p className="m-0">{process?.description || '—'}</p>
        </div>
        <div>
          <label className="text-xs text-color-secondary">Estado</label>
          <p className="m-0"><Tag severity="info" value={process?.status} /></p>
        </div>
      </div>

      <div className="flex align-items-center justify-content-between">
        <h4 className="m-0">Pasos del proceso</h4>
        <Button label="Agregar paso" icon="pi pi-plus" size="small" onClick={openAddStep} />
      </div>

      <DataTable
        value={steps}
        loading={loadingSteps}
        emptyMessage="No hay pasos definidos"
        className="surface-card border-round"
        size="small"
        stripedRows
      >
        <Column field="orderIndex" header="#" style={{ width: '4rem' }} />
        <Column field="name" header="Paso" sortable />
        <Column field="code" header="Código" sortable />
        <Column field="status" header="Estado" body={stepStatusBody} sortable />
        <Column field="slaHours" header="SLA (h)" sortable />
        <Column field="createdAt" header="Creado" body={stepDateBody} sortable />
      </DataTable>

      <Dialog
        header="Agregar Paso"
        visible={showAddStep}
        onHide={() => setShowAddStep(false)}
        style={{ width: '480px' }}
        footer={
          <div className="flex justify-content-end gap-2">
            <Button label="Cancelar" outlined onClick={() => setShowAddStep(false)} />
            <Button label="Agregar" icon="pi pi-check" loading={isSubmitting} onClick={handleSubmit((data) => addStepMutation.mutate(data))} />
          </div>
        }
      >
        <form className="flex flex-column gap-3">
          <div className="flex flex-column gap-1">
            <label className="text-sm font-medium">Nombre *</label>
            <Controller name="name" control={control} render={({ field }) => (
              <InputText {...field} className={errors.name ? 'p-invalid' : ''} />
            )} />
            {errors.name && <small className="p-error">{errors.name.message}</small>}
          </div>
          <div className="flex flex-column gap-1">
            <label className="text-sm font-medium">Código *</label>
            <Controller name="code" control={control} render={({ field }) => (
              <InputText {...field} className={errors.code ? 'p-invalid' : ''} />
            )} />
            {errors.code && <small className="p-error">{errors.code.message}</small>}
          </div>
          <div className="flex flex-column gap-1">
            <label className="text-sm font-medium">Descripción</label>
            <Controller name="description" control={control} render={({ field }) => (
              <InputTextarea {...field} rows={2} />
            )} />
          </div>
          <div className="flex gap-3">
            <div className="flex flex-column gap-1 flex-1">
              <label className="text-sm font-medium">Orden *</label>
              <Controller name="orderIndex" control={control} render={({ field }) => (
                <InputNumber {...field} onValueChange={(e) => field.onChange(e.value)} min={0} className={errors.orderIndex ? 'p-invalid' : ''} />
              )} />
              {errors.orderIndex && <small className="p-error">{errors.orderIndex.message}</small>}
            </div>
            <div className="flex flex-column gap-1 flex-1">
              <label className="text-sm font-medium">SLA (horas)</label>
              <Controller name="slaHours" control={control} render={({ field }) => (
                <InputNumber {...field} onValueChange={(e) => field.onChange(e.value ?? 0)} min={0} />
              )} />
            </div>
          </div>
        </form>
      </Dialog>
    </div>
  );
}
