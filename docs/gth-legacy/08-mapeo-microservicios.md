# 08 — Mapeo Legacy → Microservicios

Guía orientativa de correspondencia entre el monolito legacy y los microservicios del monorepo (migración en curso). Los servicios nuevos corren con **Spring Boot 4.0.7 / Java 21 / WebFlux / R2DBC / Postgres**.

## Servicios en el monorepo

| Servicio | Ruta gateway | Puerto | Bounded context |
|---|---|---|---|
| `identity-service` | `/identity/**` | 8081 | Usuarios, roles, privilegios |
| `recruitment-service` | `/recruitment/**` | 8082 | Requerimientos (DGP), procesos, pasos, inbox, centros de costo |
| `contract-service` | `/contract/**` | 8083 | Contratos, plantillas, adjuntos |
| `gth-gtw` | — | 8085 | Gateway (Spring Cloud Gateway) |
| `gth-ms` | `/gth/**` | 8080 | **Legacy** (en convivencia durante la migración) |

## Correspondencia funcional

| Módulo legacy | Controller legacy | Microservicio destino | Estado |
|---|---|---|---|
| Login / sesión | `IndexController` (`valida`) | identity-service (auth + JWT) | ✅ (fase 3; rate-limit + anti-enumeración en `99211148`) |
| Usuarios, roles, privilegios | `UserController`, `RolController`, `PrivilegeController`, `ModuleController`, `MenuController` | identity-service | ✅ CRUD users, roles y privileges |
| Requerimiento/DGP | `DGPController`, `RequerimientoController`, `AuthorizationController`, `AcademicImboxController`, `ProcessStatusController` | recruitment-service | ✅ requisitions + comments/docs (V9) |
| Proceso / pasos | `ProcessController`, `PhaseController`, `CommentaryController`, `TermController` | recruitment-service | ✅ process + steps + inbox |
| Centros de costo | `CentroCostoController`, `MCCostoController` | recruitment-service | ✅ cost-centers |
| Presupuesto / períodos | `PresupuestoController` | recruitment-service | 🔄 budget periods + allocations (V10); SFP/solicitud pendiente |
| Contrato | `ContractController`, `PrintController`, `ContratoAdjuntoController`, `MassivePrintController` | contract-service | ✅ contracts, sign, attachments, signed-document |
| Plantillas | `ContractTemplateController`, `PlantillaContractualController` | contract-service | ✅ templates + template_assignment (V4) |
| Trabajador / persona | `PersonController`, `EmployeeController`, `FamiliarController` | recruitment-service | 🔄 CRUD workers; familiar/hijos/docs pendiente |
| Puesto / organigrama | `JobController`, `CDir_Puesto`, `CFuncion`, `CUbigeo` | recruitment-service | ✅ organizational-units jerárquicas; funciones/ubigeo pendiente |
| Académico | `CargaAcademicaController`, `PagoDocenteController` | recruitment-service | 🔄 careers + universities (V8); carga/pago docente pendiente |
| Reportes / historial | `ReporteController`, `ReporteHijoController`, `ReporteHistorialController` | (pendiente) | ⬜ |
| Horario / formatos | `FormatoHorarioController`, `HorarioController` | (pendiente) | ⬜ |

## Decisiones de diseño resueltas

- ✅ **Login JWT** en identity-service (`POST /api/v1/auth/login`) y validación en el gateway (`JwtAuthFilter`, `RateLimitFilter`).
- ✅ **Consumo del SPA** vía gateway `gth-gtw` como único punto de entrada (`/identity|/recruitment|/contract`).
- ✅ **RBAC por rol** (ADMIN) enfocado en gateway + filtros WebFlux de recruitment/contract (`99211148`).

## Decisiones de diseño pendientes (backlog)

- Separar la entidad **`dgp`** de `requisition` (decisión del usuario).
- Extraer especificación real del monolito (tablas `RHTV_*`/`RHTC_*`/`RHTR_*`/`RHTD_*`, SPs `RHSP_*`) para los módulos pendientes (reportes, horarios, académico).

## Notas

- El gateway `gth-gtw` ya enruta al legacy (`/gth/**` → `:8080`) para **convivencia durante la transición**.
- Cada microservicio debe replicar la lógica de negocio de su controller legacy (`opc=`) como endpoints REST limpios, con SQL parametrizado (los DAOs legacy concatenan SQL).

## Referencia

Detalle funcional por módulo en [02-modules/](02-modules/README.md). Código legacy: [jairleo95/TALENTO_HUMANO](https://github.com/jairleo95/TALENTO_HUMANO).
