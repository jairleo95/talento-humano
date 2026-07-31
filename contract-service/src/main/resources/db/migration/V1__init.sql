CREATE TABLE IF NOT EXISTS contract_template (
    id UUID PRIMARY KEY,
    name VARCHAR(140) NOT NULL,
    version INT NOT NULL,
    content TEXT NOT NULL,
    file_name VARCHAR(255),
    status VARCHAR(32),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by VARCHAR(120),
    UNIQUE(name, version)
);

CREATE TABLE IF NOT EXISTS contract (
    id UUID PRIMARY KEY,
    requisition_id UUID NOT NULL,
    template_id UUID NOT NULL REFERENCES contract_template (id),
    contract_number VARCHAR(64),
    position_id VARCHAR(64),
    worker_id VARCHAR(64),
    start_date TIMESTAMP WITH TIME ZONE,
    end_date TIMESTAMP WITH TIME ZONE,
    termination_date TIMESTAMP WITH TIME ZONE,
    condition_type VARCHAR(64),
    salary_amount NUMERIC(14,2),
    reintegration_amount NUMERIC(14,2),
    family_allowance NUMERIC(14,2),
    weekly_hours NUMERIC(8,2),
    daily_hours NUMERIC(8,2),
    labor_regime VARCHAR(64),
    pension_regime VARCHAR(64),
    contract_type VARCHAR(64),
    observation TEXT,
    status VARCHAR(32) NOT NULL,
    signed_at TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,
    created_by VARCHAR(120),
    updated_by VARCHAR(120)
);

CREATE TABLE IF NOT EXISTS contract_attachment (
    id UUID PRIMARY KEY,
    contract_id UUID NOT NULL REFERENCES contract (id) ON DELETE CASCADE,
    filename VARCHAR(255) NOT NULL,
    content_type VARCHAR(120) NOT NULL,
    uri VARCHAR(512) NOT NULL,
    size_bytes BIGINT,
    checksum VARCHAR(128),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);
