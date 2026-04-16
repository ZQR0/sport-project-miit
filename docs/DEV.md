# Документация по проекту «Все на спорт»

**Дата обновления:** 2026-04-16
**Версия API:** 0.1.0
**Сервер:** http://localhost:4444

---

## 1. Иерархия сущностей

### 1.1. AbstractEntity<ID>

Базовый абстрактный класс для всех сущностей. Содержит идентификатор `id` с автоматической генерацией (IDENTITY). Использует аннотацию `@MappedSuperclass`.

**Пакет:** `com.sport.project.dao.entity`

- `id` (ID extends Serializable): первичный ключ, генерируется автоматически через `@GeneratedValue(strategy = GenerationType.IDENTITY)`

### 1.2. FullName

Встраиваемый класс (Embeddable) для хранения ФИО.

**Пакет:** `com.sport.project.dao.entity`

- `firstName` (String): имя, column: `first_name`, NOT NULL
- `lastName` (String): фамилия, column: `last_name`, NOT NULL
- `patronymic` (String): отчество, column: `patronymic`, может быть NULL

### 1.3. UserEntity<ID>

Абстрактный базовый класс для пользователей. Наследует `AbstractEntity<ID>`. Помечен `@MappedSuperclass`.

**Пакет:** `com.sport.project.dao.entity`

- `login` (String): уникальный логин, column: `login`, unique, length=50, NOT NULL
- `passwordHash` (String): хэш пароля, column: `password_hash`, NOT NULL
- `fullName` (FullName): встраиваемый объект (Embedded) с именем, фамилией, отчеством
- `birthday` (LocalDate): дата рождения, column: `birthday`, columnDefinition="DATE"

---

## 2. Сущности (Entity)

### 2.1. StudentEntity

Наследник `UserEntity`, представляет студента.

**Пакет:** `com.sport.project.dao.entity`
**Таблица:** `students`
**JPA name:** `student_entity`

**Собственные поля:**
- `healthGroup` (HealthGroupsEntity): ManyToOne(fetch=LAZY), joinColumn: `health_group_id`, NOT NULL
- `group` (GroupEntity): ManyToOne(fetch=LAZY), joinColumn: `group_id`, NOT NULL
- `section` (SectionEntity): ManyToOne(fetch=LAZY), joinColumn: `section_id`, может быть NULL
- `visits` (List<VisitsEntity>): OneToMany(mappedBy="student", cascade={PERSIST, MERGE, REMOVE}, fetch=LAZY)

**Cascade:** на visits: `{PERSIST, MERGE, REMOVE}`

**Builder:** `StudentEntity.StudentEntityBuilder`
- `login(String)`, `passwordHash(String)`, `firstName(String)`, `lastName(String)`, `patronymic(String)`, `birthday(LocalDate)`, `healthGroup(HealthGroupsEntity)`, `group(GroupEntity)`, `section(SectionEntity)`, `visits(List<VisitsEntity>)`

### 2.2. TeacherEntity

Наследник `UserEntity`, представляет преподавателя.

**Пакет:** `com.sport.project.dao.entity`
**Таблица:** `teachers`
**JPA name:** `teacher_entity`

**Собственные поля:**
- `moderator` (boolean): флаг модератора, column: `is_moderator`, NOT NULL
- `lessons` (List<LessonsEntity>): OneToMany(mappedBy="teacher", cascade={PERSIST, MERGE}, fetch=LAZY)

**Cascade:** на lessons: `{PERSIST, MERGE}` (без REMOVE, т.к. в БД SET NULL)

**Builder:** `TeacherEntity.TeacherEntityBuilder`
- `login(String)`, `passwordHash(String)`, `firstName(String)`, `lastName(String)`, `patronymic(String)`, `birthday(LocalDate)`, `moderator(boolean)`, `lessons(List<LessonsEntity>)`

### 2.3. GroupEntity

Учебная группа.

**Пакет:** `com.sport.project.dao.entity`
**Таблица:** `groups`
**JPA name:** `groups_entity`

**Поля:**
- `name` (String): уникальное название, column: `name`, unique, length=15, NOT NULL
- `institute` (String): название института, column: `institute`, length=100, NOT NULL
- `students` (List<StudentEntity>): OneToMany(mappedBy="group", cascade={PERSIST, MERGE}, fetch=LAZY)

**Cascade:** на students: `{PERSIST, MERGE}` (без REMOVE, т.к. в БД RESTRICT)

**Конструкторы:**
- `GroupEntity(String name, String institute)`
- `@NoArgsConstructor`

**Builder:** `GroupEntityBuilder`

### 2.4. HealthGroupsEntity

Медицинская группа здоровья.

**Пакет:** `com.sport.project.dao.entity`
**Таблица:** `health_groups`
**JPA name:** `health_groups_entity`

**Поля:**
- `name` (String): уникальное название, column: `name`, unique, NOT NULL
- `description` (String): описание, column: `description`, length=100, NOT NULL
- `students` (List<StudentEntity>): OneToMany(mappedBy="healthGroup", cascade={PERSIST, MERGE}, fetch=LAZY)

**Cascade:** на students: `{PERSIST, MERGE}`

**Конструкторы:**
- `HealthGroupsEntity(String name, String description)`
- `HealthGroupsEntity(String name, String description, List<StudentEntity> students)`

**Builder:** `HealthEntityBuilder`

### 2.5. SectionEntity

Спортивная секция.

**Пакет:** `com.sport.project.dao.entity`
**Таблица:** `sections`
**JPA name:** `section_entity`

**Поля:**
- `name` (String): уникальное название, column: `name`, unique, length=100, NOT NULL
- `description` (String): описание, column: `description`, length=100, NOT NULL
- `studentsOnSection` (List<StudentEntity>): OneToMany(mappedBy="section") — **без cascade**

**Cascade:** нет (в БД SET NULL)

**Конструкторы:**
- `SectionEntity(String name, String description)`
- `SectionEntity(String name, String description, List<StudentEntity> studentsOnSection)`

**Builder:** `SectionEntityBuilder`

### 2.6. DisciplineEntity

Дисциплина (например, «Физическая культура и спорт»).

**Пакет:** `com.sport.project.dao.entity`
**Таблица:** `disciplines`
**JPA name:** `discipline_entity`

**Поля:**
- `name` (String): уникальное название, column: `name`, unique, length=100, NOT NULL
- `lessonsEntities` (List<LessonsEntity>): OneToMany(mappedBy="discipline", cascade={PERSIST, MERGE, REMOVE}, fetch=LAZY)

**Cascade:** на lessons: `{PERSIST, MERGE, REMOVE}` (в БД CASCADE)

**Конструкторы:**
- `DisciplineEntity(String name)`
- `DisciplineEntity(String name, List<LessonsEntity> lessonsEntities)`

**Builder:** `DisciplineEntityBuilder`

### 2.7. LessonsEntity

Занятие (пара).

**Пакет:** `com.sport.project.dao.entity`
**Таблица:** `lessons`
**JPA name:** `lessons_entity`

**Поля:**
- `discipline` (DisciplineEntity): ManyToOne, joinColumn: `discipline_id`, NOT NULL
- `dateOfLesson` (LocalDate): дата занятия, column: `date_of_lesson`, columnDefinition="DATE", NOT NULL
- `teacher` (TeacherEntity): ManyToOne, joinColumn: `teacher_id`, NOT NULL
- `visits` (List<VisitsEntity>): OneToMany(mappedBy="lessons", cascade={PERSIST, MERGE, REMOVE}, fetch=LAZY)
- `startAt` (LocalTime): время начала, column: `start_at`, NOT NULL
- `endAt` (LocalTime): время окончания, column: `end_at`, NOT NULL

**Cascade:** на visits: `{PERSIST, MERGE, REMOVE}` (в БД CASCADE)

**Конструкторы:**
- `LessonsEntity(LocalDate dateOfLesson)`
- `LessonsEntity(LocalDate dateOfLesson, TeacherEntity teacher)`
- `@NoArgsConstructor`

**Builder:** `LessonsEntity.LessonEntityBuilder`

### 2.8. VisitsEntity

Посещаемость студента на занятии.

**Пакет:** `com.sport.project.dao.entity`
**Таблица:** `visits`
**JPA name:** `visits_entity`

**Поля:**
- `student` (StudentEntity): ManyToOne(fetch=LAZY), joinColumn: `student_id`
- `lessons` (LessonsEntity): ManyToOne(fetch=LAZY), joinColumn: `lesson_id`
- `isExists` (boolean): присутствие, column: `is_exists`, NOT NULL

**Cascade:** нет (родительские сущности управляют cascade)

**Конструкторы:**
- `VisitsEntity(boolean isExists)`
- `@NoArgsConstructor`

**Builder:** `VisitsEntityBuilder`

---

## 3. Сводная таблица связей сущностей

| Сущность | Таблица | Связи | Cascade |
|----------|---------|-------|---------|
| StudentEntity | students | ManyToOne: healthGroup, group, section; OneToMany: visits | {PERSIST, MERGE, REMOVE} на visits |
| TeacherEntity | teachers | OneToMany: lessons | {PERSIST, MERGE} на lessons |
| GroupEntity | groups | OneToMany: students | {PERSIST, MERGE} на students |
| HealthGroupsEntity | health_groups | OneToMany: students | {PERSIST, MERGE} на students |
| SectionEntity | sections | OneToMany: studentsOnSection | нет |
| DisciplineEntity | disciplines | OneToMany: lessonsEntities | {PERSIST, MERGE, REMOVE} на lessons |
| LessonsEntity | lessons | ManyToOne: discipline, teacher; OneToMany: visits | {PERSIST, MERGE, REMOVE} на visits |
| VisitsEntity | visits | ManyToOne: student, lessons | нет |

---

## 4. Репозитории (Repository)

### 4.1. StudentRepository

**Пакет:** `com.sport.project.dao.repository`
**Сущность:** `StudentEntity`
**Наследует:** `JpaRepository<StudentEntity, Integer>`

**Методы:**

| Метод | Возвращает | Описание |
|-------|------------|----------|
| `findByLogin(String login)` | `Optional<StudentEntity>` | Поиск студента по логину |
| `findByFullNameFirstNameAndFullNameLastNameAndFullNamePatronymic(firstName, lastName, patronymic)` | `List<StudentEntity>` | Поиск по ФИО |
| `findByFullNameLastName(String lastName)` | `List<StudentEntity>` | Поиск по фамилии |
| `findBySectionName(String sectionName)` | `List<StudentEntity>` | Студенты с секцией |
| `findByDateOfLesson(LocalDate date)` | `List<StudentEntity>` | Студенты с занятиями на дату (JOIN FETCH) |
| `findByDisciplineAndAttendance(String disciplineName)` | `List<StudentEntity>` | Студенты, посетившие дисциплину |
| `deleteByGroup_Name(String groupName)` | `void` | Удаление студентов группы |
| `countByGroup_Name(String groupName)` | `int` | Подсчёт студентов в группе |
| `findByGroupId(Integer groupId)` | `List<StudentEntity>` | Студенты по ID группы |
| `findBySectionId(Integer sectionId)` | `List<StudentEntity>` | Студенты по ID секции |
| `findByHealthGroupId(Integer healthGroupId)` | `List<StudentEntity>` | Студенты по ID мед. группы |
| `existsByLogin(String login)` | `boolean` | Проверка существования по логину |
| `findByGroupName(String groupName)` | `List<StudentEntity>` | Студенты по названию группы (JPQL) |

### 4.2. TeacherRepository

**Пакет:** `com.sport.project.dao.repository`
**Сущность:** `TeacherEntity`
**Наследует:** `JpaRepository<TeacherEntity, Integer>`

**Методы:**

| Метод | Возвращает | Описание |
|-------|------------|----------|
| `findByLogin(String login)` | `Optional<TeacherEntity>` | Поиск по логину |
| `findByIsModerator(boolean moderator)` | `List<TeacherEntity>` | Поиск по статусу модератора (JPQL) |
| `findByFullNameFirstName(String firstName)` | `List<TeacherEntity>` | Поиск по имени |
| `findByFullNameLastName(String lastName)` | `List<TeacherEntity>` | Поиск по фамилии |
| `findByFullNamePatronymic(String patronymic)` | `List<TeacherEntity>` | Поиск по отчеству |
| `findByFullNameFirstNameAndFullNameLastName(firstName, lastName)` | `List<TeacherEntity>` | Поиск по имени и фамилии |
| `findByFullNameFirstNameAndFullNameLastNameAndFullNamePatronymic(firstName, lastName, patronymic)` | `List<TeacherEntity>` | Поиск по полному ФИО |

### 4.3. GroupRepository

**Пакет:** `com.sport.project.dao.repository`
**Сущность:** `GroupEntity`
**Наследует:** `JpaRepository<GroupEntity, Integer>`

**Методы:**

| Метод | Возвращает | Описание |
|-------|------------|----------|
| `findById(Integer id)` | `Optional<GroupEntity>` | Поиск по ID |
| `findByName(String name)` | `Optional<GroupEntity>` | Поиск по названию |
| `findStudentsByGroupId(Integer group_id)` | `List<StudentEntity>` | Студенты группы по ID (JPQL) |
| `findStudentsByGroupName(String group_name)` | `List<StudentEntity>` | Студенты группы по названию (JPQL) |
| `findByInstitute(String institute)` | `List<GroupEntity>` | Группы по институту |
| `findByInstitute(String institute, Pageable pageable)` | `List<GroupEntity>` | Группы по институту с пагинацией |
| `existsByName(String name)` | `boolean` | Проверка существования по названию |

### 4.4. HealthGroupRepository

**Пакет:** `com.sport.project.dao.repository`
**Сущность:** `HealthGroupsEntity`
**Наследует:** `JpaRepository<HealthGroupsEntity, Integer>`

**Методы:**

| Метод | Возвращает | Описание |
|-------|------------|----------|
| `findById(Integer id)` | `Optional<HealthGroupsEntity>` | Поиск по ID |
| `findByName(String name)` | `Optional<HealthGroupsEntity>` | Поиск по названию |
| `findStudentsByHealthGroupId(Integer healthGroup_id)` | `List<StudentEntity>` | Студенты по ID мед. группы (JPQL) |
| `findStudentsByHealthGroupName(String healthGroup_name)` | `List<StudentEntity>` | Студенты по названию мед. группы (JPQL) |
| `existsByName(String name)` | `boolean` | Проверка существования по названию |

### 4.5. SectionRepository

**Пакет:** `com.sport.project.dao.repository`
**Сущность:** `SectionEntity`
**Наследует:** `JpaRepository<SectionEntity, Integer>`

**Методы:**

| Метод | Возвращает | Описание |
|-------|------------|----------|
| `findByName(String name)` | `Optional<SectionEntity>` | Поиск по названию |
| `existsByName(String name)` | `boolean` | Проверка существования по названию |

### 4.6. DisciplineRepository

**Пакет:** `com.sport.project.dao.repository`
**Сущность:** `DisciplineEntity`
**Наследует:** `JpaRepository<DisciplineEntity, Integer>`

**Методы:**

| Метод | Возвращает | Описание |
|-------|------------|----------|
| `findByName(String name)` | `Optional<DisciplineEntity>` | Поиск по названию |
| `existsByName(String name)` | `boolean` | Проверка существования по названию |

### 4.7. LessonsRepository

**Пакет:** `com.sport.project.dao.repository`
**Сущность:** `LessonsEntity`
**Наследует:** `JpaRepository<LessonsEntity, Integer>`

**Методы:**

| Метод | Возвращает | Описание |
|-------|------------|----------|
| `findByDisciplineId(Integer disciplineId)` | `List<LessonsEntity>` | Занятия по ID дисциплины (JPQL) |
| `findByDiscipline_Name(String disciplineName)` | `List<LessonsEntity>` | Занятия по названию дисциплины |
| `findByTeacher_Login(String teacherLogin)` | `List<LessonsEntity>` | Занятия преподавателя (JPQL, JOIN FETCH) |
| `findByTeacherId(Integer teacher_id)` | `List<LessonsEntity>` | Занятия по ID преподавателя (JPQL) |
| `findByDateOfLesson(LocalDate date)` | `List<LessonsEntity>` | Занятия по дате |
| `findByDisciplineAndDate(Integer disciplineId, LocalDate date)` | `Optional<LessonsEntity>` | Занятие по дисциплине и дате (JPQL) |
| `findByDateRange(LocalDate from, LocalDate to)` | `List<LessonsEntity>` | Занятия по диапазону дат (JPQL) |
| `deleteByDiscipline_Name(String disciplineName)` | `void` | Удаление занятий по дисциплине |

### 4.8. VisitsRepository

**Пакет:** `com.sport.project.dao.repository`
**Сущность:** `VisitsEntity`
**Наследует:** `JpaRepository<VisitsEntity, Integer>`

**Методы:**

| Метод | Возвращает | Описание |
|-------|------------|----------|
| `findAllOptimized()` | `List<VisitsEntity>` | Все посещения с JOIN FETCH (JPQL) |
| `findByStudentId(Integer studentId)` | `List<VisitsEntity>` | Посещения по ID студента |
| `findByStudentLogin(String studentLogin)` | `List<VisitsEntity>` | Посещения по логину студента (JPQL) |
| `findByStudentIdAndLessonId(String studentLogin, Integer lessonId)` | `Optional<VisitsEntity>` | Посещение по студенту и занятию (JPQL) |
| `findByLessonId(Integer lessonId)` | `List<VisitsEntity>` | Посещения по ID занятия (JPQL) |
| `deleteByStudent_Login(String studentLogin)` | `void` | Удаление посещений студента |
| `findByDateRange(LocalDate from, LocalDate to)` | `List<VisitsEntity>` | Посещения по диапазону дат (JPQL) |
| `deleteByLessonId(Integer lessonId)` | `void` | Удаление посещений занятия (@Modifying, JPQL) |

---

## 5. Сервисы (Service Layer)

### 5.1. StudentService

**Пакет интерфейса:** `com.sport.project.service`
**Пакет реализации:** `com.sport.project.service.impl`

**Методы интерфейса:**

| Метод | Возвращает | Описание |
|-------|------------|----------|
| `findById(Integer id)` | `StudentDTO` | Поиск по ID |
| `findByLogin(String login)` | `StudentDTO` | Поиск по логину |
| `findByFullName(firstName, lastName, patronymic)` | `List<StudentDTO>` | Поиск по ФИО |
| `findAll()` | `List<StudentDTO>` | Все студенты |
| `findByGroup(Integer groupId)` | `List<StudentDTO>` | Студенты группы |
| `findBySection(Integer sectionId)` | `List<StudentDTO>` | Студенты секции |
| `findByHealthGroup(Integer healthGroupId)` | `List<StudentDTO>` | Студенты мед. группы |
| `existsByLogin(String login)` | `boolean` | Проверка существования |

**StudentCreationService:**
- `create(StudentCreationDTO dto): StudentDTO` — создание студента

**StudentServiceImpl** реализует: `StudentService`, `StudentCreationService`

### 5.2. TeacherService

**Пакет интерфейса:** `com.sport.project.service`

**Методы интерфейса:**

| Метод | Возвращает | Описание |
|-------|------------|----------|
| `findById(Integer id)` | `TeacherDTO` | Поиск по ID |
| `findByLogin(String login)` | `TeacherDTO` | Поиск по логину |
| `findByFullName(firstName, lastName, patronymic)` | `List<TeacherDTO>` | Поиск по ФИО |
| `findAll()` | `List<TeacherDTO>` | Все преподаватели |
| `findAllModerators()` | `List<TeacherDTO>` | Все модераторы |
| `findByLessonsDate(LocalDate date)` | `List<TeacherDTO>` | Преподаватели с занятиями на дату |
| `existsByLogin(String login)` | `boolean` | Проверка существования |

**TeacherCreationService:**
- `create(TeacherCreationDTO dto): TeacherDTO` — создание преподавателя

### 5.3. GroupService

**Пакет интерфейса:** `com.sport.project.service`

**Методы интерфейса:**

| Метод | Возвращает | Описание |
|-------|------------|----------|
| `findById(Integer id)` | `GroupDTO` | Поиск по ID |
| `findByName(String name)` | `GroupDTO` | Поиск по названию |
| `findAll()` | `List<GroupDTO>` | Все группы |
| `findByInstitute(String institute)` | `List<GroupDTO>` | Группы института |
| `getStudents(Integer groupId)` | `List<StudentDTO>` | Студенты группы по ID |
| `getStudents(String groupName)` | `List<StudentDTO>` | Студенты группы по названию |
| `existsByName(String name)` | `boolean` | Проверка существования |

**GroupCreationService:** `create(GroupCreationDTO): GroupDTO`, `create(String, String): GroupDTO`
**GroupUpdatingService:** `updateName(Integer, String): GroupDTO`, `updateInstitute(Integer, String): GroupDTO`
**GroupDeletingService:** `deleteById(Integer): void`, `deleteByName(String): void`
**GroupBusinessService:** `getStudentCount(Integer): int`, `isEmpty(Integer): boolean`, `getStudentsWithAttendance(Integer, LocalDate, LocalDate): List<StudentDTO>`, `transferStudents(Integer, Integer): void`

### 5.4. HealthGroupService

**Пакет интерфейса:** `com.sport.project.service`

**Методы интерфейса:**

| Метод | Возвращает | Описание |
|-------|------------|----------|
| `findById(Integer id)` | `HealthGroupDTO` | Поиск по ID |
| `findByName(String name)` | `HealthGroupDTO` | Поиск по названию |
| `findAll()` | `List<HealthGroupDTO>` | Все мед. группы |
| `getStudents(Integer healthGroupId)` | `List<StudentDTO>` | Студенты по ID |
| `getStudents(String healthGroupName)` | `List<StudentDTO>` | Студенты по названию |
| `existsByName(String name)` | `boolean` | Проверка существования |

**HealthGroupCreationService:** `create(HealthGroupCreationDTO): HealthGroupDTO`, `create(String, String): HealthGroupDTO`
**HealthGroupUpdatingService:** `updateName(Integer, String): HealthGroupDTO`, `updateDescription(Integer, String): HealthGroupDTO`
**HealthGroupDeletingService:** `deleteById(Integer): void`, `deleteByName(String): void`
**HealthGroupBusinessService:** `getStudentCount(Integer): int`, `canDelete(Integer): boolean`, `getStudentsWithDetails(Integer): List<StudentDTO>`

### 5.5. SectionService

**Пакет интерфейса:** `com.sport.project.service`

**Методы интерфейса:**

| Метод | Возвращает | Описание |
|-------|------------|----------|
| `findById(Integer id)` | `SectionDTO` | Поиск по ID |
| `findByName(String name)` | `SectionDTO` | Поиск по названию |
| `findAll()` | `List<SectionDTO>` | Все секции |
| `getStudents(Integer sectionId)` | `List<StudentDTO>` | Студенты по ID |
| `getStudents(String sectionName)` | `List<StudentDTO>` | Студенты по названию |
| `existsByName(String name)` | `boolean` | Проверка существования |

**SectionCreationService:** `create(SectionCreationDTO): SectionDTO`, `create(String, String): SectionDTO`
**SectionUpdatingService:** `updateName(Integer, String): SectionDTO`, `updateDescription(Integer, String): SectionDTO`
**SectionDeletingService:** `deleteById(Integer): void`, `deleteByName(String): void`
**SectionBusinessService:** `getStudentCount(Integer): int`, `canDelete(Integer): boolean`, `addStudent(Integer, String): void`, `removeStudent(Integer, String): void`, `getStudentsWithAttendance(Integer): List<StudentDTO>`

### 5.6. DisciplineService

**Пакет интерфейса:** `com.sport.project.service`

**Методы интерфейса:**

| Метод | Возвращает | Описание |
|-------|------------|----------|
| `findById(Integer id)` | `DisciplineDTO` | Поиск по ID |
| `findByName(String name)` | `DisciplineDTO` | Поиск по названию |
| `findAll()` | `List<DisciplineDTO>` | Все дисциплины |
| `getLessons(Integer disciplineId)` | `List<LessonDTO>` | Занятия по ID |
| `getLessons(String disciplineName)` | `List<LessonDTO>` | Занятия по названию |
| `existsByName(String name)` | `boolean` | Проверка существования |

**DisciplineCreationService:** `create(DisciplineCreationDTO): DisciplineDTO`, `create(String): DisciplineDTO`
**DisciplineUpdatingService:** `updateName(Integer, String): DisciplineDTO`
**DisciplineDeletingService:** `deleteById(Integer): void`, `deleteByName(String): void`
**DisciplineBusinessService:** `getLessonCount(Integer): int`, `canDelete(Integer): boolean`, `getLessonsWithTeacher(Integer): List<LessonDTO>`, `getLessonsByDateRange(Integer, LocalDate, LocalDate): List<LessonDTO>`

### 5.7. LessonService

**Пакет интерфейса:** `com.sport.project.service`

**Методы интерфейса:**

| Метод | Возвращает | Описание |
|-------|------------|----------|
| `findById(Integer id)` | `LessonDTO` | Поиск по ID |
| `findByDisciplineAndDate(Integer disciplineId, LocalDate date)` | `LessonDTO` | Занятие по дисциплине и дате |
| `findAll()` | `List<LessonDTO>` | Все занятия |
| `findByDate(LocalDate date)` | `List<LessonDTO>` | Занятия по дате |
| `findByTeacher(Integer teacherId)` | `List<LessonDTO>` | Занятия преподавателя |
| `findByDiscipline(Integer disciplineId)` | `List<LessonDTO>` | Занятия дисциплины по ID |
| `findByDisciplineName(String disciplineName)` | `List<LessonDTO>` | Занятия по названию |
| `findByDateRange(LocalDate from, LocalDate to)` | `List<LessonDTO>` | Занятия по диапазону дат |
| `existsById(Integer id)` | `boolean` | Проверка существования |

**LessonCreationService:** `create(LessonCreationDTO): LessonDTO`, `create(LocalDate, Integer, Integer): LessonDTO`
**LessonUpdatingService:** `updateDate(Integer, Date): LessonDTO`, `updateTeacher(Integer, Integer): LessonDTO`, `updateDiscipline(Integer, Integer): LessonDTO`
**LessonDeletingService:** `deleteById(Integer): void`, `deleteByDiscipline(Integer): void`, `deleteByTeacher(Integer): void`, `deleteByDate(LocalDate): void`
**LessonBusinessService:** `getAttendance(Integer): List<VisitDTO>`, `markAttendance(Integer, String, boolean): void`, `getExpectedStudents(Integer): List<StudentDTO>`, `getAttendanceCount(Integer): int`, `canDelete(Integer): boolean`, `getWithFullDetails(Integer): LessonDTO`, `bulkMarkAttendance(Integer, Map<String, Boolean>): void`

### 5.8. VisitService

**Пакет интерфейса:** `com.sport.project.service`

**Методы интерфейса:**

| Метод | Возвращает | Описание |
|-------|------------|----------|
| `findById(Integer id)` | `VisitDTO` | Поиск по ID |
| `findByStudentAndLesson(String studentLogin, Integer lessonId)` | `VisitDTO` | Посещение по студенту и занятию |
| `findAll()` | `List<VisitDTO>` | Все посещения |
| `findByStudent(String studentLogin)` | `List<VisitDTO>` | Посещения студента |
| `findByLesson(Integer lessonId)` | `List<VisitDTO>` | Посещения занятия |
| `findByDateRange(LocalDate from, LocalDate to)` | `List<VisitDTO>` | Посещения по диапазону дат |
| `existsById(Integer id)` | `boolean` | Проверка существования |

**VisitCreationService:** `create(VisitCreationDTO): VisitDTO`, `create(String, Integer, boolean): VisitDTO`
**VisitUpdatingService:** `updateStatus(Integer, boolean): void`
**VisitDeletingService:** `deleteById(Integer): void`, `deleteByStudentLogin(String): void`, `deleteByLesson(Integer): void`
**VisitBusinessService:** `getStudentAttendanceMap(String): Map<LocalDate, Boolean>`, `getTotalVisits(String): int`, `getTotalAbsences(String): int`, `getAttendancePercentage(String): double`, `getAbsentStudentsForLesson(Integer): List<StudentDTO>`, `bulkMarkAttendance(Integer, Map<String, Boolean>): void`

---

## 6. DTO (Data Transfer Objects)

### 6.1. StudentDTO

**Пакет:** `com.sport.project.dto`

| Поле | Тип | Описание |
|------|-----|----------|
| `id` | Integer | Уникальный идентификатор |
| `firstName` | String | Имя |
| `lastName` | String | Фамилия |
| `patronymic` | String | Отчество |
| `login` | String | Логин |
| `healthGroup` | Integer | ID медицинской группы |
| `exist` | Map<LocalDate, Boolean> | Статус посещения по датам |

### 6.2. StudentCreationDTO

**Пакет:** `com.sport.project.dto`

| Поле | Тип | Описание |
|------|-----|----------|
| `firstName` | String | Имя |
| `lastName` | String | Фамилия |
| `patronymic` | String | Отчество |
| `login` | String | Логин |
| `password` | String | Пароль |
| `healthGroupId` | Integer | ID медицинской группы |
| `groupId` | Integer | ID группы |
| `birthday` | LocalDate | Дата рождения |

### 6.3. TeacherDTO

**Пакет:** `com.sport.project.dto`

| Поле | Тип | Описание |
|------|-----|----------|
| `id` | Integer | Уникальный идентификатор |
| `firstName` | String | Имя |
| `lastName` | String | Фамилия |
| `patronymic` | String | Отчество |
| `login` | String | Логин |
| `passwordHash` | String | Хэш пароля |
| `moderator` | boolean | Статус модератора |
| `schedule` | Map<LocalDate, String> | Расписание занятий |
| `students` | List<StudentDTO> | Список студентов |

### 6.4. TeacherCreationDTO

**Пакет:** `com.sport.project.dto`

| Поле | Тип | Описание |
|------|-----|----------|
| `firstName` | String | Имя |
| `lastName` | String | Фамилия |
| `patronymic` | String | Отчество |
| `login` | String | Логин |
| `password` | String | Пароль |
| `moderator` | Boolean | Статус модератора |
| `birthday` | LocalDate | Дата рождения |

### 6.5. GroupDTO

**Пакет:** `com.sport.project.dto`

| Поле | Тип | Описание |
|------|-----|----------|
| `id` | Integer | Уникальный идентификатор |
| `name` | String | Название группы |
| `institute` | String | Название института |

### 6.6. GroupCreationDTO

**Пакет:** `com.sport.project.dto`

| Поле | Тип | Описание |
|------|-----|----------|
| `name` | String | Название группы |
| `institute` | String | Название института |

### 6.7. HealthGroupDTO

**Пакет:** `com.sport.project.dto`

| Поле | Тип | Описание |
|------|-----|----------|
| `id` | Integer | Уникальный идентификатор |
| `name` | String | Название |
| `description` | String | Описание |

### 6.8. HealthGroupCreationDTO

**Пакет:** `com.sport.project.dto`

| Поле | Тип | Описание |
|------|-----|----------|
| `name` | String | Название |
| `description` | String | Описание |

### 6.9. SectionDTO

**Пакет:** `com.sport.project.dto`

| Поле | Тип | Описание |
|------|-----|----------|
| `id` | Integer | Уникальный идентификатор |
| `name` | String | Название секции |
| `description` | String | Описание |

### 6.10. SectionCreationDTO

**Пакет:** `com.sport.project.dto`

| Поле | Тип | Описание |
|------|-----|----------|
| `name` | String | Название |
| `description` | String | Описание |

### 6.11. DisciplineDTO

**Пакет:** `com.sport.project.dto`

| Поле | Тип | Описание |
|------|-----|----------|
| `id` | Integer | Уникальный идентификатор |
| `name` | String | Название дисциплины |

### 6.12. DisciplineCreationDTO

**Пакет:** `com.sport.project.dto`

| Поле | Тип | Описание |
|------|-----|----------|
| `name` | String | Название дисциплины |

### 6.13. LessonDTO

**Пакет:** `com.sport.project.dto`

| Поле | Тип | Описание |
|------|-----|----------|
| `id` | Integer | Уникальный идентификатор |
| `disciplineId` | Integer | ID дисциплины |
| `disciplineName` | String | Название дисциплины |
| `dateOfLesson` | LocalDate | Дата занятия |
| `teacherId` | Integer | ID преподавателя |
| `teacherFullName` | String | ФИО преподавателя |
| `startAt` | LocalTime | Время начала |
| `endAt` | LocalTime | Время окончания |

### 6.14. LessonCreationDTO

**Пакет:** `com.sport.project.dto`

| Поле | Тип | Описание |
|------|-----|----------|
| `dateOfLesson` | LocalDate | Дата занятия |
| `teacherId` | Integer | ID преподавателя |
| `disciplineId` | Integer | ID дисциплины |
| `startAt` | LocalTime | Время начала |
| `endAt` | LocalTime | Время окончания |

### 6.15. VisitDTO

**Пакет:** `com.sport.project.dto`

| Поле | Тип | Описание |
|------|-----|----------|
| `id` | Integer | Уникальный идентификатор |
| `studentId` | Integer | ID студента |
| `studentLogin` | String | Логин студента |
| `lessonId` | Integer | ID занятия |
| `isExists` | boolean | Статус присутствия |

### 6.16. VisitCreationDTO

**Пакет:** `com.sport.project.dto`

| Поле | Тип | Описание |
|------|-----|----------|
| `studentLogin` | String | Логин студента |
| `lessonId` | Integer | ID занятия |
| `isExists` | boolean | Статус присутствия |

### 6.17. Другие DTO

**UserDetailsImpl** — данные пользователя для Spring Security
**ErrorResponseDto** — формат ошибок API (statusCode, message, trace)

---

## 7. Контроллеры (Controllers)

### 7.1. MVC Контроллеры

#### IndexController
- `GET /index` — главная страница

#### AdminController
- `GET /admin/dashboard` — панель администратора
- `GET /admin/students` — страница студентов
- `POST /admin/students/create` — создание студента
- `GET /admin/teachers` — страница преподавателей
- `POST /admin/teachers/create` — создание преподавателя
- `GET /admin/groups` — страница групп
- `GET /admin/health-groups` — страница медицинских групп
- `POST /admin/health-groups/create` — создание медицинской группы
- `GET /admin/sections` — страница секций
- `POST /admin/sections/create` — создание секции
- `GET /admin/disciplines` — страница дисциплин
- `POST /admin/disciplines/create` — создание дисциплины
- `GET /admin/lessons` — страница занятий
- `POST /admin/lessons/create` — создание занятия
- `GET /admin/visits` — страница посещений
- `POST /admin/visits/create` — создание посещения
- `POST /admin/visits/update/{id}` — обновление статуса посещения

#### StudentLoginController
- `GET /student-by-login` — страница поиска студента по логину
- `POST /student-by-login` — поиск студента по логину
- `GET /api/student/full-name` — поиск студента по ФИО (REST)

#### CustomErrorController
- `GET /error` — страница ошибки
- `GET /already-exists` — страница "уже существует"

### 7.2. REST Контроллеры

#### RestStudentController (`/students`)
| Метод | Endpoint | Описание |
|-------|----------|----------|
| `GET` | `/students/find-by-login?login=` | Получить студента по логину |
| `GET` | `/students/find-by-full-name?first-name=&last-name=&patronymic=` | Получить по ФИО |
| `GET` | `/students/find-all` | Получить всех студентов |
| `GET` | `/students/find-by-group?group-id=` | Получить студентов группы |
| `GET` | `/students/find-by-health-group?health-group-id=` | Получить студентов мед. группы |
| `GET` | `/students/find-by-section?section-id=` | Получить студентов секции |
| `GET` | `/students/is-exists-by-login?login=` | Проверка существования по логину |
| `POST` | `/students/create` | Создать студента |

#### RestTeacherController (`/teachers`)
| Метод | Endpoint | Описание |
|-------|----------|----------|
| `GET` | `/teachers/find-by-login?login=` | Получить преподавателя по логину |
| `GET` | `/teachers/find-by-full-name?first-name=&last-name=&patronymic=` | Получить по ФИО |
| `GET` | `/teachers/find-all` | Получить всех преподавателей |
| `GET` | `/teachers/find-all-moderators` | Получить всех модераторов |
| `GET` | `/teachers/get-by-lesson-date?date=` | Получить преподавателей по дате занятия |
| `GET` | `/teachers/is-exists-by-login?login=` | Проверка существования по логину |
| `POST` | `/teachers/create` | Создать преподавателя |

#### GroupController (`/api/group`)
| Метод | Endpoint | Описание |
|-------|----------|----------|
| `GET` | `/api/group/get-all` | Получить все группы |
| `GET` | `/api/group/{id}` | Получить группу по ID |
| `GET` | `/api/group/by-name?name=` | Получить группу по названию |
| `GET` | `/api/group/by-institute?institute=` | Получить группы института |
| `GET` | `/api/group/{groupId}/students` | Получить студентов группы по ID |
| `GET` | `/api/group/{groupName}/students` | Получить студентов группы по названию |

#### HealthGroupController (`/api/healthGroup`)
| Метод | Endpoint | Описание |
|-------|----------|----------|
| `GET` | `/api/healthGroup/{id}` | Получить мед. группу по ID |
| `GET` | `/api/healthGroup/name/{name}` | Получить мед. группу по названию |
| `GET` | `/api/healthGroup/find-all` | Получить все мед. группы |
| `GET` | `/api/healthGroup/{id}/students` | Получить студентов мед. группы по ID |
| `GET` | `/api/healthGroup/name/{name}/students` | Получить студентов мед. группы по названию |
| `GET` | `/api/healthGroup/exists/name/{name}` | Проверка существования по названию |
| `POST` | `/api/healthGroup/create` | Создать мед. группу |

#### SectionController (`/api/section`)
| Метод | Endpoint | Описание |
|-------|----------|----------|
| `GET` | `/api/section/get-all` | Получить все секции |
| `GET` | `/api/section/{id}` | Получить секцию по ID |
| `GET` | `/api/section/by-name?name=` | Получить секцию по названию |
| `GET` | `/api/section/section-student-by-section-id/{sectionId}/students` | Студенты секции по ID |
| `GET` | `/api/section/section-student-by-section-name/{sectionName}/students` | Студенты секции по названию |
| `POST` | `/api/section/create` | Создать секцию |

#### DisciplineController (`/api/discipline`)
| Метод | Endpoint | Описание |
|-------|----------|----------|
| `GET` | `/api/discipline/find-all` | Получить все дисциплины |
| `GET` | `/api/discipline/{id}` | Получить дисциплину по ID |
| `GET` | `/api/discipline/by-name?name=` | Получить дисциплину по названию |
| `GET` | `/api/discipline/by-discipline-id/{disciplineId}/lessons` | Занятия по ID дисциплины |
| `GET` | `/api/discipline/by-discipline-name/{disciplineName}/lessons` | Занятия по названию дисциплины |
| `GET` | `/api/discipline/is-exists-by-name?disciplineName=` | Проверка существования |
| `POST` | `/api/discipline/create` | Создать дисциплину (DTO) |
| `POST` | `/api/discipline/create-by-name` | Создать дисциплину (по названию) |

#### LessonsController (`/api/lessons`)
| Метод | Endpoint | Описание |
|-------|----------|----------|
| `GET` | `/api/lessons/{id}` | Получить занятие по ID |
| `GET` | `/api/lessons/discipline-and-date?disciplineId=&date=` | Занятие по дисциплине и дате |
| `GET` | `/api/lessons/find-all` | Получить все занятия |
| `GET` | `/api/lessons/date?date=` | Занятия по дате |
| `GET` | `/api/lessons/teacher/{teacherId}` | Занятия преподавателя |
| `GET` | `/api/lessons/discipline/{disciplineId}` | Занятия дисциплины по ID |
| `GET` | `/api/lessons/discipline-name/{name}` | Занятия дисциплины по названию |
| `GET` | `/api/lessons/range?from=&to=` | Занятия по диапазону дат |
| `GET` | `/api/lessons/exists/{id}` | Проверка существования |
| `POST` | `/api/lessons/create` | Создать занятие |

#### VisitsController (`/api/visits`)
| Метод | Endpoint | Описание |
|-------|----------|----------|
| `GET` | `/api/visits/find-all` | Получить все посещения |
| `GET` | `/api/visits/{id}` | Получить посещение по ID |
| `GET` | `/api/visits/student/{login}` | Посещения студента |
| `GET` | `/api/visits/lesson/{lessonId}` | Посещения занятия |
| `GET` | `/api/visits/student/{login}/lesson/{lessonId}` | Посещение студента на занятии |
| `GET` | `/api/visits/range?from=&to=` | Посещения по диапазону дат |
| `GET` | `/api/visits/exists/{id}` | Проверка существования |
| `GET` | `/api/visits/get-total-absences/{studentLogin}` | Общее количество пропусков |
| `POST` | `/api/visits/create` | Создать посещение |
| `DELETE` | `/api/visits/delete/{id}` | Удалить посещение по ID |
| `DELETE` | `/api/visits/delete/student/{login}` | Удалить все посещения студента |
| `DELETE` | `/api/visits/delete/lesson/{lessonId}` | Удалить все посещения занятия |
| `PUT` | `/api/visits/update/{id}/status/{exists}` | Обновить статус посещения |

#### TestController (`/test`)
| Метод | Endpoint | Описание |
|-------|----------|----------|
| `GET` | `/test/findDisciplineByName?name=` | Найти дисциплину по названию (тест) |
| `GET` | `/test/findTeacherByLogin?login=` | Найти преподавателя по логину (тест) |

---

## 8. Обработка исключений

### 8.1. RestAdviceErrorController

**Пакет:** `com.sport.project.controller.rest`

Обработчик исключений для REST API:

| Исключение | HTTP статус | Описание |
|------------|-------------|----------|
| `EntityNotFoundException` | 404 | Сущность не найдена |
| `EntityAlreadyExistsException` | 400 | Сущность уже существует |
| `IllegalArgumentException` | 400 | Некорректный аргумент |

### 8.2. EntityNotFoundException

**Пакет:** `com.sport.project.exception`
**Наследует:** `RuntimeException`

Конструктор: `EntityNotFoundException(String message)`

### 8.3. EntityAlreadyExistsException

**Пакет:** `com.sport.project.exception`
**Наследует:** `RuntimeException`

Конструктор: `EntityAlreadyExistsException(String message)`

---

## 9. Конфигурация

### 9.1. WebSecurityConfig

**Пакет:** `com.sport.project.config`
**Аннотации:** `@EnableWebSecurity`, `@Configuration`, `@RequiredArgsConstructor`

- Отключена аутентификация (все запросы разрешены для разработки)
- `SecurityFilterChain securityFilterChain(HttpSecurity http)` — разрешает все запросы, отключает CSRF и formLogin

### 9.2. MvcConfig

**Пакет:** `com.sport.project.config`

- `SpringResourceTemplateResolver templateResolver()` — настройка Thymeleaf (classpath:/templates/, .html)

### 9.3. OpenApiConfig

**Пакет:** `com.sport.project.config`

Настройка Swagger OpenAPI:
- **Title:** "Sport Project API"
- **Version:** "0.1.0"
- **Server:** http://localhost:4444

---

## 10. Технологический стек

- **Framework:** Spring Boot 4.0.3
- **Language:** Java 21
- **Database:** PostgreSQL 18 (Docker Compose, порт 5432)
- **ORM:** Spring Data JPA + Hibernate
- **Security:** Spring Security (временно все запросы разрешены)
- **Template Engine:** Thymeleaf
- **Migrations:** Liquibase (changelog: `classpath:/db.changelog/changelog-master.yml`)
- **Build:** Gradle
- **Server Port:** 4444
- **API Docs:** OpenAPI Swagger

---

## 11. Build & Run Commands

```bash
# Build the project
./gradlew build

# Run tests
./gradlew test

# Run a specific test class
./gradlew test --tests "com.sport.project.ProjectApplicationTests"

# Run the application (requires PostgreSQL running)
./gradlew bootRun

# Start PostgreSQL database
docker-compose -f docker-compose.dev.yaml up

# Stop database
docker-compose -f docker-compose.dev.yaml stop
```

### Environment Setup

Создать `.env` файл в корне проекта:
```
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
POSTGRES_DB=all-on-sport
POSTGRES_DB_PORT=5432
```

---

## 12. Database Configuration

```yaml
spring:
  liquibase:
    enabled: true
    change-log: classpath:/db.changelog/changelog-master.yml
  jpa:
    hibernate:
      ddl-auto: none  # используется Liquibase
```

---

## 13. Mapper

**Пакет:** `com.sport.project.mapper`

Утилитарный класс для маппинга Entity <-> DTO.

**Методы:**
- `map(Entity entity): DTO` — конвертация сущности в DTO

---

## 14. Утилиты

### UserUtils

**Пакет:** `com.sport.project.utils`

Утилиты для работы с пользователями.

### AuthorizedUserUtils

**Пакет:** `com.sport.project.utils`

Утилиты для работы с авторизованными пользователями.

---

## 15. Примечания

### FIXME и TODO

1. **StudentRepository:74** — `findByLFP` закомментирован, CONCAT не поддерживается в HQL, требуется замена
2. **StudentRepository:81** — `findByGroupName` JPQL может требовать исправления
3. **TeacherRepository** — требуются методы для работы с расписанием (`api/teachers/update-schedule`) и уведомлениями (`api/teachers/notice`)
4. **LessonServiceImpl** — не реализованы интерфейсы `LessonUpdatingService`, `LessonDeletingService`, `LessonBusinessService`
5. **VisitBusinessService** — некоторые методы являются заглушками (`getStudentAttendanceMap`, `getAttendancePercentage`, `getAbsentStudentsForLesson`, `bulkMarkAttendance`)

### Временные решения

1. **StudentEntity:79-82** — `equals()` возвращает `true` (временная заглушка)
2. **StudentEntity:84-88** — `hashCode()` закомментирован
