package com.auth.api.controllers;

import com.auth.api.models.Person;
import com.auth.api.models.PersonRepository;
import com.auth.api.models.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    public static String hashSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
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

    @DeleteMapping("/delete/{uid}/{did}")
    public String Remove(@PathVariable("uid") Long uid, @PathVariable("did") Long did)
    {
        Person currUser = _db.getById(uid);
        System.out.println(currUser.getAuthLevel());
        if (currUser.getAuthLevel().equals("admin"))
        {
            Person person = _db.remove(did);

            return "Removed id " + person.getId();
        }
        else {
            return "Not Authorized";
        }

    }

    @PostMapping("/add")
    public Person Add(@RequestBody Person person)
    {
        String pepper = "coolbeans";
        String pass = person.getPassword();
        person.setPassword(hashSHA256(pass + pepper));
        _db.addPerson(person);

        return person;
    }

    @PutMapping("/edit/{uid}/{eid}")
    public Person Edit(@PathVariable("uid") Long uid, @PathVariable("eid") Long eid, @RequestBody Person person)
    {
        Person currUser = _db.getById(uid);

        if (currUser.getAuthLevel().equals("admin"))
        {
            return _db.edit(eid,person);
        }
        return null;
    }
}
