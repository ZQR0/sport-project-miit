package com.sport.project.service.interfaces.teacher;

import com.sport.project.exception.EntityNotFoundException;

public interface TeacherUpdatingService {
    void updateFSP(String newFSP, String login) throws EntityNotFoundException;
    void updateLogin(String newLogin, String login) throws EntityNotFoundException;
    void updateIsModerator(boolean isModerator, int id) throws EntityNotFoundException;
}
