# 07 — Integraciones

## WebSocket `/serverGth`

- **Clase**: `com.app.controller.notify.PushNotification`, anotada `@ServerEndpoint("/serverGth")` (JSR-356, registrada por el Tomcat embebido).
- Mantiene un `Set<Session>` global sincronizado de clientes conectados.
- `@OnMessage`: hace **broadcast** del mensaje a todos los demás clientes en JSON `{"message": ..., "username": ...}` (usando `userSession.toString()`).
- **Propósito**: canal de notificaciones/chat en tiempo real. No se encontró cliente JS activo que lo consuma en el frontend actual (el `Principal.js` abre el WebSocket `ws://.../TALENTO_HUMANO/serverGth` y escucha mensajes de tipo "Autorizacion").

## Notificaciones (`cnot`)

`NotificationController` (POST `cnot`, parámetro `op` int):
- `1` / `3`: listar notificaciones por usuario.
- `2`: marcar como leída.
- `4`: marcar como visualizadas.
- `5`: contar no leídas autorizadas / no autorizadas.

## WebService académico (SOAP)

- **Cliente**: `controller/util/WebServiceClient.java` (`jakarta.xml.ws-api` + `jaxws-rt`).
- **Endpoint**: `webapp.upeu.edu.pe`.
- **Uso**: sincronización de carga académica de docentes (`CargaAcademicaController` — `List_ws`, `statusSyncUpCargaAcademica`, `stopSyncUpCargaAcademica`).
- Claves `keyApp`/`keyID` en `globalProperties.java`.

## Correo (`javax.mail`)

- **Clases**: `controller/util/Mail.java`, `controller/inbox/MailController.java` (test), `CorreoDAO`.
- Notificaciones de "requerimiento por autorizar" con link `gth.upeu.edu.pe/TALENTO_HUMANO`.
- Config hardcodeada (user `jairleo95@gmail.com` en tests).

## Spring Cloud Config Server

- `application.properties`: `spring.config.import=configserver:`, `spring.cloud.config.uri=http://localhost:8888`, usuario/contraseña `admin/admin`.
- El datasource Oracle y otras propiedades de entorno se resuelven vía Config Server (`spring.application.name=gth`).
- Repo del config server: `https://github.com/jairleo95/spring-cloudconfig-server.git`.

## Servicios web externos (URLs de archivos)

- Archivos de trabajadores servidos en `http://gth.upeu.edu.pe/DATA_FILES_GTH/Archivo/` (constante en `globalProperties.java`).

## Referencia

Código: `gth-ms/src/main/java/com/app/controller/notify/`, `controller/util/WebServiceClient.java`, `config/globalProperties.java`. Repo: [jairleo95/TALENTO_HUMANO](https://github.com/jairleo95/TALENTO_HUMANO).
