package org.gruppe06.persistance;

import org.gruppe06.interfaces.IProducer;

public class Producer extends Person implements IProducer {

    private String producerRole;

    public Producer(String ID, String name) {
        super(ID, name);
    }

    public Producer(String ID, String name, String producerRole) {
        super(ID, name);
        this.producerRole = producerRole;
    }

    public String getProducerRole() {
        return producerRole;
    }

    public void setProducerRole(String producerRole) {
        this.producerRole = producerRole;
    }

    public String getNameAndRole(){
        return this.getName() + ": " + this.producerRole;
    }

    @Override
    public String toString(){
        return this.getName() + " - " + this.getID();
    }
}
