package com.sport.project.service.interfaces.teacher;

import com.sport.project.exception.EntityNotFoundException;

public interface TeacherDeletingService {
    void deleteById(int id) throws EntityNotFoundException;
    void deleteByFSP(String fsp) throws EntityNotFoundException;
    void deleteByLogin(String login) throws EntityNotFoundException;
}
