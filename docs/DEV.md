# Документация по проекту «Все на спорт»

## 1. Иерархия сущностей

### 1.1. AbstractEntity

Базовый абстрактный класс для всех сущностей. Содержит идентификатор `id` с автоматической генерацией (IDENTITY). Использует аннотацию `@MappedSuperclass`.

**Пакет:** `com.sport.project.dao.entity`

- `id` (ID, Serializable): первичный ключ, генерируется автоматически.

### 1.2. Entity

Устаревший интерфейс, помечен `@Deprecated`. Не используется.

### 1.3. BaseEntity

Устаревший класс, помечен `@Deprecated`. Не используется.

---

## 2. Сущности (Entity)

Ниже приведено описание всех JPA-сущностей, их полей, связей и особенностей.

### 2.1. UserEntity

Абстрактный класс, наследующий `AbstractEntity`. Содержит общие поля для `StudentEntity` и `TeacherEntity`. Помечен `@MappedSuperclass`.

**Таблица:** не имеет своей таблицы (mapped superclass)

- `login` (String): уникальный логин, не может быть null, длина 50.
- `passwordHash` (String): хэш пароля, не может быть null.
- `fullName` (FullName): встраиваемый объект (Embedded) с именем, фамилией, отчеством.
- `birthday` (LocalDate): дата рождения, не может быть null, проверка на прошедшую дату.

### 2.2. FullName

Встраиваемый класс (Embeddable), используется в `UserEntity`.

- `firstName` (String): имя, не может быть null.
- `lastName` (String): фамилия, не может быть null.
- `patronymic` (String): отчество, может быть null.

### 2.3. StudentEntity

Наследник `UserEntity`, представляет студента.

**Таблица:** `students`
**JPA name:** `student_entity`

**Собственные поля:**
- `healthGroup` (HealthGroupsEntity): многие-к-одному (ManyToOne) к `health_groups`. Обязательное поле.
- `group` (GroupEntity): многие-к-одному к `groups`. Обязательное поле.
- `section` (SectionEntity): многие-к-одному к `sections`. Может быть null (студент может не быть записан на секцию).
- `visits` (List<VisitsEntity>): один-ко-многим (OneToMany) с `visits`, каскад ALL, mappedBy = "student".

**Builder:** `StudentEntityBuilder` (неполная реализация, TODO: закинуть всё остальное)

### 2.4. TeacherEntity

Наследник `UserEntity`, представляет преподавателя.

**Таблица:** `teachers`
**JPA name:** `teacher_entity`

**Собственные поля:**
- `isModerator` (boolean): флаг, является ли преподаватель модератором. Не может быть null.
- `lessons` (List<LessonsEntity>): один-ко-многим (OneToMany) с `lessons` (преподаватель проводит занятия). Каскад ALL, orphanRemoval = true, fetch = LAZY, mappedBy = "teacher".

**Builder:** `TeacherEntityBuilder` (полная реализация)

### 2.5. GroupEntity

Учебная группа.

**Таблица:** `groups`
**JPA name:** `groups_entity`

**Поля:**
- `name` (String): уникальное название, не может быть null, длина 15.
- `institute` (String): название института, не может быть null, длина 100.
- `students` (List<StudentEntity>): один-ко-многим (OneToMany) со студентами, каскад PERSIST, mappedBy = "group".

**Конструктор:** `GroupEntity(String name, String institute)`

### 2.6. HealthGroupsEntity

Медицинская группа здоровья.

**Таблица:** `health_groups`
**JPA name:** `health_groups_entity`

**Поля:**
- `name` (String): уникальное название, не может быть null.
- `description` (String): описание, не может быть null, длина 100.
- `students` (List<StudentEntity>): один-ко-многим (OneToMany) со студентами, каскад PERSIST, mappedBy = "healthGroup".

**Конструкторы:**
- `HealthGroupsEntity(String name, String description)`
- `HealthGroupsEntity(String name, String description, List<StudentEntity> students)`

### 2.7. SectionEntity

Спортивная секция.

**Таблица:** `sections`
**JPA name:** `section_entity`

**Поля:**
- `name` (String): уникальное название, не может быть null, длина 100.
- `description` (String): описание, не может быть null, длина 100.
- `studentsOnSection` (List<StudentEntity>): один-ко-многим (OneToMany) со студентами (те, кто записан на секцию). Без каскада, mappedBy = "section".

**Конструкторы:**
- `SectionEntity(String name, String description)`
- `SectionEntity(String name, String description, List<StudentEntity> studentsOnSection)`

### 2.8. DisciplineEntity

Дисциплина (например, «Физическая культура и спорт»).

**Таблица:** `disciplines`
**JPA name:** `discipline_entity`

**Поля:**
- `name` (String): уникальное название, не может быть null, длина 100.
- `lessonsEntities` (List<LessonsEntity>): один-ко-многим (OneToMany) с занятиями, каскад ALL, mappedBy = "discipline".

**Конструкторы:**
- `DisciplineEntity(String name)`
- `DisciplineEntity(String name, List<LessonsEntity> lessonsEntities)`

### 2.9. LessonsEntity

Занятие (пара).

**Таблица:** `lessons`
**JPA name:** `lessons_entity`

**Поля:**
- `discipline` (DisciplineEntity): многие-к-одному (ManyToOne) к дисциплине, обязательное поле, join column = "discipline_id".
- `dateOfLesson` (Date): дата занятия, не может быть null, column definition = "DATE".
- `teacher` (TeacherEntity): многие-к-одному (ManyToOne) к преподавателю, может быть null, join column = "teacher_id".

**Конструкторы:**
- `LessonsEntity(Date dateOfLesson)`
- `LessonsEntity(Date dateOfLesson, TeacherEntity teacher)`

### 2.10. VisitsEntity

Посещаемость студента на занятии.

**Таблица:** `visits`
**JPA name:** `visits_entity`

**Поля:**
- `student` (StudentEntity): многие-к-одному (ManyToOne) к студенту, join column = "student_id".
- `lessons` (LessonsEntity): многие-к-одному (ManyToOne) к занятию, join column = "lesson_id".
- `isExists` (boolean): присутствовал ли студент. Не может быть null.

**Конструктор:** `VisitsEntity(boolean isExists)`

---

## 3. Репозитории (Repository)

Репозитории используют Spring Data JPA. Ниже приведено подробное описание всех методов.

### 3.1. AbstractRepository

**Статус:** `@Deprecated` (не используется)

**Пакет:** `com.sport.project.dao.repository`

**Тип:** Дженерик интерфейс `AbstractRepository<E extends Entity<ID>, ID extends Number>`

**Методы:**
- `Optional<E> findById(ID id)`: поиск сущности по ID.
- `E save(E entity)`: сохранение сущности.
- `List<E> findAll()`: получение всех сущностей.

### 3.2. DisciplineRepository

**Пакет:** `com.sport.project.dao.repository`

**Сущность:** `DisciplineEntity`
**Тип ID:** `Integer`

**Наследует:** `JpaRepository<DisciplineEntity, Integer>`

**Объявленные методы:**

| Метод | Возвращает | Описание |
|-------|------------|----------|
| `findByName(String name)` | `Optional<DisciplineEntity>` | Поиск дисциплины по уникальному имени |

**Замечания:**
- Стандартные методы `JpaRepository` (например, `findById`, `findAll`, `save`, `delete`) доступны из базового интерфейса.

### 3.3. GroupRepository

**Пакет:** `com.sport.project.dao.repository`

**Сущность:** `GroupEntity`
**Тип ID:** `Integer`

**Наследует:** `JpaRepository<GroupEntity, Integer>`

**Объявленные методы:**

| Метод | Возвращает | Описание |
|-------|------------|----------|
| `findByName(String name)` | `Optional<GroupEntity>` | Поиск группы по уникальному названию |
| `findStudentsByGroup(@Param("group_name") String group_name)` | `List<StudentEntity>` | JPQL-запрос: получение всех студентов группы по названию группы |
| `findByInstitute(String institute)` | `List<GroupEntity>` | Поиск групп по названию института (возвращает список) |
| `findAll()` | `List<GroupEntity>` | Получение всех групп (переопределение стандартного метода) |

**JPQL-запрос:**
```java
@Query("SELECT student FROM student_entity student WHERE student.group.name = :group_name")
```

### 3.4. LessonsRepository

**Пакет:** `com.sport.project.dao.repository`

**Сущность:** `LessonsEntity`
**Тип ID:** `Integer`

**Наследует:** `JpaRepository<LessonsEntity, Integer>`

**Объявленные методы:**
- Нет кастомных методов (используются только стандартные `JpaRepository`)

**Замечания:**
- В документации не указан, но может понадобиться для работы с занятиями.

**Требуемые методы для сервисов:**

| Метод | Возвращает | Описание |
|-------|------------|----------|
| `findByDateOfLesson(LocalDate date)` | `List<LessonsEntity>` | Получение занятий по дате |
| `findByTeacherId(Integer teacherId)` | `List<LessonsEntity>` | Получение занятий преподавателя |
| `findByDisciplineNameAndDateOfLesson(String disciplineName, LocalDate date)` | `Optional<LessonsEntity>` | Поиск занятия по дисциплине и дате |
| `findByDisciplineName(String disciplineName)` | `List<LessonsEntity>` | Получение всех занятий дисциплины |
| `deleteByDisciplineName(String disciplineName)` | `void` | Очистка занятий при удалении дисциплины |

### 3.5. StudentRepository

**Пакет:** `com.sport.project.dao.repository`

**Сущность:** `StudentEntity`
**Тип ID:** `Integer`

**Наследует:** `JpaRepository<StudentEntity, Integer>`

**Объявленные методы:**

| Метод | Возвращает | Описание |
|-------|------------|----------|
| `findByLogin(String login)` | `Optional<StudentEntity>` | Поиск студента по уникальному логину |
| `findByGroupName(String groupName)` | `Optional<StudentEntity>` | Поиск студента по названию группы |

**Требуемые методы для сервисов:**

| Метод | Возвращает | Описание |
|-------|------------|----------|
| `findByFullNameFirstNameAndFullNameLastNameAndFullNamePatronymic(String firstName, String lastName, String patronymic)` | `Optional<StudentEntity>` | Поиск студента по полному ФИО |
| `findByGroupId(Integer groupId)` | `List<StudentEntity>` | Получение всех студентов группы по ID |
| `existsByLogin(String login)` | `boolean` | Проверка существования студента по логину |
| `findBySectionName(String sectionName)` | `List<StudentEntity>` | Получение всех студентов секции |
| `deleteByGroupName(String groupName)` | `void` | Удаление всех студентов группы |
| `countByGroupName(String groupName)` | `int` | Подсчёт количества студентов в группе |

### 3.6. TeacherRepository

**Пакет:** `com.sport.project.dao.repository`

**Сущность:** `TeacherEntity`
**Тип ID:** `Integer`

**Наследует:** `JpaRepository<TeacherEntity, Integer>`

**Объявленные методы:**

| Метод | Возвращает | Описание |
|-------|------------|----------|
| `findByLogin(String login)` | `Optional<TeacherEntity>` | Поиск преподавателя по уникальному логину |
| `findByIsModerator(boolean isModerator)` | `List<TeacherEntity>` | Фильтрация по флагу модератора |
| `findByFullNameFirstName(String firstName)` | `List<TeacherEntity>` | Поиск по имени (FirstName из FullName) |
| `findByFullNameLastName(String lastName)` | `List<TeacherEntity>` | Поиск по фамилии (LastName из FullName) |
| `findByFullNamePatronymic(String patronymic)` | `List<TeacherEntity>` | Поиск по отчеству (Patronymic из FullName) |
| `findByFullNameFirstNameAndFullNameLastName(String firstName, String lastName)` | `List<TeacherEntity>` | Поиск по имени и фамилии |
| `findByFullNameFirstNameAndFullNameLastNameAndFullNamePatronymic(String firstName, String lastName, String patronymic)` | `List<TeacherEntity>` | Поиск по полному ФИО |

**TODO:**
- Методы для работы с расписанием (`api/teachers/update-schedule`)
- Методы для уведомлений (`api/teachers/notice`)

**Требуемые методы для сервисов:**

| Метод | Возвращает | Описание |
|-------|------------|----------|
| `existsByLogin(String login)` | `boolean` | Проверка существования преподавателя по логину |
| `findByIdWithLessons(Integer id)` | `Optional<TeacherEntity>` | Получение преподавателя с занятиями (eager fetch) |
| `findByLessonsDateOfLesson(LocalDate date)` | `List<TeacherEntity>` | Получение преподавателей, проводящих занятия на дату |

### 3.7. VisitsRepository

**Пакет:** `com.sport.project.dao.repository`

**Сущность:** `VisitsEntity`
**Тип ID:** `Integer` (наследуется от AbstractEntity)

**Наследует:** `JpaRepository<VisitsEntity, Integer>`

**Объявленные методы:**

| Метод                                     | Возвращает | Описание |
|-------------------------------------------|------------|----------|
| `findByStudentId(Integer studentId)`      | `Optional<VisitsEntity>` | Поиск записи о посещении по ID студента |
| `findByStudentLogin(String studentLogin)` | `Optional<VisitsEntity>` | Поиск записи о посещении по логину студента |

**Требуемые методы для сервисов:**

| Метод | Возвращает | Описание |
|-------|------------|----------|
| `findByStudentIdAndLessonId(Integer studentId, Integer lessonId)` | `Optional<VisitsEntity>` | Поиск записи о посещении по студенту и занятию |
| `findByLessonId(Integer lessonId)` | `List<VisitsEntity>` | Получение всех посещений занятия |
| `deleteByStudentLogin(String studentLogin)` | `void` | Очистка истории посещений при удалении студента |

---

## 4. Реализации репозиториев (Impl)

Классы в пакете `com.sport.project.dao.repository.impl` помечены `@Deprecated` и не используются.

### 4.1. AbstractRepositoryImpl

**Статус:** `@Deprecated`

Базовая реализация репозитория с использованием `EntityManager`. Не используется.

### 4.2. StudentRepositoryImpl

**Статус:** `@Deprecated`

Закомментированная реализация с методами:
- `findAll()`
- `findByFullName(String fullName)`
- `findByLogin(String login)`

### 4.3. TeacherRepositoryImpl

**Статус:** `@Deprecated`

Закомментированная реализация с методами:
- `findAll()`
- `findByFullName(String fullName)`
- `findByLogin(String login)`
- `findAllModerators()`

---

## 5. Проектирование бизнес-методов

Для реализации функциональности, описанной в ролевой модели и API-документации, необходимо дополнить репозитории кастомными запросами, а также определить методы в сервисном слое. В данном разделе мы опишем необходимые бизнес-методы, их назначение, параметры и предполагаемую реализацию с использованием Spring Data JPA.

**Примечание:** некоторые методы, указанные в API, могут быть реализованы на уровне сервисов с использованием стандартных методов репозиториев (например, `findById`). Здесь мы акцентируем внимание на сложных запросах, требующих кастомных JPQL-запросов или использования спецификаций.

### 4.1. Методы для работы с посещаемостью

#### 4.1.1. Отметка посещаемости студента

**Бизнес-сценарий:** преподаватель отмечает, присутствовал студент на занятии или нет.

- **Endpoint:** `POST /api/teachers/notice`
- **Входные данные:**
  - `firstName` (String) — имя студента
  - `lastName` (String) — фамилия студента
  - `patronymic` (String) — отчество студента (может быть null)
  - `lessonId` (Integer) — идентификатор занятия
  - `isExists` (boolean) — присутствие (true/false)
- **Действие:** найти студента по ФИО, создать или обновить запись в `visits` для данного студента и занятия.

**Реализация:**
В `StudentRepository` добавить метод для поиска студента по ФИО:
```java
Optional<StudentEntity> findByFullNameFirstNameAndFullNameLastNameAndFullNamePatronymic(
    String firstName, String lastName, String patronymic);
```

В `VisitsRepository` добавить метод для проверки существующей записи:
```java
Optional<VisitsEntity> findByStudentIdAndLessonId(Integer studentId, Integer lessonId);
```

---

### 5.2. Дополнительные бизнес-методы репозиториев

#### 5.2.1. Получение студентов по секции

**Метод:** `List<StudentEntity> findBySectionName(String sectionName)`
**Репозиторий:** `StudentRepository`
**Возвращает:** Список всех студентов, записанных на секцию
**Назначение:** Получить всех студентов конкретной спортивной секции для отображения списка участников или массовой операции.

#### 5.2.2. Получение занятий по дисциплине

**Метод:** `List<LessonsEntity> findByDisciplineName(String disciplineName)`
**Репозиторий:** `LessonsRepository`
**Возвращает:** Список всех занятий дисциплины
**Назначение:** Получить расписание занятий по конкретной дисциплине.

#### 5.2.3. Получение занятий по преподавателю

**Метод:** `List<LessonsEntity> findByTeacherLogin(String teacherLogin)`
**Репозиторий:** `LessonsRepository`
**Возвращает:** Список всех занятий, которые проводит преподаватель
**Назначение:** Получить расписание преподавателя.

#### 5.2.4. Получение занятий по дате

**Метод:** `List<LessonsEntity> findByDateOfLesson(LocalDate date)`
**Репозиторий:** `LessonsRepository`
**Возвращает:** Список всех занятий на указанную дату
**Назначение:** Получить расписание на конкретный день.

#### 5.2.5. Получение групп по институту с пагинацией

**Метод:** `List<GroupEntity> findByInstitute(String institute, Pageable pageable)`
**Репозиторий:** `GroupRepository`
**Возвращает:** Список групп института с пагинацией
**Назначение:** Эффективная загрузка больших списков групп.

#### 5.2.6. Удаление студентов группы

**Метод:** `void deleteByGroupName(String groupName)`
**Репозиторий:** `StudentRepository`
**Возвращает:** `void` (удаление записей)
**Назначение:** Удаление всех студентов группы при её удалении (если не настроен cascade).

#### 5.2.7. Поиск студентов по фамилии

**Метод:** `List<StudentEntity> findByFullNameLastName(String lastName)`
**Репозиторий:** `StudentRepository`
**Возвращает:** Список студентов с указанной фамилией
**Назначение:** Поиск студентов по фамилии (например, для алфавитного списка).

#### 5.2.8. Подсчёт количества студентов в группе

**Метод:** `int countByGroupName(String groupName)`
**Репозиторий:** `StudentRepository`
**Возвращает:** Количество студентов в группе
**Назначение:** Быстрое получение размера группы без загрузки всех сущностей.

#### 5.2.9. Проверка существования группы по названию

**Метод:** `boolean existsByName(String name)`
**Репозиторий:** `GroupRepository`
**Возвращает:** `true` если группа существует, иначе `false`
**Назначение:** Валидация уникальности названия группы перед созданием.

#### 5.2.10. Проверка существования дисциплины по названию

**Метод:** `boolean existsByName(String name)`
**Репозиторий:** `DisciplineRepository`
**Возвращает:** `true` если дисциплина существует, иначе `false`
**Назначение:** Валидация уникальности названия дисциплины.

#### 5.2.11. Получение преподавателей-модераторов

**Метод:** `List<TeacherEntity> findByIsModeratorTrue()`
**Репозиторий:** `TeacherRepository`
**Возвращает:** Список всех преподавателей со статусом модератора
**Назначение:** Быстрое получение активных модераторов системы.

#### 5.2.12. Удаление посещений по студенту

**Метод:** `void deleteByStudentLogin(String studentLogin)`
**Репозиторий:** `VisitsRepository`
**Возвращает:** `void`
**Назначение:** Очистка истории посещений при удалении студента.

#### 5.2.13. Удаление занятий по дисциплине

**Метод:** `void deleteByDisciplineName(String disciplineName)`
**Репозиторий:** `LessonsRepository`
**Возвращает:** `void`
**Назначение:** Очистка занятий при удалении дисциплины.

---

## 6. Сервисы (Service Layer)

Сервисный слой разделён на интерфейсы по ответственности: создание, обновление, удаление и бизнес-логика. Это соответствует принципу единственной ответственности (SRP).

### 6.1. Student Service Interfaces

#### 6.1.1. StudentCreationService

**Пакет:** `com.sport.project.service.interfaces.student`

**Назначение:** Создание нового студента. Валидирует входные данные, проверяет уникальность логина, создаёт сущность `StudentEntity` и сохраняет её через репозиторий.

**Методы:**

| Метод | Возвращает | Параметры | Исключения | Описание |
|-------|------------|-----------|------------|----------|
| `createStudent(StudentCreationDTO dto)` | `StudentDTO` | `dto` — DTO для создания студента (fullName, login, password, healthGroup, teacherId) | `EntityAlreadyExistsException` | Создание нового студента. Валидация уникальности логина, хеширование пароля, связывание с группой, медицинской группой и преподавателем |

**Зависимости:**
- `StudentRepository` — для сохранения сущности
- `TeacherRepository` — для получения преподавателя по ID
- `GroupRepository` — для получения группы по названию
- `HealthGroupsRepository` — для получения медицинской группы по ID
- `PasswordEncoder` — для хеширования пароля

---

#### 6.1.2. StudentUpdatingService

**Пакет:** `com.sport.project.service.interfaces.student`

**Назначение:** Обновление данных студента. Находит студента по идентификатору (логину) и обновляет переданные поля.

**Методы:**

| Метод | Возвращает | Параметры | Исключения | Описание |
|-------|------------|-----------|------------|----------|
| `updateFullName(String newFullName, String login)` | `void` | `newFullName` — новое ФИО, `login` — текущий логин студента | `EntityNotFoundException` | Обновление ФИО студента |
| `updateLogin(String newLogin, String login)` | `void` | `newLogin` — новый логин, `login` — текущий логин | `EntityNotFoundException`, `EntityAlreadyExistsException` | Обновление логина студента. Проверка уникальности нового логина |
| `updateHealthGroup(int newHealthGroup, String login)` | `void` | `newHealthGroup` — ID новой медицинской группы, `login` — логин студента | `EntityNotFoundException` | Присвоение студенту новой медицинской группы |
| `updateStudentTeacher(TeacherEntity teacher, String login)` | `void` | `teacher` — сущность преподавателя, `login` — логин студента | `EntityNotFoundException` | Привязка студента к преподавателю |

**Зависимости:**
- `StudentRepository` — для поиска и обновления студента
- `TeacherRepository` — для получения преподавателя
- `HealthGroupsRepository` — для получения медицинской группы

---

#### 6.1.3. StudentDeletingService

**Пакет:** `com.sport.project.service.interfaces.student`

**Назначение:** Удаление студента. Удаляет сущность из базы данных, каскадно удаляя связанные записи посещаемости.

**Методы:**

| Метод | Возвращает | Параметры | Исключения | Описание |
|-------|------------|-----------|------------|----------|
| `deleteById(int id)` | `void` | `id` — идентификатор студента | `EntityNotFoundException` | Удаление студента по ID |
| `deleteByFullName(String fullName)` | `void` | `fullName` — ФИО студента | `EntityNotFoundException` | Удаление студента по ФИО |
| `deleteByLogin(String login)` | `void` | `login` — логин студента | `EntityNotFoundException` | Удаление студента по логину |

**Зависимости:**
- `StudentRepository` — для поиска и удаления сущности
- `VisitsRepository` — для удаления записей о посещении (каскадно через CascadeType.ALL)

---

#### 6.1.4. StudentBusiness

**Пакет:** `com.sport.project.service.interfaces.student`

**Назначение:** Бизнес-логика студента. Содержит методы, специфичные для предметной области.

**Методы:**

| Метод | Возвращает | Параметры | Исключения | Описание |
|-------|------------|-----------|------------|----------|
| `getStudentSchedule(StudentEntity student)` | `Map<LocalDate, String>` | `student` — сущность студента | — | Получение расписания занятий студента. Возвращает мапу: дата → название занятия/дисциплины |

**Зависимости:**
- `LessonsRepository` — для получения занятий по дате
- `DisciplineRepository` — для получения названия дисциплины

---

### 6.2. Teacher Service Interfaces

#### 6.2.1. TeacherCreationService

**Пакет:** `com.sport.project.service.interfaces.teacher`

**Назначение:** Создание нового преподавателя. Валидирует входные данные, проверяет уникальность логина, создаёт сущность `TeacherEntity` и сохраняет её.

**Методы:**

| Метод | Возвращает | Параметры | Исключения | Описание |
|-------|------------|-----------|------------|----------|
| `createTeacher(TeacherCreationDTO dto)` | `TeacherDTO` | `dto` — DTO для создания преподавателя (fullName, login, password, isModerator) | `EntityAlreadyExistsException` | Создание нового преподавателя. Валидация уникальности логина, хеширование пароля, установка флага модератора |

**Зависимости:**
- `TeacherRepository` — для сохранения сущности
- `PasswordEncoder` — для хеширования пароля

---

#### 6.2.2. TeacherUpdatingService

**Пакет:** `com.sport.project.service.interfaces.teacher`

**Назначение:** Обновление данных преподавателя. Находит преподавателя по идентификатору и обновляет переданные поля.

**Методы:**

| Метод | Возвращает | Параметры | Исключения | Описание |
|-------|------------|-----------|------------|----------|
| `updateFullName(String newFullName, String login)` | `void` | `newFullName` — новое ФИО, `login` — текущий логин | `EntityNotFoundException` | Обновление ФИО преподавателя |
| `updateLogin(String newLogin, String login)` | `void` | `newLogin` — новый логин, `login` — текущий логин | `EntityNotFoundException`, `EntityAlreadyExistsException` | Обновление логина преподавателя |
| `updateIsModerator(boolean isModerator, int id)` | `void` | `isModerator` — новый статус модератора, `id` — ID преподавателя | `EntityNotFoundException` | Изменение статуса модератора преподавателя |

**Зависимости:**
- `TeacherRepository` — для поиска и обновления сущности

---

#### 6.2.3. TeacherDeletingService

**Пакет:** `com.sport.project.service.interfaces.teacher`

**Назначение:** Удаление преподавателя. Удаляет сущность из базы данных, каскадно удаляя связанные занятия.

**Методы:**

| Метод | Возвращает | Параметры | Исключения | Описание |
|-------|------------|-----------|------------|----------|
| `deleteById(int id)` | `void` | `id` — идентификатор преподавателя | `EntityNotFoundException` | Удаление преподавателя по ID |
| `deleteByFullName(String fullName)` | `void` | `fullName` — ФИО преподавателя | `EntityNotFoundException` | Удаление преподавателя по ФИО |
| `deleteByLogin(String login)` | `void` | `login` — логин преподавателя | `EntityNotFoundException` | Удаление преподавателя по логину |

**Зависимости:**
- `TeacherRepository` — для поиска и удаления сущности
- `LessonsRepository` — для удаления связанных занятий (orphanRemoval = true)

---

#### 6.2.4. TeacherBusiness

**Пакет:** `com.sport.project.service.interfaces.teacher`

**Назначение:** Бизнес-логика преподавателя. Содержит методы для работы с расписанием и уведомлениями.

**Методы:**

| Метод | Возвращает | Параметры | Исключения | Описание |
|-------|------------|-----------|------------|----------|
| `updateSchedule(LocalDate date, String lessonName)` | `void` | `date` — дата занятия, `lessonName` — название занятия | — | Обновление расписания преподавателя на указанную дату | - этот метод делать не обязательно, можно сделать потом
| `noticeStudent(String login)` | `boolean` | `login` — логин студента | `EntityNotFoundException` | Отметка посещаемости студента. Проверка наличия студента, создание/обновление записи в `VisitsEntity` |

**Зависимости:**
- `TeacherRepository` — для получения преподавателя
- `StudentRepository` — для поиска студента по логину
- `LessonsRepository` — для работы с занятиями
- `VisitsRepository` — для отметки посещаемости

---

### 6.3. Общие сервисы (CRUD)

#### 6.3.1. StudentService

**Пакет:** `com.sport.project.service`

**Назначение:** Базовые CRUD-операции для студентов. Используется для чтения данных.

**Методы:**

| Метод | Возвращает | Параметры | Исключения | Описание |
|-------|------------|-----------|------------|----------|
| `findById(Integer id)` | `StudentDTO` | `id` — идентификатор студента | `EntityNotFoundException` | Получение данных студента по ID |
| `findByLogin(String login)` | `StudentDTO` | `login` — логин студента | `EntityNotFoundException` | Получение данных студента по логину |
| `findByFullName(String fullName)` | `StudentDTO` | `fullName` — ФИО студента | `EntityNotFoundException` | Получение данных студента по ФИО |
| `findAll()` | `List<StudentDTO>` | — | — | Получение всех студентов |

**Зависимости:**
- `StudentRepository` — для поиска сущностей
- `Mapper` — для преобразования Entity → DTO

---

#### 6.3.2. TeacherService

**Пакет:** `com.sport.project.service`

**Назначение:** Базовые CRUD-операции для преподавателей. Используется для чтения данных.

**Методы:**

| Метод | Возвращает | Параметры | Исключения | Описание |
|-------|------------|-----------|------------|----------|
| `findById(Integer id)` | `TeacherDTO` | `id` — идентификатор преподавателя | `EntityNotFoundException` | Получение данных преподавателя по ID |
| `findByLogin(String login)` | `TeacherDTO` | `login` — логин преподавателя | `EntityNotFoundException` | Получение данных преподавателя по логину |
| `findByFullName(String fullName)` | `TeacherDTO` | `fullName` — ФИО преподавателя | `EntityNotFoundException` | Получение данных преподавателя по ФИО |
| `findAllModerators()` | `List<TeacherDTO>` | — | — | Получение всех преподавателей со статусом модератора |
| `findAll()` | `List<TeacherDTO>` | — | — | Получение всех преподавателей |

**Зависимости:**
- `TeacherRepository` — для поиска сущностей
- `Mapper` — для преобразования Entity → DTO

---

### 6.4. DTO (Data Transfer Objects)

#### 6.4.1. StudentCreationDTO

**Пакет:** `com.sport.project.dto`

**Назначение:** DTO для создания нового студента. Содержит только необходимые для создания поля.

**Поля:**
- `fullName` (String) — ФИО студента (обязательное)
- `login` (String) — логин (обязательное)
- `password` (String) — пароль (обязательное)
- `healthGroup` (Integer) — ID медицинской группы
- `teacherId` (Integer) — ID преподавателя

---

#### 6.4.2. StudentDTO

**Пакет:** `com.sport.project.dto`

**Назначение:** DTO для отображения данных студента.

**Поля:**
- `id` (Integer) — идентификатор
- `fullName` (String) — ФИО
- `login` (String) — логин
- `passwordHash` (String) — хеш пароля
- `healthGroup` (Integer) — ID медицинской группы
- `exist` (Map<LocalDate, Boolean>) — карта посещаемости: дата → статус
- `teacherId` (Integer) — ID преподавателя

---

#### 6.4.3. TeacherCreationDTO

**Пакет:** `com.sport.project.dto`

**Назначение:** DTO для создания нового преподавателя.

**Поля:**
- `fullName` (String) — ФИО (обязательное)
- `login` (String) — логин (обязательное)
- `password` (String) — пароль (обязательное)
- `isModerator` (Boolean) — статус модератора

---

#### 6.4.4. TeacherDTO

**Пакет:** `com.sport.project.dto`

**Назначение:** DTO для отображения данных преподавателя.

**Поля:**
- `id` (Integer) — идентификатор
- `fullName` (String) — ФИО
- `login` (String) — логин
- `passwordHash` (String) — хеш пароля
- `isModerator` (boolean) — статус модератора
- `schedule` (Map<LocalDate, String>) — расписание: дата → название занятия
- `students` (List<StudentEntity>) — список студентов преподавателя

---

### 6.5. Исключения

#### EntityNotFoundException

**Пакет:** `com.sport.project.exception`

**Назначение:** Бросается, когда сущность не найдена в базе данных.

---

#### EntityAlreadyExistsException

**Пакет:** `com.sport.project.exception`

**Назначение:** Бросается при попытке создать сущность с дублирующимся уникальным полем (логин, название).

---

## 7. Требуемые методы репозиториев для сервисов

Для корректной работы сервисов необходимо добавить следующие методы в репозитории:

### StudentRepository
- `Optional<StudentEntity> findByFullNameFirstNameAndFullNameLastNameAndFullNamePatronymic(String firstName, String lastName, String patronymic)` — поиск студента по ФИО
- `List<StudentEntity> findByGroupId(Integer groupId)` — получение всех студентов группы по ID
- `boolean existsByLogin(String login)` — проверка существования студента по логину

### TeacherRepository
- `boolean existsByLogin(String login)` — проверка существования преподавателя по логину
- `Optional<TeacherEntity> findByIdWithLessons(Integer id)` — получение преподавателя с занятиями (eager fetch)

### VisitsRepository
- `Optional<VisitsEntity> findByStudentIdAndLessonId(Integer studentId, Integer lessonId)` — поиск записи о посещении по студенту и занятию
- `List<VisitsEntity> findByStudentId(Integer studentId)` — получение всех посещений студента
- `List<VisitsEntity> findByLessonId(Integer lessonId)` — получение всех посещений занятия

### LessonsRepository
- `List<LessonsEntity> findByDateOfLesson(LocalDate date)` — получение занятий по дате
- `List<LessonsEntity> findByTeacherId(Integer teacherId)` — получение занятий преподавателя
- `Optional<LessonsEntity> findByDisciplineNameAndDateOfLesson(String disciplineName, LocalDate date)` — поиск занятия по дисциплине и дате
