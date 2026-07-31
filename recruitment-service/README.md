## Recruitment Service (microservicio de requerimientos/DGP)

Stack:
- Spring Boot 4.0.0-SNAPSHOT (Java 21), WebFlux, R2DBC PostgreSQL, Flyway, hilos virtuales habilitados.
- Observabilidad: Actuator + tracing OTEL listo para exportar.

Configuración (`src/main/resources/application.yml`):
- App name: `recruitment-service`, puerto `8082`.
- R2DBC Postgres: `r2dbc:postgresql://localhost:5432/recruitment_db`, credenciales `recruitment_user/changeme`.
- Flyway: misma base vía JDBC.
- Hilos virtuales: `spring.threads.virtual.enabled=true`.
- Management: health, info, prometheus expuestos.

Migraciones:
- `db/migration/V1__init.sql` crea tabla `requisition` (incluye requestNumber, payrollTypeId, positionId, costCenterId y campos de fechas/salario/bonos/domicilio/hora-banco).
- `db/migration/V2__process_inbox_costcenter.sql` crea `process`, `process_step` (code, description, slaHours), `inbox_item` y `cost_center` (departmentId, percentage).
- `db/migration/V3__dgp_extended.sql` añade columnas DGP si la base ya existía.

Agregado actual:
- Requisition: título, descripción, requestNumber, payrollTypeId, positionId, costCenterId, fechas (start/end), salario/bonos, lugar/servicio, periodo pago, domicilio fiscal, horarios (capacitaciones/refrigerio), días trabajo/capacitación, antecedentes/certificados, datos bancarios, estado (OPEN por defecto), timestamps, createdBy.
- Endpoints:
  - `GET /api/v1/recruitment/requisitions` (filtro opcional `status`)
  - `GET /api/v1/recruitment/requisitions/{id}`
  - `POST /api/v1/recruitment/requisitions` (payload `RequisitionRequest`: title, description, createdBy, requestNumber, payrollTypeId?, positionId?, costCenterId?, startDate?, endDate?, salaryAmount?, foodBonus?, workDays?, serviceLocation?, serviceDescription?, paymentPeriod?, fiscalAddress?, allowanceDescription?, trainingSchedule?, breakSchedule?, trainingDays?, policeRecordDesc?, healthCertificateDesc?, bankName?, bankAccount?)
  - `PATCH /api/v1/recruitment/requisitions/{id}/status` (payload `RequisitionStatusRequest`: status)
  - `GET /api/v1/recruitment/processes` (filtro `status`)
  - `POST /api/v1/recruitment/processes` (payload `ProcessRequest`: name, code, description?)
  - `POST /api/v1/recruitment/processes/{id}/steps` (payload `ProcessStepRequest`: name, orderIndex, code, description?, slaHours?)
  - `GET /api/v1/recruitment/processes/{id}/steps`
  - `PATCH /api/v1/recruitment/processes/{id}/status?status=...`
  - `GET /api/v1/recruitment/inbox` (filtro `assignee`)
  - `POST /api/v1/recruitment/inbox` (payload `InboxItemRequest`: requisitionId, processStepId, assignee)
  - `PATCH /api/v1/recruitment/inbox/{id}/status?status=...`
  - `GET /api/v1/recruitment/cost-centers`
  - `POST /api/v1/recruitment/cost-centers` (payload `CostCenterRequest`: code, name, departmentId?, percentage?)

Arranque local:
1. Crear base y usuario en PostgreSQL:
   ```sql
   CREATE DATABASE recruitment_db;
   CREATE USER recruitment_user WITH ENCRYPTED PASSWORD 'changeme';
   GRANT ALL PRIVILEGES ON DATABASE recruitment_db TO recruitment_user;
   ```
2. Desde `recruitment-service`: `./gradlew bootRun`
   - Flyway aplica `V1__init.sql`.

Pruebas rápidas:
```bash
http :8082/api/v1/recruitment/requisitions title="Requerimiento docente" description="Curso X 2025" createdBy="admin"
http :8082/api/v1/recruitment/requisitions
```

Notas siguientes:
- Extender con dominio DGP completo (procesos/pasos, inbox, centros de costo, horarios) y relaciones entre entidades.
- Añadir seguridad (JWT/OIDC) y validaciones de negocio/estados.
