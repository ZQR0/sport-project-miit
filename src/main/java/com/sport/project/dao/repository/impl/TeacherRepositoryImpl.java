package com.sport.project.dao.repository.impl;

import com.sport.project.dao.entity.TeacherEntity;
import com.sport.project.dao.repository.TeacherRepository;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class TeacherRepositoryImpl extends AbstractRepositoryImpl<TeacherEntity, Integer> implements TeacherRepository {

    public TeacherRepositoryImpl(Class<TeacherEntity> entityClass) {
        super(entityClass);
    }

    public TeacherRepositoryImpl() {
        super(TeacherEntity.class);
    }

    @Override
    public List<TeacherEntity> findAll() {
        String queryString = "SELECT * FROM teacher_entity e;";
        TypedQuery<TeacherEntity> query = this.entityManager.createQuery(queryString, TeacherEntity.class);
        if (query.getResultList() == null || query.getResultList().isEmpty()) {
            return  Collections.emptyList();
        }

        return query.getResultList();
    }

    @Override
    public Optional<TeacherEntity> findByFSP(String fsp) {
        if (fsp != null) {
            String queryString = "SELECT e FROM teacher_entity e WHERE e.fsp = :fsp;";
            TypedQuery<TeacherEntity> query = this.entityManager.createQuery(queryString, TeacherEntity.class);
            query.setParameter("fsp", fsp);
            return Optional.of(query.getSingleResult());
        }

        return Optional.empty();
    }

    @Override
    public Optional<TeacherEntity> findByLogin(String login) {
        if (login != null) {
            String queryString = "SELECT e FROM teacher_entity e WHERE e.login=:login;";
            TypedQuery<TeacherEntity> query = this.entityManager.createQuery(queryString, TeacherEntity.class);
            query.setParameter("login", login);
            return Optional.of(query.getSingleResult());
        }

        return Optional.empty();
    }

    @Override
    public List<TeacherEntity> findAllModerators() {
        String queryString = "SELECT * FROM teacher_entity e WHERE e.isModerator = TRUE;";
        TypedQuery<TeacherEntity> query = this.entityManager.createQuery(queryString, TeacherEntity.class);
        return query.getResultList();
    }
}
