package com.sport.project.dao.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "health_groups_entity")
@Table(schema = "public", name = "health_groups")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HealthGroupsEntity extends AbstractEntity<Integer> implements Serializable {

    String name;
    String description;
    private List<StudentEntity> students;

    public HealthGroupsEntity(String name, String description) {
        setName(name);
        setDescription(description);
        this.students = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "health_group_id")
    @Override
    public Integer getId() {
        return this.id;
    }


    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @OneToMany
    @JoinColumn(name = "health_group_id")
    public List<StudentEntity> getStudents() {
        return students;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public boolean addStudent(StudentEntity student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        return this.students.add(student);
    }
}
