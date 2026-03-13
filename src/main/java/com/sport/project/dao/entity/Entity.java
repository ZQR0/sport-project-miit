package com.sport.project.dao.entity;

@Deprecated(forRemoval = true)
public interface Entity<ID extends Number> {
    ID getId();
    String getFsp();
    String getLogin();
    String getPasswordHash();
}
