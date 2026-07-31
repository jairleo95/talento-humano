# Módulo: Trabajador

## Funcional

Gestiona la **ficha maestra del colaborador**: datos personales, académicos, sociales, familiares y documentos.

**Flujo de negocio**:
1. **Registrar trabajador** (`Reg_Trabajador.jsp`, `Datos_Generales.jsp`): alta del colaborador con datos generales (documento, dirección, nacionalidad, ubigeo).
2. **Aspecto académico** (`Aspecto_Academico.jsp`, `Mod_Aspecto_Academico.jsp`): estudios, carreras, instituciones.
3. **Aspecto social** (`Aspecto_Social.jsp`, `Mod_Aspecto_Social.jsp`): situación social, religión, diezmo.
4. **Familiares** (`Familiar/`): cónyuge, padres/madres, hijos.
5. **Documentos** (`Documento/`, `List_Doc_Trabajador.jsp`): documentos del trabajador.
6. **Detalle / ficha** (`Detalle_Trabajador.jsp`, `Ficha_Trabajador.jsp`): vista consolidada.
7. **Listados** (`List_Dgp_Trabajador.jsp`, `List_Doc_Trabajador.jsp`): DGP y documentos por trabajador.
8. **Edición de perfil** (`editarPerfil`): el propio usuario edita sus datos.

## Técnico

**Controllers**:
- `recruitment/person/PersonController` (`trabajador`).
- `recruitment/person/EmployeeController` (`empleado`) — en parte.
- `recruitment/person/job/FamiliarController` (`familiar`).
- `recruitment/person/job/CCarrera_Institucion` (`detalle_carrera`).
- `recruitment/documents/*` — documentos.
- `user/PhotoController` (`foto`) — foto del trabajador.

**opc= principales** (`PersonController`): `Buscar`, `Buscar_Trabajador`, `Form_Reg`, `Registrar`, `reg_trb`, `list`, `list_reg_tra`, `actualizar`, `Editar_Dat_Gen`, `Modificar_Dat_Gen`, `Editar_Asp_Acad`, `Modificar_Asp_Acad`, `Editar_Asp_Soc`, `Modificar_Asp_Soc`, `Listar_Asp_Social`, `Editar_Asp_Rel`, `Modificar_Asp_Rel`, `Documento_Trabajador`, `Val_num_Doc`, `validar_cod_uni`, `ShowAPS`, `ShowAFP_SP`, `ShowEsDiezmoTrabajador`, `ShowPorcentageTrabajador`, `UpdateEsDiezmo`, `ModDiezmoDetalleTrabajador`, `getTiHoraPago`, `Mostrar_Cod_APS`, `Form_Cambiar_Clave`, `edit_perfil`, `aut`, `reg_aps_masivo`, `registrar_huella`. Sub-rutas POST: `diezmo`, `afp`, `tiHoraPago`.

**FamiliarController opc=**: `Registrar Conyugue`, `Registrar Padres`, `Detalle_Familiar`, `Listar_Hijo_id_tr`, `REGISTRAR HIJO`, `modificar`, `eliminar`, `MODIFICAR HIJO`, `Editar_Familiar`, `Modificar_Padre_madre`, `List_Padre`, `Modificar_Padres`, `MODIFICAR_PMC`.

**Entidades de dominio**: `Trabajador`, `Employee`, `V_Trabajador`, `Datos_Generales`, `Direccion`, `Nacionalidad`, `Tipo_Documento`, `Via`, `Zona`, `Ub_Departamento`, `Ub_Provincia`, `Ub_Distrito`, `Datos_Hijo_Trabajador`, `Padre_Madre_Conyugue`, `Hist_Estado_Civil`, `Regimen_Laboral`, `Situacion_Educativa`, `Documentos`, `Lis_Doc_tra`, `Fotos_Trabajador`, `V_Ficha_Trab_Num_C`, `V_Documento_Trabajador`.

**JSPs**: `Trabajador/` (22 archivos): `Reg_Trabajador`, `Datos_Generales`, `Mod_Datos_Generales`, `Aspecto_Academico`, `Mod_Aspecto_Academico`, `Aspecto_Social`, `Mod_Aspecto_Social`, `Detalle_Trabajador`, `Ficha_Trabajador`, `List_Dgp_Trabajador`, `List_Doc_Trabajador`, `Familiar/` (Reg_Conyugue, Reg_Padres, Reg_Datos_Hijo, Mod_Datos_Hijos, List_Hijo, Detalle_Familiar, Mod_Familiar, Mod_Padre_Madre_Conyugue, Busc_Conyugue), `Documento/`, `Historial_Religion/`.

**JS de negocio**: `static/js/businessLogic/Trabajador/`.

## Notas de migración

- Dominio amplio aún sin microservicio destino; candidato a un servicio de "persona/trabajador" o a ampliar identity-service.

## Referencia

Código local: `gth-ms/src/main/java/com/app/controller/recruitment/person/`; `gth-ms/src/main/webapp/WEB-INF/jsp/views/Trabajador/`. Repo: [jairleo95/TALENTO_HUMANO](https://github.com/jairleo95/TALENTO_HUMANO).
