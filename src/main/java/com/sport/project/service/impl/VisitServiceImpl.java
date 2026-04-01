package com.sport.project.service.impl;

import com.sport.project.dao.entity.VisitsEntity;
import com.sport.project.dao.repository.VisitsRepository;
import com.sport.project.dto.VisitDTO;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.mapper.Mapper;
import com.sport.project.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class VisitServiceImpl implements VisitService {

    private final VisitsRepository visitsRepository;

    @Override
    public VisitDTO findById(Integer id) throws EntityNotFoundException {
        VisitsEntity entity = this.visitsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Visit with id %s not found", id)));
        return Mapper.map(entity);
    }

    @Override
    public VisitDTO findByStudentAndLesson(String studentLogin, Integer lessonId) throws EntityNotFoundException {
        VisitsEntity entity = this.visitsRepository.findByStudentIdAndLessonId(studentLogin, lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Not found"));
        return Mapper.map(entity);
    }

    @Override
    public List<VisitDTO> findAll() {
        return this.visitsRepository.findAll()
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<VisitDTO> findByStudent(String studentLogin) {
        return this.visitsRepository.findByStudentLogin(studentLogin)
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<VisitDTO> findByLesson(Integer lessonId) {
        return this.visitsRepository.findByLessonId(lessonId)
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<VisitDTO> findByDateRange(LocalDate from, LocalDate to) {
        return this.visitsRepository.findByDateRange(from, to)
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public boolean existsById(Integer id) {
        return this.visitsRepository.findById(id).isPresent();
    }
}
