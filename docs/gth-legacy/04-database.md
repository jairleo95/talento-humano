# 04 — Base de Datos (Oracle)

## Contexto

- **Motor**: Oracle (SID `xe`), host `DESKTOP-MS5NN2M`, puerto `1521`.
- **Usuario/credenciales**: `procesosrh` / `gestionrrhh` (hardcodeadas en `gth-ms/src/main/java/com/app/config/globalProperties.java`).
- **Driver**: `ojdbc7.jar` (lib local `gth-ms/libs/`).
- **Conexión**: `FactoryConnectionDB.open(FactoryConnectionDB.ORACLE)` — sin pool, una conexión por operación.

## Convenciones de esquema

| Prefijo | Tipo | Ejemplos |
|---|---|---|
| `RHTC_*` | Catálogos | tablas de catálogos |
| `RHTR_*` | Roles | tablas de roles/relaciones |
| `RHVD_*` | Vistas | `rhvd_usuario`, `rhvd_var_usuario` |
| `RHSP_*` | Stored procedures | `RHSP_INSERT_USUARIO`, `RHSP_MOD_USUARIO_CL`, `RHSP_ELIMINAR_USUARIO`, `RHSP_MOD_PERFIL`, `RHSP_ACTIVAR/DESACTIVAR_USUARIO` |

## Modelo de dominio vs. esquema

El paquete `domain/model/` (118 entidades) refleja las tablas/vistas:

- **Núcleo RH**: `Trabajador`, `Employee`, `User`, `V_Usuario`, `V_Trabajador`, `Evaluacion_Emp`, `Fotos_Trabajador`, `Datos_Hijo_Trabajador`, `Padre_Madre_Conyugue`, `Datos_Generales`, `Direccion`, `Documentos`, `Nacionalidad`, `Tipo_Documento`, `Via`, `Zona`, `Ub_Departamento`, `Ub_Provincia`, `Ub_Distrito`, `Hist_Estado_Civil`, `Regimen_Laboral`, `Situacion_Educativa`
- **DGP / Requerimientos**: `DGP`, `Autorizacion`, `Comentario_DGP`, `Detalle_Pasos`, `Detalle_Req_Proceso`, `Pasos`, `Proceso`, `Requerimiento`, `Detalle_Privilegio` + vistas `V_*`/`X_*`
- **Contratos**: `Contract`, `Tipo_Contrato`, `Contrato_Adjunto`, `Plantilla_Contractual`, `Plantilla_Puesto`
- **Puestos / Organigrama**: `Job`, `Area`, `Department`, `Seccion`, `Funciones`, `Grupo_Ocupaciones`
- **Horarios**: `Horario`, `Detalle_Horario`, `Formato_Horario`, `Tipo_Horario`
- **Académico**: `AcademicCharge`, `ProcesoCargaAcademica`, `PagoDocente`, `Periodo_Pago`, `Carrera`, `Universidad`, `SituacionEspecial`, `SalaryAccount`
- **Presupuesto / CCosto**: `CostCenter`, `CostCenterDetail`
- **Seguridad**: `Rol`, `Modulo`, `Privilegio`, `Privilegio_Rol`, `Notification`

## Stored procedures principales (`RHSP_*`)

| SP | Función |
|---|---|
| `RHSP_INSERT_USUARIO` | Alta de usuario |
| `RHSP_MOD_USUARIO_CL` | Modificación de clave |
| `RHSP_ELIMINAR_USUARIO` | Baja de usuario |
| `RHSP_MOD_PERFIL` | Edición de perfil |
| `RHSP_ACTIVAR_USUARIO` / `RHSP_DESACTIVAR_USUARIO` | Habilitar/deshabilitar acceso |

## Scripts SQL

En `gth-ms/sql/`:
- `step-0-script-bd-user.txt`
- `step-1-export_12_03_2016.sql`
- `step-2-update-Jair08-11.sql`
- `step-3-Update for academic submodule.sql`
- `backup/` (dumps como `detalletrabajador.txt`)
- `execution-sql.log`, `cambios en la bd RH`

## Estilo de acceso (DAO)

- Cada DAO abre su conexión al inicio del método y la cierra en `finally`.
- Consultas: `Statement` con **SQL concatenado** (`conn.query(sql)`) — riesgo de inyección, no parametrizado.
- Inserts/updates de usuario: `CallableStatement` a los SPs `RHSP_*`.
- No hay datasource Spring; la conexión la entrega `ORACLEConnectionDB`.

## Referencia

Para los scripts completos y definiciones: [jairleo95/TALENTO_HUMANO](https://github.com/jairleo95/TALENTO_HUMANO) → carpeta `gth-ms/sql/` (local) y `gth-ms/src/main/java/com/app/persistence/dao/`.
