package com.sport.project.dao.entity;

import jakarta.persistence.*;

import java.io.Serializable;

/**
 * Теперь будет использоваться для сущностей вместо
 * {@link BaseEntity} и {@link Entity}
 * */
@MappedSuperclass
public abstract class AbstractEntity<ID extends Serializable> {

    protected ID id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public ID getId() {
        return this.id;
    }

    protected void setId(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        this.id = id;
    }
}
