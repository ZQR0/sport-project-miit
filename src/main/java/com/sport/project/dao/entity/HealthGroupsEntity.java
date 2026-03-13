package com.sport.project.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

//@Entity(name = "health_groups_entity")
//@Table(schema = "sport_schema", name = "health_groups")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HealthGroupsEntity extends BaseEntity<Integer> implements Serializable {

    String name;
    String description;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "health_group_id")
    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String getFsp() {
        return null;
    }

    @Override
    public String getLogin() {
        return null;
    }

    @Override
    public String getPasswordHash() {
        return null;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }
}
