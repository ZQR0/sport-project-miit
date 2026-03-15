package com.sport.project.dao.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name = "student_entity")
@Table(schema = "public", name = "students")
@NoArgsConstructor
public class StudentEntity extends UserEntity<Integer> implements Serializable {


    private HealthGroupsEntity healthGroup;
    private GroupEntity group;
    @Setter
    private SectionEntity section;
    private List<VisitsEntity> visits;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    @Override
    public Integer getId() {
        return this.id;
    }

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

    @OneToMany
    @JoinColumn(name = "student_id")
    public List<VisitsEntity> getVisits() {
        return this.visits;
    }

    public void setHealthGroup(HealthGroupsEntity healthGroup) {
        if (healthGroup == null) {
            throw new IllegalArgumentException("Health group cannot be null");
        }
        this.healthGroup = healthGroup;
    }

    public void setGroup(GroupEntity group) {
        if (group == null) {
            throw new IllegalArgumentException("Group cannot be null");
        }
        this.group = group;
    }



    //TODO: написать Builder, т.к. конструктор будет слишком большим
}
