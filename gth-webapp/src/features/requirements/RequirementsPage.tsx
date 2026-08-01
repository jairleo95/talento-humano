import { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import { Dropdown } from 'primereact/dropdown';
import { InputText } from 'primereact/inputtext';
import { InputTextarea } from 'primereact/inputtextarea';
import { InputNumber } from 'primereact/inputnumber';
import { Toolbar } from 'primereact/toolbar';
import { Tag } from 'primereact/tag';
import { Divider } from 'primereact/divider';
import { useForm, Controller } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import dayjs from 'dayjs';
import { apiGet, apiPost, apiPatch } from '../../core/api/client';
import type { RequirementResponse, RequirementRequest } from './types';
import { REQUIREMENT_STATUSES } from './types';
import { VALIDATION_RULES } from '../../shared/validations';

const BASE_PATH = '/recruitment/api/v1/recruitment/requisitions';

const MAX = VALIDATION_RULES;

const createSchema = z.object({
  title: z.string().min(1, 'Requerido').max(MAX.TITLE_MAX, `Máximo ${MAX.TITLE_MAX} caracteres`),
  description: z.string().min(1, 'Requerido').max(MAX.DESCRIPTION_MAX, `Máximo ${MAX.DESCRIPTION_MAX} caracteres`),
  createdBy: z.string().min(1, 'Requerido'),
  requestNumber: z.string().min(1, 'Requerido'),
  workerId: z.string().max(MAX.BANK_ACCOUNT_MAX).optional().default(''),
  motive: z.string().max(32).optional().default(''),
  isMfl: z.boolean().optional().default(false),
  isBudgeted: z.boolean().optional().default(false),
  ruc: z.string().max(MAX.RUC_MAX, `Máximo ${MAX.RUC_MAX} caracteres`).optional().default(''),
  salaryAmount: z.number().min(0, 'Debe ser >= 0').optional().default(0),
  foodBonus: z.number().min(0).optional().default(0),
  positionBonus: z.number().min(0).optional().default(0),
  bevBonus: z.number().min(0).optional().default(0),
  familyAllowance: z.number().min(0).optional().default(0),
  workDays: z.string().max(MAX.NAME_MAX).optional().default(''),
  serviceLocation: z.string().max(MAX.LOCATION_MAX, `Máximo ${MAX.LOCATION_MAX} caracteres`).optional().default(''),
  serviceDescription: z.string().max(MAX.SERVICE_DESC_MAX, `Máximo ${MAX.SERVICE_DESC_MAX} caracteres`).optional().default(''),
  paymentPeriod: z.string().max(32).optional().default(''),
  fiscalAddress: z.string().max(MAX.DESCRIPTION_MAX).optional().default(''),
  allowanceDescription: z.string().max(MAX.DESCRIPTION_MAX).optional().default(''),
  trainingSchedule: z.string().max(MAX.NAME_MAX).optional().default(''),
  breakSchedule: z.string().max(MAX.NAME_MAX).optional().default(''),
  trainingDays: z.string().max(MAX.NAME_MAX).optional().default(''),
  policeRecordDesc: z.string().max(MAX.DESCRIPTION_MAX).optional().default(''),
  healthCertificateDesc: z.string().max(MAX.DESCRIPTION_MAX).optional().default(''),
  bankName: z.string().max(MAX.NAME_MAX).optional().default(''),
  bankAccount: z.string().max(MAX.BANK_ACCOUNT_MAX, `Máximo ${MAX.BANK_ACCOUNT_MAX} caracteres`).optional().default(''),
  subsidy: z.string().max(MAX.DESCRIPTION_MAX).optional().default(''),
  honorariumAmount: z.number().min(0).optional().default(0),
});

type CreateForm = z.infer<typeof createSchema>;

const DEFAULT_VALUES: CreateForm = {
  title: '', description: '', createdBy: '', requestNumber: '',
  workerId: '', motive: '', isMfl: false, isBudgeted: false, ruc: '',
  salaryAmount: 0, foodBonus: 0, positionBonus: 0, bevBonus: 0, familyAllowance: 0,
  workDays: '', serviceLocation: '', serviceDescription: '', paymentPeriod: '',
  fiscalAddress: '', allowanceDescription: '', trainingSchedule: '', breakSchedule: '',
  trainingDays: '', policeRecordDesc: '', healthCertificateDesc: '',
  bankName: '', bankAccount: '', subsidy: '', honorariumAmount: 0,
};

const STATUS_TAGS: Record<string, { severity: 'info' | 'success' | 'warning' | 'danger'; label: string }> = {
  OPEN: { severity: 'info', label: 'Abierto' },
  DRAFT: { severity: 'info', label: 'Borrador' },
  SUBMITTED: { severity: 'warning', label: 'Enviado' },
  IN_REVIEW: { severity: 'warning', label: 'Revisión' },
  APPROVED: { severity: 'success', label: 'Aprobado' },
  REJECTED: { severity: 'danger', label: 'Rechazado' },
};

const MOTIVE_LABELS: Record<string, string> = {
  NEW_WORKER: 'Trabajador Nuevo',
  RENEWAL: 'Renovación',
};

function statusBody(row: RequirementResponse) {
  const tag = STATUS_TAGS[row.status] ?? { severity: 'info', label: row.status };
  return <Tag severity={tag.severity as never} value={tag.label} />;
}

function dateBody(row: RequirementResponse) {
  return dayjs(row.createdAt).format('DD/MM/YYYY');
}

function totalSalary(row: RequirementResponse) {
  const total = (row.salaryAmount || 0) + (row.positionBonus || 0) + (row.bevBonus || 0) + (row.familyAllowance || 0);
  return `Gs. ${total.toLocaleString()}`;
}

function motiveBody(row: RequirementResponse) {
  return <span>{MOTIVE_LABELS[row.motive || ''] || row.motive || '—'}</span>;
}

function budgetedBody(row: RequirementResponse) {
  return <Tag severity={row.isBudgeted ? 'success' : 'danger'} value={row.isBudgeted ? 'Sí' : 'No'} />;
}

function mflBody(row: RequirementResponse) {
  return <Tag severity={row.isMfl ? 'warning' : 'info'} value={row.isMfl ? 'Sí' : 'No'} />;
}

interface FieldProps {
  control: ReturnType<typeof useForm<CreateForm>>['control'];
  name: keyof CreateForm;
  label: string;
  component: React.ReactNode;
  error?: string;
}

function Field({ label, component, error }: FieldProps) {
  return (
    <div className="flex flex-column gap-1">
      <label className="text-sm font-medium">{label}</label>
      {component}
      {error && <small className="p-error">{error}</small>}
    </div>
  );
}

function Section({ icon, title, children }: { icon: string; title: string; children: React.ReactNode }) {
  return (
    <div className="gth-section">
      <div className="gth-section-title">
        <i className={icon} /> {title}
      </div>
      {children}
    </div>
  );
}

export function RequirementsPage() {
  const queryClient = useQueryClient();
  const [filterStatus, setFilterStatus] = useState<string | null>(null);
  const [showCreate, setShowCreate] = useState(false);
  const [detailReq, setDetailReq] = useState<RequirementResponse | null>(null);

  const { data: requirements = [] } = useQuery({
    queryKey: ['requirements', filterStatus],
    queryFn: () => {
      const param = filterStatus ? `?status=${filterStatus}` : '';
      return apiGet<RequirementResponse[]>(`${BASE_PATH}${param}`);
    },
  });

  const createMutation = useMutation({
    mutationFn: (req: RequirementRequest) => apiPost<RequirementResponse>(BASE_PATH, req),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['requirements'] });
      setShowCreate(false);
    },
  });

  const statusMutation = useMutation({
    mutationFn: ({ id, status }: { id: string; status: string }) =>
      apiPatch<RequirementResponse>(`${BASE_PATH}/${id}/status`, { status }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['requirements'] });
    },
  });

  const { control, handleSubmit, reset, formState: { errors } } = useForm<CreateForm>({
    resolver: zodResolver(createSchema),
    defaultValues: DEFAULT_VALUES,
  });

  const openCreate = () => {
    reset(DEFAULT_VALUES);
    setShowCreate(true);
  };

  const textInput = (name: keyof CreateForm, opts?: { maxLength?: number }) => (
    <Controller name={name} control={control} render={({ field }) => (
      <InputText {...field} value={String(field.value ?? '')} className={errors[name] ? 'p-invalid' : ''} maxLength={opts?.maxLength} />
    )} />
  );

  const numberInput = (name: keyof CreateForm, min?: number) => (
    <Controller name={name} control={control} render={({ field }) => (
      <InputNumber {...field} value={Number(field.value) || 0} onValueChange={(e) => field.onChange(e.value ?? 0)} min={min ?? 0} mode="currency" currency="PYG" locale="es-PY" className={errors[name] ? 'p-invalid' : ''} />
    )} />
  );

  const boolDropdown = (name: keyof CreateForm) => (
    <Controller name={name} control={control} render={({ field }) => (
      <Dropdown value={field.value} options={[{ label: 'Sí', value: true }, { label: 'No', value: false }]} onChange={(e) => field.onChange(e.value)} className="w-full" />
    )} />
  );

  const toolbarEnd = (
    <Button label="Nuevo" icon="pi pi-plus" onClick={openCreate} />
  );

  return (
    <div className="flex flex-column gap-3">
      <h3 className="m-0">Requerimientos</h3>
      <Toolbar end={toolbarEnd} className="surface-card border-round" />

      <div className="flex align-items-center gap-2">
        <label className="text-sm font-medium">Estado:</label>
        <Dropdown value={filterStatus}
          options={[{ label: 'Todos', value: null }, ...REQUIREMENT_STATUSES.map((s) => ({ label: s.label, value: s.value }))]}
          onChange={(e) => setFilterStatus(e.value)} className="w-12rem" />
      </div>

      <DataTable value={requirements} paginator rows={10} rowsPerPageOptions={[5, 10, 20]}
        emptyMessage="No hay requerimientos" className="surface-card border-round" size="small" stripedRows
        onRowClick={(e) => setDetailReq(e.data as RequirementResponse)}
        rowClassName={() => 'cursor-pointer'}
      >
        <Column field="requestNumber" header="Nro DGP" sortable />
        <Column field="title" header="Título" sortable />
        <Column field="status" header="Estado" body={statusBody} sortable />
        <Column field="workerId" header="Trabajador" body={(r: RequirementResponse) => r.workerId || '—'} sortable />
        <Column field="motive" header="Motivo" body={motiveBody} sortable />
        <Column body={totalSalary} header="Total" sortable sortField="salaryAmount" />
        <Column body={budgetedBody} header="Presup." sortable sortField="isBudgeted" />
        <Column body={mflBody} header="MFL" sortable sortField="isMfl" />
        <Column field="createdAt" header="Fecha" body={dateBody} sortable />
        <Column header="Cambiar estado"
          body={(row: RequirementResponse) => (
            <Dropdown value={row.status}
              options={REQUIREMENT_STATUSES.map((s) => ({ label: s.label, value: s.value }))}
              onChange={(e) => statusMutation.mutate({ id: row.id, status: e.value })}
              className="w-9rem" disabled={statusMutation.isPending} />
          )}
        />
      </DataTable>

      {/* Detail dialog */}
      <Dialog header={detailReq?.title || 'Detalle'} visible={!!detailReq}
        onHide={() => setDetailReq(null)} style={{ width: '680px' }} maximizable
      >
        {detailReq && (
          <div className="flex flex-column gap-3">
            <div className="grid">
              <div className="col-4"><label className="text-xs text-color-secondary">Nro DGP</label><p className="m-0 font-medium">{detailReq.requestNumber}</p></div>
              <div className="col-4"><label className="text-xs text-color-secondary">Estado</label><p className="m-0">{statusBody(detailReq)}</p></div>
              <div className="col-4"><label className="text-xs text-color-secondary">Solicitante</label><p className="m-0">{detailReq.createdBy}</p></div>
              <div className="col-4"><label className="text-xs text-color-secondary">Trabajador</label><p className="m-0">{detailReq.workerId || '—'}</p></div>
              <div className="col-4"><label className="text-xs text-color-secondary">Motivo</label><p className="m-0">{MOTIVE_LABELS[detailReq.motive || ''] || detailReq.motive || '—'}</p></div>
              <div className="col-4"><label className="text-xs text-color-secondary">Presupuestado</label><p className="m-0">{detailReq.isBudgeted ? 'Sí' : 'No'}</p></div>
            </div>
            <Divider />
            <div className="grid">
              <div className="col-4"><label className="text-xs text-color-secondary">Salario</label><p className="m-0">Gs. {(detailReq.salaryAmount || 0).toLocaleString()}</p></div>
              <div className="col-4"><label className="text-xs text-color-secondary">Bono Alimentación</label><p className="m-0">Gs. {(detailReq.foodBonus || 0).toLocaleString()}</p></div>
              <div className="col-4"><label className="text-xs text-color-secondary">Bono Puesto</label><p className="m-0">Gs. {(detailReq.positionBonus || 0).toLocaleString()}</p></div>
              <div className="col-4"><label className="text-xs text-color-secondary">BEV</label><p className="m-0">Gs. {(detailReq.bevBonus || 0).toLocaleString()}</p></div>
              <div className="col-4"><label className="text-xs text-color-secondary">Asig. Familiar</label><p className="m-0">Gs. {(detailReq.familyAllowance || 0).toLocaleString()}</p></div>
              <div className="col-4"><label className="text-xs text-color-secondary">Subvención</label><p className="m-0">{detailReq.subsidy || '—'}</p></div>
            </div>
            <Divider />
            <div className="grid">
              <div className="col-6"><label className="text-xs text-color-secondary">Lugar de Servicio</label><p className="m-0">{detailReq.serviceLocation || '—'}</p></div>
              <div className="col-6"><label className="text-xs text-color-secondary">Dirección Fiscal</label><p className="m-0">{detailReq.fiscalAddress || '—'}</p></div>
              <div className="col-6"><label className="text-xs text-color-secondary">Horario</label><p className="m-0">{detailReq.trainingSchedule || '—'}</p></div>
              <div className="col-6"><label className="text-xs text-color-secondary">Refrigerio</label><p className="m-0">{detailReq.breakSchedule || '—'}</p></div>
              <div className="col-6"><label className="text-xs text-color-secondary">Días</label><p className="m-0">{detailReq.workDays || detailReq.trainingDays || '—'}</p></div>
              <div className="col-6"><label className="text-xs text-color-secondary">RUC</label><p className="m-0">{detailReq.ruc || '—'}</p></div>
            </div>
            <Divider />
            <div className="grid">
              <div className="col-6"><label className="text-xs text-color-secondary">Banco</label><p className="m-0">{detailReq.bankName || '—'}</p></div>
              <div className="col-6"><label className="text-xs text-color-secondary">Cuenta</label><p className="m-0">{detailReq.bankAccount || '—'}</p></div>
              <div className="col-12"><label className="text-xs text-color-secondary">Descripción del Servicio</label><p className="m-0">{detailReq.serviceDescription || '—'}</p></div>
              <div className="col-12"><label className="text-xs text-color-secondary">Descripción</label><p className="m-0">{detailReq.description || '—'}</p></div>
            </div>
          </div>
        )}
      </Dialog>

      {/* Create dialog */}
      <Dialog header="Nuevo Requerimiento" visible={showCreate} onHide={() => setShowCreate(false)}
        style={{ width: '680px' }} maximizable
        footer={
          <div className="flex justify-content-end gap-2">
            <Button label="Cancelar" outlined onClick={() => setShowCreate(false)} />
            <Button label="Crear" icon="pi pi-check" onClick={handleSubmit((d) => createMutation.mutate(d))} />
          </div>
        }
      >
        <form className="p-2" style={{ maxHeight: '60vh', overflowY: 'auto' }}>
          <Section icon="pi pi-file" title="Información básica">
            <div className="grid">
              <div className="col-6">
                <Field control={control} name="title" label="Título *" error={errors.title?.message}
                  component={<Controller name="title" control={control} render={({ field }) => (
                    <InputText {...field} className={errors.title ? 'p-invalid' : ''} maxLength={140} />
                  )} />} />
              </div>
              <div className="col-3">
                <Field control={control} name="requestNumber" label="Nro DGP *" error={errors.requestNumber?.message}
                  component={<Controller name="requestNumber" control={control} render={({ field }) => (
                    <InputText {...field} className={errors.requestNumber ? 'p-invalid' : ''} />
                  )} />} />
              </div>
              <div className="col-3">
                <Field control={control} name="createdBy" label="Solicitante *" error={errors.createdBy?.message}
                  component={<Controller name="createdBy" control={control} render={({ field }) => (
                    <InputText {...field} className={errors.createdBy ? 'p-invalid' : ''} />
                  )} />} />
              </div>
              <div className="col-12">
                <Field control={control} name="description" label="Descripción *" error={errors.description?.message}
                  component={<Controller name="description" control={control} render={({ field }) => (
                    <InputTextarea {...field} className={errors.description ? 'p-invalid' : ''} rows={3} />
                  )} />} />
              </div>
            </div>
          </Section>

          <Section icon="pi pi-id-card" title="Trabajador y tipo de contratación">
            <div className="grid">
              <div className="col-3">
                <Field control={control} name="workerId" label="ID Trabajador" component={textInput('workerId')} />
              </div>
              <div className="col-3">
                <Field control={control} name="motive" label="Motivo" component={
                  <Controller name="motive" control={control} render={({ field }) => (
                    <Dropdown value={field.value || ''} options={[
                      { label: 'Trabajador Nuevo', value: 'NEW_WORKER' },
                      { label: 'Renovación', value: 'RENEWAL' },
                    ]} onChange={(e) => field.onChange(e.value)} className="w-full" />
                  )} />
                } />
              </div>
              <div className="col-3">
                <Field control={control} name="isMfl" label="MFL" component={boolDropdown('isMfl')} />
              </div>
              <div className="col-3">
                <Field control={control} name="isBudgeted" label="Presupuestado" component={boolDropdown('isBudgeted')} />
              </div>
            </div>
          </Section>

          <Section icon="pi pi-dollar" title="Información financiera">
            <div className="grid">
              <div className="col-4"><Field control={control} name="salaryAmount" label="Salario" component={numberInput('salaryAmount')} /></div>
              <div className="col-4"><Field control={control} name="foodBonus" label="Bono Alimentación" component={numberInput('foodBonus')} /></div>
              <div className="col-4"><Field control={control} name="positionBonus" label="Bono Puesto" component={numberInput('positionBonus')} /></div>
              <div className="col-4"><Field control={control} name="bevBonus" label="BEV" component={numberInput('bevBonus')} /></div>
              <div className="col-4"><Field control={control} name="familyAllowance" label="Asig. Familiar" component={numberInput('familyAllowance')} /></div>
              <div className="col-4"><Field control={control} name="honorariumAmount" label="Honorarios" component={numberInput('honorariumAmount')} /></div>
              <div className="col-12"><Field control={control} name="subsidy" label="Subvención" component={textInput('subsidy')} /></div>
            </div>
          </Section>

          <Section icon="pi pi-map-marker" title="Ubicación y horario">
            <div className="grid">
              <div className="col-6"><Field control={control} name="serviceLocation" label="Lugar de Servicio" component={textInput('serviceLocation')} /></div>
              <div className="col-6"><Field control={control} name="fiscalAddress" label="Dirección Fiscal" component={textInput('fiscalAddress')} /></div>
              <div className="col-3"><Field control={control} name="ruc" label="RUC" component={textInput('ruc')} /></div>
              <div className="col-3"><Field control={control} name="trainingDays" label="Días" component={textInput('trainingDays')} /></div>
              <div className="col-3"><Field control={control} name="trainingSchedule" label="Horario Capacitación" component={textInput('trainingSchedule')} /></div>
              <div className="col-3"><Field control={control} name="breakSchedule" label="Refrigerio" component={textInput('breakSchedule')} /></div>
              <div className="col-6"><Field control={control} name="workDays" label="Días Laborales" component={textInput('workDays')} /></div>
              <div className="col-6"><Field control={control} name="paymentPeriod" label="Periodo de Pago" component={textInput('paymentPeriod')} /></div>
            </div>
          </Section>

          <Section icon="pi pi-info-circle" title="Información adicional">
            <div className="grid">
              <div className="col-12"><Field control={control} name="serviceDescription" label="Descripción del Servicio" component={
                <Controller name="serviceDescription" control={control} render={({ field }) => (
                  <InputTextarea {...field} value={field.value || ''} rows={2} />
                )} />
              } /></div>
              <div className="col-12"><Field control={control} name="allowanceDescription" label="Descripción de Beneficios" component={
                <Controller name="allowanceDescription" control={control} render={({ field }) => (
                  <InputTextarea {...field} value={field.value || ''} rows={2} />
                )} />
              } /></div>
              <div className="col-6"><Field control={control} name="policeRecordDesc" label="Antecedentes Policiales" component={textInput('policeRecordDesc')} /></div>
              <div className="col-6"><Field control={control} name="healthCertificateDesc" label="Certificado de Salud" component={textInput('healthCertificateDesc')} /></div>
              <div className="col-6"><Field control={control} name="bankName" label="Banco" component={textInput('bankName')} /></div>
              <div className="col-6"><Field control={control} name="bankAccount" label="Cuenta Bancaria" component={textInput('bankAccount')} /></div>
            </div>
          </Section>
        </form>
      </Dialog>
    </div>
  );
}
