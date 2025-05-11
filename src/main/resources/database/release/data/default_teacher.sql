--liquibase formatted sql

--changeset yaroslav:default_teacher-2
INSERT INTO sport_schema.teacher_table (fsp, is_moderator, schedule, login, password_hash)
VALUES ('Иванов Иван Иванович', false, '{"2025-04-04": "PE"}', 'test_login', '$2y$10$rEkUN5MD9NPywbeztOI0SeQU3Jc8MWRMRRdAAcJPkv4GMKZ4Ndg0K');
