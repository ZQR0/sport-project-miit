package com.sport.project.dao.entity;

public interface Entity<ID extends Number> {
    ID getId();
    String getFsp();
    String getLogin();
    String getPasswordHash();
}
