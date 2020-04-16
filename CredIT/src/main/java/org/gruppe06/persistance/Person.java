package org.gruppe06.persistance;

import org.gruppe06.interfaces.IPerson;
import org.gruppe06.interfaces.IProgram;

public class Person implements IPerson {

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
