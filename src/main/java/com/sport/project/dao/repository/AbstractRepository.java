package com.sport.project.dao.repository;

import com.sport.project.dao.entity.Entity;

import java.util.List;
import java.util.Optional;

public interface AbstractRepository<E extends Entity<ID>, ID extends Number> {
    Optional<E> findById(ID id);
    E save(E entity);
    List<E> findAll();
}
