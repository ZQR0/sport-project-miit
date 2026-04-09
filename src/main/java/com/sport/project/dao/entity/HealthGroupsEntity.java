package com.sport.project.dao.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public static HealthEntityBuilder builder() { return new HealthEntityBuilder(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        HealthGroupsEntity that = (HealthGroupsEntity) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(students, that.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    public static class HealthEntityBuilder {
        HealthGroupsEntity healthGroups = new HealthGroupsEntity();

        public HealthEntityBuilder name(String name) {
            healthGroups.setName(name);
            return this;
        }

        public HealthEntityBuilder description(String description) {
            healthGroups.setDescription(description);
            return this;
        }

        public HealthEntityBuilder students(List<StudentEntity> students) {
            healthGroups.setStudents(students);
            return this;
        }

        public HealthGroupsEntity build() { return this.healthGroups; }
    }
}
