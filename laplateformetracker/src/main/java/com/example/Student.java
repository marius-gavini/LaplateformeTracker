package com.example;

public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private int promotion_id;

    public Student() {
    }

    public Student(int id, String firstName, String lastName, int age, int promotion_id) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.promotion_id = promotion_id;
    }

    public Student(String firstName, String lastName, int age, int promotion_id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.promotion_id = promotion_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPromotion() {
        return promotion_id;
    }

    public void setPromotion(int promotion_id) {
        this.promotion_id = promotion_id;
    }
}
