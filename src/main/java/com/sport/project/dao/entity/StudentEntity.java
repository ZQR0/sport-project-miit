package com.sport.project.dao.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity(name = "student_entity")
@Table(name = "students")
@NoArgsConstructor
public class StudentEntity extends UserEntity<Integer> implements Serializable {

    private HealthGroupsEntity healthGroup;
    private GroupEntity group;
    private SectionEntity section;
    private List<VisitsEntity> visits;


    @ManyToOne
    @JoinColumn(name = "health_group_id", nullable = false)
    public HealthGroupsEntity getHealthGroup() {
        return this.healthGroup;
    }

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    public GroupEntity getGroup() {
        return this.group;
    }

    @ManyToOne
    @JoinColumn(name = "section_id")
    public SectionEntity getSection() {
        return this.section;
    }

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    public List<VisitsEntity> getVisits() {
        return this.visits;
    }

    public void setHealthGroup(HealthGroupsEntity healthGroup) {
        if (healthGroup == null) {
            throw new IllegalArgumentException("Health group cannot be null");
        }
        this.healthGroup = healthGroup;
    }

    public boolean addVisits(VisitsEntity visit) {
        if (visit == null) throw new IllegalArgumentException("Visit cannot be null");
        return this.visits.add(visit);
    }

    public void setGroup(GroupEntity group) {
        if (group == null) {
            throw new IllegalArgumentException("Group cannot be null");
        }
        this.group = group;
    }

    public void setSection(@NonNull SectionEntity section) {
        this.section = section;
    }

    public void setVisits(@NonNull List<VisitsEntity> visits) {
        this.visits = visits;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        return true; // ВРеменная заглушка
    }

    // Временная заглушка
//    @Override
//    public int hashCode() {
//        return Objects.hash(healthGroup, group, section, visits);
//    }

    public static StudentEntityBuilder builder() {
        return new StudentEntityBuilder();
    }

    public static class StudentEntityBuilder {
        StudentEntity student = new StudentEntity();
        FullName fullName = new FullName();

        public StudentEntityBuilder login(String login) {
            student.setLogin(login);
            return this;
        }

        public StudentEntityBuilder passwordHash(String passwordHash) {
            student.setPasswordHash(passwordHash);
            return this;
        }

        public StudentEntityBuilder firstName(String firstName) {
            fullName.setFirstName(firstName);
            return this;
        }

        public StudentEntityBuilder lastName(String lastName) {
            fullName.setLastName(lastName);
            return this;
        }

        public StudentEntityBuilder patronymic(String patronymic) {
            fullName.setPatronymic(patronymic);
            return this;
        }

        public StudentEntityBuilder birthday(LocalDate birthday) {
            student.setBirthday(birthday);
            return this;
        }

        public StudentEntityBuilder healthGroup(HealthGroupsEntity healthGroup) {
            student.setHealthGroup(healthGroup);
            return this;
        }

        public StudentEntityBuilder group(GroupEntity group) {
            student.setGroup(group);
            return this;
        }

        public StudentEntityBuilder section(SectionEntity section) {
            student.setSection(section);
            return this;
        }

        public StudentEntityBuilder visits(List<VisitsEntity> visits) {
            student.setVisits(visits);
            return this;
        }

        public StudentEntity build() {
            student.setFullName(this.fullName);
            return student;
        }
    }
}
