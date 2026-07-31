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
| Login / sesión | `IndexController` (`valida`) | identity-service (auth + JWT) | Pendiente (Fase 3) |
| Usuarios, roles, privilegios | `UserController`, `RolController`, `PrivilegeController`, `ModuleController`, `MenuController` | identity-service | Parcial (users CRUD) |
| Requerimiento/DGP | `DGPController`, `RequerimientoController`, `AuthorizationController`, `AcademicImboxController`, `ProcessStatusController` | recruitment-service | Parcial (requisitions, inbox) |
| Proceso / pasos | `ProcessController`, `PhaseController`, `CommentaryController`, `TermController` | recruitment-service | Parcial (process, steps) |
| Centros de costo | `CentroCostoController`, `MCCostoController` | recruitment-service | Parcial (cost-centers) |
| Contrato | `ContractController`, `PrintController`, `ContratoAdjuntoController`, `MassivePrintController` | contract-service | Parcial (contracts, attachments) |
| Plantillas | `ContractTemplateController`, `PlantillaContractualController` | contract-service | Parcial (templates) |
| Trabajador / persona | `PersonController`, `EmployeeController`, `FamiliarController` | (nuevo dominio) | Pendiente |
| Puesto / organigrama | `JobController`, `CDir_Puesto`, `CFuncion`, `CUbigeo` | (nuevo dominio) | Pendiente |
| Presupuesto / SFP | `PresupuestoController` | (nuevo dominio) | Pendiente |
| Académico | `CargaAcademicaController`, `PagoDocenteController` | (nuevo dominio) | Pendiente |
| Reportes / historial | `ReporteController`, `ReporteHijoController`, `ReporteHistorialController` | (nuevo dominio) | Pendiente |
| Horario / formatos | `FormatoHorarioController`, `HorarioController` | (nuevo dominio) | Pendiente |

## Decisiones de diseño pendientes (backlog)

- Separar la entidad **`dgp`** de `requisition` (decisión del usuario).
- Extraer especificación real del monolito (tablas `RHTV_*`/`RHTC_*`/`RHTR_*`/`RHTD_*`, SPs `RHSP_*`) antes de diseñar contratos.
- Diseñar el endpoint de **login JWT** en identity-service y la autenticación en el gateway.
- Definir cómo el SPA nuevo consume la API (vía gateway `gth-gtw`, único punto de entrada).

## Notas

- El gateway `gth-gtw` ya enruta al legacy (`/gth/**` → `:8080`) para **convivencia durante la transición**.
- Cada microservicio debe replicar la lógica de negocio de su controller legacy (`opc=`) como endpoints REST limpios, con SQL parametrizado (los DAOs legacy concatenan SQL).

## Referencia

Detalle funcional por módulo en [02-modules/](02-modules/README.md). Código legacy: [jairleo95/TALENTO_HUMANO](https://github.com/jairleo95/TALENTO_HUMANO).
