package com.sport.project.service.impl;

import com.sport.project.dao.entity.GroupEntity;
import com.sport.project.dao.repository.GroupRepository;
import com.sport.project.dao.repository.StudentRepository;
import com.sport.project.dto.GroupCreationDTO;
import com.sport.project.dto.GroupDTO;
import com.sport.project.dto.StudentDTO;
import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.exception.EntityNotFoundException;
import com.sport.project.mapper.Mapper;
import com.sport.project.service.GroupService;
import com.sport.project.service.interfaces.group.GroupBusinessService;
import com.sport.project.service.interfaces.group.GroupCreationService;
import com.sport.project.service.interfaces.group.GroupDeletingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupServiceImpl implements GroupService, GroupCreationService, GroupBusinessService, GroupDeletingService {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;


    @Override
    public GroupDTO create(String name, String institute) throws EntityAlreadyExistsException {
        if (this.existsByName(name)) {
            throw new EntityAlreadyExistsException(
                    String.format("Group with name '%s' already exists", name)
            );
        }

        GroupEntity entity = GroupEntity.builder()
                .name(name)
                .institute(institute)
                .build();

        GroupEntity saved = groupRepository.save(entity);

        return Mapper.map(saved);
    }

    @Override
    public GroupDTO create(GroupCreationDTO dto) throws EntityAlreadyExistsException {
        return create(dto.getName(), dto.getInstitute());
    }

    @Override
    public GroupDTO findById(Integer id) throws EntityNotFoundException {
        GroupEntity entity = this.groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Group with id %s not found", id)));

        return Mapper.map(entity);
    }

    @Override
    public GroupDTO findByName(String name) throws EntityNotFoundException {
        GroupEntity entity = this.groupRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Group with name %s not found", name)));
        return Mapper.map(entity);
    }

    @Override
    public List<GroupDTO> findAll() {
        return this.groupRepository
                .findAll()
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<GroupDTO> findByInstitute(String institute) {
        return this.groupRepository
                .findAll()
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<StudentDTO> getStudents(Integer groupId) throws EntityNotFoundException {
        if (!groupRepository.existsById(groupId)) {
            throw new EntityNotFoundException("Группа с ID " + groupId + " не найдена");
        }

        // Получаем студентов группы
        return studentRepository.findByGroupId(groupId)
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<StudentDTO> getStudents(String groupName) throws EntityNotFoundException {
        if (!groupRepository.existsByName(groupName)) {
            throw new EntityNotFoundException("Группа с названием:  " + groupName + " не найдена");
        }
        // Получаем студентов группы
        return studentRepository.findByGroupName(groupName)
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public boolean existsByName(String name) {
        return this.groupRepository
                .existsByName(name);
    }

    @Override
    public int getStudentCount(Integer groupId) throws EntityNotFoundException {
        GroupEntity group = this.groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Group with id %s not found", groupId)));

        return group.getStudents().size();

    }

    @Override
    public boolean isEmpty(Integer groupId) throws EntityNotFoundException {
        GroupEntity group = this.groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Group with id %s not found", groupId)));
        return this.groupRepository.isEmpty(groupId);
    }

    @Override
    public List<StudentDTO> getStudentsWithAttendance(Integer groupId, LocalDate from, LocalDate to) throws EntityNotFoundException {
        if (!groupRepository.existsById(groupId)) {
            throw new EntityNotFoundException(String.format("Group with id %s not found", groupId));
        }

        if (from == null || to == null) {
            throw new IllegalArgumentException("Params of date cannot be null");
        }

        return this.groupRepository.getStudentsWithAttendance(groupId, from, to)
                .stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {EntityNotFoundException.class, IllegalArgumentException.class, jakarta.persistence.EntityNotFoundException.class})
    public void transferStudents(Integer fromGroupId, Integer toGroupId) throws EntityNotFoundException {
        if (!groupRepository.existsById(fromGroupId)) {
            throw new EntityNotFoundException(String.format("Group with id %s not found", fromGroupId));
        }
        if (!groupRepository.existsById(toGroupId)) {
            throw new EntityNotFoundException(String.format("Group with id %s not found", toGroupId));
        }

        groupRepository.transferStudents(fromGroupId, toGroupId);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {EntityNotFoundException.class, IllegalArgumentException.class, jakarta.persistence.EntityNotFoundException.class})
    public void deleteById(Integer id) throws EntityNotFoundException {
        if (id <= 0) throw new IllegalArgumentException("ID cannot be less or equal zero");
        this.groupRepository.deleteById(id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {EntityNotFoundException.class, IllegalArgumentException.class, jakarta.persistence.EntityNotFoundException.class})
    public void deleteByName(String groupName) throws EntityNotFoundException {
        if (!groupRepository.existsByName(groupName)) {
            throw new EntityNotFoundException(String.format("Group with name %s not found", groupName));
        }
        this.groupRepository.deleteByName(groupName);
    }
}
