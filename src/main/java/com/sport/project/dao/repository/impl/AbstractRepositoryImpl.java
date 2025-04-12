package com.sport.project.dao.repository.impl;

import com.sport.project.dao.entity.Entity;
import com.sport.project.dao.repository.AbstractRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
@RequiredArgsConstructor
public abstract class AbstractRepositoryImpl<E extends Entity<ID>, ID extends Number> implements AbstractRepository<E, ID> {

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<E> entityClass;

    @Override
    public Optional<E> findById(ID id) {
        E entity = this.entityManager.find(this.entityClass, id);
        if (entity == null) return Optional.empty();

        return Optional.of(entity);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public E save(E entity) {

        if (entity == null) {
            throw new NullPointerException("Entity can not be null");
        }

        if (this.entityManager.contains(entity)) {
            this.entityManager.persist(entity);
        } else {
            this.entityManager.merge(entity);
        }

        return entity;
    }

    @Override
    public abstract List<E> findAll();
}
