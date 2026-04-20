package com.sport.project.service.interfaces.teacher;

import com.sport.project.dto.TeacherBusinessLessonCreationDTO;

/**
 * Сервис бизнес логики для учителя
 */
public interface TeacherBusinessService {

    // Создаётся занятие -> выбирается группа -> всем студентам группы создаётся visit -> каждый visit привязывается к транзакции
    // Заполнение занятий наперёд. Препод создаёт занятие для группы и автоматически для каждого студента создаётся посещение
    // но там будет isExists = false
    //TODO: Никитос, если читаешь, подумай, можно ли так реализовать или лучше как-то по-другому
    void createLessonForFuture(TeacherBusinessLessonCreationDTO dto);
}
