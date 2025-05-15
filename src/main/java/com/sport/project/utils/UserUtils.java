package com.sport.project.utils;

import com.sport.project.dao.entity.StudentEntity;
import com.sport.project.dao.entity.TeacherEntity;
import com.sport.project.dao.repository.impl.StudentRepositoryImpl;
import com.sport.project.dao.repository.impl.TeacherRepositoryImpl;
import com.sport.project.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserUtils {

    private final TeacherRepositoryImpl teacherRepository;
    private final StudentRepositoryImpl studentRepository;

    public boolean isTeacherExistsByLogin(String login) {
        Optional<TeacherEntity> optionalTeacherEntity = this.teacherRepository.findByLogin(login);

        return optionalTeacherEntity.isPresent();
    }

    public boolean isStudentExistsByLogin(String login) {
        Optional<StudentEntity> optionalStudentEntity = this.studentRepository.findByLogin(login);

        return optionalStudentEntity.isPresent();
    }

}
