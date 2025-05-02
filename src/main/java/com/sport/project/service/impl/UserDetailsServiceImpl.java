package com.sport.project.service.impl;

import com.sport.project.dao.entity.StudentEntity;
import com.sport.project.dao.entity.TeacherEntity;
import com.sport.project.dao.repository.impl.StudentRepositoryImpl;
import com.sport.project.dao.repository.impl.TeacherRepositoryImpl;
import com.sport.project.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final TeacherRepositoryImpl teacherRepo;
    private final StudentRepositoryImpl studentRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<TeacherEntity> teacher_username = teacherRepo.findByLogin(username);
        if (teacher_username.isPresent()) {
            return Mapper.mapUserDetails(teacher_username.get());
        }

        Optional<StudentEntity> student_username = studentRepo.findByLogin(username);
        if (student_username.isPresent()) {
            return Mapper.mapUserDetails(student_username.get());
        }

        return null;
    }
}
