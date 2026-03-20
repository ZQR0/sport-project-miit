package com.sport.project.dao.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

import java.io.Serializable;

/**
 * Теперь будет использоваться для сущностей вместо
 * {@link BaseEntity} и {@link Entity}
 * */
@MappedSuperclass
@Access(AccessType.PROPERTY)
public abstract class AbstractEntity<ID extends Serializable> {


    @Transient
    public abstract ID getId();

    /**
     * Не рекомендуется к использованию
     * НУЖЕН ТОЛЬКО ДЛЯ ТЕСТИРОВАНИЯ
     * */
    protected void setId(ID id) {
        // Теперь пустой, если понадобится, то придётся переопределить
    }

}
