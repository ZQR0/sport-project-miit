package com.sport.project.service.interfaces;

import com.sport.project.dao.entity.TeacherEntity;
import com.sport.project.exception.EntityNotFoundException;

public interface TeacherUpdatingService {
    void updateFSP(String newFSP, TeacherEntity entity) throws EntityNotFoundException;
    void updateLogin(String newLogin, TeacherEntity entity) throws EntityNotFoundException;
    void updateIsModerator(boolean isModerator, TeacherEntity entity) throws EntityNotFoundException;
}
