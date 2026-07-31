export interface ProcessResponse {
  id: string;
  name: string;
  code: string;
  description: string;
  status: string;
  createdAt: string;
  updatedAt: string;
}

export interface ProcessRequest {
  name: string;
  code: string;
  description: string;
}

export interface ProcessStepResponse {
  id: string;
  processId: string;
  name: string;
  code: string;
  description: string;
  status: string;
  orderIndex: number;
  slaHours: number;
  createdAt: string;
  updatedAt: string;
}

export interface ProcessStepRequest {
  name: string;
  orderIndex: number;
  code: string;
  description: string;
  slaHours: number;
}

export const PROCESS_STATUSES = [
  { label: 'Activo', value: 'ACTIVE' },
  { label: 'Inactivo', value: 'INACTIVE' },
  { label: 'Completado', value: 'COMPLETED' },
] as const;

export const STEP_STATUSES = [
  { label: 'Pendiente', value: 'PENDING' },
  { label: 'En curso', value: 'IN_PROGRESS' },
  { label: 'Completado', value: 'COMPLETED' },
  { label: 'Omitido', value: 'SKIPPED' },
] as const;
