package com.sport.project.dao.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Entity(name = "visits_entity")
@Table(name = "visits")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VisitsEntity extends AbstractEntity<Integer> implements Serializable {

    StudentEntity student;
    LessonsEntity lessons;

    @Setter
    boolean isExists;

    public VisitsEntity(boolean isExists) {
        this.isExists = isExists;
    }


    @ManyToOne
    @JoinColumn(name = "student_id")
    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(@NonNull StudentEntity student) {
        this.student = student;
    }

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    public LessonsEntity getLessons() {
        return lessons;
    }

    public void setLessons(@NonNull LessonsEntity lessons) {
        this.lessons = lessons;
    }

    @Column(name = "is_exists", nullable = false)
    public boolean isExists() {
        return isExists;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        VisitsEntity that = (VisitsEntity) o;
        return isExists == that.isExists && Objects.equals(student, that.student) && Objects.equals(lessons, that.lessons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, lessons, isExists);
    }
}
