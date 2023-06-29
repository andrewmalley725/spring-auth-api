package com.auth.api.models;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findFirstByUsername(String username);
}
