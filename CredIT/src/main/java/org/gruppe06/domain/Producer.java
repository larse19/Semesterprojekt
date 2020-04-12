package org.gruppe06.domain;

import java.util.ArrayList;

public class Producer extends Person{

    public Producer(String ID, String name) {
        super(ID, name);
    }

    @Override
    public String toString(){
        return super.getName();
    }
}
