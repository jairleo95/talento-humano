# Módulo: Solicitud

## Funcional

Gestiona las **solicitudes de requerimiento** previas a la generación del DGP.

**Flujo de negocio**:
1. **Registrar solicitud** (`Registrar_solicitud`): se crea una solicitud de requerimiento.
2. **Listar solicitudes** (`Detalle_Solicitud.jsp`, `Reporte_Solicitud.jsp`): pendientes, autorizadas, todas.
3. **Ver detalle** (`Ver_Solicitud`, `Ver_Detalle_Solicitud`).
4. **Procesar solicitud** (`Procesar_Solicitud`): conversión en requerimiento.
5. **Validación de envío** (`Val_Envio_Solicitud`).

## Técnico

**Controller**: `recruitment/request/SolicitudRequerimientoController` (`solicitud_requerimiento`).

**opc= principales**: `Listar_Solicitud`, `Listar_Sol_Pendientes`, `Listar_Sol_Aut`, `Registrar_solicitud`, `Reg_List_Solicitud`, `Ver_Detalle_Solicitud`, `Ver_Solicitud`, `Procesar_Solicitud`, `Val_Envio_Solicitud`.

**Entidades de dominio**: `V_Solicitud_Requerimiento`, `Solicitud` (vía DAO).

**JSPs**: `Solicitud/` (2): `Detalle_Solicitud`, `Reporte_Solicitud`.

## Notas de migración

- Relacionado con el flujo de requerimientos (recruitment-service); la solicitud es el paso previo del DGP.

## Referencia

Código local: `gth-ms/src/main/java/com/app/controller/recruitment/request/SolicitudRequerimientoController.java`; `gth-ms/src/main/webapp/WEB-INF/jsp/views/Solicitud/`. Repo: [jairleo95/TALENTO_HUMANO](https://github.com/jairleo95/TALENTO_HUMANO).
