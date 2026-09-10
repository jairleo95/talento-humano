CREATE TABLE IF NOT EXISTS academic_modality (
    id UUID PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    code VARCHAR(32) NOT NULL UNIQUE,
    name VARCHAR(128) NOT NULL,
    sub_modality VARCHAR(128),
    sort_order INT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS academic_period (
    id UUID PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    code VARCHAR(32) NOT NULL UNIQUE,
    name VARCHAR(128) NOT NULL,
    start_date DATE,
    end_date DATE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

ALTER TABLE academic_charge ADD COLUMN IF NOT EXISTS modality_id UUID REFERENCES academic_modality(id);
ALTER TABLE academic_charge ADD COLUMN IF NOT EXISTS period_id UUID REFERENCES academic_period(id);

-- Semillas iniciales para modalidades
INSERT INTO academic_modality (id, version, code, name, sub_modality, sort_order, is_active, created_at)
VALUES
    (gen_random_uuid(), 0, 'PRES', 'Presencial', 'Presencial Regular', 1, TRUE, now()),
    (gen_random_uuid(), 0, 'SEMI', 'Semipresencial', 'Fines de Semana', 2, TRUE, now()),
    (gen_random_uuid(), 0, 'VIRT', 'Virtual', 'Distancia / E-learning', 3, TRUE, now())
ON CONFLICT (code) DO NOTHING;

-- Semillas iniciales para períodos académicos
INSERT INTO academic_period (id, version, code, name, start_date, end_date, is_active, created_at)
VALUES
    (gen_random_uuid(), 0, '2026-I', 'Semestre Académico 2026-I', '2026-03-01', '2026-07-15', TRUE, now()),
    (gen_random_uuid(), 0, '2026-II', 'Semestre Académico 2026-II', '2026-08-01', '2026-12-15', TRUE, now())
ON CONFLICT (code) DO NOTHING;
