package com.sport.project.dao.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity(name = "section_entity")
@Table(schema = "sport_schema", name = "sections")
@NoArgsConstructor
public class SectionEntity extends AbstractEntity<Integer> implements Serializable {

    private String name;
    private String description;
    private List<StudentEntity> studentsOnSection;

    public SectionEntity(String name, String description){
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

    @Column(name = "name", nullable = false, unique = true, length = 100)
    public String getName() {
        return name;
    }

    @Column(name = "description", nullable = false, length = 100)
    public String getDescription() {
        return description;
    }

    //FIXME: решить что использовать правильнее: Immutable список или нет
    @OneToMany
    @JoinColumn(name = "section_id")
    public List<StudentEntity> getStudentsOnSection() {
        return this.studentsOnSection;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("New name cannot be null");
        }
        this.name = name;
    }

    public void setDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("New description cannot be null");
        }
        this.description = description;
    }
}
