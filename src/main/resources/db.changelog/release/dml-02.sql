--liquibase formatted sql

--changeset yaroslav:dml-02
INSERT INTO students (login, password_hash, last_name, first_name, patronymic, birthday, health_group_id, group_id, section_id) VALUES
    -- Группа УВП-111
    ('МилиБро2', '$2a$10$GuSp6jApr.jGCA8uWn/QxOgDM6YirQ0dVZe9uNQ9H7CHAmjYVRSGe', 'Речкалов', 'Ярослав', 'Иванович', '2006-04-21', 1, 1, 7),
    ('student48', '$2a$10$GuSp6jApr.jGCA8uWn/QxOgDM6YirQ0dVZe9uNQ9H7CHAmjYVRSGe', 'Борисова', 'Анна', 'Павловна', '2005-06-23', 2, 1, 4);


INSERT INTO teachers (login, password_hash, last_name, first_name, patronymic, birthday, is_moderator) VALUES
    ('ivanov_ivchik', '$2a$10$GuSp6jApr.jGCA8uWn/QxOgDM6YirQ0dVZe9uNQ9H7CHAmjYVRSGe', 'Иванов', 'Иван', 'Иванович', '1980-05-15', true),
    ('petrova_mama', '$2a$10$GuSp6jApr.jGCA8uWn/QxOgDM6YirQ0dVZe9uNQ9H7CHAmjYVRSGe', 'Петрова', 'Мария', 'Александровна', '1985-08-22', false);