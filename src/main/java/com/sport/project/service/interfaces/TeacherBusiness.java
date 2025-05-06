package com.sport.project.service.interfaces;

import com.sport.project.dao.entity.StudentEntity;
import com.sport.project.exception.EntityNotFoundException;

import java.util.Date;
import java.util.Map;

public interface TeacherBusiness {
    Map<Date, String> updateSchedule(Map<Date, String> newSchedule);
    boolean noticeStudent(StudentEntity student) throws EntityNotFoundException;
}
