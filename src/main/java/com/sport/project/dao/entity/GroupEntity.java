package com.sport.project.dao.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity(name = "groups_entity")
@Table(schema = "public", name = "groups")
@NoArgsConstructor
@Access(AccessType.PROPERTY)
public class GroupEntity extends AbstractEntity<Integer> implements Serializable {


    private Integer id;

    private String name;
    private String institute;
    @Setter
    private List<StudentEntity> students;

    public GroupEntity(String name, String institute) {
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

    @OneToMany(mappedBy = "group")
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

    public boolean addStudent(StudentEntity student) {
        if (student == null) {
            throw new IllegalArgumentException("New student cannot be null");
        }
        return this.students.add(student);
    }
}
