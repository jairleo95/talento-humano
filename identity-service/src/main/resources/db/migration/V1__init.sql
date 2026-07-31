CREATE TABLE IF NOT EXISTS role (
    id UUID PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    name VARCHAR(64) UNIQUE NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS privilege (
    id UUID PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    code VARCHAR(64) UNIQUE NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS role_privilege (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    version BIGINT NOT NULL DEFAULT 0,
    role_id UUID NOT NULL REFERENCES role (id) ON DELETE CASCADE,
    privilege_id UUID NOT NULL REFERENCES privilege (id) ON DELETE CASCADE,
    UNIQUE(role_id, privilege_id)
);

CREATE TABLE IF NOT EXISTS user_account (
    id UUID PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    username VARCHAR(64) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS user_role (
    id UUID PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    user_id UUID NOT NULL REFERENCES user_account (id) ON DELETE CASCADE,
    role_id UUID NOT NULL REFERENCES role (id) ON DELETE CASCADE,
    UNIQUE(user_id, role_id)
);

INSERT INTO role (id, name, description) VALUES
    (gen_random_uuid(), 'ADMIN', 'Administrador del sistema'),
    (gen_random_uuid(), 'USER', 'Usuario estándar');
