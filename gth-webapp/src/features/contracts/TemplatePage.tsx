import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { InputTextarea } from 'primereact/inputtextarea';
import { InputNumber } from 'primereact/inputnumber';
import { Toolbar } from 'primereact/toolbar';
import { Tag } from 'primereact/tag';
import { useForm, Controller } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import dayjs from 'dayjs';
import { apiGet, apiPost } from '../../core/api/client';
import type { TemplateResponse, TemplateRequest } from './types';

const BASE = '/contract/api/v1/contracts/templates';

const createSchema = z.object({
  name: z.string().min(1, 'Requerido').max(140),
  version: z.number().int().min(1, '>= 1'),
  content: z.string().min(1, 'Requerido'),
  fileName: z.string().optional().default(''),
  status: z.string().optional().default('ACTIVE'),
  createdBy: z.string().min(1, 'Requerido'),
});

type CreateForm = z.infer<typeof createSchema>;

function dateBodyRow(row: TemplateResponse) {
  return row.createdAt ? dayjs(row.createdAt).format('DD/MM/YYYY HH:mm') : '—';
}

export function TemplatePage() {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const [showCreate, setShowCreate] = useState(false);

  const { data: templates = [], isLoading } = useQuery({
    queryKey: ['templates'],
    queryFn: () => apiGet<TemplateResponse[]>(BASE),
  });

  const createMutation = useMutation({
    mutationFn: (req: TemplateRequest) => apiPost<TemplateResponse>(BASE, req),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['templates'] });
      setShowCreate(false);
    },
  });

  const { control, handleSubmit, reset, formState: { errors } } = useForm<CreateForm>({
    resolver: zodResolver(createSchema),
    defaultValues: { name: '', version: 1, content: '', fileName: '', status: 'ACTIVE', createdBy: '' },
  });

  const openCreate = () => {
    reset({ name: '', version: 1, content: '', fileName: '', status: 'ACTIVE', createdBy: '' });
    setShowCreate(true);
  };

  return (
    <div className="flex flex-column gap-3">
      <div className="flex align-items-center gap-2">
        <Button icon="pi pi-arrow-left" text rounded onClick={() => navigate('/contracts')} />
        <h3 className="m-0">Plantillas de Contrato</h3>
      </div>

      <Toolbar end={<Button label="Nueva" icon="pi pi-plus" onClick={openCreate} />}
        className="surface-card border-round" />

      <DataTable value={templates} loading={isLoading} paginator rows={10}
        emptyMessage="No hay plantillas" className="surface-card border-round" size="small" stripedRows
      >
        <Column field="name" header="Nombre" sortable />
        <Column field="version" header="Versión" sortable />
        <Column field="fileName" header="Archivo" sortable />
        <Column field="status" header="Estado" body={(r: TemplateResponse) => (
          <Tag severity={r.status === 'ACTIVE' ? 'success' : 'danger'} value={r.status} />
        )} sortable />
        <Column field="createdAt" header="Fecha" body={dateBodyRow} sortable />
        <Column field="createdBy" header="Creado por" sortable />
      </DataTable>

      <Dialog header="Nueva Plantilla" visible={showCreate} onHide={() => setShowCreate(false)}
        style={{ width: '600px' }}
        footer={
          <div className="flex justify-content-end gap-2">
            <Button label="Cancelar" outlined onClick={() => setShowCreate(false)} />
            <Button label="Crear" icon="pi pi-check" onClick={handleSubmit((d) => createMutation.mutate(d))} />
          </div>
        }
      >
        <form className="flex flex-column gap-3">
          <div className="grid">
            <div className="col-8">
              <div className="flex flex-column gap-1">
                <label className="text-sm font-medium">Nombre *</label>
                <Controller name="name" control={control} render={({ field }) => (
                  <InputText {...field} className={errors.name ? 'p-invalid' : ''} maxLength={140} />
                )} />
                {errors.name && <small className="p-error">{errors.name.message}</small>}
              </div>
            </div>
            <div className="col-4">
              <div className="flex flex-column gap-1">
                <label className="text-sm font-medium">Versión *</label>
                <Controller name="version" control={control} render={({ field }) => (
                  <InputNumber {...field} value={field.value} onValueChange={(e) => field.onChange(e.value ?? 1)} min={1} />
                )} />
                {errors.version && <small className="p-error">{errors.version.message}</small>}
              </div>
            </div>
          </div>
          <div className="flex flex-column gap-1">
            <label className="text-sm font-medium">Nombre de archivo</label>
            <Controller name="fileName" control={control} render={({ field }) => (
              <InputText {...field} />
            )} />
          </div>
          <div className="flex flex-column gap-1">
            <label className="text-sm font-medium">Contenido *</label>
            <Controller name="content" control={control} render={({ field }) => (
              <InputTextarea {...field} rows={6} className={errors.content ? 'p-invalid' : ''} />
            )} />
            {errors.content && <small className="p-error">{errors.content.message}</small>}
          </div>
          <div className="flex flex-column gap-1">
            <label className="text-sm font-medium">Creado por *</label>
            <Controller name="createdBy" control={control} render={({ field }) => (
              <InputText {...field} className={errors.createdBy ? 'p-invalid' : ''} />
            )} />
            {errors.createdBy && <small className="p-error">{errors.createdBy.message}</small>}
          </div>
        </form>
      </Dialog>
    </div>
  );
}
