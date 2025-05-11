--liquibase formatted sql

--changeset yaroslav:default_student-5
INSERT INTO sport_schema.student_table (fsp, login, password_hash, health_group, exist, teacher_id)
VALUES ('Сидоров Петя Владимирович', 'test_student', '$2y$10$rEkUN5MD9NPywbeztOI0SeQU3Jc8MWRMRRdAAcJPkv4GMKZ4Ndg0K', 1, '{"2025-04-04": true}', 1);