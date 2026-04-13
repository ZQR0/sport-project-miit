--liquibase formatted sql

--changeset olesya:ddl-02
CREATE TABLE IF NOT EXISTS groups (
    id    INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name        VARCHAR(15)      NOT NULL,
    institute   VARCHAR(100)    NOT NULL,

    CONSTRAINT groups_name_length_malo CHECK(LENGTH(name) <= 15),
    CONSTRAINT institute_length_mnogo CHECK (LENGTH(institute) <= 100),
    CONSTRAINT name_correct CHECK (name ~ '^[А-Я]{3}[а-я]?-[0-9]{3}$'),
    CONSTRAINT name_unique UNIQUE(name)
);

CREATE TABLE IF NOT EXISTS sections (
    id     INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name           VARCHAR     NOT NULL,
    description    VARCHAR     NOT NULL,

    CONSTRAINT sections_name_length_malo CHECK (LENGTH(name) <= 100),
    CONSTRAINT sections_name_unique UNIQUE(name),

    CONSTRAINT sections_description_length_exceeding CHECK (LENGTH(description) <= 100)
);

CREATE TABLE IF NOT EXISTS health_groups (
    id     INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name                VARCHAR     NOT NULL,
    description         VARCHAR     NOT NULL,

    CONSTRAINT health_groups_name_length_malo CHECK (LENGTH(name) <= 100),
    CONSTRAINT health_groups_unique UNIQUE(name),

    CONSTRAINT health_groups_description_length_exceeding CHECK (LENGTH(description) <= 100)
);

CREATE TABLE IF NOT EXISTS disciplines (
    id   INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name            VARCHAR    NOT NULL,

    CONSTRAINT disciplines_name_length_malo CHECK (LENGTH(name) <= 100),
    CONSTRAINT disciplines_name_unique UNIQUE(name)
);


CREATE TABLE IF NOT EXISTS students (
    id      INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    login           VARCHAR(50) NOT NULL,
    password_hash   VARCHAR     NOT NULL,
    last_name       VARCHAR(50) NOT NULL,
    first_name      VARCHAR(50) NOT NULL,
    patronymic      VARCHAR(50) NOT NULL,
    birthday        DATE        NOT NULL,
    health_group_id INTEGER     NOT NULL,
    group_id        INTEGER     NOT NULL,
    section_id      INTEGER,

    CONSTRAINT students_login_length_malo CHECK (LENGTH(login) <= 50),
    CONSTRAINT students_last_name_length_malo CHECK (LENGTH(last_name) <= 50),
    CONSTRAINT students_first_name_length_malo CHECK (LENGTH(first_name) <= 50),
    CONSTRAINT students_patronymic_length_malo CHECK (LENGTH(patronymic) <= 50),
    CONSTRAINT students_login_unique UNIQUE(login),

    CONSTRAINT students_health_group_foreign_key FOREIGN KEY (health_group_id) REFERENCES health_groups(id) ON DELETE RESTRICT,
    CONSTRAINT students_group_id_foreign_key FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE RESTRICT,
    CONSTRAINT students_section_id_foreign_key FOREIGN KEY (section_id) REFERENCES sections(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS teachers (
    id      INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    login           VARCHAR,
    password_hash   VARCHAR NOT NULL,
    last_name       VARCHAR(50) NOT NULL,
    first_name      VARCHAR(50) NOT NULL,
    patronymic      VARCHAR(50) NOT NULL,
    birthday        DATE NOT NULL,
    is_moderator    BOOLEAN NOT NULL,

    CONSTRAINT teachers_login_length_malo CHECK (LENGTH(login) <= 50),
    CONSTRAINT teachers_last_name_length_malo CHECK (LENGTH(last_name) <= 50),
    CONSTRAINT teachers_first_name_length_malo CHECK (LENGTH(first_name) <= 50),
    CONSTRAINT teachers_patronymic_length_malo CHECK (LENGTH(patronymic) <= 50),
    CONSTRAINT teachers_login_not_null CHECK (login IS NOT NULL),
    CONSTRAINT teachers_login_unique UNIQUE(login)
);

CREATE TABLE IF NOT EXISTS lessons (
    id       INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    discipline_id   INTEGER NOT NULL,
    date_of_lesson  DATE NOT NULL,
    teacher_id      INTEGER NOT NULL,
    start_at        TIME NOT NULL,
    end_at          TIME NOT NULL,

    CONSTRAINT discipline_id_foreign_key FOREIGN KEY (discipline_id) REFERENCES disciplines(id) ON DELETE CASCADE,
    CONSTRAINT teacher_id_foreign_key FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS visits (
    id    INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    student_id  INTEGER,
    lesson_id   INTEGER,
    is_exists   BOOLEAN,

    CONSTRAINT student_id_foreign_key FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    CONSTRAINT lesson_id_foreign_key FOREIGN KEY (lesson_id) REFERENCES lessons(id) ON DELETE CASCADE
);