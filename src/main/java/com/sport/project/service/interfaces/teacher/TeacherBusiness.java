package com.sport.project.service.interfaces.teacher;

import com.sport.project.dao.entity.StudentEntity;
import com.sport.project.dao.entity.TeacherEntity;
import com.sport.project.exception.EntityNotFoundException;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

public interface TeacherBusiness {
    void updateSchedule(LocalDate date, String lessonName);
    boolean noticeStudent(String login) throws EntityNotFoundException;
}
