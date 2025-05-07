package com.sport.project.service.interfaces.student;

import com.sport.project.dao.entity.TeacherEntity;
import com.sport.project.exception.EntityNotFoundException;

public interface StudentUpdatingService {
    void updateFSP(String newFSP, String login) throws EntityNotFoundException;
    void updateLogin(String newLogin, String login) throws EntityNotFoundException;
    void updateHealthGroup(int newHealthGroup, String login) throws EntityNotFoundException;
    void updateStudentTeacher(TeacherEntity entity, String login) throws EntityNotFoundException;
}
