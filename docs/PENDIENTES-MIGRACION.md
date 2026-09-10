# Pendientes de Migración — Hoja de Ruta para Continuar

> Documento de trabajo para que un agente de IA continúe la migración del monolito legacy (`gth-ms`) a los microservicios Spring WebFlux + React. Cada sección indica exactamente qué falta, dónde está el código legacy de referencia, qué construir y cómo verificar.

---

## 0. Contexto operativo (leer antes de empezar)

| Ítem | Detalle |
|---|---|
| Rama | `master`. Idioma del código y commits: **español**. Estilo de commit: `tipo(scope): descripción` en minúsculas (ej. `feat(academic): carga académica y pago docente`) |
| Monolito legacy | `gth-ms/` — **no compila en este entorno** (falta `ojdbc7.jar`). Sirve solo para extraer lógica/referencias. |
| Servicios activos | `identity-service` (8081), `recruitment-service` (8082), `contract-service` (8083), `gth-gtw` gateway (8085), `gth-webapp` (vite, 4200), `push-notification-server` (9898) |
| Servicios EXCLUIDOS (no tocar) | `gth-ms`, `dgp-ms`, `users-ms`, `gth-config-server` |
| BD | Postgres container `talento-postgres`, host puerto **5434**. Usuarios: `identity_user`, `recruitment_user`, `contract_user` |
| Env | **NO** hacer `source .env` (el shell expande `$2b$12...` y corrompe hashes). Exportar manualmente: `export JWT_SECRET="$(awk -F= '/^JWT_SECRET=/{sub(/^JWT_SECRET=/,""); print}' .env)"` y lo mismo para `DB_PASSWORD` |
| Node | `export PATH="$HOME/.nvm/versions/node/v20.20.2/bin:$PATH"` (el node del sistema es v12, falla) |
| Gateway | Boot 4.0.7, Spring Cloud 2025.1.2, prefijo de rutas `spring.cloud.gateway.server.webflux.*`. Rutas: `/identity/**→8081`, `/recruitment/**→8082`, `/contract/**→8083`, `/gth/**→8080(legacy)`, con `StripPrefix=1` |
| Auth | Login → `POST /identity/api/v1/auth/login` (`/api/v1/auth/login` con prefijo gateway). JWT exige rol `ADMIN` casi en todo (RBAC). Credenciales vivas: `admin / 0yoIrGuj2QuIXBVivZJP`, `user / Df34eBgZcdUd6v8e8tho` |
| Rate limit | Gateway `RateLimitFilter` (10/60s en login y `/gth/valida`). En smoke tests usar header `X-Forwarded-For` variado |
| Controles de calidad | Backend: `cd <service> && ./gradlew compileJava` — exit 0. Frontend: `cd gth-webapp && npx tsc -b` (exit 0) y `npm run build`. ESLint NO está instalado en el proyecto (solo el script); no es gate. |
| Migraciones | Flyway por servicio, en `src/main/resources/db/migration/V<N>__*.sql`. Versiones reales: identity V1–V4, recruitment V1–V11, contract V1–V4 |
| Patrón de backend | Reactive (R2DBC) + WebFlux. Estructura: `domain/` (entidades), `persistence/` (repos Spring Data R2DBC), `service/` (lógica, `TransactionalOperator`), `web/` (controller + mapper MapStruct), `web/dto/` (records con validación `jakarta.validation`). Errores → `ResponseStatusException` o `IllegalArgumentException`; `GlobalExceptionHandler` traduce a JSON. |
| Patrón de frontend | React + TanStack Query + PrimeReact. `apiGet/apiPost/apiPatch` en `gth-webapp/src/core/api/client.ts`; `useAuth()` da `user.username`; validaciones con zod + `react-hook-form`; `VALIDATION_RULES` en `shared/validations.ts`; moneda `S/` con `toLocaleString('es-PE', ...)`. |
| Documentación de referencia | `docs/gth-legacy/*.md` (módulos) + `docs/migracion-frontend-react.md` (plan maestro). `images/` NO se versiona (excluir de commits). |

---

## 1. Estado global

| Módulo | Backend | Frontend | Cobertura |
|---|---|---|---|
| R0 Andamiaje (auth, router, shell) | ✅ | ✅ | 100% |
| R1 Requerimientos (DGP) | ✅ | ✅ | ~90% |
| R2 Proceso + Inbox | ✅ | ✅ | 100% |
| R3 Contrato | ✅ | ✅ | ~92% |
| R4 Usuario/Seguridad | ✅ | ✅ | ~85% |
| R5a Trabajador | ✅ | ✅ | ~72% |
| R5b Organigrama + Puesto | ✅ | ✅ | ~97% |
| R5c Presupuesto | ✅ | ✅ | ~55% |
| R5d Académico | ✅ | ✅ | ~80% |
| R5e Reportes | ⬜ | placeholder | 0% |
| R5f Funciones | ⬜ | placeholder | 0% |
| R6 Seguridad (RBAC, rate-limit) | ✅ | — | 100% |

---

## 2. Prioridades y orden sugerido

1. **R5f Funciones** (0%→90%) — 1 tabla nueva, CRUD simple, esfuerzo bajo.
2. **14.7 Académico** (80%→95%) — 2 catálogos (Modalidad, Período), esfuerzo bajo.
3. **14.1 DGP** (90%→96%) — Plazos + Horario semanal ligados al requerimiento, esfuerzo bajo-medio.
4. **14.4 Organigrama** (97%→100%) — catálogos Grupo_Ocupaciones / Ubigeo, esfuerzo bajo.
5. **R5e Reportes** (0%→60%+) — 4 consultas de solo lectura + frontend, esfuerzo medio.
6. **14.3 Trabajador** (72%→90%) — Familiar/Hijos, documentos, esfuerzo medio.
7. **14.2 Contrato** (92%→97%) — catálogo Tipo_Contrato + workflow Casos Especiales, esfuerzo medio.
8. **14.5 Presupuesto** (55%→80%) — Pedido/SFP/autorización, esfuerzo ALTO (el más complejo del backlog).

---

## 3. Módulo: Funciones por Puesto (R5f) — pendiente 100%

**Qué falta:** CRUD de funciones descriptivas por puesto y su asignación. Backend nuevo en recruitment-service + reemplazo del placeholder `FunctionsPage`.

### Referencia legacy
- Controllers: `gth-ms/src/main/java/com/app/controller/recruitment/person/job/CFuncion.java`
- DAO: `gth-ms/src/main/java/com/app/persistence/dao/FuncionDAO.java`, interfaz `dao_imp/IFuncionDAO.java`
- Modelo: `gth-ms/src/main/java/com/app/domain/model/Funciones.java`
- Vistas JSP: `gth-ms/src/main/webapp/WEB-INF/jsp/views/Funciones/List_Funciones.jsp`, `Priv_Funciones.jsp`
- Doc módulo: `docs/gth-legacy/02-modules/puesto-organigrama.md`
- Tabla legacy: `Funciones`

### Sugerencia de backend
- Migración **V12** en `recruitment-service/src/main/resources/db/migration/V12__puesto_functions.sql`:
  - `puesto_function` (id, position_id FK→`position`/`puesto`, code, name, description, sort_order, created_at, updated_at, constraint unique(position_id, code)).
- Seguir patrón `AcademicChargeService`: domain `PuestoFunction`, repo `PuestoFunctionRepository` (findByPositionIdOrderBySortOrderAsc), DTOs `PuestoFunctionRequest/Response`, mapper MapStruct, controller `PuestoFunctionController` en `/api/v1/recruitment/positions/{positionId}/functions` (GET list, POST, PATCH `/{id}`, DELETE `/{id}`, PATCH `/{id}/toggle`).
- Verificar: `./gradlew compileJava` exit 0; re-arrancar service para que Flyway aplique V12; crear/editar/eliminar funciones con token admin.

### Sugerencia de frontend
- Reemplazar `gth-webapp/src/features/functions/FunctionsPage.tsx`: tabla Puesto (ya existe en `/recruitment/api/v1/recruitment/organizational-units` tipo PUESTO) con select + tabla de funciones editable (nombre, código, orden) + dialog nuevo + botones activar/desactivar/eliminar.

---

## 4. Módulo: Académico — catálogos Modalidad y Período (pendiente ~20%)

**Qué falta:** tabla `Modalidad` (Modalidad/Sub_Modalidad, ya usada por PagoDocente legacy) y `Periodo_Academico`. Sin dependencia de pago docente.

### Referencia legacy
- Entidades: `Modalidad`, `Sub_Modalidad`, `Periodo_Pago` (domain: `gth-ms/.../domain/model/`; DAO: `Sub_ModalidadDAO.java`, `Periodo_PagoDAO.java`)
- Doc: `docs/gth-legacy/02-modules/academico.md`
- JSP: `gth-ms/src/main/webapp/WEB-INF/jsp/views/Academico/Carga_Academica/`

### Sugerencia de backend (V13)
- `academic_modality` (id, code, name, sub_modality, sort_order, is_active) y `academic_period` (id, code, name, start_date, end_date, is_active).
- Controllers livianos (`AcademicModalityController`, `AcademicPeriodController`) en `/api/v1/recruitment/academic/modalities` y `/academic/periods`: GET list, POST, PATCH toggle.
- Opcional: ampliar la carga académica existente para que `AcademicChargeRequest` acepte opcionalmente `modalityId`/`periodId`.

### Sugerencia de frontend
- En `AcademicPage.tsx` añadir 2 tabs (o un tab único "Catálogos") con las tablas + dialogs. Seguir patrón del tab Universidades/Carreras.

---

## 5. Módulo: Requerimientos (DGP) — Plazos y Horario semanal (pendiente ~10%)

**Qué falta:** Vistas legacy `V_Dgp_Plazo` y `V_Dgp_Horario` (horario de 7 días con turnos). Backend nuevo en recruitment-service + enriquecer el detalle de DGP existente.

### Referencia legacy
- Doc módulo: `docs/gth-legacy/02-modules/dgp-requerimiento.md` (secciones de plazos y horario)
- JSP legacy (buscar en `gth-ms/src/main/webapp/WEB-INF/jsp/views/Solicitud`): formularios de plazos y horario semanal.
- `docs/gth-legacy/08-mapeo-microservicios.md` marca ambos como ⬜.

### Sugerencia de backend (V14)
- `requisition_deadline` (id, requisition_id FK, responsible_user, start_date, end_date, description, sort_order) y `requisition_weekly_schedule` (id, requisition_id FK, day_number 1..7, start_time, end_time, shift_name).
- Endpoints bajo `/api/v1/recruitment/requisitions/{requisitionId}/deadlines` y `/.../weekly-schedule` (GET/POST/PATCH/DELETE). Incluir ambos arrays en el detail response del requisito (ampliar `RequisitionService.findById` y su mapper).

### Sugerencia de frontend
- En `RequirementsPage.tsx` (dialog de detalle `DgpDetailSections`): añadir secciones "Plazos" y "Horario semanal" (tabla 7 días × turnos, editable).

---

## 6. Módulo: Organigrama — Grupo_Ocupaciones / Ubigeo (pendiente ~3%)

**Qué falta:** catálogos estáticos referenciados por puestos y trabajadores (campo `occupationalGroup` en el puesto/organigrama).

### Referencia legacy
- `docs/gth-legacy/02-modules/puesto-organigrama.md`
- Tabla legacy `Grupo_Ocupaciones`
- Ubigeo: tablas `Ub_Departamento`, `Ub_Provincia`, `Ub_Distrito` (el trabajador ya guarda `departmentId/provinceId/districtId`).

### Sugerencia de backend (V15)
- `occupational_group` (id, code, name, is_active) + script de seed con los grupos legacy (extraer del monolito o de `docs/gth-legacy/04-database.md`).
- Ubigeo como tablas de solo lectura (`ubigeo_department`, `ubigeo_province`, `ubigeo_district`) con seed o endpoint que consulte los códigos ya almacenados en `worker`.
- Endpoint `GET /api/v1/recruitment/occupational-groups` y `GET /api/v1/recruitment/ubigeo` (departamento→provincia→distrito encadenado).

### Sugerencia de frontend
- Poblar los Dropdown "Grupo Ocupacional" (Organigrama) y Ubigeo (Trabajador) desde estos endpoints.

---

## 7. Módulo: Reportes (R5e) — pendiente 100%

**Qué falta:** todo. `ReportsPage.tsx` es solo tarjetas estáticas. Backend nuevo (solo lectura) en el servicio que corresponda al dato.

### Referencia legacy
- Controllers: `gth-ms/src/main/java/com/app/controller/report/ReporteController.java`, `ReporteHijoController.java`, `ReporteHistorialController.java`
- DAO: `gth-ms/src/main/java/com/app/persistence/dao/ReporteDAO.java`, `Reporte_HistorialDAO.java`
- Vistas: `gth-ms/src/main/webapp/WEB-INF/jsp/views/Reportes/*.jsp`
- Doc: `docs/gth-legacy/02-modules/reportes.md`

### Alcance mínimo propuesto (4 reportes)
1. **Trabajadores** por dirección/departamento/situación → `GET /recruitment/api/v1/recruitment/reports/workers?departmentId=&areaId=&situation=`
2. **DGP por estado y fecha** → `GET /recruitment/api/v1/recruitment/reports/requisitions?status=&from=&to=` (reutilizar listado existente con filtros)
3. **Contratos firmados / por vencer / vencidos** → `GET /contract/api/v1/contracts/reports/status?type=firmados|por-vencer|vencidos`
4. **Resumen presupuestario por CC y departamento** → `GET /recruitment/api/v1/recruitment/reports/budget-summary?departmentId=&costCenterId=`

### Sugerencia de backend
- Patrón: **solo consultas SQL/query methods en repos R2DBC**, DTO de respuesta plano (`ReportRowResponse` genérico o DTOs específicos). Sin escrituras. Respetar RBAC (rol ADMIN o rol con privilegio REPORTES si se quiere granular).
- Implementar de 1 a 1 con los DAOs legacy para respetar los cálculos originales (revisar `ReporteDAO.java`).

### Sugerencia de frontend
- Convertir `ReportsPage.tsx` en un TabView con un tab por reporte: filtros (Dropdowns/Calendar) + `DataTable` con el payload del endpoint.

---

## 8. Módulo: Trabajador — Familiar/Hijos, Documentos, Fotos (pendiente ~28%)

**Qué falta:** familiares del trabajador (padres, cónyuge, hijos), documentos del trabajador y fotos/historial.

### Referencia legacy
- Controller: `gth-ms/src/main/java/com/app/controller/recruitment/person/job/FamiliarController.java` (`@RequestMapping("familiar")`), `gth-ms/.../recruitment/documents/DocumentroTrabajadorController.java`
- DAO: `Datos_Hijo_TrabajadorDAO.java`, `Fotos_TrabajadorDAO.java` (+ interfaces `dao_imp/`)
- Tablas legacy: `Padre_Madre_Conyugue`, `Datos_Hijo_Trabajador`, `Fotos_Trabajador`, `Hist_Estado_Civil`, `Documentos`, `Datos_Generales`
- Vistas JSP: `gth-ms/src/main/webapp/WEB-INF/jsp/views/Trabajador/` (carpeta `Familiar/` con `Reg_Padres`, `List_Hijo`, `Reg_Datos_Hijo`, `Mod_Datos_Hijos`, etc. y `List_Doc_Trabajador.jsp`)
- Doc: `docs/gth-legacy/02-modules/trabajador.md`

### Sugerencia de backend (V16)
- `worker_familiar` (id, worker_id FK, last_name, first_name, document_number, relationship (PADRE/MADRE/CONYUGE/HIJO), birth_date, education_level, occupation, is_deceased, phone, created_at).
- `worker_foto` (id, worker_id FK, content_type, data o storage_key, captured_at, description).
- Endpoints `/api/v1/recruitment/workers/{workerId}/familiars` (GET/POST/PATCH/DELETE) y `/workers/{workerId}/photos` (POST multipart, GET).
- **Límite de alcance:** documentar el flujo en el detalle del trabajador y agregar el CRUD de familiares primero; fotos pueden quedar como subida simple de base64.

### Sugerencia de frontend
- En `WorkerDetailPage.tsx`: secciones expandibles "Familiar / Hijos" (tabla + dialog) y "Documentos" (lista + subida). Reutilizar patrón DGP de comentarios/documentos.

---

## 9. Módulo: Contrato — Tipo_Contrato y Casos Especiales (pendiente ~8%)

**Qué falta:** catálogo de tipos de contrato y completar el flujo de Casos Especiales (CE).

### Referencia legacy
- Tabla: `Tipo_Contrato`
- Contract controller legacy: `gth-ms/src/main/java/com/app/controller/recruitment/contract/` (buscar el manejador de tipos y casos especiales; `ContratoAdjuntoController.java` ya migrado)
- DAO: `gth-ms/.../dao/ContratoDAO.java`, `Periodo_PagoDAO.java`
- Doc: `docs/gth-legacy/02-modules/contrato.md`
- Frontend actual: `ContractListPage` ya detecta CE con tag "Caso Especial" cuando no hay requisición. Falta el *workflow* (aprobación/estados) y el catálogo.

### Sugerencia de backend (V5 en contract-service)
- `contract_type` (id, code, name, is_active) + seed.
- `GET /contract/api/v1/contracts/types` (catálogo), `POST`, `PATCH /{id}/toggle`.
- Workflow CE: revisar `docs/gth-legacy/08-mapeo-microservicios.md` (marcado 🔄 parcial) y decidir con el usuario si se modela un estado adicional en `contract` (ej. `case_status`) o un sub-flujo.

### Sugerencia de frontend
- Dropdown "Tipo de Contrato" en el dialog de creación de contrato y sección de configuración de casos especiales.

---

## 10. Módulo: Presupuesto — Pedido/SFP (pendiente 45%, el MÁS COMPLEJO)

**Qué falta:** el flujo completo de gestión presupuestal legacy: presupuesto por puesto, detalle por puesto, solicitantes y **SFP (Solicitud Fuera de Presupuesto)** con autorización.

### Referencia legacy
- Controller: `gth-ms/src/main/java/com/app/controller/recruitment/PresupuestoController.java` (`@RequestMapping("presupuesto")`, **único `switch(opc)`** con ~40 operaciones)
- DAO: `gth-ms/src/main/java/com/app/persistence/dao/PresupuestoDAO.java` y `dao_imp/InterfacePresupuestoDAO.java`
- Entidad: `gth-ms/src/main/java/com/app/domain/model/` → `Presupuesto`, `CostCenter`, `CostCenterDetail`, `V_Solicitud_Requerimiento`
- JSP: `gth-ms/src/main/webapp/WEB-INF/jsp/views/Presupuesto/` (Gestionar_Presupuesto, List_Fuera_Presupuesto, statusSFP, reportes) + `CorePresupuesto.js`, `LogicPresup.js`
- Doc: `docs/gth-legacy/02-modules/presupuesto.md`
- Ya migrado: `budget_period`, `budget_allocation` (V10), `cost_center` (V10), endpoints `/recruitment/api/v1/recruitment/budget/*` y `/cost-centers`.

### Sugerencia de backend (V17)
Nuevas tablas, modelando el flujo legacy:
- `budget` (id, period_id FK, org_unit_id FK, status, created_by, created_at) — presupuesto de un destino.
- `budget_detail` (id, budget_id FK, requisition_id FK o null, worker_count, state).
- `budget_detail_position` (id, budget_detail_id FK, position_id FK, worker_count, min_/max_ salary/bonus/food_bonus — mapear `UpdateDetSueldo`/`Reg_DetSueldo`).
- `budget_sfp` (id, budget_detail_position_id FK, requested_by, comment, status PENDIENTE/APROBADO/RECHAZADO, requested_at) — flujo `regSFP`/`authPres`.

Endpoints REST (reemplazan el `switch(opc)`):
- `GET/POST /api/v1/recruitment/budget` — CRUD de presupuestos por período/destino.
- `GET/POST/PATCH /api/v1/recruitment/budget/{budgetId}/details` — detalle por requisición.
- `GET/POST/PATCH /api/v1/recruitment/budget/details/{detailId}/positions` — puestos + sueldos (`updateSueldo`, `regPP`, `regPuesTra`, `updPuesTra`).
- `GET/POST /api/v1/recruitment/budget/sfp` + `PATCH /api/v1/recruitment/budget/sfp/{id}/approve|reject` — SFP y autorización (`authPres`, `listSFPP`, `listAllSFP`).
- `GET /api/v1/recruitment/budget/reports` — resumen (`listResumenPres`, `listResumenDetPres`).

**ADVERTENCIA para el agente:** este módulo tiene validaciones de conteo de trabajadores (`comprobarContratadosBy*`, `calcTrabPresBy*`) que NO deben replicarse ciegamente; revisar cada regla en `PresupuestoDAO.java` y confirmar con el usuario el alcance antes de implementar. Definir un solo lote por PR/commit.

### Sugerencia de frontend
- Ampliar `CostCenterPage.tsx`: nuevo tab "Presupuesto" (por período/destino con detalle por puesto en master-detail tipo temporadas) y tab "SFP" (solicitudes + aprobación con botones Aprobar/Rechazar y observación).

---

## 11. Reglas de verificación antes de terminar cada módulo

1. **Migración**: crear `V<N>__*.sql` (naming incremental por servicio, sin colisionar con las existentes). Re-arrancar el servicio (`kill` + `nohup ./gradlew bootRun` con JWT_SECRET/DB_PASSWORD exportados) y confirmar en `/tmp/<service>.log` el evento `Migrating schema ... to version "N"` + `Successfully applied`.
2. **Backend**: `./gradlew compileJava` exit 0.
3. **Frontend**: `npx tsc -b` exit 0 y `npm run build` exit 0 (PATH node v20).
4. **Smoke test** via gateway (puerto 8085) con refresh del token admin por llamada (+ timeout de rate limit) y `X-Forwarded-For` variado: login → crear → listar → actualizar → caso de error esperado (400/404).
5. **Rol USER** debe recibir 403 (regresión RBAC).
6. **Docs**: actualizar `docs/migracion-frontend-react.md` (sección espejo: §6 tabla de fases, §7 endpoints, §8 migraciones, §9 paridad, §14 % de brechas) y `docs/gth-legacy/08-mapeo-microservicios.md` (correspondencia → ✅/🔄/⬜ y decisiones resueltas).
7. **Commit**: `git add` explícito (NUNCA `images/`, NUNCA `.env`), mensaje en español estilo `tipo(scope): descripción`. Commits previos de referencia: `f441b466` (academic), `99211148` (security).
8. **No sobre-ingenierías:** no crear abstracciones para casos futuros; no añadir endpoints que el frontend no consuma; no tocar servicios excluidos (`gth-ms`, `dgp-ms`, `users-ms`, `gth-config-server`).

---

## 12. Índice de documentación de referencia

| Documento | Contenido |
|---|---|
| `docs/migracion-frontend-react.md` | Plan maestro de migración (fases, endpoints, migraciones, brechas §14) |
| `docs/gth-legacy/08-mapeo-microservicios.md` | Correspondencia módulo→microservicio + decisiones de diseño |
| `docs/gth-legacy/02-modules/*.md` | Especificación funcional por módulo legacy |
| `docs/gth-legacy/04-database.md` | Esquema de base de datos legacy |
| `docs/gth-legacy/03-api-endpoints.md` | Endpoints REST legacy |
| `docs/gth-legacy/07-integraciones.md` | Integraciones (SOAP UPeU, etc.) |
| `docs/gth-legacy/06-frontend-jsp.md` | Estructura de frontend legacy |
| `AGENTS.md` | Directivas core del agente (leer antes de codificar) |