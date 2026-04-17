package com.sport.project.service.impl;


import com.sport.project.dao.entity.HealthGroupsEntity;
import com.sport.project.dao.repository.HealthGroupRepository;
import com.sport.project.dto.HealthGroupCreationDTO;
import com.sport.project.dto.HealthGroupDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.mapper.Mapper;
import com.sport.project.service.HealthGroupService;
import com.sport.project.service.interfaces.healthgroup.HealthGroupCreationService;
import com.sport.project.service.interfaces.healthgroup.HealthGroupDeletingService;
import com.sport.project.service.interfaces.healthgroup.HealthGroupUpdatingService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HealthGroupServiceImpl implements HealthGroupService, HealthGroupCreationService, HealthGroupDeletingService, HealthGroupUpdatingService {

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

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,
            rollbackFor = {
                    jakarta.persistence.EntityNotFoundException.class,
                    EntityAlreadyExistsException.class,
                    EntityNotFoundException.class
            })
    public HealthGroupDTO create(HealthGroupCreationDTO dto) throws EntityAlreadyExistsException {
        final String name = dto.getName();
        final String description = dto.getDescription();

        log.info("Health Group creation [DTO] started: {}, {}", name, description);

        HealthGroupsEntity healthGroups = HealthGroupsEntity.builder()
                .name(name)
                .description(description)
                .build();

        this.healthGroupRepository.save(healthGroups);

        log.info("Health Group saved [DTO] {}", healthGroups.getId());

        return Mapper.map(healthGroups);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,
            rollbackFor = {
                    jakarta.persistence.EntityNotFoundException.class,
                    EntityAlreadyExistsException.class,
                    EntityNotFoundException.class
            })
    public HealthGroupDTO create(String name, String description) throws EntityAlreadyExistsException {

        log.info("Health Group creation [PARAMS] started: {}, {}", name, description);

        HealthGroupsEntity healthGroups = HealthGroupsEntity.builder()
                .name(name)
                .description(description)
                .build();

        this.healthGroupRepository.save(healthGroups);

        log.info("Health Group saved [PARAMS] {}", healthGroups.getId());

        return Mapper.map(healthGroups);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {EntityNotFoundException.class, IllegalArgumentException.class, jakarta.persistence.EntityNotFoundException.class})
    public void deleteById(@NonNull Integer id) throws EntityNotFoundException {
        if (id <= 0) throw new IllegalArgumentException("ID cannot be less or equal zero");
        this.healthGroupRepository.deleteById(id);
        log.info("Deleting health group by id {} completed", id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {EntityNotFoundException.class, IllegalArgumentException.class, jakarta.persistence.EntityNotFoundException.class})
    public void deleteByName(@NonNull String name) throws EntityNotFoundException {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        HealthGroupsEntity healthGroup = this.healthGroupRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Health group with name '%s' not found", name)));

        this.healthGroupRepository.deleteByName(name);

        log.info("Deleting health group by name '{}' completed. Deleted health group with id {}",
                name, healthGroup.getId());
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {EntityNotFoundException.class, IllegalArgumentException.class, jakarta.persistence.EntityNotFoundException.class})
    public HealthGroupDTO updateName(Integer id, String name) throws EntityNotFoundException {
        log.info("Update name started");

        if (id <= 0) {
            throw new IllegalArgumentException("Health group id cannot be less or equal zero");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("New name cannot be null or empty");
        }

        HealthGroupsEntity healthGroup = this.healthGroupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Health group with id %s not found", id)));

        String old_name = healthGroup.getName();
        healthGroup.setName(name);

        log.info("Health group {} name updated from {} to {}", id, old_name, name);
        return Mapper.map(healthGroup);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {EntityNotFoundException.class, IllegalArgumentException.class, jakarta.persistence.EntityNotFoundException.class})
    public HealthGroupDTO updateDescription(Integer id, String description) throws EntityNotFoundException {
        log.info("Update description started");

        if (id <= 0) {
            throw new IllegalArgumentException("Health group id cannot be less or equal zero");
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("New description cannot be null or empty");
        }

        HealthGroupsEntity healthGroup = this.healthGroupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Health group with id %s not found", id)));

        String old_description = healthGroup.getDescription();
        healthGroup.setDescription(description);

        log.info("Health group {} description updated from {} to {}", id, old_description, description);
        return Mapper.map(healthGroup);
    }


}
