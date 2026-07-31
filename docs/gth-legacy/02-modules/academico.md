# Módulo: Académico

## Funcional

Gestiona la **carga académica de los docentes** y el **pago docente**, sincronizado con el sistema académico externo (UPeU).

**Flujo de negocio**:
1. **Carga académica** (`Carga_Academica/Rep_Carga_Academica.jsp`): registro y procesamiento de la carga de cursos por docente.
2. **Autorización de carga** (`Autorizar_Carga_Academica.jsp`): flujo de aprobación (también desde la bandeja `academicImbox`).
3. **Horario de cursos** (`Carga_Academica/horarioCursosAcademico.html`).
4. **Sincronización con WS externo** (`List_ws`, `statusSyncUpCargaAcademica`, `stopSyncUpCargaAcademica`): trae datos del sistema académico.
5. **Pago docente** (`pago_docente`): cuotas y detalle de pago por docente.

## Técnico

**Controllers** (`recruitment/academicCharge/`):
- `CargaAcademicaController` (`carga_academica`).
- `PagoDocenteController` (`pago_docente`).

**opc= principales**:
- `CargaAcademicaController`: GET `getDetCargaAcademica`, `validateTrabajador`, `listCargaAcademica`; POST `Registrar_CA`, `Procesar`, `Completar_Datos`, `initUpdateCAData`, `updateCAData`, `horarioCursosAcademico`, `List_ws`, `listEsCargaAcademica`, `getProcesoCargaAcademicaById`, `statusSyncUpCargaAcademica`, `stopSyncUpCargaAcademica`.
- `PagoDocenteController`: `Listar_Cuotas`, `getPagoDocenteHtml`.

**Entidades de dominio**: `AcademicCharge`, `ProcesoCargaAcademica`, `PagoDocente`, `Periodo_Pago`, `TipoHoraPago`, `Carrera`, `Universidad`, `Universidad_Carrera`, `Modalidad`, `Sub_Modalidad`, `SituacionEspecial`, `Tipo_Institucion`, `SalaryAccount`, `V_Detalle_Carga_Academica`, `Renombrar`, `Auto_Mostrar`.

**JSPs**: `Academico/` (2): `Autorizar_Carga_Academica`, `Carga_Academica/`.

**Integración**: `WebServiceClient` (SOAP `webapp.upeu.edu.pe`), claves `keyApp`/`keyID`.

## Notas de migración

- Sin microservicio destino aún; depende de la integración SOAP con el sistema académico.

## Referencia

Código local: `gth-ms/src/main/java/com/app/controller/recruitment/academicCharge/`; `gth-ms/src/main/webapp/WEB-INF/jsp/views/Academico/`. Repo: [jairleo95/TALENTO_HUMANO](https://github.com/jairleo95/TALENTO_HUMANO).
