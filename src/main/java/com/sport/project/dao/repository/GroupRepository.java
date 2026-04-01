package com.sport.project.dao.repository;

import com.sport.project.dao.entity.GroupEntity;
import com.sport.project.dao.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
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
//                JOIN student.groups group
//                JOIN student.healthGroup healthGr
//                WHERE group.name = :groupName
//            """)
//    List<StudentEntity> findByGroupName(@Param("groupName") String groupName);

    //Поиск по названию института
    List<GroupEntity> findByInstitute(String institute);

    //Получение групп по институту с пагинацией
    List<GroupEntity> findByInstitute(String institute, Pageable pageable);

    //Проверка существования группы по названию
    boolean existsByName(String name);
}
