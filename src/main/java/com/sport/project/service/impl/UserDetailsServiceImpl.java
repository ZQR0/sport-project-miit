package com.sport.project.service.impl;

import com.sport.project.dao.entity.StudentEntity;
import com.sport.project.dao.entity.TeacherEntity;
import com.sport.project.dao.repository.StudentRepository;
import com.sport.project.dao.repository.TeacherRepository;
import com.sport.project.dao.repository.impl.StudentRepositoryImpl;
import com.sport.project.dao.repository.impl.TeacherRepositoryImpl;
import com.sport.project.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Этот сервис нужнн для реализации аутентификации пользователя
 * НЕ УДАЛЯТЬ
 * */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

//    private final TeacherRepositoryImpl teacherRepo;
//    private final StudentRepositoryImpl studentRepo;

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Optional<StudentEntity> student = this.studentRepository.findByLogin(login);
        if (student.isPresent()) {
            StudentEntity studentEntity = student.get();
            return Mapper.mapUserDetails(studentEntity);
        }
        Optional<TeacherEntity> teacher = this.teacherRepository.findByLogin(login);
        if (teacher.isPresent()) {
            TeacherEntity teacherEntity = teacher.get();
            return Mapper.mapUserDetails(teacherEntity);
        }

        throw new UsernameNotFoundException(String.format("User with login %s not found", login));
    }
}
