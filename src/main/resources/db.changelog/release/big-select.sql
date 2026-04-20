SELECT
    --id, ФИО студента--
    s.id,
    s.last_name,
    s.first_name,
    s.patronymic,

    --Название группы, институт, которому принадлежит группа--
    g.id AS group_id,
    g.name AS group_name,
    g.institute AS group_institute,

    --Название секции и описание--
    sec.id AS section_id,
    sec.name AS section_name,
    sec.description AS section_description,

    --GROUP_CONCAT(
         --DISTINCT CONCAT(sec.id, sec.name, ' (', sec.description, ')')
         --ORDER BY sec.name
         --SEPARATOR '; '
    --) AS id_section_sections_with_description,

    --Группа здоровья--
    he_gr.id AS id_health_group,
    he_gr.name AS health_group_name,

    --Про преподавателя--
    teach.id AS id_teacher,
    teach.last_name AS teacher_last_name,
    teach.first_name AS teacher_first_name,
    teach.patronymic AS teacher_patronymic,
    teach.is_moderator AS teacher_is_moderator,

    --Количество всех посещений
    COUNT(vis.id) OVER (PARTITION BY s.id) AS total_lessons,
    --Информация о посещении
    vis.id AS visit_id,
    vis.student_id AS visit_student_id,
    vis.lesson_id AS visit_lesson_id,
    vis.is_exists AS visit_is_exists,

    --Информация о занятии
    les.id AS lesson_id,
    les.discipline_id AS lesson_disc_id,
    les.date_of_lesson AS date_of_lesson,
    les.teacher_id AS lesson_teacher_id,

    --Последнее занятие (общее для студента, повторяется в каждой строке)
    MAX(les.date_of_lesson) OVER (PARTITION BY s.id) AS last_lesson_date,

    --Дисциплины
    disc.id AS discipline_id,
    disc.name AS discipline_name

FROM students s

INNER JOIN groups g ON s.group_id = g.id
LEFT JOIN sections sec ON s.section_id = sec.id
LEFT JOIN health_groups he_gr ON s.health_group_id = he_gr.id
LEFT JOIN visits vis ON s.id = vis.student_id
LEFT JOIN lessons les ON vis.lesson_id = les.id
LEFT JOIN teacher teach ON les.teacher_id = teach.id
LEFT JOIN disciplines disc ON les.discipline_id = disc.id

WHERE s.id = 1;

--TRUNCATE TABLE team RESTART IDENTITY CASCADE;
--TRUNCATE TABLE have_powers RESTART IDENTITY CASCADE;
--TRUNCATE TABLE fighters RESTART IDENTITY CASCADE;
--TRUNCATE TABLE hero RESTART IDENTITY CASCADE;
--TRUNCATE TABLE headquarters RESTART IDENTITY CASCADE;
--TRUNCATE TABLE epic_fight RESTART IDENTITY cascade;
--TRUNCATE TABLE super_powers RESTART IDENTITY CASCADE;