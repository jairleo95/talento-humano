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
import { Divider } from 'primereact/divider';
import { useForm, Controller } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import dayjs from 'dayjs';
import { apiGet, apiPost, apiPatch } from '../../core/api/client';
import type { ContractResponse, ContractRequest, AttachmentResponse } from './types';
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
  PENDING_SIGNATURE: { severity: 'warning', label: 'Pendiente Firma' },
  ACTIVE: { severity: 'success', label: 'Activo' },
  EXPIRED: { severity: 'danger', label: 'Vencido' },
  TERMINATED: { severity: 'danger', label: 'Terminado' },
};

function statusBody(row: ContractResponse) {
  const tag = STATUS_TAGS[row.status] ?? { severity: 'info', label: row.status };
  return <Tag severity={tag.severity as never} value={tag.label} />;
}

function dateBody(val: string) {
  return val ? dayjs(val).format('DD/MM/YYYY') : '—';
}

function salaryBody(row: ContractResponse) {
  return row.salaryAmount ? `S/ ${row.salaryAmount.toLocaleString('es-PE', { minimumFractionDigits: 2 })}` : '—';
}

export function ContractListPage() {
  const queryClient = useQueryClient();
  const [showCreate, setShowCreate] = useState(false);

  const [detailContract, setDetailContract] = useState<ContractResponse | null>(null);

  const { data: contracts = [], isLoading } = useQuery({
    queryKey: ['contracts'],
    queryFn: () => apiGet<ContractResponse[]>(BASE_PATH),
  });

  const { data: attachments = [] } = useQuery({
    queryKey: ['contract-attachments-detail', detailContract?.id],
    queryFn: () => apiGet<AttachmentResponse[]>(`${BASE_PATH}/attachments?contractId=${detailContract?.id}`),
    enabled: !!detailContract?.id,
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
      <InputNumber {...field} value={Number(field.value) || 0} onValueChange={(e) => field.onChange(e.value ?? 0)} min={0} mode="currency" currency="PEN" locale="es-PE" className={errors[name] ? 'p-invalid' : ''} />
    )} />
  );

  const boolDropdown = (name: keyof CreateForm) => (
    <Controller name={name} control={control} render={({ field }) => (
      <Dropdown value={field.value} options={[{ label: 'Sí', value: true }, { label: 'No', value: false }]} onChange={(e) => field.onChange(e.value)} className="w-full" />
    )} />
  );

  const [selectedContracts, setSelectedContracts] = useState<ContractResponse[]>([]);
  const [batchHtml, setBatchHtml] = useState<string | null>(null);
  const [signedModalContract, setSignedModalContract] = useState<ContractResponse | null>(null);
  const [signedFileUrl, setSignedFileUrl] = useState('');
  const [signedByInput, setSignedByInput] = useState('admin');
  const [workerHistory, setWorkerHistory] = useState<ContractResponse[] | null>(null);
  const [workerHistoryName, setWorkerHistoryName] = useState('');

  const batchRenderMutation = useMutation({
    mutationFn: (contractIds: string[]) => apiPost<string>(`${BASE_PATH}/batch-render`, contractIds),
    onSuccess: (html) => setBatchHtml(html),
  });

  const uploadSignedMutation = useMutation({
    mutationFn: ({ id, url, user }: { id: string; url: string; user: string }) =>
      apiPatch<ContractResponse>(`${BASE_PATH}/${id}/signed-document?fileUrl=${encodeURIComponent(url)}&signedBy=${encodeURIComponent(user)}`),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['contracts'] });
      setSignedModalContract(null);
    },
  });

  const loadWorkerTimeline = async (workerId: string) => {
    setWorkerHistoryName(workerId);
    const history = await apiGet<ContractResponse[]>(`${BASE_PATH}/worker/${workerId}/history`);
    setWorkerHistory(history);
  };

  const handleBatchPrint = () => {
    if (selectedContracts.length === 0) return;
    const ids = selectedContracts.map((c) => c.id);
    batchRenderMutation.mutate(ids);
  };

  const toolbarEnd = (
    <div className="flex gap-2">
      {selectedContracts.length > 0 && (
        <Button label={`Impresión Masiva (${selectedContracts.length})`} icon="pi pi-print" severity="info" size="small"
          loading={batchRenderMutation.isPending} onClick={handleBatchPrint} />
      )}
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
        selectionMode="checkbox"
        selection={selectedContracts} onSelectionChange={(e) => setSelectedContracts((e.value as ContractResponse[]) || [])}
        dataKey="id"
        onRowClick={(e) => setDetailContract(e.data as ContractResponse)}
        rowClassName={() => 'cursor-pointer'}
      >
        <Column selectionMode="multiple" headerStyle={{ width: '3rem' }} />
        <Column field="contractNumber" header="Nro Contrato" sortable />
        <Column field="workerId" header="Trabajador ID" body={(r: ContractResponse) => (
          <Button label={r.workerId || '—'} link size="small" className="p-0 font-bold" onClick={(e) => { e.stopPropagation(); loadWorkerTimeline(r.workerId); }} tooltip="Ver Historial / Adendas" />
        )} sortable />
        <Column field="requisitionId" header="Requerimiento ID" body={(r: ContractResponse) => r.isSpecialCase ? <Tag severity="warning" value="Caso Especial" /> : (r.requisitionId?.substring(0, 8) + '...')} sortable />
        <Column field="status" header="Estado" body={statusBody} sortable />
        <Column body={salaryBody} header="Salario" sortable sortField="salaryAmount" />
        <Column field="startDate" header="Inicio" body={(r: ContractResponse) => dateBody(r.startDate)} sortable />
        <Column field="endDate" header="Fin" body={(r: ContractResponse) => dateBody(r.endDate)} sortable />
        <Column header="Acciones" body={(row: ContractResponse) => (
          <div className="flex gap-1" onClick={(e) => e.stopPropagation()}>
            <Button icon="pi pi-eye" text rounded size="small" onClick={() => setDetailContract(row)} tooltip="Ver Detalle" />
            <Link to={`/contracts/${row.id}`}><Button icon="pi pi-external-link" text rounded size="small" tooltip="Página Detalle" /></Link>
            <Button icon="pi pi-history" text rounded size="small" severity="secondary" onClick={() => loadWorkerTimeline(row.workerId)} tooltip="Línea de Tiempo / Adendas" />
            <Button icon="pi pi-upload" text rounded size="small" severity="help" onClick={() => { setSignedModalContract(row); setSignedFileUrl(row.signedFileUrl || ''); }} tooltip="Subir PDF Firmado" />
            {row.status === 'DRAFT' && (
              <Button icon="pi pi-check" text rounded size="small" severity="success"
                onClick={() => signMutation.mutate(row.id)} loading={signMutation.isPending}
                tooltip="Firmar" tooltipOptions={{ position: 'top' }} />
            )}
          </div>
        )} />
      </DataTable>

      {/* Modal Detalle de Contrato */}
      <Dialog header={`Detalle del Contrato: ${detailContract?.contractNumber || ''}`} visible={!!detailContract}
        onHide={() => setDetailContract(null)} style={{ width: '720px' }} maximizable
      >
        {detailContract && (
          <div className="flex flex-column gap-3">
            <div className="flex align-items-center justify-content-between">
              <div className="flex align-items-center gap-2">
                <span className="text-xl font-bold text-primary">{detailContract.contractNumber}</span>
                {detailContract.isSpecialCase && <Tag severity="warning" value="Caso Especial (Sin DGP)" />}
              </div>
              <div className="flex align-items-center gap-2">
                <Link to={`/contracts/${detailContract.id}`}><Button icon="pi pi-external-link" label="Ver Página Completa" outlined size="small" /></Link>
                {statusBody(detailContract)}
              </div>
            </div>

            <div className="surface-ground border-round p-3">
              <h4 className="mt-0 mb-2 text-primary flex align-items-center gap-2">
                <i className="pi pi-id-card" /> Información General
              </h4>
              <div className="grid">
                <div className="col-4"><label className="text-xs text-color-secondary">ID Trabajador</label><p className="m-0 font-medium">{detailContract.workerId || '—'}</p></div>
                <div className="col-4"><label className="text-xs text-color-secondary">ID Requerimiento</label><p className="m-0 font-medium">{detailContract.requisitionId || '—'}</p></div>
                <div className="col-4"><label className="text-xs text-color-secondary">ID Puesto</label><p className="m-0">{detailContract.positionId || '—'}</p></div>
                <div className="col-4"><label className="text-xs text-color-secondary">Tipo Contrato</label><p className="m-0 font-medium">{detailContract.contractType || '—'}</p></div>
                <div className="col-4"><label className="text-xs text-color-secondary">Condición</label><p className="m-0">{detailContract.conditionType || '—'}</p></div>
                <div className="col-4"><label className="text-xs text-color-secondary">Creado por</label><p className="m-0">{detailContract.createdBy || '—'}</p></div>
                <div className="col-4"><label className="text-xs text-color-secondary">Régimen Laboral</label><p className="m-0">{detailContract.laborRegime || '—'}</p></div>
                <div className="col-4"><label className="text-xs text-color-secondary">Régimen Pensión</label><p className="m-0">{detailContract.pensionRegime || '—'}</p></div>
                <div className="col-4"><label className="text-xs text-color-secondary">Fecha Firma</label><p className="m-0">{dateBody(detailContract.signedAt || detailContract.signingDate || '')}</p></div>
              </div>
            </div>

            <Divider />

            <div className="surface-ground border-round p-3">
              <h4 className="mt-0 mb-2 text-primary flex align-items-center gap-2">
                <i className="pi pi-calendar" /> Vigencia y Horario
              </h4>
              <div className="grid">
                <div className="col-4"><label className="text-xs text-color-secondary">Fecha Inicio</label><p className="m-0 font-semibold text-green-500">{dateBody(detailContract.startDate)}</p></div>
                <div className="col-4"><label className="text-xs text-color-secondary">Fecha Fin</label><p className="m-0 font-semibold text-orange-500">{dateBody(detailContract.endDate)}</p></div>
                <div className="col-4"><label className="text-xs text-color-secondary">Fecha Terminación</label><p className="m-0">{dateBody(detailContract.terminationDate)}</p></div>
                <div className="col-6"><label className="text-xs text-color-secondary">Jornada Semanal / Diaria</label><p className="m-0">{detailContract.weeklyHours || 48} hrs/sem ({detailContract.dailyHours || 8} hrs/día)</p></div>
              </div>
            </div>

            <Divider />

            <div className="surface-ground border-round p-3">
              <h4 className="mt-0 mb-2 text-primary flex align-items-center gap-2">
                <i className="pi pi-dollar" /> Remuneración y Beneficios (Soles - S/)
              </h4>
              <div className="grid">
                <div className="col-4"><label className="text-xs text-color-secondary">Salario Base</label><p className="m-0 font-bold text-lg text-primary">{salaryBody(detailContract)}</p></div>
                <div className="col-4"><label className="text-xs text-color-secondary">Asignación Familiar</label><p className="m-0">S/ {(detailContract.familyAllowance || 0).toLocaleString('es-PE', { minimumFractionDigits: 2 })}</p></div>
                <div className="col-4"><label className="text-xs text-color-secondary">Bono Alimentación</label><p className="m-0">S/ {(detailContract.foodBonus || 0).toLocaleString('es-PE', { minimumFractionDigits: 2 })}</p></div>
                <div className="col-4"><label className="text-xs text-color-secondary">Bono Puesto</label><p className="m-0">S/ {(detailContract.positionBonus || 0).toLocaleString('es-PE', { minimumFractionDigits: 2 })}</p></div>
                <div className="col-4"><label className="text-xs text-color-secondary">BEV Bonus</label><p className="m-0">S/ {(detailContract.bevBonus || 0).toLocaleString('es-PE', { minimumFractionDigits: 2 })}</p></div>
                <div className="col-4"><label className="text-xs text-color-secondary">Reintegro</label><p className="m-0">S/ {(detailContract.reintegrationAmount || 0).toLocaleString('es-PE', { minimumFractionDigits: 2 })}</p></div>
              </div>
            </div>

            <Divider />

            <div className="surface-ground border-round p-3">
              <h4 className="mt-0 mb-2 text-primary flex align-items-center gap-2">
                <i className="pi pi-comment" /> Observaciones
              </h4>
              <p className="m-0 text-color-secondary">{detailContract.observation || 'Sin observaciones registradas.'}</p>
            </div>

            <Divider />

            <div className="flex flex-column gap-2">
              <h4 className="m-0 mb-1">Documentos Adjuntos ({attachments.length})</h4>
              <DataTable value={attachments} emptyMessage="No hay documentos adjuntos" size="small">
                <Column field="filename" header="Archivo" />
                <Column field="contentType" header="Tipo" />
                <Column field="sizeBytes" header="Tamaño" body={(r: AttachmentResponse) => (r.sizeBytes ? `${(r.sizeBytes / 1024).toFixed(1)} KB` : '—')} />
                <Column field="createdAt" header="Fecha" body={(r: AttachmentResponse) => dateBody(r.createdAt)} />
              </DataTable>
            </div>
          </div>
        )}
      </Dialog>

      {/* Modal Nuevo Contrato */}
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

      {/* Modal Visor de Impresión Masiva (Lote) */}
      <Dialog header={`Impresión Masiva de Contratos (${selectedContracts.length} Documentos)`} visible={!!batchHtml}
        onHide={() => setBatchHtml(null)} style={{ width: '850px' }} maximizable
        footer={
          <div className="flex justify-content-end gap-2">
            <Button label="Cerrar" outlined onClick={() => setBatchHtml(null)} />
            <Button label="Imprimir Documentos" icon="pi pi-print" severity="success" onClick={() => window.print()} />
          </div>
        }
      >
        <div className="surface-ground p-3 border-round border-1 surface-border" style={{ maxHeight: '70vh', overflowY: 'auto' }}>
          <div className="alert alert-info text-xs mb-3 flex align-items-center gap-2">
            <i className="pi pi-info-circle" /> Se compiló el lote continuo de contratos. Haga clic en <strong>Imprimir Documentos</strong> para enviar a la impresora.
          </div>
          <div dangerouslySetInnerHTML={{ __html: batchHtml || '' }} />
        </div>
      </Dialog>

      {/* Modal Subir Contrato Firmado Escaneado */}
      <Dialog header={`Subir Documento Firmado: ${signedModalContract?.contractNumber || ''}`} visible={!!signedModalContract}
        onHide={() => setSignedModalContract(null)} style={{ width: '550px' }}
        footer={
          <div className="flex justify-content-end gap-2">
            <Button label="Cancelar" outlined onClick={() => setSignedModalContract(null)} />
            <Button label="Registrar y Marcar como Firmado" icon="pi pi-check" severity="success"
              loading={uploadSignedMutation.isPending}
              onClick={() => {
                if (!signedFileUrl) return alert('Ingrese la URL del documento firmado');
                uploadSignedMutation.mutate({ id: signedModalContract!.id, url: signedFileUrl, user: signedByInput });
              }} />
          </div>
        }
      >
        <div className="flex flex-column gap-3 pt-2">
          <div className="flex flex-column gap-1">
            <label className="text-sm font-semibold">URL / URI del PDF Escaneado Firmado *</label>
            <InputText value={signedFileUrl} onChange={(e) => setSignedFileUrl(e.target.value)} placeholder="https://docs.empresa.com/contratos/cto_47651835_firmado.pdf" />
          </div>
          <div className="flex flex-column gap-1">
            <label className="text-sm font-semibold">Usuario que Registra la Firma *</label>
            <InputText value={signedByInput} onChange={(e) => setSignedByInput(e.target.value)} />
          </div>
        </div>
      </Dialog>

      {/* Modal Histórico de Adendas y Renovaciones por Trabajador */}
      <Dialog header={`Histórico y Línea de Tiempo de Contratos: ${workerHistoryName}`} visible={!!workerHistory}
        onHide={() => setWorkerHistory(null)} style={{ width: '750px' }} maximizable
      >
        <div className="flex flex-column gap-3 pt-2">
          {workerHistory && workerHistory.length === 0 ? (
            <div className="p-3 text-center text-color-secondary italic">No se encontraron contratos ni adendas anteriores para este trabajador</div>
          ) : (
            <div className="flex flex-column gap-3">
              <span className="text-xs font-semibold text-color-secondary">Secuencia cronológica de contratos y adendas:</span>
              {workerHistory?.map((c, idx) => (
                <div key={c.id} className="surface-card border-round p-3 border-1 surface-border shadow-1 flex flex-column gap-2">
                  <div className="flex align-items-center justify-content-between">
                    <div className="flex align-items-center gap-2">
                      <Tag severity={idx === 0 ? 'info' : 'warning'} value={idx === 0 ? 'Contrato Inicial' : `Renovación / Adenda #${idx}`} />
                      <span className="font-bold text-primary text-base">{c.contractNumber}</span>
                    </div>
                    {statusBody(c)}
                  </div>
                  <div className="grid text-sm text-color-secondary">
                    <div className="col-4"><strong>Puesto:</strong> {c.positionId || '—'}</div>
                    <div className="col-4"><strong>Salario:</strong> {salaryBody(c)}</div>
                    <div className="col-4"><strong>Régimen:</strong> {c.laborRegime || '—'}</div>
                    <div className="col-6"><strong>Vigencia:</strong> {dateBody(c.startDate)} al {dateBody(c.endDate)}</div>
                    <div className="col-6"><strong>Firmado:</strong> {c.signedAt ? dayjs(c.signedAt).format('DD/MM/YYYY HH:mm') : 'Pendiente'}</div>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </Dialog>
    </div>
  );
}
