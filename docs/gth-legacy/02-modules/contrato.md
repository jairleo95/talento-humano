# Módulo: Contrato

## Funcional

Gestiona el ciclo de vida de los **contratos laborales** de los colaboradores, vinculados a requerimientos (DGP) y plantillas contractuales.

**Flujo de negocio**:
1. **Registrar contrato** (`Reg_Contrato.jsp`): selecciona requerimiento/trabajador, plantilla y datos contractuales (tipo, régimen, fechas, montos, horas).
2. **Buscar / filtrar** (`Busc_Contrato.jsp`): consulta de contratos por criterios.
3. **Detalle contractual** (`Detalle_Info_Contractualq.jsp`): información completa del contrato.
4. **Generación de contrato** (`Gen_Contrato_CE.jsp`, `Filtro_Contrato_CE.jsp`): contratos de casos especiales (CE).
5. **Impresión / adjuntos**: imprimir contrato (`Imprimir_Subir_Contrato.jsp`), subir contrato firmado (`Subir_Contrato_Firmado.jsp`), impresión masiva (`Impresion_Masiva.jsp`, `Impresion_Masiva2.jsp`).
6. **Plantillas**: editor de plantillas contractuales (`Plantilla/Editor_Plantilla.jsp`, `Editor_Plantilla2.jsp`), formatos por puesto (`Formato_Plantilla/`).
7. **Casos especiales** (`Reg_Casos_Especiales.jsp`).

## Técnico

**Controllers**:
- `recruitment/contract/ContractController` (`contrato`).
- `recruitment/contract/ContratoAdjuntoController` (`contrato_archivo_adjunto`) → HTML.
- `recruitment/contract/PrintController` (`imprimir`).
- `recruitment/contract/MassivePrintController` (`impresion_masiva`).
- `recruitment/contract/template/PlantillaContractualController` (`plantilla_contractual`).
- `recruitment/editor/ContractTemplateController` (`templates`).

**opc= principales**:
- `ContractController`: `REGISTRAR CONTRATO`, `Detalle_Contractual`, `Editar`, `actualizar`, `MODIFICAR CONTRATO`, `gen_cont`, `casos_especiales`, `REG_CASOS_ESP`, `Buscar`, `filtrar`, `Subir_Contrato`, `Subir_Contrato2`, `Ver_Plantilla`, `List_ti_contrato`, `Actualizar_Firma`, `Habilitar_is`, `validar_contrato`, `enviar`, `Reporte_CE`, `SelectorListaContrato`.
- `PrintController`: `Imprimir`, `Imprimir1`, `Listar_contrato`.
- `MassivePrintController`: `filtrar`.
- `PlantillaContractualController`: `List_planti`, `Listpuesto`, `cargar`, `Imprimir`.
- `ContractTemplateController`: `Listar`, `List_Plamtillas`, `Crear_Plantilla`, `Actualizar`, `asignar`/`Asignar`, `activar_pp`, `Desactivar_pp`, `UpdateNameFile`.

**Entidades de dominio**: `Contract`, `Tipo_Contrato`, `Contrato_Adjunto`, `Plantilla_Contractual`, `Plantilla_Puesto`, `List_Rh_Contrato_Fec`, `V_Documento_Trabajador`, `V_Ficha_Trab_Num_C`, `V_Contrato_dgp`.

**JSPs**: `Contrato/` (17 archivos): `Reg_Contrato`, `Busc_Contrato`, `Detalle_Info_Contractualq`, `Gen_Contrato_CE`, `Filtro_Contrato_CE`, `Imprimir_Subir_Contrato`, `Reg_Casos_Especiales`, `Plantilla/` (editor, dirección general), `Formato_Plantilla/` (reg, subir firmado, impresión masiva).

**JS de negocio**: `static/js/businessLogic/Contrato/`.

## Notas de migración

- Mapea a **contract-service** (contracts, templates, attachments). La firma de contratos (PATCH sign) ya existe en el servicio nuevo.
- La impresión usa formatos HTML/plantillas; requiere definir cómo se generará el PDF en el stack nuevo.

## Referencia

Código local: `gth-ms/src/main/java/com/app/controller/recruitment/contract/`; `gth-ms/src/main/webapp/WEB-INF/jsp/views/Contrato/`. Repo: [jairleo95/TALENTO_HUMANO](https://github.com/jairleo95/TALENTO_HUMANO).
