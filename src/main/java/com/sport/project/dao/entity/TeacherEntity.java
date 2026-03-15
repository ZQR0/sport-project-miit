package com.sport.project.dao.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Entity(name = "teacher_entity")
@Table(schema = "public", name = "teachers")
@NoArgsConstructor
public class TeacherEntity extends UserEntity<Integer> implements Serializable {

    @Setter
    private boolean isModerator;

    private List<LessonsEntity> lessons;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id", nullable = false)
    @Override
    public Integer getId() {
        return this.id;
    }


    @Column(name = "is_moderator", nullable = false)
    public boolean isModerator() {
        return this.isModerator;
    }

    @OneToMany
    @JoinColumn(name = "teacher_id")
    public List<LessonsEntity> getLessons() {
        return lessons;
    }

    //TODO: написать builder, т.к. конструктор будет большой

}
