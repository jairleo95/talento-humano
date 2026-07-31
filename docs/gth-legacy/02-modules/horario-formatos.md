# Módulo: Horario / Formatos

## Funcional

Mantiene los **formatos de horario** y los **horarios por puesto** que se usan en los requerimientos (DGP).

**Funcionalidades**:
1. **Formatos de horario** (`Formato_Horario/List_Formato_Horario.jsp`, `Reg_Formato_Horario.jsp`, `Detalle_Formato_Horario.jsp`): crear, editar y ver formatos; registrar turnos por día (lun-dom) con horas desde/hasta; cálculo de horas semanales totales.
2. **Horarios por requerimiento** (`Dgp/Horario/Reg_Horario.jsp`, `Detalle_Horario.jsp`): horarios asociados a un DGP.
3. **Tipos de horario** (`Listar_Tip_Horario`).

**Regla de negocio notable**: el JS (`editDGP.js`) calcula horas semanales sumando turnos por día (`HORA_DESDE_<dia><n>` / `HORA_HASTA_<dia><n>`) y valida el acumulado (máx 2880 min = 48 h).

## Técnico

**Controllers**:
- `recruitment/dgp/FormatoHorarioController` (`schedule`).
- `recruitment/dgp/HorarioController` (`horario`).

**opc= principales**:
- `FormatoHorarioController`: `LISTAR_FORMATO_HORARIO`, `REGISTRAR_FORMATOS`, `REGISTRAR_FOR_HORARIO`, `GuardarFH`, `GuardarFHAdmin`, `ultimo_fh`, `editar_fh`, `eliminar_fh`, `Eliminar_turno`, `Listar_Horario`, `Listar_Tip_Horario`, `Listar_Horas_horario`, `statupdate`, `cargar_dep`, `LFH`, `registrar`.
- `HorarioController`: `REGISTRAR HORARIO`, `Listar`, `Listar2`, `listaHorario`.

**Entidades de dominio**: `Horario`, `Detalle_Horario`, `Formato_Horario`, `Tipo_Horario`, `V_Horario`.

**JSPs**: `Formato_Horario/` (3) + `Dgp/Horario/` (2).

**JS de negocio**: `static/js/businessLogic/Dgp/editDGP.js` (cálculo de horas, manejo de turnos).

## Notas de migración

- Sin microservicio destino aún; dominio candidato a incluirse en recruitment-service (formatos/horarios de puesto).

## Referencia

Código local: `gth-ms/src/main/java/com/app/controller/recruitment/dgp/FormatoHorarioController.java`; `gth-ms/src/main/webapp/WEB-INF/jsp/views/Formato_Horario/`. Repo: [jairleo95/TALENTO_HUMANO](https://github.com/jairleo95/TALENTO_HUMANO).
