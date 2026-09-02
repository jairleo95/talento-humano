export interface CareerResponse { id: string; name: string; universityId: string; createdAt: string; }
export interface UniversityResponse { id: string; name: string; shortName: string; createdAt: string; }

export interface AcademicCourseResponse {
  id: string; chargeId: string; campus: string; courseName: string; groupNumber: string;
  schedule: string; hours: number; courseCondition: string; courseType: string;
}

export interface AcademicPaymentResponse {
  id: string; chargeId: string; quotaNumber: number; amount: number; paymentDate: string | null; status: string;
}

export interface AcademicChargeResponse {
  id: string; workerId: string; workerName: string | null; documentNumber: string | null;
  semester: string; faculty: string; school: string; educationalSituation: string; profession: string;
  condition: string; payType: string; totalHours: number;
  startDate: string | null; endDate: string | null; status: string;
  createdBy: string; createdAt: string; updatedAt: string;
  courses: AcademicCourseResponse[]; payments: AcademicPaymentResponse[];
}

export interface WorkerOption {
  id: string; firstName: string; lastNamePaternal: string; lastNameMaternal: string | null;
  documentNumber: string; documentType: string; workerType: string;
}

export const CHARGE_STATUS_LABEL: Record<string, string> = {
  BORRADOR: 'Borrador',
  PROCESADO: 'Procesado',
  ANULADO: 'Anulado',
};

export const PAYMENT_STATUS_LABEL: Record<string, string> = {
  PENDIENTE: 'Pendiente',
  PAGADO: 'Pagado',
};

export const PAY_TYPE_OPTIONS = [
  { label: 'Tiempo completo', value: 'TIEMPO_COMPLETO' },
  { label: 'Tiempo parcial', value: 'TIEMPO_PARCIAL' },
  { label: 'Honorarios', value: 'HONORARIOS' },
];