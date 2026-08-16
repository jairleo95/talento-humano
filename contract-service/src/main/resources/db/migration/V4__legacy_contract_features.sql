-- Add signed document fields to contract table
ALTER TABLE contract ADD COLUMN IF NOT EXISTS signed_file_url VARCHAR(512);
ALTER TABLE contract ADD COLUMN IF NOT EXISTS signed_at TIMESTAMP WITH TIME ZONE;
ALTER TABLE contract ADD COLUMN IF NOT EXISTS signed_by VARCHAR(120);

-- Table for hierarchical template assignment by org structure and position
CREATE TABLE IF NOT EXISTS contract_template_assignment (
    id UUID PRIMARY KEY,
    template_id UUID NOT NULL REFERENCES contract_template(id),
    filial_id VARCHAR(64),
    direction_id VARCHAR(64),
    department_id VARCHAR(64),
    area_id VARCHAR(64),
    position_id VARCHAR(64),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);
