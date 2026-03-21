package com.sport.project.dao.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "health_groups_entity")
@Table(name = "health_groups")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HealthGroupsEntity extends AbstractEntity<Integer> implements Serializable {

    String name;
    String description;
    List<StudentEntity> students;

    public HealthGroupsEntity(String name, String description) {
        setName(name);
        setDescription(description);
        this.students = new ArrayList<>();
    }

    public HealthGroupsEntity(String name, String description, List<StudentEntity> students) {
        setName(name);
        setDescription(description);
        setStudents(students);
    }


    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    //TODO: определить нормальный каскад
    @OneToMany(mappedBy = "healthGroup", cascade = CascadeType.PERSIST)
    public List<StudentEntity> getStudents() {
        return students;
    }

    @Column(name = "description", nullable = false, length = 100)
    public String getDescription() {
        return description;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public void setStudents(@NonNull List<StudentEntity> students) {
        this.students = students;
    }

    public boolean addStudent(StudentEntity student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        return this.students.add(student);
    }
}
