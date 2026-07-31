# Módulo: Proceso

## Funcional

Configura el **motor de flujos** de la institución: procesos de contratación, sus pasos, tipos de requerimiento y plazos.

**Funcionalidades**:
1. **Mantenimiento de procesos** (`Mant_Proceso.jsp`): alta/edición/eliminación de procesos.
2. **Mantenimiento de pasos** (`Mant_Paso.jsp`): pasos que componen un proceso (orden, estado).
3. **Tipos de requerimiento** (`Mant_Tipo_Requerimiento.jsp`): catálogo de tipos.
4. **Menú de mantenimiento** (`Menu_Mantenimiento.jsp`).
5. **Plazos de DGP** (`Plazo/Reg_Plazo.jsp`, `Detalle_Plazo.jsp`, `Reg_Plazo_Dgp.jsp`): plazos asociados al requerimiento.
6. **Comentarios** (`Comentario/List_Coment.jsp`, `Reg_Comentario.jsp`): comentarios sobre el proceso.

## Técnico

**Controllers**:
- `process/ProcessController` (`Proceso`).
- `process/PhaseController` (`steps`).
- `process/CommentaryController` (`comentario`).
- `process/deliveryTerm/TermController` (`plazo_dgp`).
- `inbox/ProcessStatusController` (`process/status`) — estado del proceso DGP.

**opc= principales**:
- `ProcessController`: `Mantenimiento`, `Eliminar`, `statupdate`, `insertDetalleReqProceso` (POST `createee`, `register`); GET listado, `details`, `all`.
- `PhaseController`: `Registrar`, `Modificar`, `Eliminar`, `Eliminar_PP`, `actualizar_estado`, `Update_nu_paso`; GET `{id}/jobs`.
- `TermController`: `Listar`, `Registrar`, `Modificar`, `Eliminar`, `Ver_detalle_plazo`, `Listar_Plazo`, `fecha_habilitada`, `Mantenimiento`, `List_id_plazo`.
- `CommentaryController`: `list`, `Comentar_Dgp`, `COMENTAR`.

**Entidades de dominio**: `Proceso`, `Process`, `Pasos`, `Detalle_Pasos`, `Detalle_Req_Proceso`, `ProcessDetail`, `V_Req_Paso_Pu`, `V_Aut_Pasos`.

**JSPs**: `Proceso/` (4): `Mant_Proceso`, `Mant_Paso`, `Mant_Tipo_Requerimiento`, `Menu_Mantenimiento`; más `Dgp/Plazo/`, `Dgp/Comentario/`.

**JS de negocio**: `static/js/businessLogic/Proceso/`, `Plazo/`, `coment/`.

## Notas de migración

- Mapea a **recruitment-service** (process, steps) — ya existen endpoints `/processes`, `/processes/{id}/steps`.

## Referencia

Código local: `gth-ms/src/main/java/com/app/controller/process/`; `gth-ms/src/main/webapp/WEB-INF/jsp/views/Proceso/`. Repo: [jairleo95/TALENTO_HUMANO](https://github.com/jairleo95/TALENTO_HUMANO).
