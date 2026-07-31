# Módulo: Empleado / Evaluación

## Funcional

Gestiona la **evaluación de empleados** y datos asociados (APS, huella, diezmo).

**Funcionalidades**:
1. **Filtro de empleados** (`Empleado/Filtro_Empleado.jsp`): búsqueda.
2. **Evaluación** (`Empleado/Evaluacion_Empleado.jsp`, `List_Evaluacion_Emp.jsp`, `Mod_Evaluacion_Emp.jsp`): registrar, listar y modificar evaluaciones.
3. **Validación de datos** (`validar_aps`, `validar_huella`): verificación de código APS y huella.
4. **Registro masivo** (`reg_aps_masivo`, `reg_huella`).
5. **Listados** (`getAllEmployees`, `getAllEmployeesWithOutUserAccount`).

## Técnico

**Controller**: `recruitment/person/EmployeeController` (`empleado`).

**opc= principales**: `Eva_Emp`, `Reg_Evaluar_Emp`, `Editar`, `modificar`, `Reporte`, `getAllEmployees`, `getAllEmployeesWithOutUserAccount`, `validar_aps`, `validar_huella`, `ShowHuella`, `ShowAPS`, `reg_huella`, `reg_aps`.

**Entidades de dominio**: `Employee`, `Evaluacion_Emp`, `V_List_Empleado`, `X_Lis_Empleados`, `V_Emp_Puesto`.

**JSPs**: `Empleado/` (4).

## Notas de migración

- Sin microservicio destino aún; dominio cercano a "trabajador" (posible servicio de persona).

## Referencia

Código local: `gth-ms/src/main/java/com/app/controller/recruitment/person/EmployeeController.java`; `gth-ms/src/main/webapp/WEB-INF/jsp/views/Empleado/`. Repo: [jairleo95/TALENTO_HUMANO](https://github.com/jairleo95/TALENTO_HUMANO).
