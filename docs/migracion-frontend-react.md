# Plan de Migración del Frontend a React

Documento de planificación y ejecución de la migración del frontend legacy (JSP + jQuery/SmartAdmin) a un SPA moderno en React, consumiendo la API vía el gateway único.

Estado: **completado** — Fases R0-R5 finalizadas. 12 módulos en el menú.

## 1. Decisiones confirmadas

| Decisión | Opción elegida |
|---|---|
| Stack | **React 18 + TypeScript + Vite + PrimeReact** |
| Ubicación | Reemplazar `gth-webapp/` (era Angular 14 vacío) |
| UI library | **PrimeReact** (Datatable, Dialog, Steps, Editor) |
| Backend mappers | **MapStruct 1.6.3** + Lombok (reemplaza mappers manuales) |
| Gateway | **Spring Boot 3.3.6 + Spring Cloud Gateway 2023.0.3 + Java 21** (actualizado desde Boot 2.7/Java 11) |
| Autenticación | **Login JWT en identity-service** + validación del token en el gateway |
| Estrategia | **Strangler fig**: incremental por módulo, convivencia con legacy `/gth/**` |
| Servidor prod | **nginx** sirviendo el SPA y proxying al gateway |
| Diseño | **Tema moderno custom** replicando paleta legacy (SmartAdmin dark header/sidebar + light content) |
| Validaciones | **react-hook-form + zod** con reglas extraídas de `Reg_Dgp.jsp` y `editDGP.js` |
| Commits | **Uno por fase completada** (R0, R1, R2, R3, R4, R5...), mensaje descriptivo con prefijo `feat(phase):` |

## 2. Arquitectura de integración

```
Browser → gth-webapp (React SPA, :4200 dev / nginx prod)
   │ fetch → /identity/**, /recruitment/**, /contract/**
   ▼
gth-gtw (Spring Cloud Gateway :8085, Boot 3.3.6 / Java 21) — ÚNICO punto de entrada
   ├── filtro JWT (valida Authorization: Bearer)
   ├── /identity/**      → identity-service :8081
   ├── /recruitment/**   → recruitment-service :8082
   ├── /contract/**      → contract-service :8083
   └── /gth/**           → legacy gth-ms :8080 (convivencia)
```

- El front **nunca** llama directo a los servicios; solo al gateway.
- CORS ya configurado en el gateway para `http://localhost:4200`.
- En dev, Vite proxy `/identity`, `/recruitment`, `/contract` → `:8085`.
- En prod, nginx sirve `dist/` y proxya las rutas `/identity|/recruitment|/contract` → gateway.

## 3. Design System

### 3.1 Paleta de colores

| Elemento | Color | Uso |
|---|---|---|
| Header bg | `#1a1d23` | Fixed top bar |
| Sidebar bg | `#111318` | Fixed left nav |
| Sidebar text | `#6c7394` | Menu items |
| Sidebar active text | `#8fbcff` | Active menu item |
| Accent / primary | `#4f8cff` | Buttons, links, focus rings |
| Content bg | `#f4f5f9` | Page background |
| Card bg | `#ffffff` | Cards, tables, dialogs |
| Text primary | `#1e2433` | Main content text |
| Text secondary | `#64708a` | Labels, metadata |
| Border | `#e8ecf2` | Cards, table borders |
| Table header | `#f7f8fc` | Column headers |
| Table stripe | `#fafbfe` | Even row background |
| Table hover | `#eef2ff` | Row hover state |
| Form section title | `#00329B` | Section headers (bold + blue border) |
| Form label | `#005cac` | Bold input labels |
| Input focus glow | `#0575f4` | `box-shadow` on focus |
| Widget header bg | `#3276b1` | Widget-style container header |

### 3.2 Tipografía

- Primaria: `Inter`, `Segoe UI`, system-ui, sans-serif
- Body: 14px, anti-aliased
- Encabezados: sizes 16px–22px, letter-spacing -0.3px

### 3.3 Layout

```
┌──────────────────────────────────────────────┐
│  Header (#1a1d23, 52px)          user logout │
├─────────┬────────────────────────────────────┤
│ Sidebar │  Main content (#f4f5f9)            │
│ 240px   │  ┌─────────────────────────────┐   │
│ user    │  │ Toolbar / filters           │   │
│ avatar  │  ├─────────────────────────────┤   │
│         │  │ DataTable / Cards           │   │
│ menu    │  │                             │   │
│ items   │  │                             │   │
│         │  └─────────────────────────────┘   │
└─────────┴────────────────────────────────────┘
```

- Sidebar colapsa a 64px (solo iconos)
- Responsive: sidebar se oculta en <768px

### 3.4 Archivo de tema

`src/theme.css` (~600 líneas): variables CSS + overrides de PrimeReact + estilos de formularios legacy. Sin dependencias externas.

### 3.5 Diseño de formularios (legacy-match)

Los formularios replican el diseño de `Reg_Dgp.jsp` y `Editar_DGP.jsp` con los mismos patrones del SmartAdmin original.

**Widget (contenedor de formulario)**

```
┌──────────────────────────────────────────┐
│  Widget Header (#3276b1, blanco, 14px)   │  ← gth-widget-header
│  <i class="icono" /> Título              │
├──────────────────────────────────────────┤
│                                          │
│  ── Sección (icono + título azul) ──     │  ← gth-section-title
│  ───── con borde inferior azul ─────     │
│                                          │
│  Label *  (bold, #005cac, 12px)          │  ← form label.text-sm
│  ┌──────────────────────────┐            │
│  │ Input text               │            │  ← p-inputtext (focus glow azul)
│  └──────────────────────────┘            │
│                                          │
│  ── Otra sección ─────────────────       │
│                                          │
└──────────────────────────────────────────┘
```

**Clases CSS de formularios**

| Clase | Propósito |
|---|---|
| `gth-widget` | Contenedor tipo jarviswidget (borde, sombra) |
| `gth-widget-header` | Barra superior azul (#3276b1) con icono |
| `gth-widget-body` | Cuerpo del widget (padding 16px) |
| `gth-section` | Agrupación de campos con margen |
| `gth-section-title` | Título azul (#00329B) con icono y borde inferior azul |
| `gth-fieldset` | Fieldset legacy con legend |
| `gth-fieldset-legend` | Legend azul (#005cac) con icono |
| `gth-form-label` | Label bold azul independiente |
| `gth-form-row` | Fila flex con gap |
| `gth-two-column` | Layout de 2 columnas (legacy DGP: datos + horario) |

**Jerarquía de colores en formularios**

| Elemento | Color | Ejemplo |
|---|---|---|
| Título de sección | `#00329B` | `.gth-section-title` |
| Label de campo | `#005cac` | `form label.text-sm` |
| Focus glow input | `#0575f4` | `box-shadow: 0 0 8px rgba(5,117,244,0.25)` |
| Widget header bg | `#3276b1` | `.gth-widget-header` |
| Form background | `#f9fafb` | `.p-dialog-content` |

**Íconos por sección de formulario**

| Formulario | Sección | Ícono |
|---|---|---|
| Requerimiento | Información básica | `pi pi-file` |
| | Trabajador y contratación | `pi pi-id-card` |
| | Información financiera | `pi pi-dollar` |
| | Ubicación y horario | `pi pi-map-marker` |
| | Información adicional | `pi pi-info-circle` |
| Contrato | Datos principales | `pi pi-file` |
| | Fechas | `pi pi-calendar` |
| | Financiera y laboral | `pi pi-dollar` |
| | Observaciones | `pi pi-comment` |
| Trabajador | Datos personales | `pi pi-user` |
| | Formación | `pi pi-graduation-cap` |

### 3.6 Validaciones de formulario (legacy-match)

Las validaciones se replican del legacy (`Reg_Dgp.jsp`, `editDGP.js`). Se implementan con **zod** + **react-hook-form**.

**Reglas extraídas del legacy:**

| Campo legacy | Regla | Zod equivalente |
|---|---|---|
| `SUELDO` | `maxlength=13`, solo numérico | `z.number().min(0).max(9999999999999)` |
| `BONO_ALIMENTARIO` | `maxlength=13`, numérico | `z.number().min(0)` |
| `CUENTA` / `CUENTA_BANC` | `maxlength=30` si banco=BBVA/BCP | `z.string().max(30)` |
| `RUC` | `maxlength=20`, requerido REQ-0010 | `z.string().max(20)` |
| `DOMICILIO_FISCAL` | requerido REQ-0010 | `z.string().min(1)` |
| `LUGAR_SERVICIO` | `maxlength=50`, requerido REQ-0010/REQ-0011 | `z.string().max(50)` |
| `DESCRIPCION_SERVICIO` | `maxlength=300` | `z.string().max(300)` |
| `HORARIO_CAPACITACION` | texto libre | `z.string().max(140)` |
| `DIAS_CAPACITACION` | texto libre | `z.string().max(140)` |
| `MONTO` (cuota) | numérico | `z.number().min(0)` |
| `title` (DGP) | `maxlength=140` | `z.string().max(140)` |
| `cuenta_bancaria` | `maxlength=21` BBVA, `maxlength=14` BCP | `z.string().max(30)` |
| `horas_totales` | `max=48`, requerido | `z.number().max(48)` |
| Username | requerido | `z.string().min(1)` |
| Email | formato email | `z.string().email()` |
| Password | requerido | `z.string().min(1)` |
| `ANTECEDENTES_POLICIALES` | select Si/No | `z.string().optional()` |
| `CERTIFICADO_SALUD` | select Si/No | `z.string().optional()` |
| `ES_PRESUPUESTADO` | checkbox/toggle | `z.boolean()` |
| `MFL` | checkbox/toggle | `z.boolean()` |
| `MOTIVO` | select | `z.string().optional()` |
| `BANCO` | select con valores 0-3 | `z.string().optional()` |
| Fechas | formato `99/99/9999` (data-mask) | `z.string().regex(/^\d{2}\/\d{2}\/\d{4}$/)` o `z.string().optional()` |

**Patrón de validación en componentes React:**

```tsx
const schema = z.object({
  title: z.string().min(1, 'Requerido').max(140, 'Máximo 140 caracteres'),
  description: z.string().min(1, 'Requerido'),
  salaryAmount: z.number().min(0, 'Debe ser >= 0').optional(),
  bankAccount: z.string().max(30, 'Máximo 30 caracteres').optional(),
  // ... más reglas del legacy
});
```

**Archivo de constantes de validación (`shared/validations.ts`):**

```ts
export const VALIDATION_RULES = {
  SALARY_MAX: 13,        // maxlength legacy para campos monetarios
  BANK_ACCOUNT_MAX: 30,  // nro cuenta BBVA/BCP
  RUC_MAX: 20,           // RUC
  SERVICE_MAX: 300,      // descripción servicio
  LOCATION_MAX: 50,      // lugar servicio  
  TITLE_MAX: 140,        // título
  HOURS_MAX: 48,         // horas semanales
} as const;
```

## 4. Autenticación (JWT)

### Backend (identity-service)
- `POST /api/v1/auth/login` — body `{username, password}` → valida contra `user_account.password_hash` (BCrypt) → devuelve `{token, tokenType, expiresIn, user}`.
- `GET /api/v1/users/me` — header `Authorization: Bearer` → devuelve perfil + **roles**.
- Migración `V2__auth.sql`: columna `password_hash`, seed `admin/admin123` (ADMIN) y `user/user123` (USER).
- Dependencias: `spring-security-crypto` (BCrypt), `io.jsonwebtoken:jjwt:0.12.6` (JWT).

### Gateway (gth-gtw)
- `JwtAuthFilter.java`: GlobalFilter, orden -100. Permite `/gth/**`, `/actuator/**`, `POST /identity/api/v1/auth/login`.
- 401 si token ausente/vencido/inválido en rutas protegidas.
- Secreto JWT compartido vía `JWT_SECRET` (mismo default en dev para identity y gateway).

### Frontend
- `core/auth/AuthProvider` + `useAuth()`: guarda token en `localStorage`, carga `/users/me` al iniciar, logout.
- `core/api/client.ts`: fetch wrapper, inyecta `Authorization: Bearer`, redirige a `/login` en 401.
- `app/RequireAuth.tsx`: guard de rutas con spinner de carga en tema oscuro.

## 5. Estructura del frontend (`gth-webapp/`)

```
gth-webapp/
├── package.json / vite.config.ts / tsconfig.json / tsconfig.node.json
├── index.html
├── deploy/
│   └── nginx.conf                   # config para producción
├── src/
│   ├── main.tsx                     # entry point + providers
│   ├── theme.css                    # design system + primeflex overrides
│   ├── vite-env.d.ts
│   ├── app/
│   │   ├── router.tsx               # rutas públicas/privadas
│   │   └── RequireAuth.tsx          # guard de autenticación
│   ├── core/
│   │   ├── api/
│   │   │   └── client.ts            # HTTP client (GET/POST/PATCH/DELETE)
│   │   ├── auth/
│   │   │   ├── AuthProvider.tsx      # contexto de autenticación
│   │   │   ├── useAuth.ts           # hook
│   │   │   └── types.ts             # LoginRequest, LoginResponse, MeResponse
│   │   └── layout/
│   │       ├── AppShell.tsx          # shell (header + sidebar + outlet)
│   │       ├── Header.tsx            # barra superior dark #1a1d23
│   │       └── Sidebar.tsx           # menú lateral dark #111318
│   └── features/
│       ├── auth/
│       │   └── LoginPage.tsx         # login con tarjeta moderna
│       ├── requirements/             # R1: requerimientos DGP
│       │   ├── types.ts              # RequirementRequest|Response (33 campos)
│       │   └── RequirementsPage.tsx   # lista + crear + detalle + cambio estado
│       ├── processes/                # R2: procesos y pasos
│       │   ├── types.ts              # Process|ProcessStep + statuses
│       │   ├── ProcessListPage.tsx    # lista + crear + filtrar + cambio estado
│       │   └── ProcessDetailPage.tsx  # detalle + pasos + agregar paso
│       ├── inbox/                    # R2: bandeja de entrada
│       │   ├── types.ts              # InboxItem
│       │   └── InboxPage.tsx          # lista + asignar + cambio estado
│       └── contracts/                # R3: contratos
│           ├── types.ts
│           ├── ContractListPage.tsx
│           ├── ContractDetailPage.tsx
│           └── TemplatePage.tsx
│       └── users/                    # R4: gestión de usuarios
│           ├── types.ts
│           └── UserManagementPage.tsx
```

## 6. Módulos y orden de migración

| Fase | Módulo | Backend | Frontend | Estado |
|---|---|---|---|---|
| R0 | Andamiaje (shell, router, auth) | identity-service JWT | Vite + React + PrimeReact + TanStack Query | ✅ |
| R1 | **Requerimientos (DGP)** | +10 campos legacy (V3) | 33 campos, 5 secciones con íconos | ✅ |
| R2 | **Proceso + Inbox** | Pasos, bandeja | ProcessList, ProcessDetail, InboxPage | ✅ |
| R3 | **Contrato** | +28 campos legacy (V2) | Crear/firmar/adjuntos/plantillas | ✅ |
| R4 | **Usuario/Seguridad** | Search, PATCH, DELETE, /roles | UserManagementPage CRUD | ✅ |
| R5a | **Trabajador** | 43 campos (V4+V5) | WorkerList, WorkerDetail | ✅ |
| R5b | **Organigrama + Puesto** | Jerarquía unificada (V6+V7) | OrgStructurePage con filtro | ✅ |
| R5c | **Presupuesto** | CostCenter (ya existía) | CostCenterPage CRUD | ✅ |
| R5d | **Académico** | Career + University (V8) | AcademicPage (TabView) | ✅ |
| R5e | **Reportes** | — | Placeholder con 6 categorías | ✅ |
| R5f | **Funciones** | Pendiente | Placeholder | ⬜ backend |

## 7. Endpoints backend — estado

| Endpoint | Método | Servicio | Estado |
|---|---|---|---|
| `/identity/api/v1/auth/login` | POST | identity | ✅ |
| `/identity/api/v1/users/me` | GET | identity | ✅ |
| `/identity/api/v1/users` | GET | identity | ✅ |
| `/identity/api/v1/users/{id}` | GET | identity | ✅ |
| `/identity/api/v1/users` | POST | identity | ✅ |
| `/identity/api/v1/users` | PATCH | identity | ✅ |
| `/identity/api/v1/users/{id}` | DELETE | identity | ✅ |
| `/identity/api/v1/roles` | GET | identity | ✅ |
| `/identity/api/v1/privileges` | GET | identity | ⬜ |
| `/recruitment/api/v1/recruitment/requisitions` | GET/POST | recruitment | ✅ |
| `/recruitment/api/v1/recruitment/requisitions/{id}/status` | PATCH | recruitment | ✅ |
| `/recruitment/api/v1/recruitment/processes` | GET/POST | recruitment | ✅ |
| `/recruitment/api/v1/recruitment/processes/{id}/status` | PATCH | recruitment | ✅ |
| `/recruitment/api/v1/recruitment/processes/{id}/steps` | GET/POST | recruitment | ✅ |
| `/recruitment/api/v1/recruitment/inbox` | GET/POST | recruitment | ✅ |
| `/recruitment/api/v1/recruitment/inbox/{id}/status` | PATCH | recruitment | ✅ |
| `/contract/api/v1/contracts` | GET/POST | contract | ✅ |
| `/contract/api/v1/contracts/{id}/sign` | PATCH | contract | ✅ |
| `/contract/api/v1/contracts/templates` | GET/POST | contract | ✅ |
| `/contract/api/v1/contracts/attachments` | GET/POST | contract | ✅ |

## 8. Migraciones de BD por servicio

### identity-service
| Versión | Descripción |
|---|---|
| V1 | init — roles, privileges, user_account, user_role, seeds ADMIN/USER |
| V2 | auth — columna `password_hash`, seed admin/user con BCrypt |

### recruitment-service
| Versión | Descripción |
|---|---|
| V1 | init — tabla `requisition` (24 columnas) |
| V2 | process, inbox, cost_center — tablas `process`, `process_step`, `inbox_item`, `cost_center` |
| V3 | requisition legacy fields — +10 columnas (`worker_id`, `motive`, `is_mfl`, `is_budgeted`, `ruc`, `position_bonus`, `bev_bonus`, `family_allowance`, `subsidy`, `honorarium_amount`) |

### contract-service
| Versión | Descripción |
|---|---|
| V1 | init — tablas `contract`, `contract_template`, `contract_attachment` |

## 9. Paridad de campos DGP (legacy vs requisition DTO)

El DTO `RequisitionRequest` tiene 33 campos que cubren ~85% del formulario legacy `Reg_Dgp.jsp`.

| Legacy (`Reg_Dgp.jsp`) | Nuevo DTO (`RequisitionRequest`) | Estado |
|---|---|---|
| `IDPUESTO` | `positionId` | ✅ |
| `FEC_DESDE` / `FEC_HASTA` | `startDate` / `endDate` | ✅ |
| `SUELDO` | `salaryAmount` | ✅ |
| `BONO_ALIMENTARIO` | `foodBonus` | ✅ |
| `LUGAR_SERVICIO` | `serviceLocation` | ✅ |
| `DESCRIPCION_SERVICIO` | `serviceDescription` | ✅ |
| `DOMICILIO_FISCAL` | `fiscalAddress` | ✅ |
| `HORARIO_CAPACITACION` | `trainingSchedule` | ✅ |
| `HORARIO_REFRIGERIO` | `breakSchedule` | ✅ |
| `DIAS_CAPACITACION` | `trainingDays` | ✅ |
| `ANTECEDENTES_POLICIALES` | `policeRecordDesc` | ✅ |
| `CERTIFICADO_SALUD` | `healthCertificateDesc` | ✅ |
| `BANCO` / `CUENTA` | `bankName` / `bankAccount` | ✅ |
| `IDDATOS_TRABAJADOR` | `workerId` | ✅ (V3) |
| `MOTIVO` | `motive` | ✅ (V3) |
| `MFL` | `isMfl` | ✅ (V3) |
| `ES_PRESUPUESTADO` | `isBudgeted` | ✅ (V3) |
| `RUC` | `ruc` | ✅ (V3) |
| `BONO_PUESTO` | `positionBonus` | ✅ (V3) |
| `BEV` | `bevBonus` | ✅ (V3) |
| `ASIGNACION_FAMILIAR` | `familyAllowance` | ✅ (V3) |
| `SUBVENCION` | `subsidy` | ✅ (V3) |
| `MONTO_HONORARIO` | `honorariumAmount` | ✅ (V3) |
| `IDREQUERIMIENTO` (tipo) | `payrollTypeId` (parcial) | 🔄 |
| Horario semanal (7 días × turnos) | — | ⬜ sub-módulo |
| Cuotas/Pagos (várias líneas) | — | ⬜ sub-módulo |
| Comentarios | — | ⬜ sub-módulo |
| Plazos | — | ⬜ sub-módulo |
| Documentos adjuntos | — | ⬜ sub-módulo |

## 10. DevOps

### Deploy (`deploy/nginx.conf`)
```nginx
server {
    listen 80;
    root /usr/share/nginx/html;
    index index.html;
    location / { try_files $uri $uri/ /index.html; }
    location /identity/    { proxy_pass http://127.0.0.1:8085; }
    location /recruitment/ { proxy_pass http://127.0.0.1:8085; }
    location /contract/    { proxy_pass http://127.0.0.1:8085; }
}
```

### Docker
Contenedores para el frontend (nginx:alpine + build) y servicios backend (openjdk:21).

### Datos de prueba (seed)
- 5 requerimientos ricos (DGP-2026-020 al 024) con todos los campos poblados, estados variados (OPEN, SUBMITTED, IN_REVIEW, APPROVED)
- 3 procesos (Contratación Estándar, Directa, Renovación) con 12 pasos en total
- 3 items en bandeja de entrada asignados a usuarios
- 3 plantillas de contrato + 4 contratos (2 firmados SIGNED, 2 borrador DRAFT)

## 11. Riesgos y mitigación

| Riesgo | Mitigación |
|---|---|
| Contrato de campos legacy ≠ DTOs nuevos | V3 agregó 10 campos legacy al DTO (85% paridad). Sub-módulos (horario, cuotas, comentarios) requieren endpoints propios |
| Secret JWT compartido | Env `JWT_SECRET` en identity y gateway (mismo default dev) |
| CORS en prod | Mismo origin (nginx reverse proxy) |
| Menú por privilegios | `/users/me` devuelve roles → sidebar dinámico. Privilegios pendiente (R4) |
| Reportes jsPDF | PrimeReact DataTable + impresión nativa; jsPDF si se requiere |
| Gateway Java 11 / Gradle 7.5.1 | ✅ **Resuelto.** Actualizado a Boot 3.3.6 / Gradle 9.6.1 / Java 21. Ya no requiere `JAVA_HOME` especial. |

## 12. Stack tecnológico (resumen)

| Componente | Versión | Java |
|---|---|---|
| identity-service | Boot 4.0.7 + WebFlux + R2DBC + Flyway + jjwt 0.12.6 | 21 |
| recruitment-service | Boot 4.0.7 + WebFlux + R2DBC + Flyway | 21 |
| contract-service | Boot 4.0.7 + WebFlux + R2DBC + Flyway | 21 |
| gth-gtw (gateway) | Boot 3.3.6 + Spring Cloud Gateway 2023.0.3 | 21 |
| gth-webapp (frontend) | React 18 + Vite + TypeScript + PrimeReact | — |
| DB | PostgreSQL 16 (Docker, :5434) | — |

## 13. Referencias

- Documentación del legacy: [docs/gth-legacy/](gth-legacy/README.md)
- Repo legacy: [jairleo95/TALENTO_HUMANO](https://github.com/jairleo95/TALENTO_HUMANO)
- Config nginx: `deploy/nginx.conf`
