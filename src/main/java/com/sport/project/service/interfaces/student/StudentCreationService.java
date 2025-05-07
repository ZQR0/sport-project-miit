package com.sport.project.service.interfaces.student;


import com.sport.project.dto.StudentCreationDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.exception.EntityAlreadyExistsException;

public interface StudentCreationService {
    StudentDTO createStudent(StudentCreationDTO dto) throws EntityAlreadyExistsException;
}
