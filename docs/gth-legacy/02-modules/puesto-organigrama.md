# Módulo: Puesto / Organigrama

## Funcional

Mantiene la **estructura organizacional** de la institución y las **funciones por puesto**.

**Flujo de negocio**:
1. **Jerarquía organizacional**: Dirección → Departamento → Área → Sección → Puesto.
2. **Mantenimiento** (`Menu_puesto.jsp`, `Mant_Direccion.jsp`, `Mant_Departamento.jsp`, `Mant_Area.jsp`, `Mant_Seccion.jsp`, `Mant_Puesto.jsp`): CRUD de cada nivel del organigrama.
3. **Funciones por puesto** (`Funciones/Otorgar_funciones.jsp`, `List_Funciones.jsp`, `Priv_Funciones.jsp`): otorgar/editar funciones a un puesto.
4. **Ubigeo**: listados de departamento/provincia/distrito.
5. **Direcciones de puesto**: relación dirección-filial, puestos por sección, paso de puestos.

## Técnico

**Controllers** (`recruitment/person/job/`):
- `JobController` (`Puesto`).
- `CDir_Puesto` (`Direccion_Puesto`).
- `CFuncion` (`funcion`).
- `CUbigeo` (`ubigeo`).

**opc= principales**:
- `JobController`: `menu`, `listar_dep_dir`, `crear/editar/activar/desactivar/eliminar` para `Dep`, `Direccion`, `area`, `seccion`, `puesto`; `list_dep_es`, `list_area_dep`, `list_area_es`, `list_sec_area`, `list_sec_es`, `list_puesto_sec`, `listar_dir_es`, `list_direccion`.
- `CDir_Puesto`: `Listar`, `Listar_dir`, `Listar_Dir`, `Listar_area`, `Listar_sec`, `Listar_sec2`, `Listar_pu_id`, `Reg_puesto_paso`, `Listar_SUB_MO`, `List_Area_RDGP`, `list_req`, `Listar_dir_dep`, `Listar_direccion_filial`, `getDirections`.
- `CFuncion`: `princpal_funcion`, `listar_x_puesto`, `listarF`, `listar_Direccion`, `otorgar_funciones`, `otorgar`, `list_pu`, `del_fun`, `edit_function`, `direccion`, `departamento`, `area`, `seccion`.
- `CUbigeo`: `dep_nac`, `pro_nac`, `Listar_D`, `Listar_P`, `Listar_Di`.

**Entidades de dominio**: `Job`, `Area`, `Department`, `Seccion`, `Direccion`, `Funciones`, `Grupo_Ocupaciones`, `V_Area_Pu`, `V_Puesto_Direccion`, `V_Puesto_Aut`, `V_Emp_Pu_Dir_Dep`, `V_Emp_Puesto`, `V_Ubigeo`.

**JSPs**: `Puesto/` (6): `Mant_Area`, `Mant_Departamento`, `Mant_Direccion`, `Mant_Puesto`, `Mant_Seccion`, `Menu_puesto`; `Funciones/` (3).

**JS de negocio**: `static/js/businessLogic/` (módulos de organigrama y funciones).

## Notas de migración

- Dominio transversal usado por trabajador, presupuesto y DGP. Sin microservicio destino aún.

## Referencia

Código local: `gth-ms/src/main/java/com/app/controller/recruitment/person/job/`; `gth-ms/src/main/webapp/WEB-INF/jsp/views/Puesto/`. Repo: [jairleo95/TALENTO_HUMANO](https://github.com/jairleo95/TALENTO_HUMANO).
