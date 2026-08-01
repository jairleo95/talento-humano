CREATE TABLE IF NOT EXISTS dgp_comment (
    id UUID PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    requisition_id UUID NOT NULL REFERENCES requisition(id) ON DELETE CASCADE,
    user_id VARCHAR(64),
    username VARCHAR(120),
    content TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS dgp_document (
    id UUID PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    requisition_id UUID NOT NULL REFERENCES requisition(id) ON DELETE CASCADE,
    filename VARCHAR(255) NOT NULL,
    content_type VARCHAR(128),
    description VARCHAR(500),
    uri VARCHAR(500),
    size_bytes BIGINT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);
