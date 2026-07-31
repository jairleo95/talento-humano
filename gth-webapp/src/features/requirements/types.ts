export interface RequirementResponse {
  id: string;
  title: string;
  description: string;
  requestNumber: string;
  payrollTypeId?: string;
  positionId?: string;
  costCenterId?: string;
  startDate?: string;
  endDate?: string;
  salaryAmount?: number;
  foodBonus?: number;
  workDays?: string;
  serviceLocation?: string;
  serviceDescription?: string;
  paymentPeriod?: string;
  fiscalAddress?: string;
  allowanceDescription?: string;
  trainingSchedule?: string;
  breakSchedule?: string;
  trainingDays?: string;
  policeRecordDesc?: string;
  healthCertificateDesc?: string;
  bankName?: string;
  bankAccount?: string;
  status: string;
  createdAt: string;
  updatedAt: string;
  createdBy: string;
  workerId?: string;
  motive?: string;
  isMfl?: boolean;
  isBudgeted?: boolean;
  ruc?: string;
  positionBonus?: number;
  bevBonus?: number;
  familyAllowance?: number;
  subsidy?: string;
  honorariumAmount?: number;
}

export interface RequirementRequest {
  title: string;
  description: string;
  createdBy: string;
  requestNumber: string;
  workerId?: string;
  motive?: string;
  isMfl?: boolean;
  isBudgeted?: boolean;
  ruc?: string;
  salaryAmount?: number;
  foodBonus?: number;
  positionBonus?: number;
  bevBonus?: number;
  familyAllowance?: number;
  workDays?: string;
  serviceLocation?: string;
  serviceDescription?: string;
  paymentPeriod?: string;
  fiscalAddress?: string;
  allowanceDescription?: string;
  trainingSchedule?: string;
  breakSchedule?: string;
  trainingDays?: string;
  policeRecordDesc?: string;
  healthCertificateDesc?: string;
  bankName?: string;
  bankAccount?: string;
  subsidy?: string;
  honorariumAmount?: number;
}

export interface RequirementStatusRequest {
  status: string;
}

export const REQUIREMENT_STATUSES = [
  { label: 'Borrador', value: 'DRAFT' },
  { label: 'Enviada', value: 'SUBMITTED' },
  { label: 'En revisión', value: 'IN_REVIEW' },
  { label: 'Aprobada', value: 'APPROVED' },
  { label: 'Rechazada', value: 'REJECTED' },
] as const;
