package com.sport.project.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/**
 * Будем встраивать этот класс в {@link StudentEntity} и {@link TeacherEntity}
 * через аннотацию {@link jakarta.persistence.Embedded}
 * */
@Embeddable
@Getter
@Setter
public class FullName {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "patronymic")
    private String patronymic;


}
