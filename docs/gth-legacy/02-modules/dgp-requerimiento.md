# Módulo: Requerimiento / DGP (núcleo)

## Funcional

El módulo **DGP (Documento de Gestión de Puestos)** es el corazón del sistema: gestiona los **requerimientos de contratación** de personal.

**Actores**: solicitante (departamento/área), autorizador, proceso de contratación.

**Flujo de negocio principal**:
1. **Registrar requerimiento** (`Reg_Dgp.jsp`, `Generar_Dgp.jsp`): el área genera un DGP especificando puesto, tipo (planilla), fechas, horas, centros de costo, horario.
2. **Listar / Buscar** (`List_Dgp.jsp`, `Busc_Req_Autorizado.jsp`, `List_req_incompl.jsp`): consulta de requerimientos, incluidos incompletos.
3. **Autorizar** (`Autorizar_Requerimiento.jsp`, bandeja `imbox`): un usuario autorizado acepta o rechaza el requerimiento.
4. **Procesar** (`Procesar_Req.jsp`, `Proceso_Dgp.jsp`): seguimiento del requerimiento a través de pasos del proceso de contratación.
5. **Detalle y seguimiento** (`Detalle_Dgp.jsp`, `Detalle_Seguimiento_Dgp.jsp`, `User_Dgp.jsp`): historial, comentarios, documentos, plazos.

**Sub-módulos**:
- **Comentario**: comentarios sobre el DGP.
- **Documento**: documentos adjuntos por requerimiento.
- **Horario**: horarios del puesto requerido.
- **Plazo**: plazos del proceso.

## Técnico

**Controllers**:
- `recruitment/dgp/DGPController` (`dgp`) — orquesta el módulo.
- `inbox/AuthorizationController` (`imbox`) — aceptar/rechazar.
- `inbox/AcademicImboxController` (`academicImbox`) — bandeja y procesamiento.
- `inbox/ProcessStatusController` (`process/status`) — estado del proceso.
- `recruitment/request/RequerimientoController` (`requerimiento`) — listados de requerimientos.
- `recruitment/documents/*` — documentos.

**opc= principales** (`DGPController`): `Listar`, `Listar_Req`, `Listar_Datos`, `Registrar`, `Reg_form`, `Modificar`, `MODIFICAR REQUERIMIENTO`, `Detalle`, `Seguimiento`, `SeguimientoH`, `Proceso`, `Incompleto`, `Terminar`, `Reg_renuncia`, `User_Aut`, `Val_Fe_Inicio`, `filtrar`, `Imprimir_det_proceso`, `RegDGPAditionalPermissions`, `List_Dgp_Tr`.

**Entidades de dominio**: `DGP`, `Requerimiento`, `Autorizacion`, `Comentario_DGP`, `Detalle_Pasos`, `Detalle_Req_Proceso`, `Pasos`, `Proceso`, `Pedido`, + vistas `V_Det_DGP`, `V_Autorizar_Dgp`, `V_Filtro_Dgp_Aut`, `V_Reg_Dgp_Tra`, `V_Es_Requerimiento`, `V_Es_Req_Incompleto`, `V_Estado_req`, `V_Aut_Pasos`, `X_User_dgp`, `X_List_id_dgp`, `X_List_det_dgp`, `X_List_De_Autorizacion`, `X_List_Comen_DGP`, `X_val_tra_dgp`, etc.

**JSPs**: `Dgp/` (22 archivos): `Reg_Dgp`, `Generar_Dgp`, `List_Dgp`, `Detalle_Dgp`, `Autorizar_Requerimiento`, `Procesar_Req`, `Proceso_Dgp`, `Detalle_Seguimiento_Dgp`, `User_Dgp`, `Busc_Req_Autorizado`, `List_req_incompl`, `Requerimiento`, `Comentario/`, `Documento/`, `Horario/`, `Plazo/`.

**JS de negocio**: `static/js/businessLogic/Dgp/` (`editDGP.js`, `js_dgp_aut.js`, `procesarRequerimiento.js`, `statusProcessDGP.js`, `Detalle/`, `Registrar/`).

## Notas de migración

- Este módulo mapea a **recruitment-service** (requisitions, inbox, process). Pendiente: separar la entidad `dgp` de `requisition`.

## Referencia

Código local: `gth-ms/src/main/java/com/app/controller/recruitment/dgp/` + `inbox/`; `gth-ms/src/main/webapp/WEB-INF/jsp/views/Dgp/`. Repo: [jairleo95/TALENTO_HUMANO](https://github.com/jairleo95/TALENTO_HUMANO).
