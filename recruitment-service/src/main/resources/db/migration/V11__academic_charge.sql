CREATE TABLE IF NOT EXISTS academic_charge (
    id UUID PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    worker_id UUID NOT NULL REFERENCES worker(id),
    semester VARCHAR(32) NOT NULL,
    faculty VARCHAR(128),
    school VARCHAR(128),
    educational_situation VARCHAR(128),
    profession VARCHAR(128),
    condicion VARCHAR(64),
    pay_type VARCHAR(64),
    total_hours NUMERIC(10,2),
    start_date DATE,
    end_date DATE,
    status VARCHAR(32) NOT NULL,
    created_by VARCHAR(120) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS academic_course (
    id UUID PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    charge_id UUID NOT NULL REFERENCES academic_charge(id) ON DELETE CASCADE,
    campus VARCHAR(64),
    course_name VARCHAR(255) NOT NULL,
    group_number VARCHAR(32),
    schedule VARCHAR(255),
    hours NUMERIC(10,2),
    course_condition VARCHAR(64),
    course_type VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS academic_payment (
    id UUID PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    charge_id UUID NOT NULL REFERENCES academic_charge(id) ON DELETE CASCADE,
    quota_number INTEGER NOT NULL,
    amount NUMERIC(14,2) NOT NULL,
    payment_date DATE,
    status VARCHAR(32) NOT NULL DEFAULT 'PENDIENTE'
);