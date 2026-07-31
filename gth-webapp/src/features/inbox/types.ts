export interface InboxItemResponse {
  id: string;
  requisitionId: string;
  processStepId: string;
  assignee: string;
  status: string;
  createdAt: string;
  updatedAt: string;
}

export interface InboxItemRequest {
  requisitionId: string;
  processStepId: string;
  assignee: string;
}

export const INBOX_STATUSES = [
  { label: 'Pendiente', value: 'PENDING' },
  { label: 'En curso', value: 'IN_PROGRESS' },
  { label: 'Completado', value: 'COMPLETED' },
  { label: 'Cancelado', value: 'CANCELLED' },
] as const;
