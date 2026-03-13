package com.sport.project.dao.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity(name = "section_entity")
@Table(schema = "sport_schema", name = "sections")
@NoArgsConstructor
public class SectionEntity extends BaseEntity<Integer> implements Serializable {

    @Setter
    private String name;
    @Setter
    private String description;

    public SectionEntity(String name, String description){
        //После изменения абстрактного класса переписать
        super(null, null, null);
        this.setName(name);
        this.setDescription(description);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    @Override
    public Integer getId() {
        return this.id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
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

    public static SectionEntityBuilder builder() {
        return new SectionEntityBuilder();
    }

    public static final class SectionEntityBuilder {
        private String name;
        private String description;

        public SectionEntityBuilder name(String name) {
            this.name = name;
            return this;
        }

        public SectionEntityBuilder description(String description) {
            this.description = description;
            return this;
        }

        public SectionEntity build() {
            return new SectionEntity(
                    this.name,
                    this.description
            );
        }
    }

}
