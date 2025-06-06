package com.sport.project.dao.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Entity(name = "teacher_entity")
@Table(schema = "sport_schema", name = "teacher_table")
@NoArgsConstructor
public class TeacherEntity extends BaseEntity<Integer> implements Serializable {

    private boolean isModerator;
    private Map<LocalDate, String> schedule = new HashMap<>();
    private List<StudentEntity> students = new ArrayList<>();


    public TeacherEntity(String fsp,
                         boolean isModerator,
                         Map<LocalDate, String> schedule,
                         String login,
                         String passwordHash)
    {
        super(fsp, login, passwordHash);
        this.setModerator(isModerator);
        this.setSchedule(schedule);
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    @Override
    public Integer getId() {
        return this.id;
    }

    @Column(name = "fsp", unique = true, nullable = false)
    @Override
    public String getFsp() {
        return this.fsp;
    }

    @Column(name = "login", unique = true, nullable = false)
    @Override
    public String getLogin() {
        return this.login;
    }

    @Column(name = "password_hash", nullable = false)
    @Override
    public String getPasswordHash() {
        return this.passwordHash;
    }

    @Column(name = "is_moderator", nullable = false)
    public Boolean isModerator() {
        return this.isModerator;
    }

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<StudentEntity> getStudents() {
        return this.students;
    }


    public void setModerator(boolean isModerator) {
        this.isModerator = isModerator;
    }


    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "schedule", columnDefinition = "JSONB", nullable = false)
    public Map<LocalDate, String> getSchedule() {
        return this.schedule;
    }


    public void setSchedule(@NonNull Map<LocalDate, String> schedule) {
        this.schedule = schedule;
    }

    public void setStudents(@NonNull List<StudentEntity> students) {
        this.students = students;
    }


    public static TeacherEntityBuilder builder() {
        return new TeacherEntityBuilder();
    }

    private boolean isValidFsp(String fsp) {
        char[] chars = fsp.toCharArray();
        int k = 0;
        for (char c : chars) {
            if (c == ' ') {
                k++;
            }
        }

        return k == 2;
    }


    public static final class TeacherEntityBuilder {

        private String fsp;
        private boolean isModerator;
        private Map<LocalDate, String> schedule;
        private String login;
        private String passwordHash;


        public TeacherEntityBuilder fsp(String fsp) {
            this.fsp = fsp;
            return this;
        }

        public TeacherEntityBuilder isModerator(boolean isModerator) {
            this.isModerator = isModerator;
            return this;
        }

        public TeacherEntityBuilder schedule(Map<LocalDate, String> schedule) {
            this.schedule = schedule;
            return this;
        }

        public TeacherEntityBuilder login(String login) {
            this.login = login;
            return this;
        }

        public TeacherEntityBuilder passwordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public TeacherEntity build() {
            return new TeacherEntity(
                    this.fsp,
                    this.isModerator,
                    this.schedule,
                    this.login,
                    this.passwordHash
            );
        }

    }

}
