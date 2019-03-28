package com.lw.example.shardingjdbc.dao;

import com.lw.example.shardingjdbc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
