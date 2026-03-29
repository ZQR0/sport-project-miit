package com.sport.project.dao.repository;

import com.sport.project.dao.entity.StudentEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

    //Поиск студента по логину
    Optional<StudentEntity> findByLogin(String login);

    //FIXME: не работает: нужно писать Query вручную
    //Поиск по ФИО
//    List<StudentEntity> findByFullNameAndFullLastNameAndFullNamePatronymic(String firstName, String lastName, String patronymic);

    //Поиск по фамилии студента
    List<StudentEntity> findByFullNameLastName(String lastName);

    //Вывод всех студентов, у которых есть секция sectionName
    List<StudentEntity> findBySectionName(String sectionName);

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

    //
    List<StudentEntity> findBySectionId(Integer sectionId);


    //FIXME: добавить метод на поиск группы здоровья по айди.
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