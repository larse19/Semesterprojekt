package org.gruppe06.persistance;

import org.gruppe06.interfaces.IProducer;

public class Producer extends Person implements IProducer {

    public Producer(String ID, String name) {
        super(ID, name);
    }

    @Override
    public String toString(){
        return super.getName();
    }
}
