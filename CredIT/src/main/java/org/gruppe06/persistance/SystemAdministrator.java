package org.gruppe06.persistance;

public class SystemAdministrator extends Person {

    public SystemAdministrator(String ID, String name) {
        super(ID, name);
    }

    @Override
    public String toString(){
        return this.getName() + " - " + this.getID();
    }

}
