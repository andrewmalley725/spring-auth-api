package com.auth.api.controllers;

import com.auth.api.models.Person;
import com.auth.api.models.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.auth.api.models.Encrypt;
import com.auth.api.models.Login;

@RestController
@RequestMapping("/api")
public class LoginController {
    private final PersonService _db;
    @Autowired
    public LoginController(PersonService temp)
    {
        _db = temp;
    }

    @PostMapping("/login")
    public String Login(@RequestBody Login login)
    {
        String username = login.getUsername();
        Person person = _db.getUser(username);

        if (person != null)
        {
            if (Encrypt.hashSHA256(login.getPassword()).equals(person.getPassword()))
            {
                return "Authenticated!";
            }
            return "Incorrect password!";
        }

        else
        {
            return "Invalid username!";
        }
    }

}
