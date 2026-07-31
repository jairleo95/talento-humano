ALTER TABLE user_account ADD COLUMN password_hash VARCHAR(100);

INSERT INTO user_account (id, version, username, email, enabled, password_hash, created_at) VALUES
    (gen_random_uuid(), 0, 'admin', 'admin@gth.local', TRUE,
     '$2b$12$N85WT8Utb.evqqL1Rbe0me5jBK0r/SwvbhhDOq03Xrxwhv7GtNIwq', now()),
    (gen_random_uuid(), 0, 'user', 'user@gth.local', TRUE,
     '$2b$12$WCGz5eWiZVUZYnTTcARcaet8X6HDM3Q.EXoDgI36QyidwRe3G6E6K', now());

INSERT INTO user_role (id, version, user_id, role_id)
SELECT gen_random_uuid(), 0, u.id, r.id
FROM user_account u
CROSS JOIN role r
WHERE u.username = 'admin' AND r.name = 'ADMIN';

INSERT INTO user_role (id, version, user_id, role_id)
SELECT gen_random_uuid(), 0, u.id, r.id
FROM user_account u
CROSS JOIN role r
WHERE u.username = 'user' AND r.name = 'USER';
