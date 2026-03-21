package com.sport.project.dao.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "section_entity")
@Table(name = "sections")
@NoArgsConstructor
public class SectionEntity extends AbstractEntity<Integer> implements Serializable {

    private String name;
    private String description;
    private List<StudentEntity> studentsOnSection;

    public SectionEntity(String name, String description) {
        this.setName(name);
        this.setDescription(description);
        this.studentsOnSection = new ArrayList<>();
    }

    public SectionEntity(String name, String description, List<StudentEntity> studentsOnSection) {
        setName(name);
        setDescription(description);
        setStudentsOnSection(studentsOnSection);
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
    @OneToMany(mappedBy = "section")
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

    public void setStudentsOnSection(List<StudentEntity> studentsOnSection) {
        if (studentsOnSection == null) {
            throw new IllegalArgumentException("Students on section cannot be null list");
        }
        this.studentsOnSection = studentsOnSection;
    }

    public boolean addStudent(StudentEntity student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        return this.studentsOnSection.add(student);
    }
}
