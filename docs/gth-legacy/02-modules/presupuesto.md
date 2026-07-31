# Módulo: Presupuesto

## Funcional

Gestiona el **presupuesto de puestos** de la institución y las **solicitudes fuera de presupuesto (SFP)**.

**Flujo de negocio**:
1. **Gestionar presupuesto** (`Gestionar_Presupuesto.jsp`): mantenimiento del presupuesto por puesto.
2. **Solicitudes fuera de presupuesto** (`List_Fuera_Presupuesto.jsp`, `statusSFP.jsp`): crear y dar seguimiento a solicitudes SFP.
3. **Autorización de presupuesto** (`authPres`): flujo de aprobación.
4. **Reportes** (`reporteResumenPresupuesto.jsp`, `resumenDetalladoPresupuesto.jsp`): resumen y detalle del presupuesto.
5. **Relación con puestos/trabajadores** (`comPues`, `regPuesTra`): asignación de puestos a presupuesto.

## Técnico

**Controllers**:
- `recruitment/PresupuestoController` (`presupuesto`) — único que usa `switch(opc)`.
- `recruitment/MCCostoController` (`Costo`) — centros de costo relacionados.

**opc= principales** (`PresupuestoController`): `gest`, `regPres`, `reg`, `regSFP`, `listSFPP`, `listAllSFP`, `listResumenPres`, `listResumenDetPres`, `authPres`, `list`, `comp`, `compByIdPP`, `getTempByIdPres`, `hist_con`, `actual`, `listActual`, `status`, `ccosto`, `n_temp`, `list_temp`, `regDetPre`, `updateDetPre`, `listDetPre`, `listNtra`, `comPues`, `regPuesTra`, `updPuesTra`, `infoPP`, `updateSueldo`, `regPP`, `infTra`, `presupuestoDetails`. Vistas: `solfpview`, `statusSFPview`, `resumenPresView`, `resumenDetPresView`.

**Entidades de dominio**: `CostCenter`, `CostCenterDetail`, `Presupuesto` (vía DAO), `V_Solicitud_Requerimiento`.

**JSPs**: `Presupuesto/` (6): `Gestionar_Presupuesto`, `Gpresupuesto`, `List_Fuera_Presupuesto`, `statusSFP`, `reporteResumenPresupuesto`, `resumenDetalladoPresupuesto`.

**JS utilitarios**: `Presupuesto/` (JS de reportes).

## Notas de migración

- Sin microservicio destino aún; dominio candidato a un servicio de "presupuesto" o integrado en recruitment-service.

## Referencia

Código local: `gth-ms/src/main/java/com/app/controller/recruitment/PresupuestoController.java`; `gth-ms/src/main/webapp/WEB-INF/jsp/views/Presupuesto/`. Repo: [jairleo95/TALENTO_HUMANO](https://github.com/jairleo95/TALENTO_HUMANO).
