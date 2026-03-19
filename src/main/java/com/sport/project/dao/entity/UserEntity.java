package com.sport.project.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;
import java.util.Date;

/**
 * В этом классе находятся все общие поля для {@link TeacherEntity} и {@link StudentEntity}
 * чтобы не переписывать их по 10 раз и избежать избыточности
 * */
@MappedSuperclass
public abstract class UserEntity<ID extends Serializable> extends AbstractEntity<ID> {

    private String login;
    private String passwordHash;
    private FullName fullName;
    private Date birthday;

    public abstract ID getId();

    @Column(name = "login", unique = true, length = 50, nullable = false)
    public String getLogin() {
        return this.login;
    }

    @Column(name = "password_hash", nullable = false)
    public String getPasswordHash() {
        return passwordHash;
    }

    @Embedded
    public FullName getFullName() {
        return fullName;
    }

    @Column(name = "birthday")
    public Date getBirthday() {
        return birthday;
    }

    public void setLogin(String login) {
        if (login == null) {
            throw new IllegalArgumentException("New login cannot be null");
        }
        this.login = login;
    }

    public void setPasswordHash(String passwordHash) {
        if (passwordHash == null) {
            throw new IllegalArgumentException("New password cannot be null");
        }
        this.passwordHash = passwordHash;
    }

    public void setFullName(FullName fullName) {
        if (fullName == null) {
            throw new IllegalArgumentException("Fullname cannot be null");
        }
        this.fullName = fullName;
    }

    public void setBirthday(Date birthday) {
        if (birthday == null) {
            throw new IllegalArgumentException("New birthday cannot be null");
        }

        if (birthday.after(new Date())) {
            throw new IllegalArgumentException("Invalid type of birthday");
        }
        this.birthday = birthday;
    }
}
