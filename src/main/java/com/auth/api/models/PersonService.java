package com.auth.api.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository)
    {
        this.personRepository = personRepository;
    }

    public void addPerson(Person person)
    {
        personRepository.save(person);
    }

    public List<Person> getAll()
    {
        List<Person> list =  personRepository.findAll();

        return list;
    }

    public void clear()
    {
        personRepository.deleteAll();
    }

    public Person remove(Long id)
    {
        Person person = personRepository.getReferenceById(id);

        personRepository.delete(person);

        return person;
    }

    public Person getById(Long id)
    {
        return personRepository.getReferenceById(id);
    }

    public Person edit(Long id, Person person)
    {
        Person toChange = personRepository.getReferenceById(id);

        toChange.setFirstName(person.getFirstName());
        toChange.setLastName(person.getLastName());
        toChange.setUsername(person.getUsername());
        toChange.setPassword(person.getPassword());
        toChange.setEmail(person.getEmail());
        toChange.setAuthLevel(person.getAuthLevel());

        personRepository.save(toChange);

        return toChange;
    }
}
