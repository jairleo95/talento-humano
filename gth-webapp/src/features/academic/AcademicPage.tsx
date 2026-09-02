import { useMemo, useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { InputNumber } from 'primereact/inputnumber';
import { Dropdown } from 'primereact/dropdown';
import { Tag } from 'primereact/tag';
import { TabView, TabPanel } from 'primereact/tabview';
import { Toolbar } from 'primereact/toolbar';
import { useForm, Controller } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import dayjs from 'dayjs';
import { apiGet, apiPost, apiPatch } from '../../core/api/client';
import { useAuth } from '../../core/auth/useAuth';
import { VALIDATION_RULES } from '../../shared/validations';
import {
  CHARGE_STATUS_LABEL,
  PAYMENT_STATUS_LABEL,
  PAY_TYPE_OPTIONS,
  type AcademicChargeResponse,
  type AcademicCourseResponse,
  type AcademicPaymentResponse,
  type WorkerOption,
} from './types';

const VR = VALIDATION_RULES;

const NEW_COURSE = { campus: '', courseName: '', groupNumber: '', schedule: '', hours: 0, courseCondition: '', courseType: '' };
const NEW_PAYMENT = { quotaNumber: 1, amount: 0, paymentDate: null as string | null };

function dateBody(val: string | null) { return val ? dayjs(val).format('DD/MM/YYYY') : '—'; }
function moneyBody(val: number | null) { return val != null ? `S/ ${val.toLocaleString('es-PE', { minimumFractionDigits: 2 })}` : '—'; }
function statusTag(status: string) {
  const severity = status === 'PROCESADO' ? 'success' : status === 'ANULADO' ? 'danger' : 'warning';
  return <Tag value={CHARGE_STATUS_LABEL[status] ?? status} severity={severity as 'success' | 'danger' | 'warning'} />;
}
function payStatusTag(status: string) {
  const severity = status === 'PAGADO' ? 'success' : 'warning';
  return <Tag value={PAYMENT_STATUS_LABEL[status] ?? status} severity={severity as 'success' | 'warning'} />;
}

const UNI_SCHEMA = z.object({
  name: z.string().min(1, 'Requerido').max(VR.NAME_MAX, `Máx ${VR.NAME_MAX}`),
  shortName: z.string().max(VR.SHORT_NAME_MAX, `Máx ${VR.SHORT_NAME_MAX}`),
});

const CAREER_SCHEMA = z.object({
  name: z.string().min(1, 'Requerido').max(VR.NAME_MAX, `Máx ${VR.NAME_MAX}`),
  universityId: z.string().min(1, 'Requerido'),
});

interface ChargeFormValues {
  workerId: string; semester: string; faculty: string; school: string; educationalSituation: string;
  profession: string; condition: string; payType: string; totalHours: number; startDate: string; endDate: string;
}

const CHARGE_DEFAULTS: ChargeFormValues = {
  workerId: '', semester: '', faculty: '', school: '', educationalSituation: '', profession: '',
  condition: '', payType: '', totalHours: 0, startDate: '', endDate: '',
};

const CHARGE_SCHEMA: z.ZodType<ChargeFormValues> = z.object({
  workerId: z.string().min(1, 'Selecciona el trabajador'),
  semester: z.string().min(1, 'Requerido').max(32),
  faculty: z.string().max(128), school: z.string().max(128), educationalSituation: z.string().max(128),
  profession: z.string().max(128), condition: z.string().max(64), payType: z.string().min(1, 'Requerido'),
  totalHours: z.number().min(0), startDate: z.string(), endDate: z.string(),
});

export function AcademicPage() {
  const queryClient = useQueryClient();
  const { user } = useAuth();
  const [activeTab, setActiveTab] = useState(0);
  const [showUni, setShowUni] = useState(false);
  const [showCareer, setShowCareer] = useState(false);
  const [showCharge, setShowCharge] = useState(false);
  const [showChargeDetail, setShowChargeDetail] = useState<AcademicChargeResponse | null>(null);
  const [courses, setCourses] = useState<typeof NEW_COURSE[]>([]);
  const [payments, setPayments] = useState<typeof NEW_PAYMENT[]>([]);

  const { data: universities = [] } = useQuery({
    queryKey: ['universities'],
    queryFn: () => apiGet<{ id: string; name: string; shortName: string; createdAt: string }[]>('/recruitment/api/v1/recruitment/universities'),
  });
  const { data: careers = [] } = useQuery({
    queryKey: ['careers'],
    queryFn: () => apiGet<{ id: string; name: string; universityId: string; createdAt: string }[]>('/recruitment/api/v1/recruitment/careers'),
  });
  const { data: charges = [], isLoading: loadingCharges } = useQuery({
    queryKey: ['academic-charges'],
    queryFn: () => apiGet<AcademicChargeResponse[]>('/recruitment/api/v1/recruitment/academic-charges'),
  });
  const { data: workers = [] } = useQuery({
    queryKey: ['workers-options'],
    queryFn: () => apiGet<WorkerOption[]>('/recruitment/api/v1/recruitment/workers'),
  });

  const workerOptions = useMemo(
    () => workers.map(w => ({
      label: `${w.documentNumber} — ${w.lastNamePaternal} ${w.lastNameMaternal ?? ''} ${w.firstName}`.replace(/\s+/g, ' ').trim(),
      value: w.id,
    })),
    [workers],
  );

  const uniMutate = useMutation({
    mutationFn: (b: Record<string, unknown>) => apiPost('/recruitment/api/v1/recruitment/universities', b),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['universities'] }); setShowUni(false); },
  });
  const careerMutate = useMutation({
    mutationFn: (b: Record<string, unknown>) => apiPost('/recruitment/api/v1/recruitment/careers', b),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['careers'] }); setShowCareer(false); },
  });
  const chargeMutate = useMutation({
    mutationFn: (b: Record<string, unknown>) => apiPost('/recruitment/api/v1/recruitment/academic-charges', b),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['academic-charges'] });
      setShowCharge(false);
      setCourses([]);
      setPayments([]);
    },
  });
  const statusMutate = useMutation({
    mutationFn: ({ id, status }: { id: string; status: string }) => apiPatch(`/recruitment/api/v1/recruitment/academic-charges/${id}/status`, { status }),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['academic-charges'] }); setShowChargeDetail(null); },
  });
  const payStatusMutate = useMutation({
    mutationFn: ({ chargeId, paymentId, status }: { chargeId: string; paymentId: string; status: string }) =>
      apiPatch(`/recruitment/api/v1/recruitment/academic-charges/${chargeId}/payments/${paymentId}/status`, { status }),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['academic-charges'] }); },
  });

  const uniOptions = universities.map(u => ({ label: u.name, value: u.id }));

  const { control, handleSubmit, reset: resetUni } = useForm({ resolver: zodResolver(UNI_SCHEMA), defaultValues: { name: '', shortName: '' } });
  const { control: c2, handleSubmit: h2, reset: r2 } = useForm({ resolver: zodResolver(CAREER_SCHEMA), defaultValues: { name: '', universityId: '' } });
  const { control: c3, handleSubmit: h3, reset: r3 } = useForm({ resolver: zodResolver(CHARGE_SCHEMA), defaultValues: CHARGE_DEFAULTS });

  function openChargeDialog() {
    r3(CHARGE_DEFAULTS);
    setCourses([{ ...NEW_COURSE }]);
    setPayments([]);
    setShowCharge(true);
  }

  function addPayment() {
    setPayments(prev => [...prev, { quotaNumber: prev.length + 1, amount: 0, paymentDate: null }]);
  }

  function submitCharge(d: ChargeFormValues) {
    const payload = {
      workerId: d.workerId,
      semester: d.semester,
      faculty: d.faculty || null,
      school: d.school || null,
      educationalSituation: d.educationalSituation || null,
      profession: d.profession || null,
      condition: d.condition || null,
      payType: d.payType,
      totalHours: d.totalHours,
      startDate: d.startDate || null,
      endDate: d.endDate || null,
      createdBy: user?.username ?? 'admin',
      courses: courses.filter(c => c.courseName.trim() !== '').map(c => ({
        campus: c.campus || null, courseName: c.courseName, groupNumber: c.groupNumber || null,
        schedule: c.schedule || null, hours: c.hours, courseCondition: c.courseCondition || null, courseType: c.courseType || null,
      })),
      payments: payments.map((p, i) => ({ quotaNumber: i + 1, amount: p.amount, paymentDate: p.paymentDate || null })),
    };
    chargeMutate.mutate(payload as unknown as Record<string, unknown>);
  }

  return (
    <div className="flex flex-column gap-3">
      <h3 className="gth-page-title">Académico</h3>

      <TabView activeIndex={activeTab} onTabChange={(e) => setActiveTab(e.index)}>
        <TabPanel header="Universidades">
          <Toolbar className="surface-card border-round my-2"
            end={<Button label="Nueva" icon="pi pi-plus" onClick={() => { resetUni({ name: '', shortName: '' }); setShowUni(true); }} />} />
          <DataTable value={universities} loading={universities.length === 0} paginator rows={10} size="small" stripedRows className="surface-card border-round"
            emptyMessage="No hay universidades">
            <Column field="shortName" header="Código" sortable />
            <Column field="name" header="Nombre" sortable />
            <Column field="createdAt" header="Creado" body={(r: { createdAt: string }) => dateBody(r.createdAt)} sortable />
          </DataTable>
        </TabPanel>

        <TabPanel header="Carreras">
          <Toolbar className="surface-card border-round my-2"
            end={<Button label="Nueva" icon="pi pi-plus" onClick={() => { r2({ name: '', universityId: uniOptions[0]?.value || '' }); setShowCareer(true); }} />} />
          <DataTable value={careers} paginator rows={10} size="small" stripedRows className="surface-card border-round"
            emptyMessage="No hay carreras">
            <Column field="name" header="Carrera" sortable />
            <Column body={(r: { universityId: string }) => universities.find(u => u.id === r.universityId)?.name || '—'} header="Universidad" sortable />
            <Column field="createdAt" header="Creado" body={(r: { createdAt: string }) => dateBody(r.createdAt)} sortable />
          </DataTable>
        </TabPanel>

        <TabPanel header="Carga Académica">
          <Toolbar className="surface-card border-round my-2"
            end={<Button label="Nueva Carga" icon="pi pi-plus" onClick={openChargeDialog} />} />
          <DataTable value={charges} loading={loadingCharges} paginator rows={10} size="small" stripedRows className="surface-card border-round"
            emptyMessage="No hay cargas académicas">
            <Column header="Docente" body={(r: AcademicChargeResponse) => r.workerName ?? r.workerId} sortable />
            <Column field="semester" header="Semestre" sortable />
            <Column field="faculty" header="Facultad" body={(r: AcademicChargeResponse) => r.faculty || '—'} sortable />
            <Column field="school" header="Escuela" body={(r: AcademicChargeResponse) => r.school || '—'} sortable />
            <Column field="totalHours" header="Horas" body={(r: AcademicChargeResponse) => r.totalHours ?? '—'} sortable />
            <Column field="startDate" header="Desde" body={(r: AcademicChargeResponse) => dateBody(r.startDate)} sortable />
            <Column field="endDate" header="Hasta" body={(r: AcademicChargeResponse) => dateBody(r.endDate)} sortable />
            <Column field="status" header="Estado" body={(r: AcademicChargeResponse) => statusTag(r.status)} sortable />
            <Column field="createdBy" header="Solicitante" body={(r: AcademicChargeResponse) => r.createdBy || '—'} sortable />
            <Column header="Acciones" body={(r: AcademicChargeResponse) => (
              <div className="flex gap-2">
                <Button icon="pi pi-eye" tooltip="Ver detalle" size="small" text onClick={() => setShowChargeDetail(r)} />
                <Button icon="pi pi-check" tooltip="Procesar" size="small" text severity="success" disabled={r.status !== 'BORRADOR'}
                  onClick={() => statusMutate.mutate({ id: r.id, status: 'PROCESADO' })} />
              </div>
            )} />
          </DataTable>
        </TabPanel>

        <TabPanel header="Pago Docente">
          <PagoDocenteTab charges={charges} onTogglePayment={(chargeId, paymentId, current) =>
            payStatusMutate.mutate({ chargeId, paymentId, status: current === 'PAGADO' ? 'PENDIENTE' : 'PAGADO' })} />
        </TabPanel>
      </TabView>

      <Dialog header="Nueva Universidad" visible={showUni} onHide={() => setShowUni(false)} style={{ width: '400px' }}
        footer={<div className="flex justify-content-end gap-2"><Button label="Cancelar" outlined onClick={() => setShowUni(false)} />
          <Button label="Crear" icon="pi pi-check" onClick={handleSubmit(d => uniMutate.mutate({ ...d, shortName: d.shortName || null }))} /></div>}>
        <form className="flex flex-column gap-3">
          <div className="flex flex-column gap-1"><label className="text-sm font-semibold">Nombre *</label>
            <Controller name="name" control={control} render={({ field, fieldState }) =>
              <InputText {...field} className={fieldState.error ? 'p-invalid' : ''} />} /></div>
          <div className="flex flex-column gap-1"><label className="text-sm font-semibold">Nombre Corto</label>
            <Controller name="shortName" control={control} render={({ field }) => <InputText {...field} />} /></div>
        </form>
      </Dialog>

      <Dialog header="Nueva Carrera" visible={showCareer} onHide={() => setShowCareer(false)} style={{ width: '400px' }}
        footer={<div className="flex justify-content-end gap-2"><Button label="Cancelar" outlined onClick={() => setShowCareer(false)} />
          <Button label="Crear" icon="pi pi-check" onClick={h2(d => careerMutate.mutate(d))} /></div>}>
        <form className="flex flex-column gap-3">
          <div className="flex flex-column gap-1"><label className="text-sm font-semibold">Nombre *</label>
            <Controller name="name" control={c2} render={({ field, fieldState }) =>
              <InputText {...field} className={fieldState.error ? 'p-invalid' : ''} />} /></div>
          <div className="flex flex-column gap-1"><label className="text-sm font-semibold">Universidad *</label>
            <Controller name="universityId" control={c2} render={({ field, fieldState }) =>
              <Dropdown value={field.value} options={uniOptions} onChange={e => field.onChange(e.value)} className={`w-full ${fieldState.error ? 'p-invalid' : ''}`} filter />} /></div>
        </form>
      </Dialog>

      <Dialog header="Nueva Carga Académica" visible={showCharge} onHide={() => setShowCharge(false)} style={{ width: '960px' }}
        footer={<div className="flex justify-content-end gap-2"><Button label="Cancelar" outlined onClick={() => setShowCharge(false)} />
          <Button label="Guardar" icon="pi pi-check" onClick={h3(submitCharge)} loading={chargeMutate.isPending} /></div>}>
        <form className="flex flex-column gap-3">
          <div className="grid">
            <div className="col-6"><label className="text-sm font-semibold">Docente *</label>
              <Controller name="workerId" control={c3} render={({ field, fieldState }) =>
                <Dropdown value={field.value} options={workerOptions} onChange={e => field.onChange(e.value)} filter showClear
                  className={`w-full ${fieldState.error ? 'p-invalid' : ''}`} filterMatchMode="contains" />} /></div>
            <div className="col-6"><label className="text-sm font-semibold">Semestre *</label>
              <Controller name="semester" control={c3} render={({ field, fieldState }) =>
                <InputText {...field} className={`w-full ${fieldState.error ? 'p-invalid' : ''}`} placeholder="Ej. 2026-I" />} /></div>

            <div className="col-4"><label className="text-sm font-semibold">Facultad</label>
              <Controller name="faculty" control={c3} render={({ field }) => <InputText {...field} className="w-full" />} /></div>
            <div className="col-4"><label className="text-sm font-semibold">Escuela (EAP)</label>
              <Controller name="school" control={c3} render={({ field }) => <InputText {...field} className="w-full" />} /></div>
            <div className="col-4"><label className="text-sm font-semibold">Situación Educativa</label>
              <Controller name="educationalSituation" control={c3} render={({ field }) => <InputText {...field} className="w-full" />} /></div>

            <div className="col-4"><label className="text-sm font-semibold">Profesión Docente</label>
              <Controller name="profession" control={c3} render={({ field }) => <InputText {...field} className="w-full" />} /></div>
            <div className="col-4"><label className="text-sm font-semibold">Condición</label>
              <Controller name="condition" control={c3} render={({ field }) => <InputText {...field} className="w-full" />} /></div>
            <div className="col-2"><label className="text-sm font-semibold">Tipo Hora Pago *</label>
              <Controller name="payType" control={c3} render={({ field, fieldState }) =>
                <Dropdown value={field.value} options={PAY_TYPE_OPTIONS} onChange={e => field.onChange(e.value)} className={`w-full ${fieldState.error ? 'p-invalid' : ''}`} />} /></div>
            <div className="col-2"><label className="text-sm font-semibold">Horas Total</label>
              <Controller name="totalHours" control={c3} render={({ field }) =>
                <InputNumber value={field.value} onValueChange={e => field.onChange(e.value ?? 0)} min={0} className="w-full" />} /></div>

            <div className="col-6"><label className="text-sm font-semibold">Inicio</label>
              <Controller name="startDate" control={c3} render={({ field }) => <InputText {...field} type="date" className="w-full" />} /></div>
            <div className="col-6"><label className="text-sm font-semibold">Cese</label>
              <Controller name="endDate" control={c3} render={({ field }) => <InputText {...field} type="date" className="w-full" />} /></div>
          </div>

          <div className="surface-50 border-round p-2">
            <div className="flex justify-content-between align-items-center mb-2">
              <label className="text-sm font-semibold">Cursos / Carga</label>
              <Button label="Agregar curso" icon="pi pi-plus" size="small" text onClick={() => setCourses(prev => [...prev, { ...NEW_COURSE }])} />
            </div>
            {courses.map((course, index) => (
              <div key={index} className="grid align-items-center">
                <div className="col-2"><InputText placeholder="Campus" value={course.campus}
                  onChange={e => setCourses(prev => prev.map((c, i) => i === index ? { ...c, campus: e.target.value } : c))} className="w-full" /></div>
                <div className="col-3"><InputText placeholder="Curso *" value={course.courseName}
                  onChange={e => setCourses(prev => prev.map((c, i) => i === index ? { ...c, courseName: e.target.value } : c))} className="w-full" /></div>
                <div className="col-1"><InputText placeholder="Grupo" value={course.groupNumber}
                  onChange={e => setCourses(prev => prev.map((c, i) => i === index ? { ...c, groupNumber: e.target.value } : c))} className="w-full" /></div>
                <div className="col-2"><InputText placeholder="Horario" value={course.schedule}
                  onChange={e => setCourses(prev => prev.map((c, i) => i === index ? { ...c, schedule: e.target.value } : c))} className="w-full" /></div>
                <div className="col-1"><InputNumber placeholder="Horas" value={course.hours} min={0}
                  onValueChange={e => setCourses(prev => prev.map((c, i) => i === index ? { ...c, hours: e.value ?? 0 } : c))} className="w-full" /></div>
                <div className="col-1"><InputText placeholder="Condición" value={course.courseCondition}
                  onChange={e => setCourses(prev => prev.map((c, i) => i === index ? { ...c, courseCondition: e.target.value } : c))} className="w-full" /></div>
                <div className="col-1"><InputText placeholder="Tipo" value={course.courseType}
                  onChange={e => setCourses(prev => prev.map((c, i) => i === index ? { ...c, courseType: e.target.value } : c))} className="w-full" /></div>
                <div className="col-1"><Button icon="pi pi-trash" size="small" text severity="danger"
                  onClick={() => setCourses(prev => prev.filter((_, i) => i !== index))} /></div>
              </div>
            ))}
          </div>

          <div className="surface-50 border-round p-2">
            <div className="flex justify-content-between align-items-center mb-2">
              <label className="text-sm font-semibold">Cuotas de pago</label>
              <Button label="Agregar cuota" icon="pi pi-plus" size="small" text onClick={addPayment} />
            </div>
            {payments.map((payment, index) => (
              <div key={index} className="grid align-items-center">
                <div className="col-2"><label className="text-xs text-color-secondary">Cuota N°</label>
                  <InputNumber value={index + 1} disabled className="w-full" /></div>
                <div className="col-4"><label className="text-xs text-color-secondary">Monto (S/)</label>
                  <InputNumber value={payment.amount} min={0} mode="currency" currency="PEN" locale="es-PE"
                    onValueChange={e => setPayments(prev => prev.map((p, i) => i === index ? { ...p, amount: e.value ?? 0 } : p))} className="w-full" /></div>
                <div className="col-4"><label className="text-xs text-color-secondary">Fecha pago aprox.</label>
                  <InputText type="date" value={payment.paymentDate ?? ''}
                    onChange={e => setPayments(prev => prev.map((p, i) => i === index ? { ...p, paymentDate: e.target.value || null } : p))} className="w-full" /></div>
                <div className="col-2"><Button icon="pi pi-trash" size="small" text severity="danger"
                  onClick={() => setPayments(prev => prev.filter((_, i) => i !== index))} /></div>
              </div>
            ))}
          </div>
        </form>
      </Dialog>

      <Dialog header={`Carga ${showChargeDetail?.semester ?? ''}`} visible={!!showChargeDetail} onHide={() => setShowChargeDetail(null)}
        style={{ width: '900px' }}>
        {showChargeDetail && (
          <div className="flex flex-column gap-3">
            <div className="grid">
              <div className="col-6"><label className="text-xs text-color-secondary">Docente</label>
                <p className="m-0">{showChargeDetail.workerName ?? '—'} {showChargeDetail.documentNumber ? `(${showChargeDetail.documentNumber})` : ''}</p></div>
              <div className="col-3"><label className="text-xs text-color-secondary">Estado</label>
                <p className="m-0">{statusTag(showChargeDetail.status)}</p></div>
              <div className="col-3"><label className="text-xs text-color-secondary">Horas</label>
                <p className="m-0">{showChargeDetail.totalHours ?? '—'}</p></div>
            </div>

            <label className="text-sm font-semibold">Cursos</label>
            <DataTable value={showChargeDetail.courses ?? []} size="small" stripedRows emptyMessage="Sin cursos">
              <Column field="campus" header="Campus" body={(r: AcademicCourseResponse) => r.campus || '—'} />
              <Column field="courseName" header="Curso" />
              <Column field="groupNumber" header="Grupo" body={(r: AcademicCourseResponse) => r.groupNumber || '—'} />
              <Column field="schedule" header="Horario" body={(r: AcademicCourseResponse) => r.schedule || '—'} />
              <Column field="hours" header="Horas" body={(r: AcademicCourseResponse) => r.hours ?? '—'} />
              <Column field="courseCondition" header="Condición" body={(r: AcademicCourseResponse) => r.courseCondition || '—'} />
              <Column field="courseType" header="Tipo" body={(r: AcademicCourseResponse) => r.courseType || '—'} />
            </DataTable>

            <label className="text-sm font-semibold">Cuotas de pago</label>
            <DataTable value={showChargeDetail.payments ?? []} size="small" stripedRows emptyMessage="Sin cuotas">
              <Column field="quotaNumber" header="N°" style={{ width: '60px' }} />
              <Column field="amount" header="Monto" body={(r: AcademicPaymentResponse) => moneyBody(r.amount)} />
              <Column field="paymentDate" header="Fecha" body={(r: AcademicPaymentResponse) => dateBody(r.paymentDate)} />
              <Column field="status" header="Estado" body={(r: AcademicPaymentResponse) => payStatusTag(r.status)} />
            </DataTable>
          </div>
        )}
      </Dialog>
    </div>
  );
}

function PagoDocenteTab(props: {
  charges: AcademicChargeResponse[];
  onTogglePayment: (chargeId: string, paymentId: string, currentStatus: string) => void;
}) {
  const [selectedChargeId, setSelectedChargeId] = useState<string | null>(null);
  const selectedCharge = props.charges.find(c => c.id === selectedChargeId) ?? null;
  const total = (selectedCharge?.payments ?? []).reduce((acc, p) => acc + p.amount, 0);
  const paid = (selectedCharge?.payments ?? []).filter(p => p.status === 'PAGADO').length;

  return (
    <div className="grid">
      <div className="col-5">
        <DataTable value={props.charges} size="small" stripedRows className="surface-card border-round" emptyMessage="Sin cargas"
          selectionMode="single" dataKey="id"
          selection={selectedChargeId ? props.charges.find(c => c.id === selectedChargeId) : null}
          onSelectionChange={e => setSelectedChargeId(e.value ? (e.value as AcademicChargeResponse).id : null)}>
          <Column field="semester" header="Semestre" sortable />
          <Column header="Docente" body={(r: AcademicChargeResponse) => r.workerName ?? r.workerId} />
        </DataTable>
      </div>
      <div className="col-7">
        {selectedCharge ? (
          <div className="flex flex-column gap-2">
            <div className="grid text-center">
              <div className="col-4"><label className="text-xs text-color-secondary">Total a pagar</label>
                <p className="m-0 font-bold">{moneyBody(total)}</p></div>
              <div className="col-4"><label className="text-xs text-color-secondary">Cuotas pagadas</label>
                <p className="m-0">{paid} / {(selectedCharge.payments ?? []).length}</p></div>
              <div className="col-4"><label className="text-xs text-color-secondary">Semestre</label>
                <p className="m-0">{selectedCharge.semester}</p></div>
            </div>
            <DataTable value={selectedCharge.payments ?? []} size="small" stripedRows emptyMessage="Sin cuotas">
              <Column field="quotaNumber" header="N°" style={{ width: '50px' }} />
              <Column field="amount" header="Monto" body={(r: AcademicPaymentResponse) => moneyBody(r.amount)} />
              <Column field="paymentDate" header="Fecha" body={(r: AcademicPaymentResponse) => dateBody(r.paymentDate)} />
              <Column field="status" header="Estado" body={(r: AcademicPaymentResponse) => payStatusTag(r.status)} />
              <Column header="" body={(r: AcademicPaymentResponse) => (
                <Button label={r.status === 'PAGADO' ? 'Revertir' : 'Marcar pagada'} size="small"
                  severity={r.status === 'PAGADO' ? 'secondary' : 'success'}
                  onClick={() => props.onTogglePayment(r.chargeId, r.id, r.status)} />
              )} />
            </DataTable>
          </div>
        ) : (
          <div className="surface-card border-round p-4 text-center text-color-secondary">Seleccione una carga académica</div>
        )}
      </div>
    </div>
  );
}