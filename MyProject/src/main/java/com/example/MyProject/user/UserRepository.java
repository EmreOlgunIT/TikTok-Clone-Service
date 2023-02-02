package com.example.MyProject.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> { //JpaRepository has methods like save, find by id, etc. JpaRepository<Class, key value type (id)>. //Interface:

    Optional<User> findByEmail(String email);  //findBy + any field name
}
