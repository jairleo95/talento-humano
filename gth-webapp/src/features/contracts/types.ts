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
  directionId: string;
  departmentId: string;
  areaId: string;
  sectionId: string;
  branchId: string;
  foodBonus: number;
  bevBonus: number;
  positionBonus: number;
  totalSalary: number;
  paymentHourType: string;
  isDisability: boolean;
  isBoss: boolean;
  agreementType: string;
  signingDate: string;
  vacationStartDate: string;
  vacationEndDate: string;
  currencyType: string;
  variableRemuneration: string;
  occupationGroupId: string;
  subModalityId: string;
  isIntern: boolean;
  documentsDelivered: boolean;
  fingerprintRegistered: boolean;
  payrollRegistered: boolean;
  companyRuc: string;
  branchCode: string;
  specialSituationId: string;
  specialSituationDesc: string;
  parentContractId?: string;
  isSpecialCase?: boolean;
  signedFileUrl?: string;
  signedBy?: string;
}

export interface ContractRequest {
  requisitionId?: string;
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
  directionId: string;
  departmentId: string;
  areaId: string;
  sectionId: string;
  branchId: string;
  foodBonus: number;
  bevBonus: number;
  positionBonus: number;
  totalSalary: number;
  paymentHourType: string;
  isDisability: boolean;
  isBoss: boolean;
  agreementType: string;
  signingDate: string;
  vacationStartDate: string;
  vacationEndDate: string;
  currencyType: string;
  variableRemuneration: string;
  occupationGroupId: string;
  subModalityId: string;
  isIntern: boolean;
  documentsDelivered: boolean;
  fingerprintRegistered: boolean;
  payrollRegistered: boolean;
  companyRuc: string;
  branchCode: string;
  specialSituationId: string;
  specialSituationDesc: string;
  parentContractId?: string;
  isSpecialCase?: boolean;
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
