# 03 — Inventario de Endpoints (Controllers y `opc=`)

Todos los controllers viven en `gth-ms/src/main/java/com/app/controller/`. La mayoría son `@RestController` que devuelven JSON según un parámetro de cadena `opc=...` (los JSP llaman a estos endpoints con `$.post("contrato", "opc=Buscar&...")`). Solo 3 devuelven `ModelAndView` (MainController, MenuController, UserController).

## 3.1 Paquete raíz

### MainController — Router de vistas JSP

`@RestController` sin `@RequestMapping` de clase. Devuelve `ModelAndView`:

| Ruta | Vista |
|---|---|
| `/` | `index` |
| `/Requerimiento` | `views/Requerimiento.html` |
| `/Reporte_Carga_Academica` | `Academico/Carga_Academica/Rep_Carga_Academica` |
| `/Buscar_Trabajador` | `Trabajador/Bus_Trabajador` |
| `/paso`, `/Mant_Paso` | `views/Proceso/Mant_Paso` |
| `/Mant_Proceso` | `views/Proceso/Mant_Proceso` |
| `/Detalle_Trabajador` | `Trabajador/Det_Trabajador` |
| `/formato_plantilla` | `Contrato/Formato_Plantilla` |
| `/List_req_incompl` | `Dgp/List_Req_incompl` |
| `/List_Dgp` | `Dgp/List_Dgp` |
| `/person` | `Trabajador/Reg_Trabajador` |
| `/durations` | `Plazo/List_Plazo` |
| `/formato_horario` | `views/Proceso/Formato_Horario` |
| `/Proceso_Dgp` | `Dgp/Proceso_Dgp` |
| `/imbox` | `Bandeja` |
| `/generate` | `Contrato/Gen_Contrato` |
| `/Reg_Dgp` | `Dgp/Reg_Dgp` |
| `/Reg_Usuario` | `Usuario/Reg_Usuario` |
| `/Filtro_Empleado` | `Empleado/Filtro_Empleado` |
| `/Procesar_Req` | `Requerimiento/Procesar_Req` |
| `/Autorizar_Carga_Academica` | `Autorizar_Carga_Academica` |
| `/Validar_Foto` | `Validar_Foto` |
| `/Gen_Contrato_CE`, `/Filtro_Contrato_CE` | `Contrato/Gen_Contrato_CE`, `Contrato/Filtro_Contrato_CE` |
| `/hist_tra`, `/Historial_Est_Civil` | `Historial/hist_tra`, `Historial/Hist_Est_Civil` |
| `/Reg_Contrato`, `/Detalle_Info_Contractualq`, `/Busc_Contrato` | `Contrato/Reg_Contrato`, `Contrato/Detalle_Info_Contractual`, `Contrato/Busc_Contrato` |
| `/Detalle_Dgp` | `Dgp/Detalle_Dgp` |
| `/Reg_Documento` | `Documento/Reg_Documento` |
| `/MantCCosto` | `Centro_Costo/MantCCosto` |

## 3.2 Paquete `inbox` — Bandeja de autorizaciones

| Controller | Mapeo | opc= | Retorno |
|---|---|---|---|
| AcademicImboxController | `academicImbox` | `List_Dgp_Aut`, `Autorizacion_CD`, `ListProcesarReq`, `ShowListProcesarReq`, `ShowCkbEstado_procesarIndiviual`, `UpdateStatusDgp_Procesar`, `ValBtnAutorizacion`, `Enviar_Correo`, `mens_cod_aps`, `mens_cod_huella`, `headerTableAutorizacionCA` | JSON |
| AuthorizationController | `imbox` (POST `core`) | `Aceptar`, `Rechazar`, `HDGP`, `eliminarDGP` | JSON |
| ProcessStatusController | `process/status`, `process/{id}/status` | — (body `ProcessStatusRequest`) | `ProcessStatusResponse` |
| MailController | — | — | HttpServlet legado (test) |

DTOs de soporte: `GetProcessStatusRequest`, `Permission`, `ProcessStatusRequest`, `ProcessStatusResponse`, `UserSession`.

## 3.3 Paquete `notify` — Notificaciones

| Controller | Mapeo | Parámetro | Casos | Retorno |
|---|---|---|---|---|
| NotificationController | `cnot` (POST) | `op` (int) | 1/3 listar, 2 marcar leída, 4 marcar visualizadas, 5 contar no leídas | JSON |
| PushNotification | `@ServerEndpoint("/serverGth")` | — | WebSocket broadcast | — |

## 3.4 Paquete `process` — Motor de procesos

| Controller | Mapeo | Métodos / opc= |
|---|---|---|
| ProcessController | `Proceso` | GET listado, `details`, `all`; POST `createee`, `register` → `Mantenimiento`, `Eliminar`, `statupdate`, `insertDetalleReqProceso` |
| PhaseController | `steps` | GET listado, `{id}/jobs`; POST `Registrar`, `Update_nu_paso`, `Modificar`, `Eliminar_PP`, `actualizar_estado`, `Eliminar` |
| CommentaryController | `comentario` | POST `COMENTAR` (HTML), `Comentar_Dgp`, `list` (JSON) |
| TermController (`deliveryTerm`) | `plazo_dgp` | GET `Listar`, `Listar_Plazo`, `Ver_detalle_plazo`, `List_id_plazo`, `fecha_habilitada`, `Mantenimiento`; POST `Registrar`, `Modificar`, `Eliminar` |

## 3.5 Paquete `recruitment`

### academicCharge
| Controller | Mapeo | opc= |
|---|---|---|
| CargaAcademicaController | `carga_academica` | GET `getDetCargaAcademica`, `validateTrabajador`, `listCargaAcademica`; POST `Registrar_CA`, `Procesar`, `Completar_Datos`, `updateCAData`, `horarioCursosAcademico`, `List_ws`, `listEsCargaAcademica`, `getProcesoCargaAcademicaById`, `statusSyncUpCargaAcademica`, `initUpdateCAData`, `stopSyncUpCargaAcademica` |
| PagoDocenteController | `pago_docente` | POST `Listar_Cuotas`, `getPagoDocenteHtml` |

### contract
| Controller | Mapeo | opc= |
|---|---|---|
| ContractController | `contrato` | GET `Detalle_Contractual`, `SelectorListaContrato`, `List_ti_contrato`, `LIST_FORMULARIO`, `Ver_Plantilla`, `casos_especiales`, `REG_CASOS_ESP`; POST `Buscar`, `filtrar`, `enviar`, `Editar`, `MODIFICAR CONTRATO`, `SI_CONNTRATO`, `Subir_Contrato`, `Subir_Contrato2`, `Actualizar_Firma`, `actualizar`, `REGISTRAR CONTRATO`, `Habilitar_is`, `validar_contrato`, `gen_cont`, `Reporte_CE` |
| ContratoAdjuntoController | `contrato_archivo_adjunto` | POST (adjuntos) → HTML |
| MassivePrintController | `impresion_masiva` | POST `filtrar` |
| PrintController | `imprimir` | POST `Imprimir`, `Imprimir1`, `Listar_contrato` |
| PlantillaContractualController | `plantilla_contractual` | POST `List_planti`, `Listpuesto`, `cargar`, `Imprimir` |

### dgp
| Controller | Mapeo | opc= |
|---|---|---|
| DGPController | `dgp` | GET listado; POST `Listar_Req`, `Listar_Datos`, `Detalle`, `Incompleto`, `Listar`, `Imprimir_det_proceso`, `List_Dgp_Tr`, `Seguimiento`, `SeguimientoH`, `Val_Fe_Inicio`, `filtrar`, `User_Aut`, `Proceso`, `rd`, `RegDGPAditionalPermissions`, `Registrar`, `Reg_form`, `Reg_renuncia`, `Terminar`, `MODIFICAR REQUERIMIENTO`, `Modificar` |
| CentroCostoController | `centro_costo` | POST `Listar_cc`, `Listar_dir`, `Listar_dep`, `Listar_CC`, `Listar_centro_id`, `Listar_centro_id_dgp`, `listCentroCostoByIdContrato`, `Cargar_cc_DGP`, `Lista_cc_area`, `Lista_cc_seccion`, `Eliminar_det_cc` |
| FormatoHorarioController | `schedule` | POST `registrar`, `GuardarFH`, `GuardarFHAdmin`, `LFH`, `ultimo_fh`, `editar_fh`, `eliminar_fh`, `REGISTRAR_FORMATOS`, `REGISTRAR_FOR_HORARIO`, `LISTAR_FORMATO_HORARIO`, `Listar_Tip_Horario`, `statupdate`, `Eliminar_turno`, `Listar_Horas_horario`, `Listar_Horario`, `cargar_dep` |
| HorarioController | `horario` | POST `REGISTRAR HORARIO`, `Listar`, `Listar2`, `listaHorario` |
| SituacionEspecialController | `SituacionEspecial` | POST `list` |

### documents
| Controller | Mapeo | opc= |
|---|---|---|
| DocumentController | `documents` | GET `Ver_Documento`, `ReqIncompletoNextStep`; POST `upload` (multipart), `Eliminar` |
| DocumentoAdjuntoController | `documento_adjunto` | POST `Eliminar` → HTML |
| DocumentroTrabajadorController | `documento_trabajador` | POST `Ver_Documento`, `Reg_Pro_Dgp`, `Eliminar` → HTML |

### editor
| Controller | Mapeo | opc= |
|---|---|---|
| ContractTemplateController | `templates` | GET `Listar`, `Listar2`, `List_Plamtillas`; POST `Asignar`, `asignar`, `Actualizar`, `UpdateNameFile`, `activar_pp`, `Desactivar_pp`, `Crear_Plantilla` |

### raíz recruitment
| Controller | Mapeo | opc= |
|---|---|---|
| MCCostoController | `Costo` | `menu`, `list_ccosto`, `list_dir`, `Asignar_cc`, `list_dep`, `list_ar`, `list_se`, `edit_cc`, `add_cc`, `del_cc` |
| PresupuestoController | `presupuesto` | `gest`, `solfpview`, `statusSFPview`, `resumenPresView`, `resumenDetPresView`, `listSFPP`, `listAllSFP`, `listResumenPres`, `listResumenDetPres`, `authPres`, `list`, `regPres`, `reg`, `comp`, `compByIdPP`, `getTempByIdPres`, `regSFP`, `hist_con`, `actual`, `listActual`, `status`, `ccosto`, `n_temp`, `list_temp`, `regDetPre`, `updateDetPre`, `listDetPre`, `listNtra`, `comPues`, `regPuesTra`, `updPuesTra`, `infoPP`, `updateSueldo`, `regPP`, `infTra`, `presupuestoDetails` |
| RemuneracionController | — | stub legado (no mapeado) |

### person
| Controller | Mapeo | opc= |
|---|---|---|
| EmployeeController | `empleado` | `Eva_Emp`, `Reg_Evaluar_Emp`, `Editar`, `modificar`, `Reporte`, `getAllEmployees`, `getAllEmployeesWithOutUserAccount`, `validar_aps`, `validar_huella`, `ShowHuella`, `ShowAPS`, `reg_huella`, `reg_aps` |
| PersonController | `trabajador` | POST `diezmo`, `afp`, `tiHoraPago`; general `Buscar`, `Buscar_Trabajador`, `Mostrar_Cod_APS`, `ShowEsDiezmoTrabajador`, `ShowDialogFotoTrabajador`, `ShowPorcentageTrabajador`, `Listar_Asp_Social`, `Modificar_Dat_Gen`, `Editar_Dat_Gen`, `Editar_Asp_Acad`, `Modificar_Asp_Acad`, `Editar_Asp_Soc`, `Modificar_Asp_Soc`, `Form_Reg`, `Registrar`, `list`, `list_reg_tra`, `actualizar`, `edit_perfil`, `Editar_Asp_Rel`, `Modificar_Asp_Rel`, `Documento_Trabajador`, `aut`, `reg_aps_masivo`, `registrar_huella`, `Val_num_Doc`, `Form_Cambiar_Clave`, `reg_trb`, `validar_cod_uni` |
| JobController (`person/job`) | `Puesto` | POST `menu`, `listar_dep_dir`, `editar-Dep`, `crear-Dep`, `activar-Dep`, `desactivar-Dep`, `eliminar-Dep`, `list_dep_es`, `list_area_dep`, `crear_area`, `editar_area`, `activar_area`, `desactivar_area`, `eliminar_area`, `list_area_es`, `list_sec_area`, `crear_seccion`, `editar_seccion`, `activar_seccion`, `desactivar_seccion`, `eliminar_seccion`, `list_sec_es`, `list_puesto_sec`, `crear_puesto`, `editar_puesto`, `activar_puesto`, `desactivar_puesto`, `eliminar_puesto`, `listar_dir_es`, `list_direccion`, `editar-Direccion`, `crear-Direccion`, `activar-Direccion`, `desactivar-Direccion`, `eliminar-Direccion` |
| FamiliarController (`person/job`) | `familiar` | POST `Registrar Conyugue`, `Registrar Padres`, `Detalle_Familiar`, `Listar_Hijo_id_tr`, `REGISTRAR HIJO`, `modificar`, `eliminar`, `MODIFICAR HIJO`, `Editar_Familiar`, `Modificar_Padre_madre`, `List_Padre`, `Modificar_Padres`, `MODIFICAR_PMC` |
| CCarrera_Institucion | `detalle_carrera` | POST `institucion`, `ti_inst`, `carrera` |
| CDir_Puesto | `Direccion_Puesto` | GET `Listar`; POST `Listar_dir`, `Listar_Dir`, `Listar_area`, `Listar_sec`, `Listar_sec2`, `Listar_pu_id`, `Reg_puesto_paso`, `Listar_SUB_MO`, `List_Area_RDGP`, `list_req`, `Listar_dir_dep`, `Listar_direccion_filial`, `getDirections` |
| CFuncion | `funcion` | POST `princpal_funcion`, `listar_x_puesto`, `listarF`, `listar_Direccion`, `otorgar_funciones`, `otorgar`, `list_pu`, `del_fun`, `edit_function`, `direccion`, `departamento`, `area`, `seccion` |
| CUbigeo | `ubigeo` | POST `dep_nac`, `pro_nac`, `Listar_D`, `Listar_P`, `Listar_Di` |

### request
| Controller | Mapeo | opc= |
|---|---|---|
| RequerimientoController | GET `requerimiento` | `Listar`, `Listar_tp`, `Listar_req_id`, `Listar_id_req`, `Listar_Tipo_Planilla` |
| SolicitudRequerimientoController | `solicitud_requerimiento` | POST `Listar_Solicitud`, `Listar_Sol_Pendientes`, `Listar_Sol_Aut`, `Registrar_solicitud`, `Reg_List_Solicitud`, `Ver_Detalle_Solicitud`, `Ver_Solicitud`, `Procesar_Solicitud`, `Val_Envio_Solicitud` |

## 3.6 Paquete `report`

| Controller | Mapeo | opc= |
|---|---|---|
| ReporteController | `reporte` | `reporte1`, `reporte_hijo`, `reporte_padre_hijo`, `reporte_cumpleaños`, `reporte_t_navidad`, `reporte_datos_genereales`, `Reporte_padres_madres`, `Reporte_datos`, `list_da`, `searchtb`, `Reporte_Datos_Hijos`, `Reporte_Datos_cumpl`, `Reporte_Navidad` (algunos con `sendRedirect` a JSP) |
| ReporteHijoController | `CReporte_Hijo` | `reporte_hijos` |
| ReporteHistorialController | `RHistorial` | `hist_tra`, `list_mod_fecha`, `mod_tra`, `list_mod_tra`, `hist_es_civil`, `list_hist_es_civil`, `Procesar_reg_ec`, `Detalle_hist_ec`, `list_detalle_ec`, `Filtro_hijo`, `Fe_Modif_Hijo`, `Fe_Modif_Hijo2`, `Comparar_dato_Hijo`, `list_hist_fecha`, `list_actual`, `Comparar_hijo`, `Listar_hijo_trabajador`, `Procesar_datos_hijos`, `Historial_Datos_Hijo`, `proc_hist`, `proc_act` |

## 3.7 Paquete `user`

| Controller | Mapeo | opc= / comportamiento |
|---|---|---|
| IndexController | POST `valida` (JSON) | Login: valida credenciales y llena la sesión |
| MenuController | `/menu` | `logout` (invalida sesión), `List_Privilegios`, menú; GET `/privileges`, `/elements` |
| ModuleController | GET `/modules`, GET `/modulo` | `lis_mod`, `lis_req`, `lis_pr_mod`, `lis_pr_mod_x_id`, `Registrar`, `activar_pri_mod`, `desactivar_pri_mod` |
| UserController | `Usuario` (`@Controller`) | GET `Reg_Usuario` (ModelAndView); POST `Registrar Usuario`, `Modificar_clave_1/2`, `Mod_Usuario_con`, `Mod_Usuario_con_2`, `Activar_Usuario_con`, `Desac_Usuario_con`, `Quitar_acceso`, `Eliminar_Usuario`, `Modificar`, `Ver_Perfil`, `editar_Perfil`, `Cambiar_clave`, `getAllUsers`, `fieldUniqueSave` |
| PhotoController | `foto` | Subida de foto multipart / listado |
| PhotoValidationController | `validar_foto` | `Index` (redirect `views/Reportes/Validar_Foto/Index.html`), `getFotos`, `validar` |
| PrivilegeController | `Privilegio` | CRUD de privilegios, roles, otorgamiento (`ListPrivilegio`, `ListModulo`, `MenuOpciones`, `Listar_Rol`, `Modificar_Rol`, `Listar_Privilegio`, `Activar_Priv`, `Desactivar_Priv`, `Eliminar_Priv`, `modificar_Priv1/2`, `Registrar`, `Otorgar`, `Listar_PR_ROL`, `Mod_det_pr`, `Mod_det_pr2`, `Activar_det_pr`, `Desactivar_det_pr`, `Elim_det_pr`, `REGISTRAR PRIVILEGIO DADO`) |
| RolController | `Roles` | `mat_rol`, `Listar_Rol`, `Modificar_Rol`, `Modificar`, `Activar_Rol`, `Desactivar_Rol`, `Eliminar_Rol`, `REGISTRAR` |

## 3.8 Paquete `util` (no controllers)

`CCriptografiar` (3DES), `DateFormat`, `GestionarJSON`, `Mail`, `Sql`, `StringMD`, `StringTools`, `WebServiceClient`.

## Notas

- Controllers que producen **HTML plano** (no JSON): `CommentaryController` (COMENTAR), `ContratoAdjuntoController`, `DocumentoAdjuntoController`, `DocumentroTrabajadorController`.
- `PresupuestoController` usa `switch(opc)`; `NotificationController` usa `op` (int); `PhotoController` usa `opc` como rama condicional.
- `RequerimientoController` e `IndexController`/`ModuleController` no tienen `@RequestMapping` de clase (ruta a nivel de método).

Para el detalle de implementación de cada endpoint, ver el código en [jairleo95/TALENTO_HUMANO](https://github.com/jairleo95/TALENTO_HUMANO) (local: `gth-ms/src/main/java/com/app/controller/`).
