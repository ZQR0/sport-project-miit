package com.sport.project.service.interfaces.teacher;

import com.sport.project.dto.TeacherCreationDTO;
import com.sport.project.dto.TeacherDTO;
import com.sport.project.exception.EntityAlreadyExistsException;

public interface TeacherCreationService {
    TeacherDTO createTeacher(TeacherCreationDTO dto) throws EntityAlreadyExistsException;
}
