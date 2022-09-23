package com.example.springmongodemo;

import org.springframework.boot.context.properties.bind.Name;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document
public class Student {
    @Id
    public String id;
    @Field
    public String name;
    @Field
    public String roll;
    public Student(){

    }

    public Student(String id, String name, String roll) {
        this.id = id;
        this.name = name;
        this.roll = roll;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getRoll() {
        return roll;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setRoll(String roll) {
        this.roll = roll;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", roll='" + roll + '\'' +
                '}';
    }
}
