export interface OrgUnitResponse {
  id: string;
  name: string;
  shortName: string;
  unitType: string;
  parentId: string;
  isActive: boolean;
  occupationGroupCode: string;
  createdAt: string;
  updatedAt: string;
}

export const UNIT_TYPES = [
  { label: 'Dirección', value: 'DIRECCION' },
  { label: 'Departamento', value: 'DEPARTAMENTO' },
  { label: 'Área', value: 'AREA' },
  { label: 'Sección', value: 'SECCION' },
  { label: 'Puesto', value: 'PUESTO' },
] as const;

export const TYPE_LABELS: Record<string, string> = {
  DIRECCION: 'Dirección',
  DEPARTAMENTO: 'Departamento',
  AREA: 'Área',
  SECCION: 'Sección',
  PUESTO: 'Puesto',
};
