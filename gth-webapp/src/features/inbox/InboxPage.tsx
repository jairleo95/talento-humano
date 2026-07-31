import { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import { Dropdown } from 'primereact/dropdown';
import { InputText } from 'primereact/inputtext';
import { Toolbar } from 'primereact/toolbar';
import { Tag } from 'primereact/tag';
import { useForm, Controller } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import dayjs from 'dayjs';
import { apiGet, apiPost, apiPatch } from '../../core/api/client';
import type { InboxItemResponse, InboxItemRequest } from './types';
import { INBOX_STATUSES } from './types';

const BASE_PATH = '/recruitment/api/v1/recruitment/inbox';

const assignSchema = z.object({
  requisitionId: z.string().min(1, 'Requerido'),
  processStepId: z.string().min(1, 'Requerido'),
  assignee: z.string().min(1, 'Requerido'),
});

type AssignForm = z.infer<typeof assignSchema>;

const STATUS_TAGS: Record<string, { severity: 'info' | 'success' | 'warn' | 'danger'; label: string }> = {
  PENDING: { severity: 'warn', label: 'Pendiente' },
  IN_PROGRESS: { severity: 'info', label: 'En curso' },
  COMPLETED: { severity: 'success', label: 'Completado' },
  CANCELLED: { severity: 'danger', label: 'Cancelado' },
};

function statusBody(row: InboxItemResponse) {
  const tag = STATUS_TAGS[row.status] ?? { severity: 'info', label: row.status };
  return <Tag severity={tag.severity as never} value={tag.label} />;
}

function dateBody(row: InboxItemResponse) {
  return dayjs(row.createdAt).format('DD/MM/YYYY HH:mm');
}

export function InboxPage() {
  const queryClient = useQueryClient();
  const [showAssign, setShowAssign] = useState(false);

  const { data: inbox = [], isLoading } = useQuery({
    queryKey: ['inbox'],
    queryFn: () => apiGet<InboxItemResponse[]>(BASE_PATH),
  });

  const assignMutation = useMutation({
    mutationFn: (req: InboxItemRequest) => apiPost<InboxItemResponse>(BASE_PATH, req),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['inbox'] });
      setShowAssign(false);
    },
  });

  const statusMutation = useMutation({
    mutationFn: ({ id, status }: { id: string; status: string }) =>
      apiPatch<InboxItemResponse>(`${BASE_PATH}/${id}/status?status=${status}`),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['inbox'] });
    },
  });

  const { control, handleSubmit, reset, formState: { errors, isSubmitting } } = useForm<AssignForm>({
    resolver: zodResolver(assignSchema),
    defaultValues: { requisitionId: '', processStepId: '', assignee: '' },
  });

  const openAssign = () => {
    reset();
    setShowAssign(true);
  };

  const toolbarEnd = (
    <Button label="Asignar" icon="pi pi-plus" onClick={openAssign} />
  );

  return (
    <div className="flex flex-column gap-3">
      <h3 className="m-0">Bandeja de entrada</h3>

      <Toolbar end={toolbarEnd} className="surface-card border-round" />

      <DataTable
        value={inbox}
        loading={isLoading}
        paginator
        rows={10}
        rowsPerPageOptions={[5, 10, 20]}
        emptyMessage="No hay elementos en la bandeja"
        className="surface-card border-round"
        size="small"
        stripedRows
      >
        <Column field="assignee" header="Asignado a" sortable />
        <Column field="requisitionId" header="Requerimiento" sortable />
        <Column field="processStepId" header="Paso" sortable />
        <Column field="status" header="Estado" body={statusBody} sortable />
        <Column field="createdAt" header="Fecha" body={dateBody} sortable />
        <Column
          header="Cambiar estado"
          body={(row: InboxItemResponse) => (
            <Dropdown
              value={row.status}
              options={INBOX_STATUSES.map((s) => ({ label: s.label, value: s.value }))}
              onChange={(e) => statusMutation.mutate({ id: row.id, status: e.value })}
              className="w-9rem"
              disabled={statusMutation.isPending}
            />
          )}
        />
      </DataTable>

      <Dialog
        header="Asignar a Bandeja"
        visible={showAssign}
        onHide={() => setShowAssign(false)}
        style={{ width: '440px' }}
        footer={
          <div className="flex justify-content-end gap-2">
            <Button label="Cancelar" outlined onClick={() => setShowAssign(false)} />
            <Button label="Asignar" icon="pi pi-check" loading={isSubmitting} onClick={handleSubmit((data) => assignMutation.mutate(data))} />
          </div>
        }
      >
        <form className="flex flex-column gap-3">
          <div className="flex flex-column gap-1">
            <label className="text-sm font-medium">ID Requerimiento *</label>
            <Controller name="requisitionId" control={control} render={({ field }) => (
              <InputText {...field} className={errors.requisitionId ? 'p-invalid' : ''} />
            )} />
            {errors.requisitionId && <small className="p-error">{errors.requisitionId.message}</small>}
          </div>
          <div className="flex flex-column gap-1">
            <label className="text-sm font-medium">ID Paso *</label>
            <Controller name="processStepId" control={control} render={({ field }) => (
              <InputText {...field} className={errors.processStepId ? 'p-invalid' : ''} />
            )} />
            {errors.processStepId && <small className="p-error">{errors.processStepId.message}</small>}
          </div>
          <div className="flex flex-column gap-1">
            <label className="text-sm font-medium">Asignado a *</label>
            <Controller name="assignee" control={control} render={({ field }) => (
              <InputText {...field} className={errors.assignee ? 'p-invalid' : ''} />
            )} />
            {errors.assignee && <small className="p-error">{errors.assignee.message}</small>}
          </div>
        </form>
      </Dialog>
    </div>
  );
}
