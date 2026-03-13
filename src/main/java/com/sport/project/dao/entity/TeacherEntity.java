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
@Table(schema = "sport_schema", name = "teacher_table")
@NoArgsConstructor
public class TeacherEntity extends BaseEntity<Integer> implements Serializable {

    @Setter
    private String firstName;
    @Setter
    private String lastName;
    @Setter
    private String patronymic;
    @Setter
    private Date birthday;

    @Setter
    private boolean isModerator;

    private Map<LocalDate, String> schedule = new HashMap<>();
    private List<StudentEntity> students = new ArrayList<>();


    public TeacherEntity(String firstName,
                         String lastName,
                         String patronymic,
                         Date birthday,
                         boolean isModerator,
                         Map<LocalDate, String> schedule,
                         String login,
                         String passwordHash) {
        //FIXME: Из-за наследования необходимо цельное ФИО, собираемое по частям. Исправить когда будет разделен.
        super(createFullName(firstName, lastName, patronymic), login, passwordHash);

        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setPatronymic(patronymic);
        this.setBirthday(birthday);

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

    //TODO: вариант с цельным ФИО для совместимости. Исправить на раздельный.
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

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "schedule", columnDefinition = "JSONB", nullable = false)
    public Map<LocalDate, String> getSchedule() {
        return this.schedule;
    }

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<StudentEntity> getStudents() {
        return this.students;
    }

    @Column(name = "is_moderator", nullable = false)
    public Boolean isModerator() {
        return this.isModerator;
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


    private static String createFullName(String firstName, String lastName, String patronymic) {
        return firstName + " " + lastName + " " + patronymic;
    }

// FIXME: нигде не используемый метод. Будет не нужен когда будет раздельный ФИО. Убрать.
//
//    private boolean isValidFsp(String fsp) {
//        char[] chars = fsp.toCharArray();
//        int k = 0;
//        for (char c : chars) {
//            if (c == ' ') {
//                k++;
//            }
//        }
//
//        return k == 2;
//    }

    //  FIXME: Нет возможности нормально переписать builder из-за старого сервиса и цельного ФИО в интерфейсе Entity.
    public static final class TeacherEntityBuilder {

        private String firstName;
        private String lastName;
        private String patronymic;
        private Date birthday;

        private boolean isModerator;
        private Map<LocalDate, String> schedule;
        private String login;
        private String passwordHash;


        public TeacherEntityBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public TeacherEntityBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public TeacherEntityBuilder patronymic(String patronymic) {
            this.patronymic = patronymic;
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

        public TeacherEntityBuilder birthday(Date birthday) {
            this.birthday = birthday;
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

        // Только для временной совместимости со старым сервисом.
        // FIXME: Сейчас делит ФИО на раздельные. Нужно убрать когда будет разделенное на БД и сервисах.
        @Deprecated
        public TeacherEntityBuilder fsp(String fsp) {
            if (fsp != null) {
                String[] parts = fsp.split(" ");
                if (parts.length == 3) {
                    this.firstName = parts[0];
                    this.lastName = parts[1];
                    this.patronymic = parts[2];
                } else {
                    // На случай если плохой формат.
                    this.firstName = fsp;
                }
            }
            return this;
        }

        public TeacherEntity build() {
            return new TeacherEntity(
                    this.firstName,
                    this.lastName,
                    this.patronymic,
                    this.birthday,
                    this.isModerator,
                    this.schedule,
                    this.login,
                    this.passwordHash
            );
        }

    }

}
