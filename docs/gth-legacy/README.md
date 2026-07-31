# Documentación del Sistema Legacy — GTH (Gestión del Talento Humano)

Documentación funcional y técnica del monolito legacy `gth-ms`: sistema de RRHH de la **UPeU** (Universidad Peruana Unión). Este directorio sirve como referencia para la migración a microservicios.

## Acceso al código

- **Repo legacy (GitHub)**: [jairleo95/TALENTO_HUMANO](https://github.com/jairleo95/TALENTO_HUMANO)
- **Código local**: `gth-ms/` (módulo Spring Boot 2.7.5 dentro de este monorepo)

## Índice

| Documento | Contenido |
|---|---|
| [00-overview.md](00-overview.md) | Qué es GTH, contexto, stack, cifras clave |
| [01-architecture.md](01-architecture.md) | Arquitectura técnica: capas, patrones, conexión a BD |
| [02-modules/](02-modules/README.md) | Funcionalidad por módulo de negocio |
| [03-api-endpoints.md](03-api-endpoints.md) | Inventario de controllers y endpoints (`opc=`) |
| [04-database.md](04-database.md) | Esquema Oracle, stored procedures, scripts SQL |
| [05-auth-sesion.md](05-auth-sesion.md) | Autenticación, sesión y permisos por rol |
| [06-frontend-jsp.md](06-frontend-jsp.md) | Frontend JSP + SmartAdmin, shell SPA y JS de negocio |
| [07-integraciones.md](07-integraciones.md) | WebService académico, mail, WebSocket, Config Server |
| [08-mapeo-microservicios.md](08-mapeo-microservicios.md) | Mapeo entidad legacy → microservicios nuevos |

## Glosario de negocio

| Término | Significado |
|---|---|
| **DGP** | Documento de Gestión de Puestos (requerimiento de contratación). Núcleo del sistema. |
| **Requerimiento** | Solicitud de puesto/contratación (tipo DGP). Pasa por un flujo de autorización. |
| **SFP** | Solicitud Fuera de Presupuesto. |
| **APS** | Código/cuenta de pago del trabajador (Asignación de Puesto y Sueldo). |
| **CE** | Contratación Especial / Casos Especiales. |
| **ti_hora_pago** | Tipo de hora de pago (académico). |
| **CC** | Centro de Costos. |
| **PRV-** | Código de privilegio (ej. `PRV-000012`). |
| **ROL-** | Código de rol (ej. `ROL-0001` administrador, `ROL-0011`, `ROL-0013`). |
| **RHSP\_** | Stored procedures del esquema Oracle (ej. `RHSP_INSERT_USUARIO`). |
| **RHTC\_ / RHTR\_ / RHVD\_** | Prefijos Oracle: catálogos, roles y vistas de usuario respectivamente. |

## Notas de uso

- Toda la documentación se basa en el código de `gth-ms/` (rama `master`).
- Para el detalle de implementación de una pantalla o endpoint específico, ver el JSP/controller/DAO indicado en cada documento o el repo legacy.
- Los módulos de `02-modules/` describen **qué hace** (funcional) y **cómo lo hace** (técnico).
