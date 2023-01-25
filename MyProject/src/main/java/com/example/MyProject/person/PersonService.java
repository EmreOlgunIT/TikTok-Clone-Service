package com.example.MyProject.person;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/*
    This is the business-logic
 */

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getPeople() {
        //new Person(1L, "Emre", "emremail.no", LocalDate.of(1997, Month.APRIL, 18))
        return personRepository.findAll();
    }

    public void addNewPerson(Person person) {
        System.out.println(person);

        Optional<Person> personWithEmail = personRepository.findPersonByEmail(person.getEmail());

        if (personWithEmail.isPresent()) {
            throw new IllegalStateException("Email is already in use");
        }

        personRepository.save(person);
    }

    public void deletePerson(Long id) {
        //personRepository.findById(id);
        if (!personRepository.existsById(id)) {
            throw new IllegalStateException("Person with id " + id + " does not exist");
        }

        personRepository.deleteById(id);
    }

    @Transactional
    public void updatePerson(Long personId, String name, String email) {
        Optional <Person> existingPerson = personRepository.findPersonById(personId);

        if (!existingPerson.isPresent()) {
            throw new IllegalStateException("Person with id " + personId + " does not exist");
        }

        Person person = existingPerson.get();

        if (!name.isEmpty()) {
            person.setName(name);
        }

        if (email != null && email.length() > 0 && !Objects.equals(person.getEmail(), email) /*No need to set the email if its the same*/ ) {

            //Check that email is not already in use
            Optional<Person> personExistingWithEmail = personRepository.findPersonByEmail(email);

            if (personExistingWithEmail.isPresent()) {
                throw new IllegalStateException("Email is already in use");
            }

            person.setEmail(email);
        }

        personRepository.save(person);

    }

}
