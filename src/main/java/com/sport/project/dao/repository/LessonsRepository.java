package com.sport.project.dao.repository;

import com.sport.project.dao.entity.LessonsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

//В документации нет, но может еще понадобится
public interface LessonsRepository extends JpaRepository<LessonsEntity, Long> {

}
