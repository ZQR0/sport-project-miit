package com.sport.project.service.interfaces.teacher;

import com.sport.project.dao.entity.StudentEntity;
import com.sport.project.dao.entity.TeacherEntity;
import com.sport.project.exception.EntityNotFoundException;

import java.util.Date;
import java.util.Map;

public interface TeacherBusiness {
    Map<Date, String> updateSchedule(Date date, String lessonName, TeacherEntity entity);
    boolean noticeStudent(StudentEntity student, Date date) throws EntityNotFoundException;
}
