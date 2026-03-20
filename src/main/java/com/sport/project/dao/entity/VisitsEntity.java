package com.sport.project.dao.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Entity(name = "visits_entity")
@Table(schema = "public", name = "visits")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Access(AccessType.PROPERTY)
public class VisitsEntity extends AbstractEntity<Integer> implements Serializable {

    private Integer id;

    StudentEntity student;
    LessonsEntity lessons;

    @Setter
    boolean isExists;

    public VisitsEntity(boolean isExists) {
        this.isExists = isExists;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id")
    @Override
    public Integer getId() {
        return this.id;
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

    @Column(name = "exists", nullable = false)
    public boolean isExists() {
        return isExists;
    }

}
