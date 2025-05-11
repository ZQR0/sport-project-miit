--liquibase formatted sql

--changeset olesyatop:student-3
CREATE TABLE IF NOT EXISTS sport_schema.student_table (
    student_id       INTEGER       NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    fsp              VARCHAR(100)  NOT NULL UNIQUE,
    login            VARCHAR       NOT NULL UNIQUE,
    password_hash    VARCHAR       NOT NULL,
    health_group     INTEGER       NOT NULL,
    exist            JSON          NOT NULL,
    teacher_id       INTEGER       NOT NULL,
    FOREIGN KEY      (teacher_id)  REFERENCES sport_schema.teacher_table
);