--liquibase formatted sql

--changeset olesyatop:teacher-2
CREATE TABLE IF NOT EXISTS sport_schema.teacher_table (
    teacher_id               INTEGER       NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    fsp              VARCHAR(100)  NOT NULL UNIQUE,
    is_moderator     BOOLEAN       NOT NULL,
    schedule         JSONB         NOT NULL,
    login            VARCHAR       NOT NULL UNIQUE,
    password_hash    VARCHAR       NOT NULL
);