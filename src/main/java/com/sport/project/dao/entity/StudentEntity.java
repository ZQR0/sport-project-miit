package com.sport.project.dao.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity(name = "student_entity")
@Table(schema = "sport_schema", name = "students")
@NoArgsConstructor
public class StudentEntity extends BaseEntity<Integer> implements Serializable {

    @Setter
    private String last_name;
    @Setter
    private String first_name;
    @Setter
    private String patronymic;
    @Setter
    private Date birthday;

    //После добавления класса HealthGroupEntity изменить тип
    @Setter
    private Integer healthGroup;

    private GroupEntity group;
    private SectionEntity section;

    public StudentEntity(String last_name,
                         String first_name,
                         String patronymic,
                         String login,
                         String passwordHash,
                         Date birthday,
                         Integer healthGroup,
                         GroupEntity group,
                         SectionEntity section)
    {
        //Заменить составную строку fsp на отдельные атрибуты
        super(last_name + " " + first_name + " " + patronymic, login, passwordHash);
        this.setLast_name(last_name);
        this.setFirst_name(first_name);
        this.setPatronymic(patronymic);
        this.setBirthday(birthday);
        this.setHealthGroup(healthGroup);
        this.setGroup(group);
        this.setSection(section);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    @Override
    public Integer getId() {
        return this.id;
    }

    //Убрать после изменения fsp
    @Column(name = "fsp", unique = true, nullable = false)
    @Override
    public String getFsp() {
        return this.fsp;
    }

    @Column(name = "last_name", nullable = false)
    public String getLast_name() {
        return this.last_name;
    }

    @Column(name = "first_name", nullable = false)
    public String getFirst_name() {
        return this.first_name;
    }

    @Column(name = "patronymic", nullable = false)
    public String getPatronymic() {
        return this.patronymic;
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

    @Column(name = "birthday", nullable = false)
    public Date getBirthday() {
        return this.birthday;
    }

    //Переписать геттер после добавления соответствующего класса
    @Column(name = "health_group_id", nullable = false)
    public Integer getHealthGroup() {
        return healthGroup;
    }

    @ManyToOne
    @JoinColumn(name = "group_id")
    public GroupEntity getGroup() {
        return this.group;
    }

    public void setGroup(@NonNull GroupEntity group) {
        this.group = group;
    }

    @ManyToOne
    @JoinColumn(name = "section_id")
    public SectionEntity getSection() {
        return this.section;
    }

    public void setSection(@NonNull SectionEntity section) {
        this.section = section;
    }

    public static StudentEntityBuilder builder() {
        return new StudentEntityBuilder();
    }

    public static final class StudentEntityBuilder {
        private String last_name;
        private String first_name;
        private String patronymic;
        private Date birthday;
        private Integer healthGroup;
        private String login;
        private String passwordHash;
        private GroupEntity group;
        private SectionEntity section;

        public StudentEntityBuilder lastName(String last_name) {
            this.last_name = last_name;
            return this;
        }

        public StudentEntityBuilder first_name(String first_name) {
            this.first_name = first_name;
            return this;
        }

        public StudentEntityBuilder patronymic(String patronymic) {
            this.patronymic = patronymic;
            return this;
        }

        public StudentEntityBuilder birthday(Date birthday) {
            this.birthday = birthday;
            return this;
        }

        //Изменить тип Integer после добавления класса HealthGroup
        public StudentEntityBuilder healthGroup(Integer healthGroup) {
            this.healthGroup = healthGroup;
            return this;
        }

        public StudentEntityBuilder group(GroupEntity group) {
            this.group = group;
            return this;
        }

        public StudentEntityBuilder section(SectionEntity section) {
            this.section = section;
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

        public StudentEntity build() {
            return new StudentEntity(
                    this.last_name,
                    this.first_name,
                    this.patronymic,
                    this.login,
                    this.passwordHash,
                    this.birthday,
                    this.healthGroup,
                    this.group,
                    this.section
            );
        }

    }

}
