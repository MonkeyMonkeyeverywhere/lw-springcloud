package com.lw.cloud.entity;

/**
 *  user实体类
 */
public class User {

    private long id;

    public User(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
