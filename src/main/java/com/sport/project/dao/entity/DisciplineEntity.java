package com.sport.project.dao.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "discipline_entity")
@Table(schema = "public", name = "disciplines")
@NoArgsConstructor
@Access(AccessType.PROPERTY)
public class DisciplineEntity extends AbstractEntity<Integer> implements Serializable {

    private Integer id;

    private String name;
    @Setter
    private List<LessonsEntity> lessonsEntities;


    public DisciplineEntity(String name) {
        this.name = name;
        this.lessonsEntities = new ArrayList<>();
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
    @OneToMany(mappedBy = "discipline")
    public List<LessonsEntity> getLessonsEntities() {
        return this.lessonsEntities;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("New name cannot be null");
        }
        this.name = name;
    }

    public boolean addLesson(LessonsEntity lesson) {
        if (lesson == null) {
            throw new IllegalArgumentException("Lesson cannot be null");
        }
        return this.lessonsEntities.add(lesson);
    }
}
