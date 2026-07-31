# Módulo: Reportes

## Funcional

Genera **reportes del personal** en HTML/PDF (client-side con DataTables + jsPDF), además de **historiales** de datos del trabajador y **validación de fotos**.

**Reportes disponibles** (`Reportes/`):
- APS (`Reporte_APS.jsp`)
- Consejo universitario (`Reporte_consejo_universitario.jsp`)
- Contratos (`Reporte_contratos.jsp`)
- Cuenta sueldo (`Reporte_cuenta_sueldo.jsp`)
- Cumpleaños (`Reporte_Cumpleanos.jsp`)
- Datos de estudios (`Reporte_Datos_Estudios_T.jsp`), datos generales (`Reporte_Datos_Generales.jsp`), datos de hijos (`Reporte_Datos_Hijos.jsp`)
- Establecimientos
- Padres/madres (`Reporte_Padres_Madres.jsp`)
- Navidad (`Reporte_Navidad.jsp`)
- Períodos, MINTRA, registro
- Hijos (`RTHijo.jsp`)
- Historiales: `Hijo/`, `Trabajador/`
- Validación de fotos (`Validar_Foto/`)

**Funcionalidades de historial** (`RHistorial`): historial de trabajador, estado civil, datos de hijos, comparación de datos.

## Técnico

**Controllers** (`report/`):
- `ReporteController` (`reporte`).
- `ReporteHijoController` (`CReporte_Hijo`).
- `ReporteHistorialController` (`RHistorial`).

**opc= principales**:
- `ReporteController`: `reporte1`, `reporte_hijo`, `reporte_padre_hijo`, `reporte_cumpleaños`, `reporte_t_navidad`, `reporte_datos_genereales`, `Reporte_padres_madres`, `Reporte_datos`, `list_da`, `searchtb`, `Reporte_Datos_Hijos`, `Reporte_Datos_cumpl`, `Reporte_Navidad` (algunos con `sendRedirect` a JSP).
- `ReporteHijoController`: `reporte_hijos`.
- `ReporteHistorialController`: `hist_tra`, `list_mod_fecha`, `mod_tra`, `list_mod_tra`, `hist_es_civil`, `list_hist_es_civil`, `Procesar_reg_ec`, `Detalle_hist_ec`, `list_detalle_ec`, `Filtro_hijo`, `Fe_Modif_Hijo`, `Fe_Modif_Hijo2`, `Comparar_dato_Hijo`, `list_hist_fecha`, `list_actual`, `Comparar_hijo`, `Listar_hijo_trabajador`, `Procesar_datos_hijos`, `Historial_Datos_Hijo`, `proc_hist`, `proc_act`.

**Entidades de dominio**: `Hist_Estado_Civil`, `Datos_Hijo_Trabajador`, `V_Det_DGP`, mapeos `X_*` de reportes.

**JSPs**: `Reportes/` (24 archivos, mayor módulo en cantidad de vistas).

**Herramientas frontend**: DataTables (botones copy/xls/print), jsPDF, alto contraste.

**PhotoValidationController** (`validar_foto`): `Index` (redirect a `views/Reportes/Validar_Foto/Index.html`), `getFotos`, `validar`.

## Notas de migración

- Los reportes son HTML/DataTables/jsPDF client-side; en el stack nuevo deben definirse (PDF server-side o impresión de componentes).

## Referencia

Código local: `gth-ms/src/main/java/com/app/controller/report/`; `gth-ms/src/main/webapp/WEB-INF/jsp/views/Reportes/`. Repo: [jairleo95/TALENTO_HUMANO](https://github.com/jairleo95/TALENTO_HUMANO).
