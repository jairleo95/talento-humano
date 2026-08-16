import { useState, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { InputNumber } from 'primereact/inputnumber';
import { Tag } from 'primereact/tag';
import { Dropdown } from 'primereact/dropdown';
import { Editor } from 'primereact/editor';
import { useForm, Controller } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import dayjs from 'dayjs';
import { apiGet, apiPost, apiPut, apiPatch, apiDelete } from '../../core/api/client';
import type { TemplateResponse, TemplateRequest } from './types';

const BASE = '/contract/api/v1/contracts/templates';

const DYNAMIC_VARIABLES = [
  { label: '${NRO_CONTRATO}', desc: 'Número Correlativo del Contrato' },
  { label: '${TRABAJADOR_ID}', desc: 'ID o Nombre del Trabajador' },
  { label: '${PUESTO_ID}', desc: 'Denominación del Puesto de Trabajo' },
  { label: '${SALARIO}', desc: 'Remuneración Base (Soles)' },
  { label: '${FECHA_INICIO}', desc: 'Fecha de Inicio de Labores' },
  { label: '${FECHA_FIN}', desc: 'Fecha de Término de Contrato' },
  { label: '${REGIMEN_LABORAL}', desc: 'Régimen Laboral (DL 728, DL 1057, etc.)' },
  { label: '${REGIMEN_PENSION}', desc: 'Régimen Pensionario (AFP / ONP)' },
  { label: '${CONDICION}', desc: 'Condición del Trabajador' },
];

const PREDEFINED_TEMPLATES = [
  {
    label: 'Contrato a Plazo Indeterminado (D.L. 728)',
    value: `CONTRATO INDIVIDUAL DE TRABAJO A PLAZO INDETERMINADO

Conste por el presente documento el Contrato de Trabajo a Plazo Indeterminado que celebran de una parte LA EMPRESA, y de otra parte don/doña \${TRABAJADOR_ID}, para desempañar el puesto de \${PUESTO_ID}.

PRIMERA: DE LA CONTRATACIÓN
LA EMPRESA contrata a EL TRABAJADOR bajo el Régimen Laboral \${REGIMEN_LABORAL} a partir del \${FECHA_INICIO}.

SEGUNDA: DE LA REMUNERACIÓN
EL TRABAJADOR percibirá una remuneración mensual de \${SALARIO} Soles, pagaderos en las fechas estipuladas por la institución.

TERCERA: JORNADA DE TRABAJO
La jornada laboral será de 48 horas semanales, sujetas al horario establecido por LA EMPRESA.

Suscrito el presente contrato en señal de conformidad.
Nro. de Contrato: \${NRO_CONTRATO}`,
  },
  {
    label: 'Contrato Sujeto a Modalidad por Necesidad de Mercado',
    value: `CONTRATO DE TRABAJO SUJETO A MODALIDAD POR NECESIDAD DE MERCADO

Conste por el presente documento el Contrato de Trabajo por Necesidad de Mercado que celebran LA EMPRESA y \${TRABAJADOR_ID}.

PRIMERA: OBJETO DEL CONTRATO
Atendiendo al incremento coyuntural de producción, se contrata a EL TRABAJADOR para el puesto de \${PUESTO_ID}.

SEGUNDA: PLAZO DE VIGENCIA
El presente contrato rige desde el \${FECHA_INICIO} hasta el \${FECHA_FIN}.

TERCERA: REMUNERACIÓN
La remuneración acordada asciende a \${SALARIO} Soles bajo el régimen \${REGIMEN_LABORAL}.

Nro. Registro: \${NRO_CONTRATO}`,
  },
  {
    label: 'Convenio de Prácticas Pre-Profesionales',
    value: `CONVENIO DE PRÁCTICAS PRE-PROFESIONALES

Conste por el presente documento el Convenio de Prácticas que suscriben LA INSTITUCIÓN y el practicante \${TRABAJADOR_ID}.

PRIMERA: ÁREA Y PUESTO
El practicante desarrollará su aprendizaje práctico en el puesto de \${PUESTO_ID} bajo la condición de \${CONDICION}.

SEGUNDA: SUBVENCIÓN ECONÓMICA
El practicante recibirá una subvención económica mensual de \${SALARIO} Soles.

TERCERA: VIGENCIA
El convenio tendrá vigencia desde el \${FECHA_INICIO} hasta el \${FECHA_FIN}.

Nro. Convenio: \${NRO_CONTRATO}`,
  },
];

const schema = z.object({
  name: z.string().min(1, 'Nombre requerido').max(140),
  version: z.number().int().min(1, 'Versión mínima 1'),
  content: z.string().min(1, 'El contenido no puede estar vacío'),
  fileName: z.string().optional().default(''),
  status: z.string().optional().default('ACTIVE'),
  createdBy: z.string().min(1, 'Creador requerido'),
});

type FormType = z.infer<typeof schema>;

function dateBodyRow(row: TemplateResponse) {
  return row.createdAt ? dayjs(row.createdAt).format('DD/MM/YYYY HH:mm') : '—';
}

export function TemplatePage() {
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const [selectedTemplate, setSelectedTemplate] = useState<TemplateResponse | null>(null);
  const [isCreatingNew, setIsCreatingNew] = useState(false);
  const [viewMode, setViewMode] = useState<'EDIT' | 'SIMULATED'>('EDIT');
  const [searchTerm, setSearchTerm] = useState('');

  const editorRef = useRef<Editor | null>(null);
  const savedIndexRef = useRef<number | null>(null);

  const { data: templates = [], isLoading } = useQuery({
    queryKey: ['templates'],
    queryFn: async () => {
      const list = await apiGet<TemplateResponse[]>(BASE);
      if (list.length > 0 && !selectedTemplate && !isCreatingNew) {
        loadTemplateIntoForm(list[0]);
      }
      return list;
    },
  });

  const { control, handleSubmit, reset, setValue, watch, formState: { errors } } = useForm<FormType>({
    resolver: zodResolver(schema),
    defaultValues: { name: '', version: 1, content: '', fileName: '', status: 'ACTIVE', createdBy: 'admin' },
  });

  const watchContent = watch('content');
  const watchName = watch('name');

  const highlightVariablesHtml = (raw: string): string => {
    if (!raw) return '';
    const unformatted = raw.replace(/<span[^>]*>(\$\{[A-Z0-9_]+\})<\/span>/g, '$1');
    return unformatted.replace(/(\$\{[A-Z0-9_]+\})/g, (match) => {
      return `<span style="background-color: #dbeafe; color: #1e40af; font-weight: bold; padding: 2px 6px; border-radius: 4px; font-family: monospace; display: inline-block; margin: 0 2px;">${match}</span>`;
    });
  };

  const loadTemplateIntoForm = (tpl: TemplateResponse) => {
    setSelectedTemplate(tpl);
    setIsCreatingNew(false);
    reset({
      name: tpl.name,
      version: tpl.version,
      content: highlightVariablesHtml(tpl.content),
      fileName: tpl.fileName || '',
      status: tpl.status,
      createdBy: tpl.createdBy || 'admin',
    });
    setViewMode('EDIT');
  };

  const handleCreateNew = () => {
    setSelectedTemplate(null);
    setIsCreatingNew(true);
    reset({
      name: 'Nueva Plantilla de Contrato',
      version: 1,
      content: highlightVariablesHtml(PREDEFINED_TEMPLATES[0].value),
      fileName: 'contrato_modelo.docx',
      status: 'ACTIVE',
      createdBy: 'admin',
    });
    setViewMode('EDIT');
  };

  const handleClone = (tpl: TemplateResponse, e: React.MouseEvent) => {
    e.stopPropagation();
    setSelectedTemplate(null);
    setIsCreatingNew(true);
    reset({
      name: `${tpl.name} (Copia)`,
      version: tpl.version + 1,
      content: highlightVariablesHtml(tpl.content),
      fileName: tpl.fileName || '',
      status: 'ACTIVE',
      createdBy: 'admin',
    });
    setViewMode('EDIT');
  };

  const createMutation = useMutation({
    mutationFn: (req: TemplateRequest) => apiPost<TemplateResponse>(BASE, req),
    onSuccess: (newTpl) => {
      queryClient.invalidateQueries({ queryKey: ['templates'] });
      loadTemplateIntoForm(newTpl);
    },
  });

  const updateMutation = useMutation({
    mutationFn: ({ id, req }: { id: string; req: TemplateRequest }) => apiPut<TemplateResponse>(`${BASE}/${id}`, req),
    onSuccess: (updatedTpl) => {
      queryClient.invalidateQueries({ queryKey: ['templates'] });
      loadTemplateIntoForm(updatedTpl);
    },
  });

  const toggleMutation = useMutation({
    mutationFn: (id: string) => apiPatch<TemplateResponse>(`${BASE}/${id}/toggle`),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['templates'] }),
  });

  const deleteMutation = useMutation({
    mutationFn: (id: string) => apiDelete(`${BASE}/${id}`),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['templates'] });
      setSelectedTemplate(null);
      handleCreateNew();
    },
  });

  const handleSelectionChange = () => {
    const quill = editorRef.current?.getQuill();
    if (quill) {
      const sel = quill.getSelection();
      if (sel) {
        savedIndexRef.current = sel.index;
      }
    }
  };

  const insertVariable = (varLabel: string) => {
    const quill = editorRef.current?.getQuill();
    const badgeHtml = `<span style="background-color: #dbeafe; color: #1e40af; font-weight: bold; padding: 2px 6px; border-radius: 4px; font-family: monospace; display: inline-block; margin: 0 2px;">${varLabel}</span>&nbsp;`;

    if (quill) {
      const sel = quill.getSelection();
      const targetIndex = sel && sel.index !== undefined ? sel.index : (savedIndexRef.current ?? quill.getLength());

      quill.clipboard.dangerouslyPasteHTML(targetIndex, badgeHtml);

      const nextIndex = targetIndex + 2;
      savedIndexRef.current = nextIndex;

      const updatedHtml = quill.root.innerHTML;
      setValue('content', updatedHtml);

      setTimeout(() => {
        quill.focus();
        quill.setSelection(nextIndex, 0);
      }, 30);
    } else {
      const current = watchContent || '';
      setValue('content', current + badgeHtml);
    }
  };

  const compilePreview = (raw: string) => {
    if (!raw) return <span className="text-color-secondary italic">Sin contenido</span>;

    const replaced = raw
      .replace(/\${NRO_CONTRATO}/g, '<span style="background-color:#dcfce7; color:#166534; font-weight:bold; padding:2px 6px; border-radius:4px;">CTO-2026-0042</span>')
      .replace(/\${TRABAJADOR_ID}/g, '<span style="background-color:#dcfce7; color:#166534; font-weight:bold; padding:2px 6px; border-radius:4px;">JUAN CARLOS PÉREZ GÓMEZ (DNI: 47651835)</span>')
      .replace(/\${PUESTO_ID}/g, '<span style="background-color:#dcfce7; color:#166534; font-weight:bold; padding:2px 6px; border-radius:4px;">ANALISTA DESARROLLADOR SENIOR</span>')
      .replace(/\${SALARIO}/g, '<span style="background-color:#dcfce7; color:#166534; font-weight:bold; padding:2px 6px; border-radius:4px;">S/ 4,500.00</span>')
      .replace(/\${FECHA_INICIO}/g, '<span style="background-color:#dcfce7; color:#166534; font-weight:bold; padding:2px 6px; border-radius:4px;">01/08/2026</span>')
      .replace(/\${FECHA_FIN}/g, '<span style="background-color:#dcfce7; color:#166534; font-weight:bold; padding:2px 6px; border-radius:4px;">31/01/2027</span>')
      .replace(/\${REGIMEN_LABORAL}/g, '<span style="background-color:#dcfce7; color:#166534; font-weight:bold; padding:2px 6px; border-radius:4px;">D.L. 728 - RÉGIMEN PRIVADO</span>')
      .replace(/\${REGIMEN_PENSION}/g, '<span style="background-color:#dcfce7; color:#166534; font-weight:bold; padding:2px 6px; border-radius:4px;">AFP INTEGRA</span>')
      .replace(/\${CONDICION}/g, '<span style="background-color:#dcfce7; color:#166534; font-weight:bold; padding:2px 6px; border-radius:4px;">CONTRATADO</span>');

    return (
      <div className="font-mono text-sm white-space-pre-wrap leading-relaxed"
        dangerouslySetInnerHTML={{ __html: replaced }}
      />
    );
  };

  const onSubmit = (data: FormType) => {
    if (selectedTemplate) {
      updateMutation.mutate({ id: selectedTemplate.id, req: data });
    } else {
      createMutation.mutate(data);
    }
  };

  const filteredTemplates = templates.filter(t => t.name.toLowerCase().includes(searchTerm.toLowerCase()));

  return (
    <div className="flex flex-column gap-3">
      <div className="flex align-items-center justify-content-between surface-card p-3 border-round border-1 surface-border shadow-1">
        <div className="flex align-items-center gap-3">
          <Button icon="pi pi-arrow-left" text rounded onClick={() => navigate('/contracts')} tooltip="Volver a Contratos" />
          <div>
            <h3 className="m-0 text-primary flex align-items-center gap-2">
              <i className="pi pi-file-edit" /> Módulo Diseñador y Editor de Plantillas
            </h3>
            <span className="text-xs text-color-secondary">Seleccione una plantilla del panel izquierdo para cargarla e inspeccionarla en vivo</span>
          </div>
        </div>
        <Button label="Nueva Plantilla" icon="pi pi-plus" onClick={handleCreateNew} severity="success" />
      </div>

      <div className="grid">
        <div className="col-12 lg:col-4 flex flex-column gap-3">
          <div className="surface-card border-round p-3 border-1 surface-border shadow-1 flex flex-column gap-2" style={{ height: '420px' }}>
            <div className="flex align-items-center justify-content-between border-bottom-1 surface-border pb-2">
              <span className="font-bold text-sm text-primary flex align-items-center gap-2">
                <i className="pi pi-list" /> Plantillas Registradas ({filteredTemplates.length})
              </span>
              <Tag severity="info" value={`${templates.length} Total`} />
            </div>

            <div className="p-input-icon-left w-full">
              <i className="pi pi-search" />
              <InputText value={searchTerm} onChange={(e) => setSearchTerm(e.target.value)} placeholder="Buscar plantilla..." className="w-full p-inputtext-sm" />
            </div>

            <div className="flex flex-column gap-2 overflow-auto pr-1 flex-1">
              {isLoading ? (
                <div className="p-3 text-center text-color-secondary">Cargando plantillas...</div>
              ) : filteredTemplates.length === 0 ? (
                <div className="p-3 text-center text-color-secondary italic">No se encontraron plantillas</div>
              ) : (
                filteredTemplates.map((tpl) => {
                  const isSelected = selectedTemplate?.id === tpl.id;
                  return (
                    <div
                      key={tpl.id}
                      onClick={() => loadTemplateIntoForm(tpl)}
                      className={`p-3 border-round border-1 cursor-pointer transition-colors transition-duration-150 flex flex-column gap-2 ${
                        isSelected
                          ? 'surface-highlight border-primary shadow-2'
                          : 'surface-card border-1 surface-border hover:surface-hover'
                      }`}
                    >
                      <div className="flex align-items-center justify-content-between">
                        <span className="font-bold text-sm text-900 line-clamp-1">{tpl.name}</span>
                        <Tag severity="info" value={`v${tpl.version}`} />
                      </div>

                      <div className="flex align-items-center justify-content-between text-xs text-color-secondary">
                        <span className="flex align-items-center gap-1">
                          <i className="pi pi-clock text-xs" /> {dateBodyRow(tpl)}
                        </span>
                        <div className="flex gap-1" onClick={(e) => e.stopPropagation()}>
                          <Button icon="pi pi-copy" text rounded size="small" severity="secondary" onClick={(e) => handleClone(tpl, e)} tooltip="Clonar" />
                          <Button icon={tpl.status === 'ACTIVE' ? 'pi pi-ban' : 'pi pi-check'} text rounded size="small"
                            severity={tpl.status === 'ACTIVE' ? 'warning' : 'success'}
                            onClick={() => toggleMutation.mutate(tpl.id)} tooltip={tpl.status === 'ACTIVE' ? 'Desactivar' : 'Activar'} />
                          <Button icon="pi pi-trash" text rounded size="small" severity="danger"
                            onClick={() => { if (confirm('¿Eliminar plantilla?')) deleteMutation.mutate(tpl.id); }} tooltip="Eliminar" />
                        </div>
                      </div>
                    </div>
                  );
                })
              )}
            </div>
          </div>

          <div className="surface-card border-round p-3 border-1 surface-border shadow-1 flex flex-column gap-2">
            <span className="font-bold text-sm text-primary flex align-items-center gap-2 border-bottom-1 surface-border pb-2">
              <i className="pi pi-code" /> Nomenclatura de Marcadores Dinámicos
            </span>
            <span className="text-xs text-color-secondary">Haz clic en cualquier variable para insertarla directamente en el cursor del editor:</span>
            <div className="flex flex-wrap gap-1 mt-1">
              {DYNAMIC_VARIABLES.map((v) => (
                <Button key={v.label} type="button" label={v.label} size="small" outlined severity="info"
                  onClick={() => insertVariable(v.label)} tooltip={v.desc} tooltipOptions={{ position: 'top' }} />
              ))}
            </div>
          </div>
        </div>

        <div className="col-12 lg:col-8">
          <div className="surface-card border-round p-4 border-1 surface-border shadow-2 flex flex-column gap-3">
            <div className="flex align-items-center justify-content-between pb-2 border-bottom-1 surface-border">
              <div>
                <h4 className="m-0 text-primary flex align-items-center gap-2">
                  <i className="pi pi-file-edit" />
                  {isCreatingNew ? 'Nueva Plantilla de Contrato' : `Editando: ${watchName || selectedTemplate?.name}`}
                </h4>
                <span className="text-xs text-color-secondary">Edite el formato del documento de contrato e inserte marcadores</span>
              </div>
              <div className="flex gap-2">
                <Button label="Simular Contrato" icon="pi pi-sync" size="small"
                  severity={viewMode === 'SIMULATED' ? 'success' : 'secondary'} text={viewMode !== 'SIMULATED'}
                  onClick={() => setViewMode(viewMode === 'SIMULATED' ? 'EDIT' : 'SIMULATED')} />
                <Button label={selectedTemplate ? 'Guardar Cambios' : 'Crear Plantilla'} icon="pi pi-check" size="small"
                  loading={createMutation.isPending || updateMutation.isPending}
                  onClick={handleSubmit(onSubmit)} />
              </div>
            </div>

            <form className="flex flex-column gap-3">
              <div className="grid">
                <div className="col-7">
                  <div className="flex flex-column gap-1">
                    <label className="text-sm font-semibold">Nombre de la Plantilla *</label>
                    <Controller name="name" control={control} render={({ field }) => (
                      <InputText {...field} className={errors.name ? 'p-invalid' : ''} placeholder="Ej. Contrato a Plazo Indeterminado DL 728" />
                    )} />
                    {errors.name && <small className="p-error">{errors.name.message}</small>}
                  </div>
                </div>
                <div className="col-2">
                  <div className="flex flex-column gap-1">
                    <label className="text-sm font-semibold">Versión *</label>
                    <Controller name="version" control={control} render={({ field }) => (
                      <InputNumber {...field} value={field.value} onValueChange={(e) => field.onChange(e.value ?? 1)} min={1} />
                    )} />
                  </div>
                </div>
                <div className="col-3">
                  <div className="flex flex-column gap-1">
                    <label className="text-sm font-semibold">Cargar Modelo Estándar</label>
                    <Dropdown options={PREDEFINED_TEMPLATES} optionLabel="label" optionValue="value" placeholder="Seleccionar Modelo"
                      onChange={(e) => setValue('content', highlightVariablesHtml(e.value))} className="w-full p-inputtext-sm" />
                  </div>
                </div>
              </div>

              <div className="flex flex-column gap-2">
                <div className="flex align-items-center justify-content-between">
                  <span className="text-xs font-semibold text-color-secondary">Cuerpo del Contrato:</span>
                  <span className="text-xs text-primary font-bold">Las variables se destacan con insignias azules</span>
                </div>

                {viewMode === 'SIMULATED' ? (
                  <div className="surface-card border-round p-3 border-1 border-green-300 shadow-1" style={{ minHeight: '380px', maxHeight: '550px', overflowY: 'auto' }}>
                    <div className="text-xs font-semibold text-green-700 mb-2 flex align-items-center gap-1">
                      <i className="pi pi-check-circle text-green-500" /> Simulación de Contrato Compilado con Datos de Prueba:
                    </div>
                    {compilePreview(watchContent)}
                  </div>
                ) : (
                  <Controller name="content" control={control} render={({ field }) => (
                    <Editor ref={editorRef} value={field.value || ''}
                      onTextChange={(e) => {
                        field.onChange(e.htmlValue || e.textValue || '');
                        handleSelectionChange();
                      }}
                      onSelectionChange={handleSelectionChange}
                      style={{ height: '380px' }} placeholder="Redacte el texto del contrato e inserte marcadores..." />
                  )} />
                )}
                {errors.content && <small className="p-error">{errors.content.message}</small>}
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
