package com.sport.project.dao.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity(name = "lessons_entity")
@Table(schema = "public", name = "lessons")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Access(AccessType.PROPERTY)
public class LessonsEntity extends AbstractEntity<Integer> implements Serializable {


    private Integer id;

    @Setter
    DisciplineEntity discipline;
    OffsetDateTime dateOfLesson;
    TeacherEntity teacher;

    public LessonsEntity(OffsetDateTime dateOfLesson) {
        setDateOfLesson(dateOfLesson);
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
    @JoinColumn(name = "discipline_id", nullable = false)
    public DisciplineEntity getDiscipline() {
        return this.discipline;
    }

    public void setDiscipline(DisciplineEntity discipline) {
        if (discipline == null) {
            throw new IllegalArgumentException("Discipline cannot be null");
        }
        this.discipline = discipline;
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
