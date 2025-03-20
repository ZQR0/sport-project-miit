--liquibase formatted sql

--changeset olesyaTop: student-1

CREATE TABLE IF NOT EXISTS sport_schema.student_table (
    id               INTEGER       NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    fsp              VARCHAR(100)  NOT NULL UNIQUE,
    login            VARCHAR       NOT NULL UNIQUE,
    password_hash    VARCHAR       NOT NULL,
    healthGroup      INTEGER       NOT NULL,
    exist            JSON          NOT NULL,
)