# Módulo: Usuario / Seguridad

## Funcional

Administra **usuarios, roles, privilegios y módulos** del sistema, así como el perfil personal del usuario.

**Funcionalidades**:
1. **Registro de usuarios** (`Reg_Usuario.jsp`, `ListaUsuario.jsp`, `List_Usuario.jsp`): alta/baja de usuarios del sistema, vinculados a trabajadores.
2. **Roles** (`Rol_Privilegio/List_Roles.jsp`, `Reg_Roles.jsp`, `Mod_Rol.jsp`): CRUD de roles.
3. **Privilegios** (`Rol_Privilegio/MantPrivilegios.jsp`, `Reg_Privilegio.jsp`, `Mod_Privilegio.jsp`, `List_Privilegios.jsp`): CRUD de privilegios y detalle por rol.
4. **Otorgar privilegios** (`Rol_Privilegio/Otorgar_Privilegio.jsp`, `Reg_Rol_Privilegio.jsp`, `List_Pri_Roles.jsp`): asignación de privilegios a roles.
5. **Módulos** (`Mant_Modulos/Mant_Mod_Privilegio.jsp`): módulos y su relación con privilegios.
6. **Perfil** (`Perfil_Usuario.jsp`, `editarPerfil`, `Mod_Usuario_rol.jsp`): datos del usuario, edición de rol.
7. **Contraseña** (`Cambiar_Clave.jsp`, `Cambiar_Pwd.jsp`): cambio de clave (cifrada con 3DES).
8. **Foto** (`Subir_Foto.jsp`): foto del usuario/trabajador.

## Técnico

**Controllers** (`user/`):
- `UserController` (`Usuario`) — único con `@Controller` + `ModelAndView`.
- `RolController` (`Roles`).
- `PrivilegeController` (`Privilegio`).
- `ModuleController` (`/modules`, `/modulo`).
- `MenuController` (`/menu`).
- `IndexController` (`valida`) — login.
- `PhotoController` (`foto`), `PhotoValidationController` (`validar_foto`).

**opc= principales**:
- `UserController`: `Registrar Usuario`, `Modificar_clave_1`, `Modificar_clave_2`, `Mod_Usuario_con`, `Mod_Usuario_con_2`, `Activar_Usuario_con`, `Desac_Usuario_con`, `Quitar_acceso`, `Eliminar_Usuario`, `Modificar`, `Ver_Perfil`, `editar_Perfil`, `Cambiar_clave`, `getAllUsers`, `fieldUniqueSave`.
- `RolController`: `Listar_Rol`, `REGISTRAR`, `Modificar`, `Modificar_Rol`, `Activar_Rol`, `Desactivar_Rol`, `Eliminar_Rol`, `mat_rol`.
- `PrivilegeController`: `Listar_Privilegio`, `Registrar`, `REGISTRAR PRIVILEGIO`, `modificar_Priv1/2`, `Activar_Priv`, `Desactivar_Priv`, `Eliminar_Priv`, `Listar_Rol`, `Modificar_Rol`, `Otorgar`, `ListPrivilegio`, `ListModulo`, `Listar_PR_ROL`, `Mod_det_pr`, `Mod_det_pr2`, `Activar_det_pr`, `Desactivar_det_pr`, `Elim_det_pr`, `REGISTRAR PRIVILEGIO DADO`, `MenuOpciones`.
- `ModuleController`: `lis_mod`, `lis_req`, `lis_pr_mod`, `lis_pr_mod_x_id`, `Registrar`, `activar_pri_mod`, `desactivar_pri_mod`.

**Entidades de dominio**: `User`, `V_Usuario`, `V_Var_Usuario`, `Rol`, `V_Rol`, `Modulo`, `Privilegio`, `V_Privilegio`, `Privilegio_Rol`, `V_Privilegio_Rol`, `Notification`, `V_User_aut`.

**JSPs**: `Usuario/` (23) + `Mant_Modulos/` (2).

**SPs usados**: `RHSP_INSERT_USUARIO`, `RHSP_MOD_USUARIO_CL`, `RHSP_ELIMINAR_USUARIO`, `RHSP_MOD_PERFIL`, `RHSP_ACTIVAR/DESACTIVAR_USUARIO`.

## Notas de migración

- Mapea a **identity-service** (users, roles, privileges). El login JWT y auth en gateway es pendiente de Fase 3.

## Referencia

Código local: `gth-ms/src/main/java/com/app/controller/user/`; `gth-ms/src/main/webapp/WEB-INF/jsp/views/Usuario/`. Repo: [jairleo95/TALENTO_HUMANO](https://github.com/jairleo95/TALENTO_HUMANO).
