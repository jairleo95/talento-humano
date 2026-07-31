# 06 — Frontend (JSP + SmartAdmin)

## Stack

- **147 páginas JSP** en `gth-ms/src/main/webapp/WEB-INF/jsp/views/`.
- **Plantilla**: SmartAdmin (jQuery 2.0.2, Bootstrap 3/4, Font Awesome), CSS/JS en `gth-ms/src/main/resources/static/`.
- **Sin tooling**: no hay `package.json`, bundler, TypeScript ni tests en `gth-ms`.

## Estructura de recursos

```
gth-ms/src/main/resources/static/
├── css/          # SmartAdmin (smartadmin-production*.css, bootstrap.min.css)
├── js/
│   ├── libs/     # jQuery 2.0.2, jQueryUI 1.10.3
│   ├── bootstrap/ plugin/ smartwidgets/  # plugins de SmartAdmin
│   ├── businessLogic/   # LÓGICA DE NEGOCIO (21 carpetas por módulo)
│   └── app.config.js, app.min.js
├── img/ fonts/ sound/ ajax/ Modulo/
gth-ms/src/main/webapp/WEB-INF/jsp/views/   # vistas por módulo
```

## Modelo de ejecución

- **`index.jsp`**: login.
- **`Principal.jsp`**: "shell" SPA — header, menú lateral dinámico por privilegios (`V_Privilegio`), breadcrumb, footer.
- **`Modulos.jsp`**: selector de módulos autorizados.
- **Carga de vistas**: `Principal.js` hace `loadURL(href, $(".newContent"))` — el contenido de cada módulo se inyecta por AJAX en `#content`. No hay router de estado real; el "enrutamiento" es la URL del controller + `opc=`.
- **Datos**: cada JSP embebe HTML + JS + datos de sesión (`<%= session.getAttribute("IDUSER") %>`) y hace `$.post("controller", "opc=...&param=...")` a su controller, que responde JSON.

## JavaScript de negocio (`static/js/businessLogic/`)

| Carpeta | Módulo |
|---|---|
| `Principal.js` | Shell, websocket, notificaciones, carga de URLs |
| `Dgp/` | Requerimiento: `editDGP.js`, `js_dgp_aut.js`, `procesarRequerimiento.js`, `statusProcessDGP.js`, subcarpetas `Detalle/`, `Registrar/` |
| `Academico/` | Carga académica |
| `Autorizacion/` | Bandeja de autorizaciones |
| `CentroCosto/` | Centros de costo |
| `Contrato/` | Contratos |
| `Empleado/` | Evaluaciones |
| `Funciones/` | Funciones por puesto |
| `Horario/` | Horarios |
| `Trabajador/` | Ficha del trabajador |
| `Js_Formulario/`, `Js_Hist_Mod/`, `Js_Modulos/`, `Js_Sort/`, `Js_Validar/` | Utilidades JS |
| Otros | `coment`, `Foto`, `Hijos`, `Modulo`, `Plazo`, `Proceso` |

## Reportes

- Reportes en JSP/HTML con **DataTables** (botones copy/xls/print) y **jsPDF** client-side (conversión HTML→PDF).
- Vistas en `WEB-INF/jsp/views/Reportes/` (Reporte_APS, Reporte_contratos, Reporte_cumpleaños, etc.).

## Notas para la migración

- El frontend legacy **no tiene contrato API consumible**: depende de la sesión y de endpoints `opc=` acoplados a JSP.
- La lógica de validación/negocio está en `businessLogic/*.js` (jQuery puro), sin framework.
- Al migrar a un SPA moderno, cada módulo deberá re-expresarse contra la API de los microservicios nuevos (ver [08-mapeo-microservicios.md](08-mapeo-microservicios.md)).

## Referencia

Código local: `gth-ms/src/main/webapp/` y `gth-ms/src/main/resources/static/`. Repo: [jairleo95/TALENTO_HUMANO](https://github.com/jairleo95/TALENTO_HUMANO).
