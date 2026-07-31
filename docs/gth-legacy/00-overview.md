# 00 — Visión General

## Qué es GTH

**GTH (Gestión del Talento Humano)** es la aplicación web de Recursos Humanos de la **UPeU (Universidad Peruana Unión)**. Gestiona el ciclo de vida laboral del colaborador: ficha maestra, requerimientos de contratación (DGP), autorizaciones, contratos, presupuesto, carga académica, evaluaciones y reportes.

## Stack tecnológico

| Capa | Tecnología |
|---|---|
| Backend | Spring Boot **2.7.5**, Java **11**, Spring MVC |
| Frontend | **JSP** (147 páginas) + plantilla **SmartAdmin** + jQuery **2.0.2** |
| Base de datos | **Oracle** (driver `ojdbc7`), sin pool (conexión por operación) |
| Autenticación | Sesión HTTP manual (Spring Security desactivado) |
| Config | Spring Cloud Config Server (`http://localhost:8888`) |
| Real-time | WebSocket JSR-356 `/serverGth` |
| Integración | WebService SOAP académico (`webapp.upeu.edu.pe`), correo `javax.mail` |
| Build | Gradle (wrapper), contexto de app `/gth`, puerto `8080` |

## Cifras clave

| Métrica | Valor |
|---|---|
| Archivos de controller | 64 (52 controllers reales) |
| Entidades de dominio | 118 |
| DAOs (implementación) | 56 |
| Interfaces de DAO | 55 |
| Páginas JSP | 147 |
| Módulos de negocio | 19 directorios de vistas |
| Stored procedures | `RHSP_*` (usuario, clave, perfil) |
| Cliente WebService | académico (carga de docentes) |

## Módulos de negocio (resumen)

| Módulo | Propósito |
|---|---|
| **Dgp / Requerimiento** | Núcleo: requerimientos de puestos y su flujo (registrar → autorizar → procesar → seguimiento) |
| **Contrato** | Registro/edición de contratos, plantillas, impresión, adjuntos, casos especiales |
| **Trabajador** | Ficha maestra del colaborador (datos generales, académicos, sociales, familiares, documentos) |
| **Presupuesto** | Gestión de presupuesto de puestos, solicitudes fuera de presupuesto (SFP) |
| **Proceso** | Configuración del motor de flujos (procesos, pasos, tipos de requerimiento) |
| **Puesto** | Organigrama: direcciones, departamentos, áreas, secciones, puestos |
| **Usuario / Seguridad** | Usuarios, roles, privilegios, módulos, perfil, foto |
| **Académico** | Carga académica de docentes (sincronizado con WS externo) y pago docente |
| **Reportes** | Reportes en JSP/HTML con DataTables y jsPDF |
| **Empleado** | Evaluación de empleados |
| **Formato_Horario / Horario** | Formatos de horario y turnos |
| **Solicitud** | Solicitudes de requerimiento |
| **Otros** | CCosto, Funciones, Vacaciones, Remuneración, Mant_Modulos |

## Convenciones de nombres en el dominio

- `V_*` → vistas Oracle (`rhvd_usuario`, `V_Privilegio`, `V_Usuario`, etc.)
- `X_*` → mapeos/consultas de pantallas y reportes (`X_List_dgp_by`, `X_List_det_dgp`)
- `I*DAO` / `Interface*DAO` → contratos de persistencia; `*DAO` → implementación

## Repos / referencias

- Repo legacy: [jairleo95/TALENTO_HUMANO](https://github.com/jairleo95/TALENTO_HUMANO)
- Código local: `gth-ms/` (este monorepo)
- Para el detalle técnico de cada módulo, ver [02-modules/](02-modules/README.md) y [03-api-endpoints.md](03-api-endpoints.md).
