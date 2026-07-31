CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS role (
    id UUID PRIMARY KEY,
    name VARCHAR(64) UNIQUE NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS privilege (
    id UUID PRIMARY KEY,
    code VARCHAR(64) UNIQUE NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS role_privilege (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    role_id UUID NOT NULL REFERENCES role (id) ON DELETE CASCADE,
    privilege_id UUID NOT NULL REFERENCES privilege (id) ON DELETE CASCADE,
    UNIQUE(role_id, privilege_id)
);

CREATE TABLE IF NOT EXISTS user_account (
    id UUID PRIMARY KEY,
    username VARCHAR(64) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS user_role (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES user_account (id) ON DELETE CASCADE,
    role_id UUID NOT NULL REFERENCES role (id) ON DELETE CASCADE,
    UNIQUE(user_id, role_id)
);

INSERT INTO role (id, name, description) VALUES
    (uuid_generate_v4(), 'ADMIN', 'Administrador del sistema'),
    (uuid_generate_v4(), 'USER', 'Usuario estándar');
