package com.sport.project.dao.repository;

import com.sport.project.dao.entity.StudentEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

    //Поиск студента по логину
    Optional<StudentEntity> findByLogin(String login);

    //Поиск по ФИО
    List<StudentEntity> findByFullNameFirstNameAndFullNameLastNameAndFullNamePatronymic(
            String firstName, String lastName, String patronymic);

    //Поиск по фамилии студента
    List<StudentEntity> findByFullNameLastName(String lastName);

    //Вывод всех студентов, у которых есть секция sectionName
    List<StudentEntity> findBySectionName(String sectionName);

    //Вывод всех студентов, конкретного занятия по конкретной дате (если гр и гр здор подтянется без join)
    @Query("""
            SELECT student FROM student_entity student
                JOIN student.visits visit
                JOIN visit.lessons lesson
                WHERE lesson.date_of_lesson = :date
            """)
    List<StudentEntity> findByDateOfLesson(@Param("date") LocalDate date);

    // Вывод студентов, которые посетили занятие по определённой дисциплине (если гр и гр здор не подтянется без join)
    @Query("""
            SELECT student FROM StudentEntity student
                JOIN student.visits visit
                JOIN visit.lessons lesson
                JOIN lesson.disciplines discipline
                JOIN student.groups group
                JOIN student.healthGroup healthGr
                WHERE visit.isExists = TRUE
                AND discipline.name = :disciplineName
            """)
    List<StudentEntity> findByDisciplineAndAttendance(@Param("disciplineName") String disciplineName);

    //Удаление всех студентов группы при удалении группы
    void deleteByGroup_Name(String groupName);

    //Подсчет количества студентов в группе
    int countByGroup_Name(String groupName);

//    @Query("SELECT student FROM StudentEntity student " +
//            "WHERE student.group.id = :groupId;"
//    )
//    List<StudentEntity> findByGroupId (Integer groupId);

    //Поиск всех студентов по айди группы
    List<StudentEntity> findByGroupId(Integer groupId);

    //Поиск по айди секции
    List<StudentEntity> findBySectionId(Integer sectionId);

    //Поиск по айди группы здоровья
    List<StudentEntity> findByHealthGroupId(Integer healthGroupId);

    //Проверка на существование студента с приведенным логином
    boolean existsByLogin(String login);

    //Поиск студента по LFP (ФИО, с заменой "_" на пробел)
    // FIXME: не заметил ошибку при ревью, CONCAT не поддерживается в HQL, потом найдём замену
//    @Query("SELECT student FROM student_entity student" +
//            "WHERE CONCAT(student.fullName.lastName, ' ', student.fullName.firstName, ' ', COALESCE(student.fullName.patronymic))" +
//            "LIKE CONCAT('%', :lfp, '%')")
//    List<StudentEntity> findByLFP(@Param("lfp") String lfp);


    //FIXME: Не работает при запуске, надо исправить query
    /*Поиск по группе. Найти всех студентов группы.*/
//    @Query("SELECT s FROM StudentEntity s WHERE s.group.name = :groupName")
//    List<StudentEntity> findByGroupName(String groupName);
}