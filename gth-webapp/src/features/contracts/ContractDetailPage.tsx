import { useParams, useNavigate } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Tag } from 'primereact/tag';
import { Divider } from 'primereact/divider';
import dayjs from 'dayjs';
import { apiGet } from '../../core/api/client';
import type { ContractResponse, AttachmentResponse } from './types';

const BASE = '/contract/api/v1/contracts';

const STATUS_TAGS: Record<string, { severity: 'info' | 'success' | 'warning' | 'danger'; label: string }> = {
  DRAFT: { severity: 'info', label: 'Borrador' },
  SIGNED: { severity: 'success', label: 'Firmado' },
  EXPIRED: { severity: 'danger', label: 'Vencido' },
  TERMINATED: { severity: 'warning', label: 'Terminado' },
};

function fmt(val: string | undefined | null) {
  return val ? dayjs(val).format('DD/MM/YYYY') : '—';
}

export function ContractDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const { data: contract, isLoading } = useQuery({
    queryKey: ['contract', id],
    queryFn: () => apiGet<ContractResponse>(`${BASE}/${id}`),
    enabled: !!id,
  });

  const { data: attachments = [] } = useQuery({
    queryKey: ['contract-attachments', id],
    queryFn: () => apiGet<AttachmentResponse[]>(`${BASE}/attachments?contractId=${id}`),
    enabled: !!id,
  });

  if (isLoading) {
    return <div className="flex align-items-center justify-content-center p-5">
      <i className="pi pi-spin pi-spinner" style={{ fontSize: '2rem' }} />
    </div>;
  }

  const tag = contract ? STATUS_TAGS[contract.status] : null;

  return (
    <div className="flex flex-column gap-3">
      <div className="flex align-items-center gap-2">
        <Button icon="pi pi-arrow-left" text rounded onClick={() => navigate('/contracts')} />
        <h3 className="m-0">Contrato {contract?.contractNumber}</h3>
        {tag && <Tag severity={tag.severity as never} value={tag.label} />}
      </div>

      {contract && (
        <>
          <div className="surface-card border-round p-3">
            <div className="grid">
              <div className="col-3"><label className="text-xs text-color-secondary">Nro Contrato</label><p className="m-0 font-medium">{contract.contractNumber}</p></div>
              <div className="col-3"><label className="text-xs text-color-secondary">Requerimiento</label><p className="m-0">{contract.requisitionId?.substring(0, 8)}...</p></div>
              <div className="col-3"><label className="text-xs text-color-secondary">Plantilla</label><p className="m-0">{contract.templateId?.substring(0, 8)}...</p></div>
              <div className="col-3"><label className="text-xs text-color-secondary">Firmado</label><p className="m-0">{fmt(contract.signedAt)}</p></div>
              <div className="col-3"><label className="text-xs text-color-secondary">Trabajador</label><p className="m-0">{contract.workerId || '—'}</p></div>
              <div className="col-3"><label className="text-xs text-color-secondary">Puesto</label><p className="m-0">{contract.positionId || '—'}</p></div>
              <div className="col-3"><label className="text-xs text-color-secondary">Creado por</label><p className="m-0">{contract.createdBy || '—'}</p></div>
              <div className="col-3"><label className="text-xs text-color-secondary">Creado</label><p className="m-0">{fmt(contract.createdAt)}</p></div>
            </div>
          </div>

          <div className="surface-card border-round p-3">
            <h4 className="mt-0 mb-2">Fechas</h4>
            <div className="grid">
              <div className="col-3"><label className="text-xs text-color-secondary">Inicio</label><p className="m-0">{fmt(contract.startDate)}</p></div>
              <div className="col-3"><label className="text-xs text-color-secondary">Fin</label><p className="m-0">{fmt(contract.endDate)}</p></div>
              <div className="col-3"><label className="text-xs text-color-secondary">Terminación</label><p className="m-0">{fmt(contract.terminationDate)}</p></div>
            </div>
          </div>

          <div className="surface-card border-round p-3">
            <h4 className="mt-0 mb-2">Información laboral y financiera</h4>
            <div className="grid">
              <div className="col-2"><label className="text-xs text-color-secondary">Salario</label><p className="m-0">Gs. {(contract.salaryAmount || 0).toLocaleString()}</p></div>
              <div className="col-2"><label className="text-xs text-color-secondary">Reintegro</label><p className="m-0">Gs. {(contract.reintegrationAmount || 0).toLocaleString()}</p></div>
              <div className="col-2"><label className="text-xs text-color-secondary">Asig. Familiar</label><p className="m-0">Gs. {(contract.familyAllowance || 0).toLocaleString()}</p></div>
              <div className="col-2"><label className="text-xs text-color-secondary">Horas sem.</label><p className="m-0">{contract.weeklyHours || '—'}</p></div>
              <div className="col-2"><label className="text-xs text-color-secondary">Horas diarias</label><p className="m-0">{contract.dailyHours || '—'}</p></div>
              <div className="col-2"><label className="text-xs text-color-secondary">Condición</label><p className="m-0">{contract.conditionType || '—'}</p></div>
              <div className="col-4"><label className="text-xs text-color-secondary">Régimen laboral</label><p className="m-0">{contract.laborRegime || '—'}</p></div>
              <div className="col-4"><label className="text-xs text-color-secondary">Régimen pensión</label><p className="m-0">{contract.pensionRegime || '—'}</p></div>
              <div className="col-4"><label className="text-xs text-color-secondary">Tipo contrato</label><p className="m-0">{contract.contractType || '—'}</p></div>
            </div>
          </div>
        </>
      )}

      <div className="surface-card border-round p-3">
        <h4 className="mt-0 mb-2">Observaciones</h4>
        <p className="m-0">{contract?.observation || 'Sin observaciones'}</p>
      </div>

      <Divider />
      <h4 className="m-0">Adjuntos ({attachments.length})</h4>
      <DataTable value={attachments} emptyMessage="Sin adjuntos" className="surface-card border-round" size="small">
        <Column field="filename" header="Archivo" sortable />
        <Column field="contentType" header="Tipo" sortable />
        <Column field="uri" header="URI" sortable />
        <Column field="sizeBytes" header="Tamaño" body={(r: AttachmentResponse) => r.sizeBytes ? `${(r.sizeBytes / 1024).toFixed(1)} KB` : '—'} />
        <Column field="createdAt" header="Fecha" body={(r: AttachmentResponse) => fmt(r.createdAt)} sortable />
      </DataTable>
    </div>
  );
}
