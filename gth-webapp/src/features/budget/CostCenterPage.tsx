import { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { InputNumber } from 'primereact/inputnumber';
import { TabView, TabPanel } from 'primereact/tabview';
import { Toolbar } from 'primereact/toolbar';
import { useForm, Controller } from 'react-hook-form';
import dayjs from 'dayjs';
import { apiGet, apiPost } from '../../core/api/client';
import { VALIDATION_RULES } from '../../shared/validations';

const VR = VALIDATION_RULES;

interface CostCenterResponse { id: string; code: string; name: string; departmentId: string; percentage: number; createdAt: string; }
interface BudgetPeriod { id: string; name: string; startDate: string; endDate: string; createdAt: string; }
interface BudgetAllocation { id: string; periodId: string; requirementType: string; positionId: string; workerCount: number; minSalary: number; maxSalary: number; minBonus: number; maxBonus: number; minFoodBonus: number; maxFoodBonus: number; }

function dateBody(val: string) { return val ? dayjs(val).format('DD/MM/YYYY') : '—'; }

export function CostCenterPage() {
  const queryClient = useQueryClient();
  const [showCc, setShowCc] = useState(false);
  const [showPeriod, setShowPeriod] = useState(false);
  const [selectedPeriod, setSelectedPeriod] = useState<string | null>(null);

  const { data: centers = [] } = useQuery({ queryKey: ['cost-centers'], queryFn: () => apiGet<CostCenterResponse[]>('/recruitment/api/v1/recruitment/cost-centers') });
  const { data: periods = [] } = useQuery({ queryKey: ['budget-periods'], queryFn: () => apiGet<BudgetPeriod[]>('/recruitment/api/v1/recruitment/budget/periods') });
  const { data: allocations = [] } = useQuery({
    queryKey: ['budget-allocations', selectedPeriod],
    queryFn: () => apiGet<BudgetAllocation[]>(`/recruitment/api/v1/recruitment/budget/periods/${selectedPeriod}/allocations`),
    enabled: !!selectedPeriod,
  });

  const ccMutate = useMutation({
    mutationFn: (b: Record<string, unknown>) => apiPost('/recruitment/api/v1/recruitment/cost-centers', b),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['cost-centers'] }); setShowCc(false); },
  });

  const periodMutate = useMutation({
    mutationFn: (b: Record<string, unknown>) => apiPost('/recruitment/api/v1/recruitment/budget/periods', b),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['budget-periods'] }); setShowPeriod(false); },
  });

  const { control: c1, handleSubmit: h1, reset: r1 } = useForm({ defaultValues: { code: '', name: '', departmentId: '', percentage: 100 } });
  const { control: c2, handleSubmit: h2, reset: r2 } = useForm({ defaultValues: { name: '', startDate: '', endDate: '' } });

  return (
    <div className="flex flex-column gap-3">
      <h3 className="gth-page-title">Presupuesto</h3>

      <TabView>
        <TabPanel header="Centros de Costo">
          <Toolbar className="surface-card border-round my-2" end={<Button label="Nuevo" icon="pi pi-plus" onClick={() => { r1({ code: '', name: '', departmentId: '', percentage: 100 }); setShowCc(true); }} />} />
          <DataTable value={centers} paginator rows={10} size="small" stripedRows className="surface-card border-round" emptyMessage="No hay centros de costo">
            <Column field="code" header="Código" sortable />
            <Column field="name" header="Nombre" sortable />
            <Column field="departmentId" header="Depto" sortable />
            <Column field="percentage" header="%" body={(r: CostCenterResponse) => `${r.percentage}%`} sortable />
            <Column field="createdAt" header="Creado" body={(r: CostCenterResponse) => dateBody(r.createdAt)} sortable />
          </DataTable>
        </TabPanel>

        <TabPanel header="Temporadas y Asignaciones">
          <Toolbar className="surface-card border-round my-2" end={<Button label="Nueva Temporada" icon="pi pi-plus" onClick={() => { r2({ name: '', startDate: '', endDate: '' }); setShowPeriod(true); }} />} />
          <div className="grid">
            <div className="col-5">
              <DataTable value={periods} paginator rows={5} size="small" stripedRows className="surface-card border-round" emptyMessage="No hay temporadas"
                selection={selectedPeriod ? periods.find(p => p.id === selectedPeriod) : null}
                onSelectionChange={e => setSelectedPeriod(typeof e.value === 'object' && e.value ? (e.value as BudgetPeriod).id : null)} dataKey="id" selectionMode="single">
                <Column field="name" header="Temporada" sortable />
                <Column field="startDate" header="Inicio" body={(r: BudgetPeriod) => dateBody(r.startDate)} sortable />
                <Column field="endDate" header="Fin" body={(r: BudgetPeriod) => dateBody(r.endDate)} sortable />
              </DataTable>
            </div>
            <div className="col-7">
              {selectedPeriod && (
                <DataTable value={allocations} paginator rows={5} size="small" stripedRows className="surface-card border-round" emptyMessage="Sin asignaciones">
                  <Column field="requirementType" header="Req." sortable style={{ width: '100px' }} />
                  <Column field="positionId" header="Puesto" sortable />
                  <Column field="workerCount" header="Nro Trab." sortable style={{ width: '80px' }} />
                  <Column header="Salario" body={(r: BudgetAllocation) => `Gs. ${r.minSalary?.toLocaleString()} – ${r.maxSalary?.toLocaleString()}`} />
                  <Column header="Bono" body={(r: BudgetAllocation) => `Gs. ${r.minBonus?.toLocaleString()} – ${r.maxBonus?.toLocaleString()}`} />
                </DataTable>
              )}
            </div>
          </div>
        </TabPanel>
      </TabView>

      <Dialog header="Nuevo Centro de Costo" visible={showCc} onHide={() => setShowCc(false)} style={{ width: '440px' }}
        footer={<div className="flex justify-content-end gap-2"><Button label="Cancelar" outlined onClick={() => setShowCc(false)} /><Button label="Crear" icon="pi pi-check" onClick={h1(d => ccMutate.mutate(d))} /></div>}>
        <form className="flex flex-column gap-3">
          <div><label className="text-sm font-semibold">Código *</label><Controller name="code" control={c1} rules={{ required: true, maxLength: { value: VR.CODE_MAX, message: `Máx ${VR.CODE_MAX}` } }} render={({ field }) => <InputText {...field} className="w-full" />} /></div>
          <div><label className="text-sm font-semibold">Nombre *</label><Controller name="name" control={c1} rules={{ required: true }} render={({ field }) => <InputText {...field} className="w-full" />} /></div>
          <div><label className="text-sm font-semibold">Departamento</label><Controller name="departmentId" control={c1} render={({ field }) => <InputText {...field} className="w-full" />} /></div>
          <div><label className="text-sm font-semibold">Porcentaje (%)</label><Controller name="percentage" control={c1} render={({ field }) => <InputNumber value={field.value} onValueChange={e => field.onChange(e.value ?? 0)} min={0} max={100} className="w-full" />} /></div>
        </form>
      </Dialog>

      <Dialog header="Nueva Temporada" visible={showPeriod} onHide={() => setShowPeriod(false)} style={{ width: '440px' }}
        footer={<div className="flex justify-content-end gap-2"><Button label="Cancelar" outlined onClick={() => setShowPeriod(false)} /><Button label="Crear" icon="pi pi-check" onClick={h2(d => periodMutate.mutate(d))} /></div>}>
        <form className="flex flex-column gap-3">
          <div><label className="text-sm font-semibold">Nombre *</label><Controller name="name" control={c2} rules={{ required: true }} render={({ field }) => <InputText {...field} className="w-full" />} /></div>
          <div><label className="text-sm font-semibold">Fecha Inicio</label><Controller name="startDate" control={c2} render={({ field }) => <InputText {...field} type="date" className="w-full" />} /></div>
          <div><label className="text-sm font-semibold">Fecha Fin</label><Controller name="endDate" control={c2} render={({ field }) => <InputText {...field} type="date" className="w-full" />} /></div>
        </form>
      </Dialog>
    </div>
  );
}
