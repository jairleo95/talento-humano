# Módulo: Otros (CCosto, Funciones, Vacaciones, Remuneración, Mant_Modulos)

Documentación breve de los módulos de menor tamaño.

## CCosto — Centros de Costos

- **Función**: mantenimiento de centros de costos y su asignación.
- **Controller**: `recruitment/dgp/CentroCostoController` (`centro_costo`) y `recruitment/MCCostoController` (`Costo`).
- **opc=** (`centro_costo`): `Listar_cc`, `Listar_dir`, `Listar_dep`, `Listar_CC`, `Listar_centro_id`, `Listar_centro_id_dgp`, `listCentroCostoByIdContrato`, `Cargar_cc_DGP`, `Lista_cc_area`, `Lista_cc_seccion`, `Eliminar_det_cc`.
- **opc=** (`Costo`): `menu`, `list_ccosto`, `list_ar`, `list_dep`, `list_dir`, `list_se`, `add_cc`, `edit_cc`, `del_cc`, `Asignar_cc`.
- **Vista**: `CCosto/MantCCosto.jsp`.
- **Dominio**: `CostCenter`, `CostCenterDetail`.
- **Migración**: mapea a recruitment-service (`/cost-centers` ya existente).

## Funciones

- **Función**: otorgar funciones a puestos y listar funciones por puesto.
- **Controller**: `recruitment/person/job/CFuncion` (`funcion`).
- **opc=**: `princpal_funcion`, `listar_x_puesto`, `listarF`, `otorgar_funciones`, `otorgar`, `list_pu`, `del_fun`, `edit_function`, `direccion`, `departamento`, `area`, `seccion`.
- **Vistas**: `Funciones/` (3): `Otorgar_funciones`, `List_Funciones`, `Priv_Funciones`.
- **Migración**: parte del dominio de organigrama/puesto.

## Vacaciones

- **Función**: listado de empleados (base para gestión de vacaciones).
- **Vista**: `Vacaciones/Lista_Empleados.jsp` (1).
- **Estado**: funcionalidad mínima; sin controller dedicado relevante.

## Remuneración

- **Función**: filtro de estado remunerativo.
- **Vista**: `Remuneracion/Filt_Est_Remun.jsp`.
- **Estado**: el `RemuneracionController` es un **stub legado** (HttpServlet sin mapeo Spring). Módulo sin implementación funcional.

## Mant_Modulos

- **Función**: mantenimiento de módulos y sus privilegios.
- **Vista**: `Mant_Modulos/Mant_Mod_Privilegio.jsp` + carpeta `Actualizar_ws/`.
- **Controller relacionado**: `user/ModuleController` (`/modules`, `/modulo`).
- **Dominio**: `Modulo`, `Privilegio`.

## Referencia

Código local: `gth-ms/src/main/webapp/WEB-INF/jsp/views/{CCosto,Funciones,Vacaciones,Remuneracion,Mant_Modulos}/`. Repo: [jairleo95/TALENTO_HUMANO](https://github.com/jairleo95/TALENTO_HUMANO).
