ALTER TABLE privilege ADD COLUMN link_url VARCHAR(255);
ALTER TABLE privilege ADD COLUMN icon VARCHAR(64);
ALTER TABLE privilege ADD COLUMN module_name VARCHAR(128);
ALTER TABLE privilege ADD COLUMN sort_order INT DEFAULT 0;

ALTER TABLE role_privilege ADD COLUMN IF NOT EXISTS sort_order INT DEFAULT 0;
ALTER TABLE role_privilege ADD COLUMN IF NOT EXISTS is_active BOOLEAN DEFAULT TRUE;

-- Seed privileges for existing modules
INSERT INTO privilege (id, version, code, description, link_url, icon, module_name, sort_order)
SELECT gen_random_uuid(), 0, 'REQUIREMENTS', 'Requerimientos DGP', '/requirements', 'pi pi-file', 'Requerimientos', 1
WHERE NOT EXISTS (SELECT 1 FROM privilege WHERE code = 'REQUIREMENTS');

INSERT INTO privilege (id, version, code, description, link_url, icon, module_name, sort_order)
SELECT gen_random_uuid(), 0, 'PROCESSES', 'Procesos', '/processes', 'pi pi-sitemap', 'Procesos', 2
WHERE NOT EXISTS (SELECT 1 FROM privilege WHERE code = 'PROCESSES');

INSERT INTO privilege (id, version, code, description, link_url, icon, module_name, sort_order)
SELECT gen_random_uuid(), 0, 'INBOX', 'Bandeja de entrada', '/inbox', 'pi pi-inbox', 'Procesos', 3
WHERE NOT EXISTS (SELECT 1 FROM privilege WHERE code = 'INBOX');

INSERT INTO privilege (id, version, code, description, link_url, icon, module_name, sort_order)
SELECT gen_random_uuid(), 0, 'CONTRACTS', 'Contratos', '/contracts', 'pi pi-id-card', 'Contratos', 4
WHERE NOT EXISTS (SELECT 1 FROM privilege WHERE code = 'CONTRACTS');

INSERT INTO privilege (id, version, code, description, link_url, icon, module_name, sort_order)
SELECT gen_random_uuid(), 0, 'WORKERS', 'Trabajadores', '/workers', 'pi pi-users', 'Personal', 5
WHERE NOT EXISTS (SELECT 1 FROM privilege WHERE code = 'WORKERS');

INSERT INTO privilege (id, version, code, description, link_url, icon, module_name, sort_order)
SELECT gen_random_uuid(), 0, 'ORG_STRUCTURE', 'Organigrama', '/organization', 'pi pi-building', 'Personal', 6
WHERE NOT EXISTS (SELECT 1 FROM privilege WHERE code = 'ORG_STRUCTURE');

INSERT INTO privilege (id, version, code, description, link_url, icon, module_name, sort_order)
SELECT gen_random_uuid(), 0, 'BUDGET', 'Presupuesto', '/budget', 'pi pi-chart-bar', 'Presupuesto', 7
WHERE NOT EXISTS (SELECT 1 FROM privilege WHERE code = 'BUDGET');

INSERT INTO privilege (id, version, code, description, link_url, icon, module_name, sort_order)
SELECT gen_random_uuid(), 0, 'ACADEMIC', 'Académico', '/academic', 'pi pi-book', 'Académico', 8
WHERE NOT EXISTS (SELECT 1 FROM privilege WHERE code = 'ACADEMIC');

INSERT INTO privilege (id, version, code, description, link_url, icon, module_name, sort_order)
SELECT gen_random_uuid(), 0, 'FUNCTIONS', 'Funciones', '/functions', 'pi pi-list', 'Personal', 9
WHERE NOT EXISTS (SELECT 1 FROM privilege WHERE code = 'FUNCTIONS');

INSERT INTO privilege (id, version, code, description, link_url, icon, module_name, sort_order)
SELECT gen_random_uuid(), 0, 'REPORTS', 'Reportes', '/reports', 'pi pi-chart-line', 'Reportes', 10
WHERE NOT EXISTS (SELECT 1 FROM privilege WHERE code = 'REPORTS');

INSERT INTO privilege (id, version, code, description, link_url, icon, module_name, sort_order)
SELECT gen_random_uuid(), 0, 'USERS', 'Usuarios', '/users', 'pi pi-shield', 'Seguridad', 11
WHERE NOT EXISTS (SELECT 1 FROM privilege WHERE code = 'USERS');

INSERT INTO privilege (id, version, code, description, link_url, icon, module_name, sort_order)
SELECT gen_random_uuid(), 0, 'ROLES', 'Roles y Privilegios', '/privileges', 'pi pi-key', 'Seguridad', 12
WHERE NOT EXISTS (SELECT 1 FROM privilege WHERE code = 'ROLES');

-- Assign all privileges to ADMIN role
INSERT INTO role_privilege (id, role_id, privilege_id, sort_order, is_active)
SELECT gen_random_uuid(), r.id, p.id, p.sort_order, TRUE
FROM role r CROSS JOIN privilege p
WHERE r.name = 'ADMIN'
AND NOT EXISTS (
    SELECT 1 FROM role_privilege rp 
    WHERE rp.role_id = r.id AND rp.privilege_id = p.id
);

-- Assign REQUIREMENTS to USER role
INSERT INTO role_privilege (id, role_id, privilege_id, sort_order, is_active)
SELECT gen_random_uuid(), r.id, p.id, 1, TRUE
FROM role r CROSS JOIN privilege p
WHERE r.name = 'USER' AND p.code = 'REQUIREMENTS'
AND NOT EXISTS (
    SELECT 1 FROM role_privilege rp 
    WHERE rp.role_id = r.id AND rp.privilege_id = p.id
);
