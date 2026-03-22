package com.sport.project.dao.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Entity(name = "teacher_entity")
@Table(name = "teachers")
@NoArgsConstructor
public class TeacherEntity extends UserEntity<Integer> implements Serializable {
    @Setter
    private boolean isModerator;
    private List<LessonsEntity> lessons;


    @Column(name = "is_moderator", nullable = false)
    public boolean isModerator() {
        return this.isModerator;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "teacher", fetch = FetchType.LAZY)
    public List<LessonsEntity> getLessons() {
        return lessons;
    }

    public void setLessons(@NonNull List<LessonsEntity> lessons) {
        this.lessons = lessons;
    }

    public boolean addLesson(LessonsEntity lesson) {
        if (lesson == null) throw new IllegalArgumentException("Lesson cannot be null");
        return this.lessons.add(lesson);
    }

    public boolean removeLesson(LessonsEntity lesson) {
        if (lesson == null) throw new IllegalArgumentException("Lesson cannot be null");
        return this.lessons.remove(lesson);
    }



    public static TeacherEntityBuilder builder() {
        return new TeacherEntityBuilder();
    }


    public static class TeacherEntityBuilder {
        TeacherEntity teacherEntity = new TeacherEntity();
        FullName fullName = new FullName();

        public TeacherEntityBuilder login(String login) {
            teacherEntity.setLogin(login);
            return this;
        }

        public TeacherEntityBuilder passwordHash(String passwordHash) {
            teacherEntity.setPasswordHash(passwordHash);
            return this;
        }

        public TeacherEntityBuilder firstName(String firstName) {
            fullName.setFirstName(firstName);
            return this;
        }

        public TeacherEntityBuilder lastName(String lastName) {
            fullName.setLastName(lastName);
            return this;
        }

        public TeacherEntityBuilder patronymic(String patronymic) {
            fullName.setPatronymic(patronymic);
            return this;
        }

        public TeacherEntityBuilder birthday(LocalDate birthday) {
            teacherEntity.setBirthday(birthday);
            return this;
        }

        public TeacherEntityBuilder isModerator(boolean isModerator){
            teacherEntity.setModerator(isModerator);
            return this;
        }

        public TeacherEntityBuilder lessons(List<LessonsEntity> lessons) {
            teacherEntity.setLessons(lessons);
            return this;
        }

        public TeacherEntity build() {
            teacherEntity.setFullName(fullName);
            return teacherEntity;
        }
    }
}
