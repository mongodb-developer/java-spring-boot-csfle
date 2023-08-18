package com.mongodb.quickstart.javaspringbootcsfle.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("personsEncrypted")
public class Person {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    private String firstName;

    private String lastName;

    @Indexed(unique = true)
    @Field
    private String passportNumber;

    public Person() {
    }

    public Person(String firstName, String lastName, String aadharNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.passportNumber = aadharNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }
}
