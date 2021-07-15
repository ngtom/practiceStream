package com.company;

public class Person {
    private String name;
    private int age;

    Person(String theName, int theAge) {
        name = theName;
        age = theAge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person: " +
                "name='" + name + '\'' +
                ", age=" + age ;
    }
}
