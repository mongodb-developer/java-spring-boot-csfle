package com.example.javaspringbootcsfle.repository;

import com.example.javaspringbootcsfle.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, String> {
}
