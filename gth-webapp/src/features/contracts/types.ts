export interface ContractResponse {
  id: string;
  requisitionId: string;
  templateId: string;
  contractNumber: string;
  positionId: string;
  workerId: string;
  startDate: string;
  endDate: string;
  terminationDate: string;
  conditionType: string;
  salaryAmount: number;
  reintegrationAmount: number;
  familyAllowance: number;
  weeklyHours: number;
  dailyHours: number;
  laborRegime: string;
  pensionRegime: string;
  contractType: string;
  observation: string;
  status: string;
  signedAt: string;
  createdAt: string;
  updatedAt: string;
  createdBy: string;
  updatedBy: string;
}

export interface ContractRequest {
  requisitionId: string;
  templateId: string;
  contractNumber: string;
  positionId: string;
  workerId: string;
  startDate: string;
  endDate: string;
  terminationDate: string;
  conditionType: string;
  salaryAmount: number;
  reintegrationAmount: number;
  familyAllowance: number;
  weeklyHours: number;
  dailyHours: number;
  laborRegime: string;
  pensionRegime: string;
  contractType: string;
  observation: string;
  createdBy: string;
}

export interface TemplateResponse {
  id: string;
  name: string;
  version: number;
  content: string;
  fileName: string;
  status: string;
  createdAt: string;
  createdBy: string;
}

export interface TemplateRequest {
  name: string;
  version: number;
  content: string;
  fileName: string;
  status: string;
  createdBy: string;
}

export interface AttachmentResponse {
  id: string;
  contractId: string;
  filename: string;
  contentType: string;
  uri: string;
  sizeBytes: number;
  checksum: string;
  createdAt: string;
}

export interface AttachmentRequest {
  contractId: string;
  filename: string;
  contentType: string;
  uri: string;
  sizeBytes: number;
  checksum: string;
}

export const CONTRACT_STATUSES = [
  { label: 'Borrador', value: 'DRAFT' },
  { label: 'Firmado', value: 'SIGNED' },
  { label: 'Vencido', value: 'EXPIRED' },
  { label: 'Terminado', value: 'TERMINATED' },
] as const;
