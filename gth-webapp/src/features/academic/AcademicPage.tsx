import { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { Dropdown } from 'primereact/dropdown';
import { TabView, TabPanel } from 'primereact/tabview';
import { Toolbar } from 'primereact/toolbar';
import { useForm, Controller } from 'react-hook-form';
import dayjs from 'dayjs';
import { apiGet, apiPost } from '../../core/api/client';
import { VALIDATION_RULES } from '../../shared/validations';

const VR = VALIDATION_RULES;

interface CareerResponse { id: string; name: string; universityId: string; createdAt: string; }
interface UniversityResponse { id: string; name: string; shortName: string; createdAt: string; }

function dateBody(val: string) { return val ? dayjs(val).format('DD/MM/YYYY') : '—'; }

export function AcademicPage() {
  const queryClient = useQueryClient();
  const [activeTab, setActiveTab] = useState(0);
  const [showUni, setShowUni] = useState(false);
  const [showCareer, setShowCareer] = useState(false);

  const { data: universities = [], isLoading: loadingUni } = useQuery({
    queryKey: ['universities'],
    queryFn: () => apiGet<UniversityResponse[]>('/recruitment/api/v1/recruitment/universities'),
  });

  const { data: careers = [], isLoading: loadingCareer } = useQuery({
    queryKey: ['careers'],
    queryFn: () => apiGet<CareerResponse[]>('/recruitment/api/v1/recruitment/careers'),
  });

  const uniMutate = useMutation({
    mutationFn: (b: Record<string, unknown>) => apiPost('/recruitment/api/v1/recruitment/universities', b),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['universities'] }); setShowUni(false); },
  });

  const careerMutate = useMutation({
    mutationFn: (b: Record<string, unknown>) => apiPost('/recruitment/api/v1/recruitment/careers', b),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['careers'] }); setShowCareer(false); },
  });

  const uniOptions = universities.map(u => ({ label: u.name, value: u.id }));
  const { control: c1, handleSubmit: h1, reset: r1 } = useForm({ defaultValues: { name: '', shortName: '' } });
  const { control: c2, handleSubmit: h2, reset: r2 } = useForm({ defaultValues: { name: '', universityId: '' } });

  return (
    <div className="flex flex-column gap-3">
      <h3 className="gth-page-title">Académico</h3>

      <TabView activeIndex={activeTab} onTabChange={(e) => setActiveTab(e.index)}>
        <TabPanel header="Universidades">
          <Toolbar className="surface-card border-round my-2"
            end={<Button label="Nueva" icon="pi pi-plus" onClick={() => { r1({ name: '', shortName: '' }); setShowUni(true); }} />} />
          <DataTable value={universities} loading={loadingUni} paginator rows={10} size="small" stripedRows className="surface-card border-round"
            emptyMessage="No hay universidades">
            <Column field="shortName" header="Código" sortable />
            <Column field="name" header="Nombre" sortable />
            <Column field="createdAt" header="Creado" body={(r: UniversityResponse) => dateBody(r.createdAt)} sortable />
          </DataTable>
        </TabPanel>

        <TabPanel header="Carreras">
          <Toolbar className="surface-card border-round my-2"
            end={<Button label="Nueva" icon="pi pi-plus" onClick={() => { r2({ name: '', universityId: uniOptions[0]?.value || '' }); setShowCareer(true); }} />} />
          <DataTable value={careers} loading={loadingCareer} paginator rows={10} size="small" stripedRows className="surface-card border-round"
            emptyMessage="No hay carreras">
            <Column field="name" header="Carrera" sortable />
            <Column body={(r: CareerResponse) => universities.find(u => u.id === r.universityId)?.name || '—'} header="Universidad" sortable />
            <Column field="createdAt" header="Creado" body={(r: CareerResponse) => dateBody(r.createdAt)} sortable />
          </DataTable>
        </TabPanel>
      </TabView>

      <Dialog header="Nueva Universidad" visible={showUni} onHide={() => setShowUni(false)} style={{ width: '400px' }}
        footer={<div className="flex justify-content-end gap-2"><Button label="Cancelar" outlined onClick={() => setShowUni(false)} />
          <Button label="Crear" icon="pi pi-check" onClick={h1(d => uniMutate.mutate(d))} /></div>}>
        <form className="flex flex-column gap-3">
          <div className="flex flex-column gap-1"><label className="text-sm font-semibold">Nombre *</label>
            <Controller name="name" control={c1} rules={{ required: 'Requerido', maxLength: { value: VR.NAME_MAX, message: `Máx ${VR.NAME_MAX}` } }}
              render={({ field, fieldState }) => <InputText {...field} className={fieldState.error ? 'p-invalid' : ''} />} /></div>
          <div className="flex flex-column gap-1"><label className="text-sm font-semibold">Nombre Corto</label>
            <Controller name="shortName" control={c1} render={({ field }) => <InputText {...field} />} /></div>
        </form>
      </Dialog>

      <Dialog header="Nueva Carrera" visible={showCareer} onHide={() => setShowCareer(false)} style={{ width: '400px' }}
        footer={<div className="flex justify-content-end gap-2"><Button label="Cancelar" outlined onClick={() => setShowCareer(false)} />
          <Button label="Crear" icon="pi pi-check" onClick={h2(d => careerMutate.mutate(d))} /></div>}>
        <form className="flex flex-column gap-3">
          <div className="flex flex-column gap-1"><label className="text-sm font-semibold">Nombre *</label>
            <Controller name="name" control={c2} rules={{ required: 'Requerido', maxLength: { value: VR.NAME_MAX, message: `Máx ${VR.NAME_MAX}` } }}
              render={({ field, fieldState }) => <InputText {...field} className={fieldState.error ? 'p-invalid' : ''} />} /></div>
          <div className="flex flex-column gap-1"><label className="text-sm font-semibold">Universidad *</label>
            <Controller name="universityId" control={c2} rules={{ required: 'Requerido' }}
              render={({ field }) => <Dropdown value={field.value} options={uniOptions} onChange={e => field.onChange(e.value)} className="w-full" filter />} /></div>
        </form>
      </Dialog>
    </div>
  );
}
