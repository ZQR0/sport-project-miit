package com.sport.project.dao.repository;

import com.sport.project.dao.entity.VisitsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VisitsRepository extends JpaRepository<VisitsEntity, Integer> {

    /*Поиск посещений студента по id студента*/
    List<VisitsEntity> findByStudentId(Integer studentId);

    /*Поиск посещений студента по логину студента*/
    Optional<VisitsEntity> findByStudentLogin (String studentLogin);

    //Метод для проверки существования записи (вроде)
    Optional<VisitsEntity> findByStudentIdAndLessonId(Integer studentId, Integer lessonId);

    //Удаление посещений по логину студента
    void deleteByStudent_Login(String studentLogin);
}
