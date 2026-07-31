## Identity Service (microservicio de usuarios/roles)

Stack:
- Spring Boot 4.0.0-SNAPSHOT (Java 21), WebFlux, R2DBC PostgreSQL, Flyway, hilos virtuales habilitados.
- Observabilidad: Actuator + tracing OTEL listo para exportar.

Configuración (`src/main/resources/application.yml`):
- App name: `identity-service`, puerto `8081`.
- R2DBC Postgres: `r2dbc:postgresql://localhost:5432/identity_db`, credenciales `identity_user/changeme`.
- Flyway: misma base vía JDBC.
- Hilos virtuales: `spring.threads.virtual.enabled=true`.
- Management: health, info, prometheus expuestos.

Migraciones:
- Flyway `db/migration/V1__init.sql` crea tablas `role`, `privilege`, `role_privilege`, `user_account`, `user_role` e inserta roles `ADMIN` y `USER`.

Endpoints actuales:
- `GET /api/v1/users` → lista usuarios con roles.
- `GET /api/v1/users/{id}` → detalle.
- `POST /api/v1/users` → crea usuario con roles (`UserRequest`: username, email, roleIds opcional).

Arranque local:
1. Crear base y usuario en PostgreSQL:
   ```sql
   CREATE DATABASE identity_db;
   CREATE USER identity_user WITH ENCRYPTED PASSWORD 'changeme';
   GRANT ALL PRIVILEGES ON DATABASE identity_db TO identity_user;
   ```
2. Desde `identity-service`: `./gradlew bootRun`
   - Flyway corre en el arranque y aplica `V1__init.sql`.

Pruebas rápidas (ejemplo `httpie`):
```bash
http :8081/api/v1/users username=jdoe email=jdoe@example.com roleIds:='[]'
http :8081/api/v1/users
```

Notas:
- R2DBC no soporta JPA; se usan repositorios reactivos Spring Data R2DBC.
- Ajusta credenciales/host de DB y añade seguridad (JWT/OIDC) según entorno.
