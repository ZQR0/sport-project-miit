package com.sport.project.dao.entity;

import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;

/**
 * Теперь будет использоваться для сущностей
 * */
@MappedSuperclass
public abstract class AbstractEntity<ID extends Serializable> {

    private ID id;

    public abstract ID getId();

    protected void setId(ID id) {
        this.id = id;
    }

}
