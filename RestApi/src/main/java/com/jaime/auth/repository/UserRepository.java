package com.jaime.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jaime.auth.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
