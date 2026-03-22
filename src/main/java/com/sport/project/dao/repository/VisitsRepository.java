package com.sport.project.dao.repository;

import com.sport.project.dao.entity.VisitsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisitsRepository extends JpaRepository<VisitsEntity, Long> {

    /*Поиск посещений студента по айди студента(вроде)*/
    Optional<VisitsEntity> findByStudentId(Long studentId);

    /*Поиск посещений студента по логину студента*/
    Optional<VisitsEntity> findByStudentLogin (String studentLogin);

    //Метод для проверки существования записи (вроде)
    Optional<VisitsEntity> findByStudentIdAndLessonId(Integer studentId, Integer lessonId);
}
