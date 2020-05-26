package org.gruppe06.persistance;

import org.gruppe06.interfaces.IProducer;

import java.util.ArrayList;

public class Producer extends Cast implements IProducer {

    private String producerRole;

    public Producer(){
    }

    public Producer(String ID, String name) {
        super(ID, name);
    }

    public Producer(String ID, String name, String producerRole) {
        super(ID, name);
        this.producerRole = producerRole;
    }

    public String getRole() {
        return producerRole;
    }

    public void setRole(String producerRole) {
        this.producerRole = producerRole;
    }

    //Returns the information about the producer. The "try" statement returns if the object has an instance of ProgramRole
    //the catch statement is called, if it doesn't
    @Override
    public String toString(){
        try {
            return "Navn: " + super.getName() + "\nProducer ID: " + super.getID() + "\n\nProgrammer: " + super.printProgramRoles();
        }catch (NullPointerException e){
            if(producerRole != null){
                return "Navn: " + super.getName() + "     Rolle: " + producerRole;
            }else{
                return super.getName();
            }
        }
    }

}
