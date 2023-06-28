package com.auth.api.controllers;

import com.auth.api.models.Person;
import com.auth.api.models.PersonRepository;
import com.auth.api.models.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BaseController {
    private final PersonService _db;

    @Autowired
    public BaseController(PersonService temp)
    {
        _db = temp;
    }
    @GetMapping("/home")
    public List<Person> Base()
    {
        List<Person> items = _db.getAll();
        
        if (items.size() > 0)
        {
            return _db.getAll();
        }

        return null;
    }

    @GetMapping("/home/{id}")
    public Person getById(@PathVariable("id") Long id)
    {
        return _db.getById(id);
    }

    @DeleteMapping("/clear")
    public String Clear()
    {
        _db.clear();
        return "Cleared";
    }

    @DeleteMapping("/delete/{id}")
    public String Remove(@PathVariable("id") Long id)
    {
        Person person = _db.remove(id);

        return "removed " + person.toString();
    }

    @PostMapping("/add")
    public String Add(@RequestBody Person person)
    {
        _db.addPerson(person);

        return person.toString();
    }

    @PutMapping("/edit/{id}")
    public Person Edit(@PathVariable("id") Long id, @RequestBody Person person)
    {
        return _db.edit(id,person);
    }
}
