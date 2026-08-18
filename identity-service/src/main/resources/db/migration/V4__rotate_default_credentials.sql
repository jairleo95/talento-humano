-- Rota las credenciales por defecto (admin/admin123, user/user123) hacia
-- valores aleatorios inyectados por variable de entorno mediante flyway placeholders.
UPDATE user_account SET password_hash = '${adminInitialPasswordHash}' WHERE username = 'admin';
UPDATE user_account SET password_hash = '${userInitialPasswordHash}' WHERE username = 'user';