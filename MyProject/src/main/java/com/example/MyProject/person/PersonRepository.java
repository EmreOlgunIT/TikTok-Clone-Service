package com.example.MyProject.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> { //Object Type, Object id type

    @Query("SELECT p FROM Person p WHERE p.email = ?1") //This is JBQL and not SQL
    Optional<Person> findPersonByEmail(String email);

    @Query("SELECT p FROM Person p WHERE p.id = ?1") //This is JBQL and not SQL
    Optional<Person> findPersonById(Long id);

}
