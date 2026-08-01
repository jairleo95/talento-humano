CREATE TABLE IF NOT EXISTS budget_period (
    id UUID PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    name VARCHAR(255) NOT NULL,
    start_date TIMESTAMP WITH TIME ZONE,
    end_date TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS budget_allocation (
    id UUID PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    period_id UUID NOT NULL REFERENCES budget_period(id) ON DELETE CASCADE,
    requirement_type VARCHAR(32),
    position_id VARCHAR(64),
    worker_count INT DEFAULT 1,
    min_salary NUMERIC(14,2),
    max_salary NUMERIC(14,2),
    min_bonus NUMERIC(14,2),
    max_bonus NUMERIC(14,2),
    min_food_bonus NUMERIC(14,2),
    max_food_bonus NUMERIC(14,2),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);
