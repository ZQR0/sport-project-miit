package com.sport.project.service.interfaces.healthgroup;

import com.sport.project.dto.StudentDTO;
import com.sport.project.exception.EntityNotFoundException;

import java.util.List;

/**
 * Сервис бизнес-логики для медицинских групп здоровья.
 */
public interface HealthGroupBusinessService {

    /**
     * Получение количества студентов в медицинской группе.
     *
     * @param healthGroupId идентификатор медицинской группы
     * @return количество студентов
     * @throws EntityNotFoundException если медицинская группа не найдена
     */
    int getStudentCount(Integer healthGroupId) throws EntityNotFoundException;

    /**
     * Проверка возможности удаления медицинской группы.
     *
     * @param healthGroupId идентификатор медицинской группы
     * @return true если группа не содержит студентов и может быть удалена
     * @throws EntityNotFoundException если медицинская группа не найдена
     */
    boolean canDelete(Integer healthGroupId) throws EntityNotFoundException;

    /**
     * Получение студентов медицинской группы с подробной информацией.
     *
     * @param healthGroupId идентификатор медицинской группы
     * @return список студентов с подробной информацией
     * @throws EntityNotFoundException если медицинская группа не найдена
     */
    List<StudentDTO> getStudentsWithDetails(Integer healthGroupId) throws EntityNotFoundException;
}
