package com.sport.project.mapper;

import com.sport.project.dao.entity.StudentEntity;
import com.sport.project.dao.entity.TeacherEntity;
import com.sport.project.dto.StudentDTO;
import com.sport.project.dto.TeacherDTO;

public class Mapper {

    public static TeacherDTO map(TeacherEntity entity) {
        return TeacherDTO.builder()
                .id(entity.getId())
                .fsp(entity.getFsp())
                .login(entity.getLogin())
                .passwordHash(entity.getPasswordHash())
                .isModerator(entity.isModerator())
                .schedule(entity.getSchedule())
                .students(entity.getStudents())
                .build();
    }

    public static StudentDTO map(StudentEntity entity) {
        return StudentDTO.builder()
                .id(entity.getId())
                .fsp(entity.getFsp())
                .login(entity.getLogin())
                .passwordHash(entity.getPasswordHash())
                .healthGroup(entity.getHealthGroup())
                .exist(entity.getExist())
                .teacherId(entity.getTeacher().getId())
                .build();
    }

}
