package com.sport.project.dao.entity;

import jakarta.persistence.Column;
import lombok.NonNull;

public abstract class BaseEntity<ID extends Number> implements Entity<ID> {


    protected ID id;
    protected String fsp;
    protected String login;
    protected String passwordHash;

    public BaseEntity() {}

    public BaseEntity(String fsp, String login, String passwordHash)
    {
        this.setFsp(fsp);
        this.setLogin(login);
        this.setPasswordHash(passwordHash);
    }

    @Override
    public abstract ID getId();

    @Override
    public abstract String getFsp();

    @Override
    public abstract String getLogin();

    @Override
    public abstract String getPasswordHash();


    private void setId(@NonNull ID id) {
        this.id = id;
    }

    public void setFsp(@NonNull String fsp) {
        this.fsp = fsp;
    }

    public void setLogin(@NonNull String login) {
        this.login = login;
    }

    public void setPasswordHash(@NonNull String passwordHash) {
        this.passwordHash = passwordHash;
    }


    private boolean isValidFsp(String fsp) {
        char[] chars = fsp.toCharArray();
        int k = 0;
        for (char c : chars) {
            if (c == ' ') {
                k++;
            }
        }

        return k == 2;
    }

}
