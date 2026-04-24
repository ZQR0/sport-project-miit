package com.sport.project.dao.repository;

import com.sport.project.dao.entity.GroupEntity;
import com.sport.project.dao.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<GroupEntity, Integer> {

    //Поиск группы по айди группы
    Optional<GroupEntity> findById(Integer id);

    //Поиск по названию группы
    Optional<GroupEntity> findByName(String name);

    //Поиск всех студентов по id группы
    @Query("SELECT student FROM student_entity student WHERE student.group.id = :group_id")
    List<StudentEntity> findStudentsByGroupId(@Param("group_id") Integer group_id);

    //Поиск всех студентов по названию группы
    @Query("SELECT student FROM student_entity student WHERE student.group.name = :group_name")
    List<StudentEntity> findStudentsByGroupName(@Param("group_name") String group_name);

    // Вывод всех студентов конкретной группы
//    (выше запрос тот же, Олесин - не знаю, подтягиваются автоматически или нужен join)
//    @Query("""
//            SELECT student FROM StudentEntity student
//                JOIN FETCH student.groups group
//                JOIN FETCH student.healthGroup healthGr
//                WHERE group.name = :groupName
//            """)
//    List<StudentEntity> findByGroupName(@Param("groupName") String groupName);

    //Поиск по названию института
    List<GroupEntity> findByInstitute(String institute);

    //Получение групп по институту с пагинацией
    List<GroupEntity> findByInstitute(String institute, Pageable pageable);

    //Проверка существования группы по названию
    boolean existsByName(String name);

    // Проверка, является ли группа пустой (не содержит студентов) (true - группа пустая, false - есть студенты)
    @Query("SELECT count(s) <= 0 FROM student_entity s WHERE s.group.id = :groupId")
    boolean isEmpty(@Param("groupId") Integer groupId);

    // Получение студентов группы с информацией о посещаемости за период
    @Query("""
            SELECT s FROM student_entity s
            JOIN FETCH s.visits v
            JOIN FETCH v.lessons l
            WHERE s.group.id = :groupId AND l.dateOfLesson BETWEEN :from AND :to
            """)
    List<StudentEntity> getStudentsWithAttendance(@Param("groupId") Integer groupId, @Param("from") LocalDate from, @Param("to") LocalDate to);

    // Перевод всех студентов из одной группы в другую
    @Modifying
    @Query("UPDATE student_entity s SET s.group.id = :toGroupId WHERE s.group.id = :fromGroupId")
    void transferStudents(@Param("fromGroupId") Integer fromGroupId, @Param("toGroupId") Integer toGroupId);

    // Удаление группы по ее названию
    @Modifying
    @Query("DELETE FROM groups_entity g WHERE g.name = :groupName")
    void deleteByName(@Param("groupName") String groupName);

}
