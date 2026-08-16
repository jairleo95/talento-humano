-- NOTA: Usar solo para desarrollo local. La contraseña debe coincidir con DB_PASSWORD.
-- Para producción usar deploy/install.sh, que crea los usuarios con una clave aleatoria.
CREATE USER identity_user WITH ENCRYPTED PASSWORD 'changeme';
CREATE DATABASE identity_db OWNER identity_user;

CREATE USER recruitment_user WITH ENCRYPTED PASSWORD 'changeme';
CREATE DATABASE recruitment_db OWNER recruitment_user;

CREATE USER contract_user WITH ENCRYPTED PASSWORD 'changeme';
CREATE DATABASE contract_db OWNER contract_user;
