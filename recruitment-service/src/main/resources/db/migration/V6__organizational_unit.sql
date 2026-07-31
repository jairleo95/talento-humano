CREATE TABLE IF NOT EXISTS organizational_unit (
    id UUID PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    name VARCHAR(255) NOT NULL,
    short_name VARCHAR(64),
    unit_type VARCHAR(32) NOT NULL,
    parent_id UUID REFERENCES organizational_unit(id),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);
