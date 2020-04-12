package org.gruppe06.domain;

public class Person {

    private String name;
    private String ID;

    public Person(String ID, String name) {
        this.name = name;
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }
}
