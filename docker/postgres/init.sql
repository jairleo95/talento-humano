CREATE USER identity_user WITH ENCRYPTED PASSWORD 'changeme';
CREATE DATABASE identity_db OWNER identity_user;

CREATE USER recruitment_user WITH ENCRYPTED PASSWORD 'changeme';
CREATE DATABASE recruitment_db OWNER recruitment_user;

CREATE USER contract_user WITH ENCRYPTED PASSWORD 'changeme';
CREATE DATABASE contract_db OWNER contract_user;
