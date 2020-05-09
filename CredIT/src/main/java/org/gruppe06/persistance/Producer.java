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

    @Override
    public String toString(){
        try {
            return "Name: " + super.getName() + "\nProducer ID: " + super.getID() + "\n\nPrograms: " + super.printProgramRoles();
        }catch (NullPointerException e){
            if(producerRole != null){
                return "Name: " + super.getName() + " Role: " + producerRole;
            }else{
                return super.getName();
            }
        }
    }

}
