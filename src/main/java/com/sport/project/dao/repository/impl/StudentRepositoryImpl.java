package com.sport.project.dao.repository.impl;

import com.sport.project.dao.entity.StudentEntity;
import com.sport.project.dao.repository.StudentRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepositoryImpl extends AbstractRepositoryImpl<StudentEntity, Integer> implements StudentRepository {

    public StudentRepositoryImpl(Class<StudentEntity> entityClass) {
        super(entityClass);
    }

    public StudentRepositoryImpl() {
        super(StudentEntity.class);
    }

    @Override
    public List<StudentEntity> findAll() {
        String queryString = "SELECT * FROM student_entity e";
        TypedQuery<StudentEntity> query = this.entityManager.createQuery(queryString, StudentEntity.class);
        final List<StudentEntity> resultList = query.getResultList();
        if (resultList == null || resultList.isEmpty()) {
            return Collections.emptyList();
        }

        return resultList;
    }

    @Override
    public Optional<StudentEntity> findByFSP(String fsp) {
        if (fsp != null) {
            try {
                String queryString = "SELECT e FROM student_entity e WHERE e.fsp =: fsp";
                TypedQuery<StudentEntity> query = this.entityManager.createQuery(queryString, StudentEntity.class);
                query.setParameter("fsp", fsp);
                return Optional.of(query.getSingleResult());
            } catch (NoResultException ex) {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<StudentEntity> findByLogin(String login) {
        if (login != null) {
            try {
                String queryString = "SELECT e FROM student_entity e WHERE e.login =: login";
                TypedQuery<StudentEntity> query = this.entityManager.createQuery(queryString, StudentEntity.class);
                query.setParameter("login", login);
                return Optional.of(query.getSingleResult());
            } catch (NoResultException ex) {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }
}
