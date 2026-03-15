package com.sport.project.dao.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Entity(name = "discipline_entity")
@Table(schema = "public", name = "disciplines")
@NoArgsConstructor
public class DisciplineEntity extends AbstractEntity<Integer> implements Serializable {

    private String name;
    private List<LessonsEntity> lessonsEntities;


    public DisciplineEntity(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discipline_id")
    @Override
    public Integer getId() {
        return this.id;
    }


    @Column(name = "name", nullable = false, unique = true, length = 100)
    public String getName() {
        return this.name;
    }

    //FIXME: Надо будет подумать как сделать правильно (immutable список или нет)
    @OneToMany
    @JoinColumn(name = "discipline_id")
    public List<LessonsEntity> getLessonsEntities() {
        return this.lessonsEntities;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("New name cannot be null");
        }
        this.name = name;
    }
}
