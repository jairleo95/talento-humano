# 05 — Autenticación, Sesión y Permisos

## Flujo de login

1. `index.jsp` muestra el formulario de login (vista servida por `MainController` en `/`).
2. `js/index.js` (`validarLogin()`) hace `POST valida` con JSON `{"username", "userPassword"}`.
3. `IndexController.authAccess` (`user/IndexController.java`):
   - Recibe el modelo `User` (`@JsonProperty("username")` → `no_usuario`, `@JsonProperty("userPassword")` → `pw_usuario`).
   - Llama `UsuarioDAO.Val_Usuario(usuario, pwd)` → SQL contra la vista `rhvd_usuario`:
     `select * from rhvd_usuario where no_usuario='...' and pw_usuario='...'`
     (**credenciales en texto plano, sin hash**).
   - Si devuelve exactamente 1 fila → autentica y llena la sesión; responde `{"rpta": true}`.
   - Si no → `{"rpta": false, "message": "Usuario o clave incorrecta."}`.
4. El JS redirige a `/menu`.

## Atributos de sesión (seteados en el login)

| Atributo | Contenido |
|---|---|
| `IDUSER` | id del usuario |
| `IDTR` | id del trabajador |
| `FECHA_MINIMA` | fecha actual del sistema |
| `USER` | nombre de usuario (`no_usuario`) |
| `IDPER` | id del empleado |
| `IDROL` | id del rol |
| `CL` | clave |
| `PUESTO_ID` | id del puesto |
| `AREA_ID`, `AREA` | id y nombre del área |
| `DEPARTAMENTO`, `DEPARTAMENTO_ID` | departamento |
| `SECCION` | sección |
| `PUESTO` | puesto |
| `AR_FOTO` | foto |
| `IDDIR` | id de dirección |
| `NOMBRE_AP` | apellidos + nombres |
| `LIST_MODULO` | módulos autorizados por rol (`RolDAO.LISTAR_MODULOS(idrol)`) |

## Logout y control de acceso

- **Logout**: `MenuController` con `?opc=logout` → `sesion.invalidate()` → vuelve a `index`.
- **Control de acceso**: no hay Spring Security efectivo; los JSP verifican `session.getAttribute("IDUSER") != null` y redirigen a `/TALENTO_HUMANO/` si no hay sesión.
- **Rol especial `ROL-0013`**: entra directo al módulo `MOD-0001`.
- **Permisos por rol**: `Principal.jsp` renderiza el menú iterando `listarURL` (lista de `V_Privilegio` en sesión), mostrando solo los ítems del rol.

## Endpoints auxiliares

| Endpoint | Función |
|---|---|
| `GET /menu/privileges` | URLs del módulo seleccionado (para el menú dinámico) |
| `GET /menu/elements` | Breadcrumb con DEPARTAMENTO/AREA/SECCION/PUESTO |

## Criptografía

`util/CCriptografiar`: 3DES (DESede) con clave derivada de MD5 de `"AlphaTeam"` — cifra contraseñas en alta/edición de usuarios y datos sensibles.

## Referencia

Código: `gth-ms/src/main/java/com/app/controller/user/IndexController.java`, `MenuController.java`; vistas `index.jsp`, `Principal.jsp`. Repo: [jairleo95/TALENTO_HUMANO](https://github.com/jairleo95/TALENTO_HUMANO).
