package com.sport.project.service.interfaces;


import com.sport.project.dao.entity.TeacherEntity;
import com.sport.project.dto.StudentCreationDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.exception.EntityAlreadyExistsException;

public interface StudentCreationService {
    StudentDTO createStudentByModerator(StudentCreationDTO dto, TeacherEntity moderator) throws EntityAlreadyExistsException;
    StudentDTO createStudent(StudentCreationDTO dto) throws EntityAlreadyExistsException;
}
