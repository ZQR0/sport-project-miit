package com.sport.project.dao.entity;

import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;

/**
 * Теперь будет использоваться для сущностей вместо
 * {@link BaseEntity} и {@link Entity}
 * */
@MappedSuperclass
public abstract class AbstractEntity<ID extends Serializable> {

    protected ID id;

    public abstract ID getId();

    /**
     * Не рекомендуется к использованию
     * НУЖЕН ТОЛЬКО ДЛЯ ТЕСТИРОВАНИЯ
     * */
    protected void setId(ID id) {
        this.id = id;
    }

}
