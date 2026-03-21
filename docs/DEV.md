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

**Замечания:**
- Метод `findByGroupName` возвращает `Optional<StudentEntity>`, что некорректно для поиска по группе (в группе может быть несколько студентов). Рекомендуется вернуть `List<StudentEntity>`.
- Закомментированный метод `findByLFP` (поиск по ФИО) содержит HQL с `CONCAT`, который не поддерживается. Требуется альтернативная реализация.

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

**Замечания:**
- Оба метода возвращают `Optional<VisitsEntity>`, что предполагает единственную запись. Однако студент может иметь несколько записей о посещении (разные занятия). Рекомендуется вернуть `List<VisitsEntity>`.

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
- `findByFSP(String fsp)`
- `findByLogin(String login)`

### 4.3. TeacherRepositoryImpl

**Статус:** `@Deprecated`

Закомментированная реализация с методами:
- `findAll()`
- `findByFSP(String fsp)`
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

#### 5.2.5. Отметка посещения по уроку

**Метод:** `List<VisitsEntity> findByLessonsDateAndDiscipline(LocalDate date, String disciplineName)`
**Репозиторий:** `VisitsRepository`
**Возвращает:** Список всех записей о посещении для занятия
**Назначение:** Получить всех студентов, отмеченных на занятии (присутствующие/отсутствующие).

#### 5.2.6. Проверка существования посещения

**Метод:** `boolean existsByStudentLoginAndLessonsDate(String studentLogin, LocalDate date)`
**Репозиторий:** `VisitsRepository`
**Возвращает:** `true` если запись о посещении существует, иначе `false`
**Назначение:** Быстрая проверка без загрузки всей сущности.

#### 5.2.7. Получение групп по институту с пагинацией

**Метод:** `List<GroupEntity> findByInstitute(String institute, Pageable pageable)`
**Репозиторий:** `GroupRepository`
**Возвращает:** Список групп института с пагинацией
**Назначение:** Эффективная загрузка больших списков групп.

#### 5.2.8. Удаление студентов группы

**Метод:** `void deleteByGroupName(String groupName)`
**Репозиторий:** `StudentRepository`
**Возвращает:** `void` (удаление записей)
**Назначение:** Удаление всех студентов группы при её удалении (если не настроен cascade).

#### 5.2.9. Поиск студентов по фамилии

**Метод:** `List<StudentEntity> findByFullNameLastName(String lastName)`
**Репозиторий:** `StudentRepository`
**Возвращает:** Список студентов с указанной фамилией
**Назначение:** Поиск студентов по фамилии (например, для алфавитного списка).

#### 5.2.10. Подсчёт количества студентов в группе

**Метод:** `int countByGroupName(String groupName)`
**Репозиторий:** `StudentRepository`
**Возвращает:** Количество студентов в группе
**Назначение:** Быстрое получение размера группы без загрузки всех сущностей.

#### 5.2.11. Проверка существования группы по названию

**Метод:** `boolean existsByName(String name)`
**Репозиторий:** `GroupRepository`
**Возвращает:** `true` если группа существует, иначе `false`
**Назначение:** Валидация уникальности названия группы перед созданием.

#### 5.2.12. Проверка существования дисциплины по названию

**Метод:** `boolean existsByName(String name)`
**Репозиторий:** `DisciplineRepository`
**Возвращает:** `true` если дисциплина существует, иначе `false`
**Назначение:** Валидация уникальности названия дисциплины.

#### 5.2.13. Получение преподавателей-модераторов

**Метод:** `List<TeacherEntity> findByIsModeratorTrue()`
**Репозиторий:** `TeacherRepository`
**Возвращает:** Список всех преподавателей со статусом модератора
**Назначение:** Быстрое получение активных модераторов системы.

#### 5.2.14. Удаление посещений по студенту

**Метод:** `void deleteByStudentLogin(String studentLogin)`
**Репозиторий:** `VisitsRepository`
**Возвращает:** `void`
**Назначение:** Очистка истории посещений при удалении студента.

#### 5.2.15. Удаление занятий по дисциплине

**Метод:** `void deleteByDisciplineName(String disciplineName)`
**Репозиторий:** `LessonsRepository`
**Возвращает:** `void`
**Назначение:** Очистка занятий при удалении дисциплины.
