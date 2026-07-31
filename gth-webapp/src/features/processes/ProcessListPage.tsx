import { useState } from 'react';
import { Link } from 'react-router-dom';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import { Dropdown } from 'primereact/dropdown';
import { InputText } from 'primereact/inputtext';
import { InputTextarea } from 'primereact/inputtextarea';
import { Toolbar } from 'primereact/toolbar';
import { Tag } from 'primereact/tag';
import { useForm, Controller } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import dayjs from 'dayjs';
import { apiGet, apiPost, apiPatch } from '../../core/api/client';
import type { ProcessResponse, ProcessRequest } from './types';
import { PROCESS_STATUSES } from './types';

const BASE_PATH = '/recruitment/api/v1/recruitment/processes';

const createSchema = z.object({
  name: z.string().min(1, 'Requerido').max(140),
  code: z.string().min(1, 'Requerido').max(50),
  description: z.string().optional().default(''),
});

type CreateForm = z.infer<typeof createSchema>;

const STATUS_TAGS: Record<string, { severity: 'info' | 'success' | 'warn' | 'danger'; label: string }> = {
  ACTIVE: { severity: 'success', label: 'Activo' },
  INACTIVE: { severity: 'danger', label: 'Inactivo' },
  COMPLETED: { severity: 'info', label: 'Completado' },
};

function statusBody(row: ProcessResponse) {
  const tag = STATUS_TAGS[row.status] ?? { severity: 'info', label: row.status };
  return <Tag severity={tag.severity as never} value={tag.label} />;
}

function dateBody(row: ProcessResponse) {
  return dayjs(row.createdAt).format('DD/MM/YYYY');
}

export function ProcessListPage() {
  const queryClient = useQueryClient();
  const [filterStatus, setFilterStatus] = useState<string | null>(null);
  const [showCreate, setShowCreate] = useState(false);

  const { data: processes = [], isLoading } = useQuery({
    queryKey: ['processes', filterStatus],
    queryFn: () => {
      const param = filterStatus ? `?status=${filterStatus}` : '';
      return apiGet<ProcessResponse[]>(`${BASE_PATH}${param}`);
    },
  });

  const createMutation = useMutation({
    mutationFn: (req: ProcessRequest) => apiPost<ProcessResponse>(BASE_PATH, req),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['processes'] });
      setShowCreate(false);
    },
  });

  const statusMutation = useMutation({
    mutationFn: ({ id, status }: { id: string; status: string }) =>
      apiPatch<ProcessResponse>(`${BASE_PATH}/${id}/status?status=${status}`),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['processes'] });
    },
  });

  const { control, handleSubmit, reset, formState: { errors, isSubmitting } } = useForm<CreateForm>({
    resolver: zodResolver(createSchema),
    defaultValues: { name: '', code: '', description: '' },
  });

  const openCreate = () => {
    reset();
    setShowCreate(true);
  };

  const toolbarEnd = (
    <Button label="Nuevo" icon="pi pi-plus" onClick={openCreate} />
  );

  return (
    <div className="flex flex-column gap-3">
      <h3 className="m-0">Procesos</h3>

      <Toolbar end={toolbarEnd} className="surface-card border-round" />

      <div className="flex align-items-center gap-2">
        <label className="text-sm font-medium">Estado:</label>
        <Dropdown
          value={filterStatus}
          options={[
            { label: 'Todos', value: null },
            ...PROCESS_STATUSES.map((s) => ({ label: s.label, value: s.value })),
          ]}
          onChange={(e) => setFilterStatus(e.value)}
          className="w-10rem"
        />
      </div>

      <DataTable
        value={processes}
        loading={isLoading}
        paginator
        rows={10}
        rowsPerPageOptions={[5, 10, 20]}
        emptyMessage="No hay procesos"
        className="surface-card border-round"
        size="small"
        stripedRows
      >
        <Column
          header="Nombre"
          body={(row: ProcessResponse) => (
            <Link to={`/processes/${row.id}`} className="text-primary font-medium">
              {row.name}
            </Link>
          )}
          sortable
        />
        <Column field="code" header="Código" sortable />
        <Column field="status" header="Estado" body={statusBody} sortable />
        <Column field="createdAt" header="Fecha" body={dateBody} sortable />
        <Column
          header="Cambiar estado"
          body={(row: ProcessResponse) => (
            <Dropdown
              value={row.status}
              options={PROCESS_STATUSES.map((s) => ({ label: s.label, value: s.value }))}
              onChange={(e) => statusMutation.mutate({ id: row.id, status: e.value })}
              className="w-8rem"
              disabled={statusMutation.isPending}
            />
          )}
        />
      </DataTable>

      <Dialog
        header="Nuevo Proceso"
        visible={showCreate}
        onHide={() => setShowCreate(false)}
        style={{ width: '480px' }}
        footer={
          <div className="flex justify-content-end gap-2">
            <Button label="Cancelar" outlined onClick={() => setShowCreate(false)} />
            <Button label="Crear" icon="pi pi-check" loading={isSubmitting} onClick={handleSubmit((data) => createMutation.mutate(data))} />
          </div>
        }
      >
        <form className="flex flex-column gap-3">
          <div className="flex flex-column gap-1">
            <label className="text-sm font-medium">Nombre *</label>
            <Controller name="name" control={control} render={({ field }) => (
              <InputText {...field} className={errors.name ? 'p-invalid' : ''} maxLength={140} />
            )} />
            {errors.name && <small className="p-error">{errors.name.message}</small>}
          </div>
          <div className="flex flex-column gap-1">
            <label className="text-sm font-medium">Código *</label>
            <Controller name="code" control={control} render={({ field }) => (
              <InputText {...field} className={errors.code ? 'p-invalid' : ''} maxLength={50} />
            )} />
            {errors.code && <small className="p-error">{errors.code.message}</small>}
          </div>
          <div className="flex flex-column gap-1">
            <label className="text-sm font-medium">Descripción</label>
            <Controller name="description" control={control} render={({ field }) => (
              <InputTextarea {...field} rows={3} />
            )} />
          </div>
        </form>
      </Dialog>
    </div>
  );
}
