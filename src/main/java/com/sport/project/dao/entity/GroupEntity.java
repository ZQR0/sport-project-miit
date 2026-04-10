package com.sport.project.dao.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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

    @OneToMany(mappedBy = "group", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
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

    public static GroupEntityBuilder builder() { return new GroupEntityBuilder(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GroupEntity groupEntity = (GroupEntity) o;
        return groupEntity.getName() != null && Objects.equals(groupEntity.getName(), this.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, institute);
    }

    public static class GroupEntityBuilder {
        GroupEntity group = new GroupEntity();

        public GroupEntityBuilder name(String name) {
            group.setName(name);
            return this;
        }

        public GroupEntityBuilder institute(String institute) {
            group.setInstitute(institute);
            return this;
        }

        public GroupEntityBuilder student(List<StudentEntity> students) {
            group.setStudents(students);
            return this;
        }

        public GroupEntity build() { return this.group; }
    }
}
