-- ПРЕПОДАВАТЕЛИ
-- api/teachers/find-by-login?login={login} Поиск преподавателя по логину
SELECT last_name, first_name, patronymic, birthday from teachers 
	WHERE login LIKE 'sid%'; -- заменить на переменную
	

SELECT last_name, first_name, patronymic, birthday from teachers
	WHERE last_name LIKE 'Иванов' and
	first_name LIKE 'Иван' and
	patronymic LIKE 'Иванович';


-- api/teachers/find-by-lfp Поиск преподавателя по ФИО
SELECT last_name, first_name, patronymic, birthday from teachers 
	WHERE last_name LIKE 'Пе%' or -- заменить на переменную
	first_name LIKE 'Пе%' or -- заменить на переменную
	patronymic LIKE 'Пе%'; -- заменить на переменную
-- одна и та же переменная для всех
-- или соединить фио в одно и прописать поиск с % с двух сторох		
	
	
-- api/teachers/find-all Список всех преподавателей
SELECT last_name, first_name, patronymic, birthday from teachers; 


-- api/teachers/find-all-moderators Список модераторов (преподавателей)
SELECT last_name, first_name, patronymic, birthday from teachers
	WHERE is_moderator = true; 


-- api/teachers/create Создание нового преподавателя
INSERT INTO teachers (login, password_hash, last_name, first_name, patronymic, birthday, is_moderator) VALUES
	('ivanov_fv', 'hash145684', 'Иванов', 'Федор', 'Владимирович', '1981-07-13', true); -- заменить вводимыми данными

	
-- api/teachers/notice Преподаватель отмечает присутствие студента на паре
UPDATE visits SET is_exists = true 
	WHERE student_id = (SELECT student_id from students WHERE login = 'student3' LIMIT 1) -- заменить на переменную
	

-- СТУДЕНТЫ
-- api/students/find-by-login Поиск студента по логину
SELECT last_name, first_name, patronymic, birthday, health_groups.name, groups.name from students 
	JOIN groups ON students.group_id = groups.group_id
	JOIN health_groups ON students.health_group = health_groups.health_group_id 
	WHERE login LIKE 'student1%'; -- заменить на переменную
	
	
-- api/students/find-by-LFP Поиск студента по LFP (ФИО, с заменой "_" на пробел). 
SELECT last_name, first_name, patronymic, birthday, health_groups.name, groups.name from students 
	JOIN groups ON students.group_id = groups.group_id
	JOIN health_groups ON students.health_group = health_groups.health_group_id 
	WHERE last_name LIKE 'Ан%' or -- заменить на переменную
	first_name LIKE 'Ан%' or -- заменить на переменную
	patronymic LIKE 'Ан%'; -- заменить на переменную
-- одна и та же переменная для всех
-- или соединить фио в одно и прописать поиск с % с двух сторох		
	
	
-- api/students/find-all Список всех студентов
SELECT last_name, first_name, patronymic, birthday, health_groups.name, groups.name from students 
	JOIN groups ON students.group_id = groups.group_id
	JOIN health_groups ON students.health_group = health_groups.health_group_id 	
	
	
-- api/students/create Создание нового студента
INSERT INTO students (login, password_hash, last_name, first_name, patronymic, birthday, health_group, group_id, section_id) VALUES
    -- Группа УВП-111
    ('student11', 'pass321', 'Киселева', 'Алина', 'Юрьевна', '2006-07-19', 1, 1, NULL); -- заменить вводимыми данными
    
    
-- ГРУППЫ    
-- /api/groups/find-by-name Поиск группы по имени группы
SELECT name from groups
	WHERE name LIKE '%111%'; -- заменить на переменную
    
	
-- /api/groups/find-students-by-group?group={group} Поиск всех студентов группы
SELECT last_name, first_name, patronymic, birthday, health_groups.name, groups.name from students 
	JOIN groups ON students.group_id = groups.group_id
	JOIN health_groups ON students.health_group = health_groups.health_group_id 
	WHERE groups.name LIKE 'УВП-111'; -- заменить на переменную

	
-- /api/groups/create Создание группы
INSERT INTO groups (name, institute) VALUES
    ('УВП-211', 'Институт управления и цифровых технологий (ИУЦТ)'); -- заменить вводимыми данными
    
    
-- /api/groups/find-by-institute?institute={insitute} Найти группы по институту
SELECT name from groups
	WHERE institute LIKE '%ИУЦТ%'; -- заменить на переменную   
	
	
-- СЕКЦИИ
-- /api/disciplines/find-by-name?name={name} Найти секцию по имени
SELECT name, description from sections
	WHERE name LIKE 'Ф%';	


-- /api/disciplines/create Создать секцию
INSERT INTO sections (name, description) VALUES
	('Бокс', 'Секция по боксу для студентов');


-- /api/disciplines/find-all Весь список секций
SELECT name, description from sections;


-- вывести всех студентов какой то конкретной группы;
SELECT last_name, first_name, patronymic,
		groups.name as group, health_groups.name as health_group from students
	JOIN groups ON students.group_id = groups.group_id
	JOIN health_groups ON students.health_group = health_groups.health_group_id
	WHERE groups.name = 'УВП-111';

--вывести студентов, которые посетили занятие (по имени дисциплины)
SELECT last_name, first_name, patronymic,
		groups.name as group, health_groups.name as health_group from students
	JOIN visits ON students.student_id = visits.student_id
	JOIN lessons ON visits.lesson_id = lessons.lesson_id
	JOIN groups ON students.group_id = groups.group_id
	JOIN disciplines ON lessons.discipline_id = disciplines.discipline_id
	JOIN health_groups ON students.health_group = health_groups.health_group_id
	WHERE visits.is_exists = true AND disciplines.name = 'Физическая культура и спорт';


-- вывести всех студентов конкретного занятия по определенной дате
SELECT last_name, first_name, patronymic,
		groups.name as group, health_groups.name as health_group from students
	JOIN visits ON students.student_id = visits.student_id
	JOIN lessons ON visits.lesson_id = lessons.lesson_id
	JOIN groups ON students.group_id = groups.group_id
	JOIN health_groups ON students.health_group = health_groups.health_group_id
	WHERE date_of_lesson = '2024-09-02';


-- вывести преподавателя и его расписание
-- (занятия, которые он провел, даты этих занятий, группы, у которых были занятия,
-- короче ключевой параметр сам препод)
SELECT DISTINCT t.last_name, t.first_name, t.patronymic,
		disciplines.name as discipline_name,
		lessons.date_of_lesson, groups.name as group_name from teachers t
	JOIN lessons ON t.teacher_id = lessons.teacher_id
	JOIN disciplines ON lessons.discipline_id = disciplines.discipline_id
	JOIN visits ON lessons.lesson_id = visits.lesson_id
	JOIN students ON visits.student_id = students.student_id
	JOIN groups ON students.group_id = groups.group_id
	WHERE t.login = 'ivanov_iv';

	