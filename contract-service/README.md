## Contract Service (microservicio de contratos/plantillas/adjuntos)

Stack:
- Spring Boot 4.0.0-SNAPSHOT (Java 21), WebFlux, R2DBC PostgreSQL, Flyway, hilos virtuales habilitados.
- Observabilidad: Actuator + tracing OTEL listo para exportar.

Configuración (`src/main/resources/application.yml`):
- App name: `contract-service`, puerto `8083`.
- R2DBC Postgres: `r2dbc:postgresql://localhost:5432/contract_db`, credenciales `contract_user/changeme`.
- Flyway: misma base vía JDBC.
- Hilos virtuales: `spring.threads.virtual.enabled=true`.
- Management: health, info, prometheus expuestos.

Migraciones:
- `db/migration/V1__init.sql` crea tablas `contract_template` (fileName, status, createdBy), `contract` (campos de vigencia, salario, régimen, tipo, observación, auditoría) y `contract_attachment` (sizeBytes, checksum).

Endpoints iniciales:
- Plantillas:
  - `GET /api/v1/contracts/templates` (filtro `name`)
  - `POST /api/v1/contracts/templates` (`TemplateRequest`: name, version, content, fileName?, status?, createdBy?)
- Contratos:
  - `GET /api/v1/contracts` (filtro `requisitionId`)
  - `POST /api/v1/contracts` (`ContractRequest`: requisitionId, templateId, contractNumber?, positionId?, workerId?, startDate?, endDate?, terminationDate?, conditionType?, salaryAmount?, reintegrationAmount?, familyAllowance?, weeklyHours?, dailyHours?, laborRegime?, pensionRegime?, contractType?, observation?, createdBy?) → estado `DRAFT`
  - `PATCH /api/v1/contracts/{id}/sign` → marca `SIGNED` con timestamp
- Adjuntos:
  - `GET /api/v1/contracts/attachments` (filtro `contractId`)
  - `POST /api/v1/contracts/attachments` (`AttachmentRequest`: contractId, filename, contentType, uri, sizeBytes?, checksum?)

Arranque local:
1. Crear base y usuario en PostgreSQL:
   ```sql
   CREATE DATABASE contract_db;
   CREATE USER contract_user WITH ENCRYPTED PASSWORD 'changeme';
   GRANT ALL PRIVILEGES ON DATABASE contract_db TO contract_user;
   ```
2. Desde `contract-service`: `./gradlew bootRun`
   - Flyway aplica `V1__init.sql`.

Pruebas rápidas:
```bash
http :8083/api/v1/contracts/templates name="Contrato Base" version:=1 content="Texto ..."
http :8083/api/v1/contracts templates==  # listar
http :8083/api/v1/contracts requisitionId==00000000-0000-0000-0000-000000000001 templateId==<templateId> contractNumber="CON-2025-001" salaryAmount:=2500 laborRegime="R1"
http PATCH :8083/api/v1/contracts/<contractId>/sign
http :8083/api/v1/contracts/attachments contractId==<contractId> filename="contrato.pdf" contentType="application/pdf" uri="s3://bucket/contrato.pdf" sizeBytes:=204800
```

Notas siguientes:
- Integrar con recruitment-service para validar `requisitionId` y con identity-service para quién firma.
- Añadir almacenamiento real de binarios (S3/MinIO) o tabla bytea si aplica; `uri` actúa como puntero.
- Añadir seguridad JWT/OIDC y OpenAPI. 
