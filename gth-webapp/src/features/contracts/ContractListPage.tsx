import { useState } from 'react';
import { Link } from 'react-router-dom';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { InputTextarea } from 'primereact/inputtextarea';
import { InputNumber } from 'primereact/inputnumber';
import { Dropdown } from 'primereact/dropdown';
import { Toolbar } from 'primereact/toolbar';
import { Tag } from 'primereact/tag';
import { useForm, Controller } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import dayjs from 'dayjs';
import { apiGet, apiPost, apiPatch } from '../../core/api/client';
import type { ContractResponse, ContractRequest } from './types';
import { VALIDATION_RULES } from '../../shared/validations';

const BASE_PATH = '/contract/api/v1/contracts';
const VR = VALIDATION_RULES;

const createSchema = z.object({
  requisitionId: z.string().min(1, 'Requerido'),
  templateId: z.string().min(1, 'Requerido'),
  contractNumber: z.string().min(1, 'Requerido'),
  positionId: z.string().optional().default(''),
  workerId: z.string().optional().default(''),
  startDate: z.string().optional().default(''),
  endDate: z.string().optional().default(''),
  terminationDate: z.string().optional().default(''),
  conditionType: z.string().optional().default(''),
  salaryAmount: z.number().min(0).optional().default(0),
  reintegrationAmount: z.number().min(0).optional().default(0),
  familyAllowance: z.number().min(0).optional().default(0),
  weeklyHours: z.number().min(0).optional().default(0),
  dailyHours: z.number().min(0).optional().default(0),
  laborRegime: z.string().optional().default(''),
  pensionRegime: z.string().optional().default(''),
  contractType: z.string().optional().default(''),
  observation: z.string().max(VR.OBSERVATION_MAX).optional().default(''),
  createdBy: z.string().min(1, 'Requerido'),
  directionId: z.string().optional().default(''),
  departmentId: z.string().optional().default(''),
  areaId: z.string().optional().default(''),
  sectionId: z.string().optional().default(''),
  branchId: z.string().optional().default(''),
  foodBonus: z.number().min(0).optional().default(0),
  bevBonus: z.number().min(0).optional().default(0),
  positionBonus: z.number().min(0).optional().default(0),
  totalSalary: z.number().min(0).optional().default(0),
  paymentHourType: z.string().optional().default(''),
  isDisability: z.boolean().optional().default(false),
  isBoss: z.boolean().optional().default(false),
  agreementType: z.string().optional().default(''),
  signingDate: z.string().optional().default(''),
  vacationStartDate: z.string().optional().default(''),
  vacationEndDate: z.string().optional().default(''),
  currencyType: z.string().optional().default(''),
  variableRemuneration: z.string().optional().default(''),
  occupationGroupId: z.string().optional().default(''),
  subModalityId: z.string().optional().default(''),
  isIntern: z.boolean().optional().default(false),
  documentsDelivered: z.boolean().optional().default(false),
  fingerprintRegistered: z.boolean().optional().default(false),
  payrollRegistered: z.boolean().optional().default(false),
  companyRuc: z.string().max(VR.RUC_MAX).optional().default(''),
  branchCode: z.string().optional().default(''),
  specialSituationId: z.string().optional().default(''),
  specialSituationDesc: z.string().max(VR.DESCRIPTION_MAX).optional().default(''),
});

type CreateForm = z.infer<typeof createSchema>;

const DEFAULT_VALUES: CreateForm = {
  requisitionId: '', templateId: '', contractNumber: '', workerId: '', positionId: '',
  startDate: '', endDate: '', terminationDate: '', conditionType: '',
  salaryAmount: 0, reintegrationAmount: 0, familyAllowance: 0,
  weeklyHours: 0, dailyHours: 0, laborRegime: '', pensionRegime: '', contractType: '',
  observation: '', createdBy: '',
  directionId: '', departmentId: '', areaId: '', sectionId: '', branchId: '',
  foodBonus: 0, bevBonus: 0, positionBonus: 0, totalSalary: 0,
  paymentHourType: '', isDisability: false, isBoss: false, agreementType: '',
  signingDate: '', vacationStartDate: '', vacationEndDate: '',
  currencyType: '', variableRemuneration: '', occupationGroupId: '', subModalityId: '',
  isIntern: false, documentsDelivered: false, fingerprintRegistered: false, payrollRegistered: false,
  companyRuc: '', branchCode: '', specialSituationId: '', specialSituationDesc: '',
};

const STATUS_TAGS: Record<string, { severity: 'info' | 'success' | 'warning' | 'danger'; label: string }> = {
  DRAFT: { severity: 'info', label: 'Borrador' },
  SIGNED: { severity: 'success', label: 'Firmado' },
  EXPIRED: { severity: 'danger', label: 'Vencido' },
  TERMINATED: { severity: 'warning', label: 'Terminado' },
};

function statusBody(row: ContractResponse) {
  const tag = STATUS_TAGS[row.status] ?? { severity: 'info', label: row.status };
  return <Tag severity={tag.severity as never} value={tag.label} />;
}

function dateBody(val: string) {
  return val ? dayjs(val).format('DD/MM/YYYY') : '—';
}

function salaryBody(row: ContractResponse) {
  return row.salaryAmount ? `Gs. ${row.salaryAmount.toLocaleString()}` : '—';
}

export function ContractListPage() {
  const queryClient = useQueryClient();
  const [showCreate, setShowCreate] = useState(false);

  const { data: contracts = [], isLoading } = useQuery({
    queryKey: ['contracts'],
    queryFn: () => apiGet<ContractResponse[]>(BASE_PATH),
  });

  const createMutation = useMutation({
    mutationFn: (req: ContractRequest) => apiPost<ContractResponse>(BASE_PATH, req),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['contracts'] });
      setShowCreate(false);
    },
  });

  const signMutation = useMutation({
    mutationFn: (id: string) => apiPatch<ContractResponse>(`${BASE_PATH}/${id}/sign`),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['contracts'] }),
  });

  const { control, handleSubmit, reset, formState: { errors } } = useForm<CreateForm>({
    resolver: zodResolver(createSchema),
    defaultValues: DEFAULT_VALUES,
  });

  const openCreate = () => { reset(DEFAULT_VALUES); setShowCreate(true); };

  const textInput = (name: keyof CreateForm) => (
    <Controller name={name} control={control} render={({ field }) => (
      <InputText {...field} value={String(field.value ?? '')} className={errors[name] ? 'p-invalid' : ''} />
    )} />
  );

  const numberInput = (name: keyof CreateForm) => (
    <Controller name={name} control={control} render={({ field }) => (
      <InputNumber {...field} value={Number(field.value) || 0} onValueChange={(e) => field.onChange(e.value ?? 0)} min={0} mode="currency" currency="PYG" locale="es-PY" className={errors[name] ? 'p-invalid' : ''} />
    )} />
  );

  const boolDropdown = (name: keyof CreateForm) => (
    <Controller name={name} control={control} render={({ field }) => (
      <Dropdown value={field.value} options={[{ label: 'Sí', value: true }, { label: 'No', value: false }]} onChange={(e) => field.onChange(e.value)} className="w-full" />
    )} />
  );

  const toolbarEnd = (
    <div className="flex gap-2">
      <Link to="/contracts/templates"><Button label="Plantillas" icon="pi pi-file" outlined size="small" /></Link>
      <Button label="Nuevo" icon="pi pi-plus" onClick={openCreate} />
    </div>
  );

  return (
    <div className="flex flex-column gap-3">
      <h3 className="m-0">Contratos</h3>
      <Toolbar end={toolbarEnd} className="surface-card border-round" />

      <DataTable value={contracts} loading={isLoading} paginator rows={10} rowsPerPageOptions={[5, 10, 20]}
        emptyMessage="No hay contratos" className="surface-card border-round" size="small" stripedRows
      >
        <Column field="contractNumber" header="Nro Contrato" sortable />
        <Column field="workerId" header="Trabajador" body={(r: ContractResponse) => r.workerId || '—'} sortable />
        <Column field="requisitionId" header="Requerimiento" body={(r: ContractResponse) => r.requisitionId?.substring(0, 8) + '...'} sortable />
        <Column field="status" header="Estado" body={statusBody} sortable />
        <Column body={salaryBody} header="Salario" sortable sortField="salaryAmount" />
        <Column field="startDate" header="Inicio" body={(r: ContractResponse) => dateBody(r.startDate)} sortable />
        <Column field="endDate" header="Fin" body={(r: ContractResponse) => dateBody(r.endDate)} sortable />
        <Column header="Acciones" body={(row: ContractResponse) => (
          <div className="flex gap-2">
            <Link to={`/contracts/${row.id}`}><Button icon="pi pi-eye" text rounded size="small" /></Link>
            {row.status === 'DRAFT' && (
              <Button icon="pi pi-check" text rounded size="small" severity="success"
                onClick={() => signMutation.mutate(row.id)} loading={signMutation.isPending}
                tooltip="Firmar" tooltipOptions={{ position: 'top' }} />
            )}
          </div>
        )} />
      </DataTable>

      <Dialog header="Nuevo Contrato" visible={showCreate} onHide={() => setShowCreate(false)}
        style={{ width: '720px' }} maximizable
        footer={
          <div className="flex justify-content-end gap-2">
            <Button label="Cancelar" outlined onClick={() => setShowCreate(false)} />
            <Button label="Crear" icon="pi pi-check" onClick={handleSubmit((d) => createMutation.mutate(d))} />
          </div>
        }
      >
        <form style={{ maxHeight: '60vh', overflowY: 'auto' }}>
          <div className="flex flex-column gap-2">
            <div className="gth-section-title"><i className="pi pi-file" /> Datos principales</div>
            <div className="grid">
              <div className="col-4">
                <div className="flex flex-column gap-1">
                  <label className="text-sm font-medium">Nro Contrato *</label>
                  {textInput('contractNumber')}
                  {errors.contractNumber && <small className="p-error">{errors.contractNumber.message}</small>}
                </div>
              </div>
              <div className="col-4">
                <div className="flex flex-column gap-1">
                  <label className="text-sm font-medium">ID Requerimiento *</label>
                  {textInput('requisitionId')}
                  {errors.requisitionId && <small className="p-error">{errors.requisitionId.message}</small>}
                </div>
              </div>
              <div className="col-4">
                <div className="flex flex-column gap-1">
                  <label className="text-sm font-medium">ID Plantilla *</label>
                  {textInput('templateId')}
                  {errors.templateId && <small className="p-error">{errors.templateId.message}</small>}
                </div>
              </div>
              <div className="col-4">
                <div className="flex flex-column gap-1">
                  <label className="text-sm font-medium">ID Trabajador</label>
                  {textInput('workerId')}
                </div>
              </div>
              <div className="col-4">
                <div className="flex flex-column gap-1">
                  <label className="text-sm font-medium">ID Puesto</label>
                  {textInput('positionId')}
                </div>
              </div>
              <div className="col-4">
                <div className="flex flex-column gap-1">
                  <label className="text-sm font-medium">Creado por *</label>
                  {textInput('createdBy')}
                  {errors.createdBy && <small className="p-error">{errors.createdBy.message}</small>}
                </div>
              </div>
            </div>
          </div>

          <div className="flex flex-column gap-2">
            <div className="gth-section-title"><i className="pi pi-calendar" /> Fechas</div>
            <div className="grid">
              <div className="col-4">
                <div className="flex flex-column gap-1">
                  <label className="text-sm font-medium">Fecha Inicio</label>
                  {textInput('startDate')}
                </div>
              </div>
              <div className="col-4">
                <div className="flex flex-column gap-1">
                  <label className="text-sm font-medium">Fecha Fin</label>
                  {textInput('endDate')}
                </div>
              </div>
              <div className="col-4">
                <div className="flex flex-column gap-1">
                  <label className="text-sm font-medium">Fecha Terminación</label>
                  {textInput('terminationDate')}
                </div>
              </div>
            </div>
          </div>

          <div className="flex flex-column gap-2">
            <div className="gth-section-title"><i className="pi pi-dollar" /> Información financiera y laboral</div>
            <div className="grid">
              <div className="col-4"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Salario</label>{numberInput('salaryAmount')}</div></div>
              <div className="col-4"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Reintegro</label>{numberInput('reintegrationAmount')}</div></div>
              <div className="col-4"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Asig. Familiar</label>{numberInput('familyAllowance')}</div></div>
              <div className="col-4"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Horas semanales</label>{numberInput('weeklyHours')}</div></div>
              <div className="col-4"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Horas diarias</label>{numberInput('dailyHours')}</div></div>
              <div className="col-4"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Tipo condición</label>{textInput('conditionType')}</div></div>
              <div className="col-4"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Régimen laboral</label>{textInput('laborRegime')}</div></div>
              <div className="col-4"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Régimen pensión</label>{textInput('pensionRegime')}</div></div>
              <div className="col-4"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Tipo de contrato</label>{textInput('contractType')}</div></div>
            </div>
          </div>

          <div className="flex flex-column gap-2">
            <div className="gth-section-title"><i className="pi pi-sitemap" /> Estructura organizacional</div>
            <div className="grid">
              <div className="col-3"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Dirección</label>{textInput('directionId')}</div></div>
              <div className="col-3"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Departamento</label>{textInput('departmentId')}</div></div>
              <div className="col-3"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Área</label>{textInput('areaId')}</div></div>
              <div className="col-3"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Sección</label>{textInput('sectionId')}</div></div>
              <div className="col-3"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Filial</label>{textInput('branchId')}</div></div>
              <div className="col-3"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Sucursal</label>{textInput('branchCode')}</div></div>
              <div className="col-3"><div className="flex flex-column gap-1"><label className="text-sm font-medium">RUC Emp.</label>{textInput('companyRuc')}</div></div>
            </div>
          </div>

          <div className="flex flex-column gap-2">
            <div className="gth-section-title"><i className="pi pi-dollar" /> Bonificaciones</div>
            <div className="grid">
              <div className="col-4"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Bono Alimentación</label>{numberInput('foodBonus')}</div></div>
              <div className="col-4"><div className="flex flex-column gap-1"><label className="text-sm font-medium">BEV</label>{numberInput('bevBonus')}</div></div>
              <div className="col-4"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Bono Puesto</label>{numberInput('positionBonus')}</div></div>
              <div className="col-4"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Total Salario</label>{numberInput('totalSalary')}</div></div>
              <div className="col-4"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Tipo Hora Pago</label>{textInput('paymentHourType')}</div></div>
              <div className="col-4"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Moneda</label>{textInput('currencyType')}</div></div>
            </div>
          </div>

          <div className="flex flex-column gap-2">
            <div className="gth-section-title"><i className="pi pi-check-square" /> Flags y fechas adicionales</div>
            <div className="grid">
              <div className="col-3"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Es Jefe</label>{boolDropdown('isBoss')}</div></div>
              <div className="col-3"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Discapacidad</label>{boolDropdown('isDisability')}</div></div>
              <div className="col-3"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Practicante</label>{boolDropdown('isIntern')}</div></div>
              <div className="col-3"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Docs. Entregados</label>{boolDropdown('documentsDelivered')}</div></div>
              <div className="col-3"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Huella Registrada</label>{boolDropdown('fingerprintRegistered')}</div></div>
              <div className="col-3"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Reg. Planilla</label>{boolDropdown('payrollRegistered')}</div></div>
              <div className="col-3"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Tipo Convenio</label>{textInput('agreementType')}</div></div>
              <div className="col-3"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Rem. Variable</label>{textInput('variableRemuneration')}</div></div>
              <div className="col-3"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Grupo Ocup.</label>{textInput('occupationGroupId')}</div></div>
              <div className="col-3"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Sub Modalidad</label>{textInput('subModalityId')}</div></div>
              <div className="col-4"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Fec. Suscripción</label>{textInput('signingDate')}</div></div>
              <div className="col-4"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Vac. Inicio</label>{textInput('vacationStartDate')}</div></div>
              <div className="col-4"><div className="flex flex-column gap-1"><label className="text-sm font-medium">Vac. Fin</label>{textInput('vacationEndDate')}</div></div>
            </div>
          </div>

          <div className="flex flex-column gap-2">
            <div className="gth-section-title"><i className="pi pi-comment" /> Observaciones</div>
            <Controller name="observation" control={control} render={({ field }) => (
              <InputTextarea {...field} value={field.value || ''} rows={2} />
            )} />
          </div>
        </form>
      </Dialog>
    </div>
  );
}
