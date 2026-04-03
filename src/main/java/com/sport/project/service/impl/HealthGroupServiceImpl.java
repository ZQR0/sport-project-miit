package com.sport.project.service.impl;


import com.sport.project.dao.entity.HealthGroupsEntity;
import com.sport.project.dao.repository.HealthGroupRepository;
import com.sport.project.dto.HealthGroupDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.mapper.Mapper;
import com.sport.project.service.HealthGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HealthGroupServiceImpl implements HealthGroupService {

    private final HealthGroupRepository healthGroupRepository;

    @Override
    public HealthGroupDTO findById(Integer id) throws EntityNotFoundException {
        HealthGroupsEntity entity = this.healthGroupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Health Group with id %s not found", id)));

        return Mapper.map(entity);
    }

    @Override
    public HealthGroupDTO findByName(String name) throws EntityNotFoundException {
        HealthGroupsEntity entity = this.healthGroupRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Health Group with name %s not found", name)));

        return Mapper.map(entity);
    }

    @Override
    public List<HealthGroupDTO> findAll() {
        return this.healthGroupRepository
                .findAll()
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<StudentDTO> getStudents(Integer healthGroupId) throws EntityNotFoundException {
        if (!healthGroupRepository.existsById(healthGroupId)) {
            throw new EntityNotFoundException("Health Group with id " + healthGroupId + "not found");
        }

        return healthGroupRepository.findStudentsByHealthGroupId(healthGroupId)
                .stream()
                .map(Mapper::map)
                .toList();
    }


    @Override
    public List<StudentDTO> getStudents(String healthGroupName) throws EntityNotFoundException{
        return healthGroupRepository.findStudentsByHealthGroupName(healthGroupName)
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public boolean existsByName(String name) {
        return this.healthGroupRepository
                .existsByName(name);
    }
}
