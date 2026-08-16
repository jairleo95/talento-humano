import { useState } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import { Tag } from 'primereact/tag';
import { Divider } from 'primereact/divider';
import dayjs from 'dayjs';
import { apiGet } from '../../core/api/client';
import type { ContractResponse, AttachmentResponse } from './types';
import type { WorkerResponse } from '../workers/types';
import type { RequirementResponse } from '../requirements/types';

const BASE = '/contract/api/v1/contracts';
const RECRUITMENT_BASE = '/recruitment/api/v1/recruitment';

const STATUS_TAGS: Record<string, { severity: 'info' | 'success' | 'warning' | 'danger'; label: string }> = {
  DRAFT: { severity: 'info', label: 'Borrador' },
  PENDING_SIGNATURE: { severity: 'warning', label: 'Pendiente Firma' },
  ACTIVE: { severity: 'success', label: 'Activo' },
  EXPIRED: { severity: 'danger', label: 'Vencido' },
  TERMINATED: { severity: 'danger', label: 'Terminado' },
};

function fmt(val: string | undefined | null) {
  return val ? dayjs(val).format('DD/MM/YYYY') : '—';
}

function money(val: number | undefined | null) {
  return val != null ? `S/ ${val.toLocaleString('es-PE', { minimumFractionDigits: 2 })}` : '—';
}

export function ContractDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const { data: contract, isLoading } = useQuery({
    queryKey: ['contract', id],
    queryFn: () => apiGet<ContractResponse>(`${BASE}/${id}`),
    enabled: !!id,
  });

  const { data: worker } = useQuery({
    queryKey: ['worker', contract?.workerId],
    queryFn: () => apiGet<WorkerResponse>(`${RECRUITMENT_BASE}/workers/${contract?.workerId}`),
    enabled: !!contract?.workerId,
  });

  const { data: requisitions = [] } = useQuery({
    queryKey: ['requisitions'],
    queryFn: () => apiGet<RequirementResponse[]>(`${RECRUITMENT_BASE}/requisitions`),
    enabled: !!contract?.requisitionId,
  });

  const requisition = requisitions.find((r) => r.id === contract?.requisitionId);

  const { data: attachments = [] } = useQuery({
    queryKey: ['contract-attachments', id],
    queryFn: () => apiGet<AttachmentResponse[]>(`${BASE}/attachments?contractId=${id}`),
    enabled: !!id,
  });

  const { data: renderedDocument, refetch: fetchRender } = useQuery({
    queryKey: ['contract-render', id],
    queryFn: () => apiGet<string>(`${BASE}/${id}/render`),
    enabled: false,
  });

  const [showPreview, setShowPreview] = useState(false);

  const handlePreview = () => {
    fetchRender();
    setShowPreview(true);
  };

  if (isLoading) {
    return (
      <div className="flex align-items-center justify-content-center p-5">
        <i className="pi pi-spin pi-spinner" style={{ fontSize: '2rem', color: '#4f8cff' }} />
      </div>
    );
  }

  const tag = contract ? STATUS_TAGS[contract.status] || { severity: 'info', label: contract.status } : null;

  return (
    <div className="flex flex-column gap-3">
      {/* Header Bar */}
      <div className="flex align-items-center justify-content-between">
        <div className="flex align-items-center gap-2">
          <Button icon="pi pi-arrow-left" text rounded onClick={() => navigate('/contracts')} />
          <h3 className="m-0">Contrato {contract?.contractNumber}</h3>
          {tag && <Tag severity={tag.severity as never} value={tag.label} />}
          {contract?.isSpecialCase && <Tag severity="warning" value="Caso Especial (Sin DGP)" />}
        </div>
        <div className="flex gap-2">
          <Button icon="pi pi-file-pdf" label="Ver Documento Generado" severity="success" size="small" onClick={handlePreview} />
          <Button icon="pi pi-print" label="Imprimir / PDF" outlined size="small" onClick={() => window.print()} />
        </div>
      </div>

      {/* Modal Previsualización de Documento */}
      <Dialog header={`Documento Generado: ${contract?.contractNumber}`} visible={showPreview} onHide={() => setShowPreview(false)} style={{ width: '700px' }} maximizable>
        <pre className="surface-ground p-3 border-round font-mono text-sm white-space-pre-wrap m-0" style={{ maxHeight: '60vh', overflowY: 'auto' }}>
          {renderedDocument || 'Cargando documento generado...'}
        </pre>
      </Dialog>

      {contract && (
        <>
          {/* General Contract Info */}
          <div className="surface-card border-round p-3">
            <h4 className="mt-0 mb-3 text-primary flex align-items-center gap-2">
              <i className="pi pi-id-card" /> Datos del Contrato
            </h4>
            <div className="grid">
              <div className="col-3">
                <label className="text-xs text-color-secondary">Nro Contrato</label>
                <p className="m-0 font-bold text-lg">{contract.contractNumber}</p>
              </div>
              <div className="col-3">
                <label className="text-xs text-color-secondary">Trabajador</label>
                <p className="m-0 font-medium">
                  {worker ? (
                    <Link to={`/workers/${worker.id}`} className="no-underline text-primary">
                      {worker.lastNamePaternal} {worker.lastNameMaternal || ''}, {worker.firstName}
                    </Link>
                  ) : (
                    contract.workerId || '—'
                  )}
                </p>
              </div>
              <div className="col-3">
                <label className="text-xs text-color-secondary">Requerimiento / Puesto</label>
                <p className="m-0 font-medium">
                  {requisition ? `${requisition.requestNumber} - ${requisition.title}` : contract.positionId || '—'}
                </p>
              </div>
              <div className="col-3">
                <label className="text-xs text-color-secondary">Tipo de Contrato</label>
                <p className="m-0 font-medium">{contract.contractType || '—'}</p>
              </div>

              <div className="col-3 mt-2">
                <label className="text-xs text-color-secondary">Condición</label>
                <p className="m-0">{contract.conditionType || '—'}</p>
              </div>
              <div className="col-3 mt-2">
                <label className="text-xs text-color-secondary">Régimen Laboral</label>
                <p className="m-0">{contract.laborRegime || '—'}</p>
              </div>
              <div className="col-3 mt-2">
                <label className="text-xs text-color-secondary">Régimen de Pensión</label>
                <p className="m-0">{contract.pensionRegime || '—'}</p>
              </div>
              <div className="col-3 mt-2">
                <label className="text-xs text-color-secondary">Fecha Suscripción / Firma</label>
                <p className="m-0">{fmt(contract.signedAt || contract.signingDate)}</p>
              </div>
            </div>
          </div>

          {/* Fechas de Vigencia */}
          <div className="surface-card border-round p-3">
            <h4 className="mt-0 mb-3 text-primary flex align-items-center gap-2">
              <i className="pi pi-calendar" /> Fechas y Horarios
            </h4>
            <div className="grid">
              <div className="col-3">
                <label className="text-xs text-color-secondary">Fecha Inicio</label>
                <p className="m-0 font-semibold text-green-500">{fmt(contract.startDate)}</p>
              </div>
              <div className="col-3">
                <label className="text-xs text-color-secondary">Fecha Fin</label>
                <p className="m-0 font-semibold text-orange-500">{fmt(contract.endDate)}</p>
              </div>
              <div className="col-3">
                <label className="text-xs text-color-secondary">Fecha Terminación</label>
                <p className="m-0">{fmt(contract.terminationDate)}</p>
              </div>
              <div className="col-3">
                <label className="text-xs text-color-secondary">Jornada Horaria</label>
                <p className="m-0">{contract.weeklyHours || 48} hrs/sem ({contract.dailyHours || 8} hrs/día)</p>
              </div>
            </div>
          </div>

          {/* Remuneración y Beneficios */}
          <div className="surface-card border-round p-3">
            <h4 className="mt-0 mb-3 text-primary flex align-items-center gap-2">
              <i className="pi pi-dollar" /> Remuneración y Beneficios (Soles - S/)
            </h4>
            <div className="grid">
              <div className="col-3">
                <label className="text-xs text-color-secondary">Salario Base</label>
                <p className="m-0 font-bold text-lg text-primary">{money(contract.salaryAmount)}</p>
              </div>
              <div className="col-3">
                <label className="text-xs text-color-secondary">Asignación Familiar</label>
                <p className="m-0 font-medium">{money(contract.familyAllowance)}</p>
              </div>
              <div className="col-3">
                <label className="text-xs text-color-secondary">Bono Alimentación</label>
                <p className="m-0 font-medium">{money(contract.foodBonus)}</p>
              </div>
              <div className="col-3">
                <label className="text-xs text-color-secondary">Bono Puesto</label>
                <p className="m-0 font-medium">{money(contract.positionBonus)}</p>
              </div>
              <div className="col-3 mt-2">
                <label className="text-xs text-color-secondary">BEV Bonus</label>
                <p className="m-0">{money(contract.bevBonus)}</p>
              </div>
              <div className="col-3 mt-2">
                <label className="text-xs text-color-secondary">Reintegro</label>
                <p className="m-0">{money(contract.reintegrationAmount)}</p>
              </div>
              <div className="col-6 mt-2">
                <label className="text-xs text-color-secondary">Remuneraciones Totales Estimadas</label>
                <p className="m-0 font-bold text-xl text-green-400">
                  {money(
                    (contract.totalSalary || contract.salaryAmount || 0) +
                      (contract.familyAllowance || 0) +
                      (contract.foodBonus || 0) +
                      (contract.positionBonus || 0)
                  )}
                </p>
              </div>
            </div>
          </div>

          {/* Observaciones y Notas */}
          <div className="surface-card border-round p-3">
            <h4 className="mt-0 mb-2 text-primary flex align-items-center gap-2">
              <i className="pi pi-comment" /> Observaciones y Notas
            </h4>
            <p className="m-0 text-color-secondary">{contract.observation || 'Sin observaciones registradas.'}</p>
          </div>
        </>
      )}

      {/* Adjuntos y Documentos */}
      <Divider />
      <div className="flex align-items-center justify-content-between">
        <h4 className="m-0">Documentos Adjuntos ({attachments.length})</h4>
      </div>
      <DataTable value={attachments} emptyMessage="No hay documentos adjuntos a este contrato." className="surface-card border-round" size="small">
        <Column field="filename" header="Archivo" sortable />
        <Column field="contentType" header="Tipo" sortable />
        <Column field="sizeBytes" header="Tamaño" body={(r: AttachmentResponse) => (r.sizeBytes ? `${(r.sizeBytes / 1024).toFixed(1)} KB` : '—')} />
        <Column field="createdAt" header="Fecha Carga" body={(r: AttachmentResponse) => fmt(r.createdAt)} sortable />
      </DataTable>
    </div>
  );
}
