package com.sport.project.service.interfaces;

import com.sport.project.dao.entity.StudentEntity;
import com.sport.project.exception.EntityNotFoundException;

public interface StudentUpdatingService {
    boolean updateFSP(String newFSP, StudentEntity entity) throws EntityNotFoundException;
    boolean updateLogin(String newLogin, StudentEntity entity) throws EntityNotFoundException;
    boolean updateHealthGroup(int newHealthGroup, StudentEntity entity) throws EntityNotFoundException;
    boolean updateStudentTeacher(int newTeacherId, StudentEntity entity) throws EntityNotFoundException;
}
