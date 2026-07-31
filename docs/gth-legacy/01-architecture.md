# 01 — Arquitectura Técnica

## Vista general

Monolito **Spring Boot 2.7.5** con MVC. El frontend son JSP servidas por Spring, que consumen los propios controllers vía AJAX (JSON). No hay API REST pública ni separación cliente/servidor real: cada JSP conoce los endpoints `opc=` de su controller.

```
Navegador
   │  GET /gth/ (index.jsp, login)
   │  POST valida (JSON) ──► IndexController ──► UsuarioDAO ──► Oracle
   │  GET /menu ──► MenuController ──► ModelAndView(Principal.jsp)
   │  AJAX GET/POST  controller?opc=... ──► @RestController ──► DAO ──► Oracle
   │
   └── JSP (SmartAdmin + jQuery 2.0.2, businessLogic/*.js)
```

## Capas del código (`gth-ms/src/main/java/com/app/`)

| Capa | Paquete | Responsabilidad |
|---|---|---|
| Controllers | `controller/` | Reciben peticiones, orquestan DAOs, devuelven JSON/ModelAndView |
| Dominio | `domain/model/` | Entidades y vistas (`V_*`, `X_*`) |
| Persistencia | `persistence/dao/` y `persistence/dao_imp/` | Acceso a Oracle (JDBC + stored procedures) |
| Config | `config/` | `Security`, `globalProperties`, `FactoryConnectionDB`, `SessionSystem` |
| Utilidades | `controller/util/` | Criptografía, fechas, mail, JSON, SQL, WS client |

## Conexión a base de datos

- **Patrón**: `FactoryConnectionDB` + `DBConnection` (abstract) en `com.app.config.factory`.
  - `FactoryConnectionDB.open(int typeDB)` → `MYSQL=1`, `ORACLE=2`.
  - Todos los DAOs usan `FactoryConnectionDB.ORACLE` (MySQL es vestigio).
- **Conexión Oracle** (`ORACLEConnectionDB`): driver `oracle.jdbc.driver.OracleDriver`, `jdbc:oracle:thin:...`.
  - Credenciales hardcodeadas en `globalProperties.java`: host `DESKTOP-MS5NN2M`, user `procesosrh`, pass `gestionrrhh`, puerto `1521`, SID `xe`.
  - Dependencia local `libs/ojdbc7.jar`.
- **Sin pool**: cada método del DAO abre y cierra su propia conexión (en `finally`).
- **Sin datasource Spring**: `application.properties` no define conexión JDBC; el datasource lo provee **Spring Cloud Config Server** (`spring.config.import=configserver:`, `http://localhost:8888`, user/admin, admin).
- **Estilo de consulta**: `Statement` con SQL concatenado (`conn.query(sql)`) y `CallableStatement` para SPs `RHSP_*`.

## Seguridad

`config/Security.java`:
- `WebSecurityConfigurerAdapter` con `csrf().disable()` y `.antMatchers("/**").permitAll()` → **Spring Security no protege nada**.
- El control de acceso es **manual por sesión** (ver [05-auth-sesion.md](05-auth-sesion.md)).
- Beans: `BCryptPasswordEncoder` (definido, no usado en el login) y `HttpFirewall` que permite `%2F`.

## Configuración (archivos)

**`build.gradle`** (Java 11, group `com.app`, versión `1.1.1`):
- `spring-boot-starter-web`, `spring-cloud-starter-config`, `actuator`, `micrometer-registry-prometheus`
- JSP: `tomcat-embed-jasper 9.0.44` + `jstl 1.2`
- `ojdbc7.jar` (local `libs/`), `spring-security-oauth2 2.0.7.RELEASE`
- Lombok, `jakarta.xml.ws-api` + `jaxws-rt` (SOAP), commons-io/fileupload, gson, javax.mail, org.json
- Spring Cloud BOM `2021.0.4`

**`application.properties`**:
- `server.servlet.context-path=/gth`, `server.port=8080`
- `management.port=8082` (Prometheus)
- `spring.mvc.view.prefix=/WEB-INF/jsp/views/`, `spring.mvc.view.suffix=.jsp`
- `security.basic.enabled=false`; Config Server `gth` en `http://localhost:8888` (admin/admin)

## Otros componentes

| Componente | Archivo | Función |
|---|---|---|
| `SessionSystem` | `config/SessionSystem.java` | `HttpSessionListener` |
| `Constants` | `config/Constants.java` | Constantes globales |
| `globalProperties` | `config/globalProperties.java` | Credenciales Oracle, claves WS (`keyApp`/`keyID`), URL archivos `http://gth.upeu.edu.pe/DATA_FILES_GTH/Archivo/` |
| `PushNotification` | `controller/notify/` | WebSocket `/serverGth` (broadcast) |
| `WebServiceClient` | `controller/util/` | Cliente del WS académico |

## Repo de referencia

Para el detalle de implementación: [jairleo95/TALENTO_HUMANO](https://github.com/jairleo95/TALENTO_HUMANO) (código local `gth-ms/`).
