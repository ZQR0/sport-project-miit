package com.sport.project.dao.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity(name = "groups_entity")
@Table(name = "groups")
@NoArgsConstructor
public class GroupEntity extends AbstractEntity<Integer> implements Serializable {

    private String name;
    private String institute;
    private List<StudentEntity> students;

    public GroupEntity(String name, String institute) {
        this.setName(name);
        this.setInstitute(institute);
    }


    @Column(name = "name", unique = true, nullable = false, length = 15)
    public String getName() {
        return name;
    }

    @Column(name = "institute", nullable = false, length = 100)
    public String getInstitute() {
        return institute;
    }

    @OneToMany(mappedBy = "group", cascade = CascadeType.PERSIST)
    public List<StudentEntity> getStudents() {
        return this.students;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("New name cannot be null");
        }
        this.name = name;
    }

    public void setInstitute(String institute) {
        if (institute == null) {
            throw new IllegalArgumentException("New institute cannot be null");
        }
        this.institute = institute;
    }

    public void setStudents(List<StudentEntity> students) {
        if (students == null) {
            throw new IllegalArgumentException("Students cannot be null");
        }
        this.students = students;
    }

    public boolean addStudent(StudentEntity student) {
        if (student == null) {
            throw new IllegalArgumentException("New student cannot be null");
        }
        return this.students.add(student);
    }
}
