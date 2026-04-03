package com.sport.project.dao.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity(name = "lessons_entity")
@Table(name = "lessons")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonsEntity extends AbstractEntity<Integer> implements Serializable {

    DisciplineEntity discipline;
    Date dateOfLesson;
    TeacherEntity teacher;
    List<VisitsEntity> visits;

    public LessonsEntity(Date dateOfLesson) {
        setDateOfLesson(dateOfLesson);
    }

    public LessonsEntity(Date dateOfLesson, TeacherEntity teacher) {
        setDateOfLesson(dateOfLesson);
        setTeacher(teacher);
    }

    @OneToMany(mappedBy = "lessons", cascade = CascadeType.ALL)
    public List<VisitsEntity> getVisits() {
        return this.visits;
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

    @Column(name = "date_of_lesson", nullable = false, columnDefinition = "DATE")
    public Date getDateOfLesson() {
        return dateOfLesson;
    }

    public void setDateOfLesson(@NonNull Date dateOfLesson) {
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

    public void setVisits(@NonNull List<VisitsEntity> visits) {
        this.visits = visits;
    }

    public boolean addVisit(@NonNull VisitsEntity visit) {
        return this.visits.add(visit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LessonsEntity that = (LessonsEntity) o;
        return Objects.equals(discipline.getName(), that.discipline.getName()) && Objects.equals(dateOfLesson, that.dateOfLesson) && Objects.equals(teacher, that.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discipline, dateOfLesson);
    }
}
