package com.sport.project.dao.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.OffsetDateTime;

//@Entity(name = "lessons_entity")
//@Table(schema = "sport_schema", name = "lessons")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonsEntity extends BaseEntity<Integer> implements Serializable {

    Integer disciplineId;
    OffsetDateTime dateOfLesson;
    TeacherEntity teacher;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String getFsp() {
        return null;
    }

    @Override
    public String getLogin() {
        return null;
    }

    @Override
    public String getPasswordHash() {
        return null;
    }

    @Column(name = "discipline_id", nullable = false)
    public Integer getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(@NonNull Integer disciplineId) {
        this.disciplineId = disciplineId;
    }

    @Column(name = "date_of_lesdsson", nullable = false)
    public OffsetDateTime getDateOfLesson() {
        return dateOfLesson;
    }

    public void setDateOfLesson(@NonNull OffsetDateTime dateOfLesson) {
        this.dateOfLesson = dateOfLesson;
    }

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    public TeacherEntity getTeacher() {
        return this.teacher;
    }

    public void setTeacher(@NonNull TeacherEntity newTeacher) {
        this.teacher = newTeacher;
    }
}
