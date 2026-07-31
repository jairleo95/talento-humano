import { useParams, useNavigate } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { Button } from 'primereact/button';
import { Tag } from 'primereact/tag';
import { Divider } from 'primereact/divider';
import dayjs from 'dayjs';
import { apiGet } from '../../core/api/client';
import type { WorkerResponse } from './types';

const BASE = '/recruitment/api/v1/recruitment/workers';

function fmt(val: string | undefined | null) {
  return val ? dayjs(val).format('DD/MM/YYYY') : '—';
}

const GENDER_MAP: Record<string, string> = { M: 'Masculino', F: 'Femenino' };
const CIVIL_MAP: Record<string, string> = { SOLTERO: 'Soltero', CASADO: 'Casado', DIVORCIADO: 'Divorciado', VIUDO: 'Viudo' };

export function WorkerDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const { data: worker, isLoading } = useQuery({
    queryKey: ['worker', id],
    queryFn: () => apiGet<WorkerResponse>(`${BASE}/${id}`),
    enabled: !!id,
  });

  if (isLoading) {
    return <div className="flex align-items-center justify-content-center p-5">
      <i className="pi pi-spin pi-spinner" style={{ fontSize: '2rem', color: '#4f8cff' }} />
    </div>;
  }

  const w = worker;
  if (!w) return null;

  return (
    <div className="flex flex-column gap-3">
      <div className="flex align-items-center gap-2">
        <Button icon="pi pi-arrow-left" text rounded onClick={() => navigate('/workers')} />
        <h3 className="m-0">{w.lastNamePaternal} {w.lastNameMaternal || ''}, {w.firstName}</h3>
        <Tag severity="info" value={w.legacyId || ''} />
      </div>

      <div className="surface-card border-round p-3">
        <h4 className="mt-0 mb-2">Datos personales</h4>
        <div className="grid">
          <div className="col-3"><label className="text-xs text-color-secondary">Tipo Doc.</label><p className="m-0">{w.documentType}</p></div>
          <div className="col-3"><label className="text-xs text-color-secondary">Nro Doc.</label><p className="m-0 font-medium">{w.documentNumber}</p></div>
          <div className="col-2"><label className="text-xs text-color-secondary">Compl.</label><p className="m-0">{w.documentNumberComplement || '—'}</p></div>
          <div className="col-2"><label className="text-xs text-color-secondary">Género</label><p className="m-0">{GENDER_MAP[w.gender] || w.gender || '—'}</p></div>
          <div className="col-2"><label className="text-xs text-color-secondary">Estado Civil</label><p className="m-0">{CIVIL_MAP[w.civilStatus] || w.civilStatus || '—'}</p></div>
          <div className="col-3"><label className="text-xs text-color-secondary">Fec. Nacimiento</label><p className="m-0">{fmt(w.birthDate)}</p></div>
          <div className="col-2"><label className="text-xs text-color-secondary">Grupo Sang.</label><p className="m-0">{w.bloodGroup || '—'} {w.rhFactor || ''}</p></div>
          <div className="col-2"><label className="text-xs text-color-secondary">Nacionalidad</label><p className="m-0">{w.nationalityId || '—'}</p></div>
          <div className="col-3"><label className="text-xs text-color-secondary">Teléfono</label><p className="m-0">{w.phone || '—'}</p></div>
          <div className="col-3"><label className="text-xs text-color-secondary">Celular</label><p className="m-0">{w.cellphone || '—'}</p></div>
          <div className="col-6"><label className="text-xs text-color-secondary">Email Personal</label><p className="m-0">{w.email || '—'}</p></div>
          <div className="col-6"><label className="text-xs text-color-secondary">Email Institucional</label><p className="m-0">{w.institutionalEmail || '—'}</p></div>
          <div className="col-6"><label className="text-xs text-color-secondary">Dirección</label><p className="m-0">{w.address || '—'}</p></div>
          <div className="col-3"><label className="text-xs text-color-secondary">Depto</label><p className="m-0">{w.departmentId || '—'}</p></div>
          <div className="col-3"><label className="text-xs text-color-secondary">Prov/Dist</label><p className="m-0">{w.provinceId || '—'} / {w.districtId || '—'}</p></div>
        </div>
      </div>

      <div className="surface-card border-round p-3">
        <h4 className="mt-0 mb-2">Formación académica</h4>
        <div className="grid">
          <div className="col-3"><label className="text-xs text-color-secondary">Nivel Educativo</label><p className="m-0">{w.educationLevel || '—'}</p></div>
          <div className="col-3"><label className="text-xs text-color-secondary">Grado</label><p className="m-0">{w.degree || '—'}</p></div>
          <div className="col-3"><label className="text-xs text-color-secondary">Título</label><p className="m-0">{w.professionalTitle || '—'}</p></div>
          <div className="col-3"><label className="text-xs text-color-secondary">Carrera</label><p className="m-0">{w.careerId || '—'}</p></div>
          <div className="col-3"><label className="text-xs text-color-secondary">Universidad</label><p className="m-0">{w.universityId || '—'}</p></div>
          <div className="col-9"><label className="text-xs text-color-secondary">Otros Estudios</label><p className="m-0">{w.otherStudies || '—'}</p></div>
        </div>
      </div>

      <div className="surface-card border-round p-3">
        <h4 className="mt-0 mb-2">Información laboral y previsional</h4>
        <div className="grid">
          <div className="col-3"><label className="text-xs text-color-secondary">Sistema Pensión</label><p className="m-0">{w.pensionSystem || '—'}</p></div>
          <div className="col-3"><label className="text-xs text-color-secondary">AFP</label><p className="m-0">{w.afpId || '—'}</p></div>
          <div className="col-3"><label className="text-xs text-color-secondary">Essalud</label><p className="m-0"><Tag severity={w.isEssaludAffiliate ? 'success' : 'danger'} value={w.isEssaludAffiliate ? 'Sí' : 'No'} /></p></div>
          <div className="col-3"><label className="text-xs text-color-secondary">Tipo Trabajador</label><p className="m-0">{w.workerType || '—'}</p></div>
          <div className="col-3"><label className="text-xs text-color-secondary">Tipo Pago Ref.</label><p className="m-0">{w.referencePayType || '—'}</p></div>
          <div className="col-3"><label className="text-xs text-color-secondary">5ta Cat. Empresa</label><p className="m-0">{w.fifthCategoryCompanyIncome || '—'}</p></div>
          <div className="col-3"><label className="text-xs text-color-secondary">5ta Cat. RUC</label><p className="m-0">{w.fifthCategoryRucIncome || '—'}</p></div>
          <div className="col-3"><label className="text-xs text-color-secondary">5ta Cat. Otros</label><p className="m-0">{w.fifthCategoryOtherIncome || '—'}</p></div>
        </div>
      </div>

      <div className="surface-card border-round p-3">
        <h4 className="mt-0 mb-2">Aspecto social</h4>
        <div className="grid">
          <div className="col-3"><label className="text-xs text-color-secondary">Religión</label><p className="m-0">{w.religion || '—'}</p></div>
          <div className="col-3"><label className="text-xs text-color-secondary">Iglesia</label><p className="m-0">{w.churchName || '—'}</p></div>
          <div className="col-3"><label className="text-xs text-color-secondary">Cargo</label><p className="m-0">{w.churchPosition || '—'}</p></div>
          <div className="col-3"><label className="text-xs text-color-secondary">Autoridad</label><p className="m-0">{w.authorityType || '—'}</p></div>
          <div className="col-3"><label className="text-xs text-color-secondary">Nombre Autoridad</label><p className="m-0">{w.authorityName || '—'}</p></div>
          <div className="col-3"><label className="text-xs text-color-secondary">Tel. Autoridad</label><p className="m-0">{w.authorityPhone || '—'}</p></div>
        </div>
      </div>

      {(w.observations || w.reference) && (
        <div className="surface-card border-round p-3">
          <h4 className="mt-0 mb-2">Notas y referencias</h4>
          <div className="grid">
            <div className="col-6"><label className="text-xs text-color-secondary">Observaciones</label><p className="m-0">{w.observations || '—'}</p></div>
            <div className="col-6"><label className="text-xs text-color-secondary">Referencia</label><p className="m-0">{w.reference || '—'}</p></div>
          </div>
        </div>
      )}

      <Divider />
      <div className="grid text-xs text-color-secondary">
        <div className="col-6">Creado: {fmt(w.createdAt)}</div>
        <div className="col-6">Actualizado: {fmt(w.updatedAt)}</div>
      </div>
    </div>
  );
}
