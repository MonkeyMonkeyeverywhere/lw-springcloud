package com.lw.example.shardingjdbc.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name="user")
public class User {
    @Id
    private Long id;
    private String city;
    private String name;

    public User() {
    }

    public User(Long id, String city, String name) {
        this.id = id;
        this.city = city;
        this.name = name;
    }
}
