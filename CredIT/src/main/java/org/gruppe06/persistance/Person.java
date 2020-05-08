package org.gruppe06.persistance;

import org.gruppe06.interfaces.IPerson;

public class Person implements IPerson {

    private String name;
    private String ID;

    public Person(String ID, String name) {
        this.name = name;
        this.ID = ID;
    }

    public Person() {
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setID(String ID) {
         this.ID = ID;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
