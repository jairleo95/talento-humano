CREATE TABLE IF NOT EXISTS process (
    id UUID PRIMARY KEY,
    name VARCHAR(140) NOT NULL,
    code VARCHAR(50) NOT NULL,
    description TEXT,
    status VARCHAR(32) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    UNIQUE(code)
);

CREATE TABLE IF NOT EXISTS process_step (
    id UUID PRIMARY KEY,
    process_id UUID NOT NULL REFERENCES process (id) ON DELETE CASCADE,
    name VARCHAR(140) NOT NULL,
    code VARCHAR(50) NOT NULL,
    description TEXT,
    status VARCHAR(32) NOT NULL,
    order_index INT NOT NULL,
    sla_hours INT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    UNIQUE(process_id, code)
);

CREATE TABLE IF NOT EXISTS inbox_item (
    id UUID PRIMARY KEY,
    requisition_id UUID NOT NULL REFERENCES requisition (id) ON DELETE CASCADE,
    process_step_id UUID NOT NULL REFERENCES process_step (id) ON DELETE CASCADE,
    assignee VARCHAR(120) NOT NULL,
    status VARCHAR(32) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS cost_center (
    id UUID PRIMARY KEY,
    code VARCHAR(32) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    department_id VARCHAR(64),
    percentage NUMERIC(5,2),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);
