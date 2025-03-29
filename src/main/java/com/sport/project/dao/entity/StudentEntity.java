package com.sport.project.dao.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity(name = "student_entity")
@Table(schema = "sport_schema", name = "student_table")
@NoArgsConstructor
public class StudentEntity extends BaseEntity<Integer> {

    private Integer healthGroup;
    private Map<Date, Boolean> exist = new HashMap<>();
    private TeacherEntity teacher;

    public StudentEntity(String fsp,
                         Map<Date, Boolean> exist,
                         String login,
                         String passwordHash,
                         TeacherEntity teacher)
    {
        super(fsp, login, passwordHash);
        this.setExist(exist);
        this.setTeacher(teacher);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    @Override
    public Integer getId() {
        return this.id;
    }


    @Column(name = "healthGroup", nullable = false)
    public Integer getHealthGroup() {
        return healthGroup;
    }

    public void setHealthGroup(@NonNull Integer healthGroup) {
        this.healthGroup = healthGroup;
    }

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "exist", nullable = false, columnDefinition = "JSONB")
    public Map<Date, Boolean> getExist() {
        return this.exist;
    }

    public void setExist(@NonNull Map<Date, Boolean> exist) {
        this.exist = exist;
    }

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    public TeacherEntity getTeacher() {
        return this.teacher;
    }

    public void setTeacher(@NonNull TeacherEntity newTeacher) {
        this.teacher = newTeacher;
    }

    public static StudentEntityBuilder builder() {
        return new StudentEntityBuilder();
    }

    public static final class StudentEntityBuilder {
        private String fsp;
        private Integer healthGroup;
        private Map<Date, Boolean> exist;
        private String login;
        private String passwordHash;
        private TeacherEntity teacher;

        public StudentEntityBuilder fsp(String fsp) {
            this.fsp = fsp;
            return this;
        }

        public StudentEntityBuilder healthGroup(Integer healthGroup) {
            this.healthGroup = healthGroup;
            return this;
        }

        public StudentEntityBuilder exist(Map<Date, Boolean> exist) {
            this.exist = exist;
            return this;
        }

        public StudentEntityBuilder login(String login) {
            this.login = login;
            return this;
        }

        public StudentEntityBuilder passwordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public StudentEntityBuilder teacher(TeacherEntity teacher) {
            this.teacher = teacher;
            return this;
        }

        public StudentEntity build() {
            return new StudentEntity(
                    this.fsp,
                    this.exist,
                    this.login,
                    this.passwordHash,
                    this.teacher
            );
        }

    }

}
