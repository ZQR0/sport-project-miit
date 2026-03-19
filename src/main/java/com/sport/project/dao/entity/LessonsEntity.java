package com.sport.project.dao.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity(name = "lessons_entity")
@Table(schema = "public", name = "lessons")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonsEntity extends AbstractEntity<Integer> implements Serializable {

    Integer disciplineId;
    OffsetDateTime dateOfLesson;
    TeacherEntity teacher;

    public LessonsEntity(OffsetDateTime dateOfLesson) {
        this.dateOfLesson = dateOfLesson;
    }

    public LessonsEntity(OffsetDateTime dateOfLesson, TeacherEntity teacher) {
        setDateOfLesson(dateOfLesson);
        setTeacher(teacher);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    @Override
    public Integer getId() {
        return this.id;
    }


    @ManyToOne
    @JoinColumn(name = "discipline_id")
    public Integer getDisciplineId() {
        return disciplineId;
    }


    @Column(name = "date_of_lesson", nullable = false)
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
