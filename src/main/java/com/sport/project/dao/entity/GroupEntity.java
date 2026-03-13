package com.sport.project.dao.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import lombok.Setter;

import java.io.Serializable;

@Entity(name = "groups_entity")
@Table(schema = "sport_schema", name = "groups")
@NoArgsConstructor
public class GroupEntity extends BaseEntity<Integer> implements Serializable {
    @Setter
    private String name;
    @Setter
    private String institute;

    public GroupEntity(String name, String institute) {
        //После изменения абстрактного класса переписать
        super(null, null, null);
        this.setName(name);
        this.setInstitute(institute);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    @Override
    public Integer getId() {
        return this.id;
    }

    @Column(name = "name", unique = true, nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "institute", nullable = false)
    public String getInstitute() {
        return institute;
    }

    //Убрать методы с аннотацией Transient после правки в абстрактном классе
    @Transient
    @Override
    public String getLogin() {
        return this.login;
    }

    @Transient
    @Override
    public String getPasswordHash() {
        return this.passwordHash;
    }

    @Transient
    @Override
    public String getFsp() {
        return this.fsp;
    }

    public static GroupEntityBuilder builder() {
        return new GroupEntityBuilder();
    }

    public static final class GroupEntityBuilder {
        private String name;
        private String institute;

        public GroupEntityBuilder name(String name) {
            this.name = name;
            return this;
        }

        public GroupEntityBuilder institute(String institute) {
            this.institute = institute;
            return this;
        }

        public GroupEntity build() {
            return new GroupEntity(
                    this.name,
                    this.institute
            );
        }
    }
}
