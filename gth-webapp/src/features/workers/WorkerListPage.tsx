import { useState } from 'react';
import { Link } from 'react-router-dom';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { Dropdown } from 'primereact/dropdown';
import { Toolbar } from 'primereact/toolbar';
import { useForm, Controller } from 'react-hook-form';
import dayjs from 'dayjs';
import { apiGet, apiPost } from '../../core/api/client';
import type { WorkerResponse } from './types';

const BASE = '/recruitment/api/v1/recruitment/workers';

const GENDER_OPTIONS = [
  { label: 'Masculino', value: 'M' },
  { label: 'Femenino', value: 'F' },
];

const CIVIL_OPTIONS = [
  { label: 'Soltero', value: 'SOLTERO' },
  { label: 'Casado', value: 'CASADO' },
  { label: 'Divorciado', value: 'DIVORCIADO' },
  { label: 'Viudo', value: 'VIUDO' },
];

const PENSION_OPTIONS = [
  { label: 'AFP', value: 'AFP' },
  { label: 'SNP', value: 'SNP' },
];

const EDU_OPTIONS = [
  { label: 'Universitario', value: 'UNIVERSITARIO' },
  { label: 'Terciario', value: 'TERCIARIO' },
  { label: 'Secundario', value: 'SECUNDARIO' },
  { label: 'Primario', value: 'PRIMARIO' },
];

const DOC_TYPES = [
  { label: 'DNI', value: 'DNI' },
  { label: 'CE', value: 'CE' },
];

function dateBody(val: string) {
  return val ? dayjs(val).format('DD/MM/YYYY') : '—';
}

export function WorkerListPage() {
  const queryClient = useQueryClient();
  const [searchTerm, setSearchTerm] = useState('');
  const [showCreate, setShowCreate] = useState(false);

  const { data: workers = [], isLoading } = useQuery({
    queryKey: ['workers', searchTerm],
    queryFn: () => {
      const param = searchTerm ? `?search=${encodeURIComponent(searchTerm)}` : '';
      return apiGet<WorkerResponse[]>(`${BASE}${param}`);
    },
  });

  const createMutation = useMutation({
    mutationFn: (body: Record<string, unknown>) => apiPost<WorkerResponse>(BASE, body),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['workers'] }); setShowCreate(false); },
  });

  const DEFAULT = { firstName: '', lastNamePaternal: '', lastNameMaternal: '', documentType: 'DNI', documentNumber: '',
    birthDate: '', gender: 'M', civilStatus: 'SOLTERO', phone: '', email: '', address: '', districtId: '',
    educationLevel: '', degree: '', professionalTitle: '', pensionSystem: 'AFP', legacyId: '' };
  const { control, handleSubmit, reset } = useForm({ defaultValues: DEFAULT });

  const toolbarEnd = (
    <div className="flex gap-2">
      <span className="p-input-icon-left">
        <i className="pi pi-search" style={{ left: '10px', top: '45%' }} />
        <InputText placeholder="Buscar..." value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)} style={{ paddingLeft: '2rem', width: '220px' }} />
      </span>
      <Button label="Nuevo" icon="pi pi-plus" onClick={() => { reset(DEFAULT); setShowCreate(true); }} />
    </div>
  );

  return (
    <div className="flex flex-column gap-3">
      <h3 className="gth-page-title">Trabajadores</h3>
      <Toolbar end={toolbarEnd} className="surface-card border-round" />

      <DataTable value={workers} loading={isLoading} paginator rows={10} rowsPerPageOptions={[5, 10, 20]}
        emptyMessage="No hay trabajadores" className="surface-card border-round" size="small" stripedRows
      >
        <Column field="documentNumber" header="Documento" sortable />
        <Column header="Nombre" body={(r: WorkerResponse) => (
          <Link to={`/workers/${r.id}`} className="text-primary font-medium">
            {r.lastNamePaternal} {r.lastNameMaternal || ''} {r.firstName}
          </Link>
        )} sortable sortField="lastNamePaternal" />
        <Column field="email" header="Email" sortable />
        <Column field="phone" header="Teléfono" sortable />
        <Column field="professionalTitle" header="Título" sortable />
        <Column field="createdAt" header="Registrado" body={(r: WorkerResponse) => dateBody(r.createdAt)} sortable />
      </DataTable>

      <Dialog header="Nuevo Trabajador" visible={showCreate} onHide={() => setShowCreate(false)}
        style={{ width: '640px' }} maximizable
        footer={
          <div className="flex justify-content-end gap-2">
            <Button label="Cancelar" outlined onClick={() => setShowCreate(false)} />
            <Button label="Crear" icon="pi pi-check" onClick={handleSubmit((d) => createMutation.mutate(d))} />
          </div>
        }
      >
        <form className="flex flex-column gap-3" style={{ maxHeight: '60vh', overflowY: 'auto' }}>

            <div className="gth-section">
              <div className="gth-section-title"><i className="pi pi-user" /> Datos personales</div>
            <div className="grid">
              <div className="col-4">
                <label className="text-xs font-semibold">Nombres *</label>
                <Controller name="firstName" control={control} rules={{ required: true }} render={({ field }) => <InputText {...field} className="w-full" />} />
              </div>
              <div className="col-4">
                <label className="text-xs font-semibold">Ap. Paterno *</label>
                <Controller name="lastNamePaternal" control={control} rules={{ required: true }} render={({ field }) => <InputText {...field} className="w-full" />} />
              </div>
              <div className="col-4">
                <label className="text-xs font-semibold">Ap. Materno</label>
                <Controller name="lastNameMaternal" control={control} render={({ field }) => <InputText {...field} className="w-full" />} />
              </div>
              <div className="col-3">
                <label className="text-xs font-semibold">Tipo Doc.</label>
                <Controller name="documentType" control={control} render={({ field }) => <Dropdown value={field.value} options={DOC_TYPES} onChange={(e) => field.onChange(e.value)} className="w-full" />} />
              </div>
              <div className="col-3">
                <label className="text-xs font-semibold">Nro Doc.</label>
                <Controller name="documentNumber" control={control} render={({ field }) => <InputText {...field} className="w-full" />} />
              </div>
              <div className="col-3">
                <label className="text-xs font-semibold">Genero</label>
                <Controller name="gender" control={control} render={({ field }) => <Dropdown value={field.value} options={GENDER_OPTIONS} onChange={(e) => field.onChange(e.value)} className="w-full" />} />
              </div>
              <div className="col-3">
                <label className="text-xs font-semibold">Estado Civil</label>
                <Controller name="civilStatus" control={control} render={({ field }) => <Dropdown value={field.value} options={CIVIL_OPTIONS} onChange={(e) => field.onChange(e.value)} className="w-full" />} />
              </div>
              <div className="col-3">
                <label className="text-xs font-semibold">Fec. Nacimiento</label>
                <Controller name="birthDate" control={control} render={({ field }) => <InputText {...field} type="date" className="w-full" />} />
              </div>
              <div className="col-3">
                <label className="text-xs font-semibold">Teléfono</label>
                <Controller name="phone" control={control} render={({ field }) => <InputText {...field} className="w-full" />} />
              </div>
              <div className="col-6">
                <label className="text-xs font-semibold">Email</label>
                <Controller name="email" control={control} render={({ field }) => <InputText {...field} className="w-full" />} />
              </div>
              <div className="col-6">
                <label className="text-xs font-semibold">Dirección</label>
                <Controller name="address" control={control} render={({ field }) => <InputText {...field} className="w-full" />} />
              </div>
              <div className="col-3">
                <label className="text-xs font-semibold">Distrito</label>
                <Controller name="districtId" control={control} render={({ field }) => <InputText {...field} className="w-full" />} />
              </div>
              <div className="col-3">
                <label className="text-xs font-semibold">Legacy ID</label>
                <Controller name="legacyId" control={control} render={({ field }) => <InputText {...field} className="w-full" />} />
              </div>
            </div>
            </div>

            <div className="gth-section">
              <div className="gth-section-title"><i className="pi pi-graduation-cap" /> Formación</div>
            <div className="grid">
              <div className="col-4">
                <label className="text-xs font-semibold">Nivel Educativo</label>
                <Controller name="educationLevel" control={control} render={({ field }) => <Dropdown value={field.value} options={EDU_OPTIONS} onChange={(e) => field.onChange(e.value)} className="w-full" />} />
              </div>
              <div className="col-4">
                <label className="text-xs font-semibold">Grado</label>
                <Controller name="degree" control={control} render={({ field }) => <InputText {...field} className="w-full" />} />
              </div>
              <div className="col-4">
                <label className="text-xs font-semibold">Título</label>
                <Controller name="professionalTitle" control={control} render={({ field }) => <InputText {...field} className="w-full" />} />
              </div>
              <div className="col-4">
                <label className="text-xs font-semibold">Sistema Pensión</label>
                <Controller name="pensionSystem" control={control} render={({ field }) => <Dropdown value={field.value} options={PENSION_OPTIONS} onChange={(e) => field.onChange(e.value)} className="w-full" />} />
              </div>
            </div>
            </div>
        </form>
      </Dialog>
    </div>
  );
}
