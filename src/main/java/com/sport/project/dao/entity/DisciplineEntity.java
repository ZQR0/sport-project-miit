package com.sport.project.dao.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity(name = "discipline_entity")
@Table(schema = "sport_schema", name = "discipline_table")
@NoArgsConstructor
public class DisciplineEntity extends BaseEntity<Integer> implements Serializable {

    @Setter
    private String name;

    //FIXME: Исправить после правки родителей или отказа от наследования.
    public DisciplineEntity(String name) {
        super(null, null, null);
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discipline_id")
    @Override
    public Integer getId() {
        return this.id;
    }

    //FIXME: Наследуемые методы из Entity. Удалить когда будет исправлены родители или отказаться от наследования.
    @Transient
    @Override
    public String getFsp() {
        return null;
    }

    @Transient
    @Override
    public String getLogin() {
        return null;
    }

    @Transient
    @Override
    public String getPasswordHash() {
        return null;
    }

    public static DisciplineEntityBuilder builder() {
        return new DisciplineEntityBuilder();
    }

    public static final class DisciplineEntityBuilder {

        private String name;

        public DisciplineEntityBuilder name(String name) {
            this.name = name;
            return this;
        }

        public DisciplineEntity build() {
            return new DisciplineEntity(
                    this.name
            );
        }
    }
}
