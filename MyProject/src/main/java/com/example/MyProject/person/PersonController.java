package com.example.MyProject.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "api/v1/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @CrossOrigin
    @GetMapping
    public List<Person> getPeople(){
        return personService.getPeople();
    }

    @PostMapping
    public void registerNewPerson(@RequestBody Person person) { //@RequestBody: takes the request body and maps it to the Person object
        personService.addNewPerson(person);
    }

    @DeleteMapping(path = "{id}")
    public void deletePerson(@PathVariable("id") Long id) {
        personService.deletePerson(id);
    }



    /*
    //Exercise: update person name and email
    @PutMapping
    public void updatePerson(@RequestBody Person person) {
        personService.updatePerson(person);
    }
     */

    //Solution
    @PutMapping(path="{personId}")
    public void updatePerson(
            @PathVariable("personId") Long personId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {

        personService.updatePerson(personId, name, email);
    }

}