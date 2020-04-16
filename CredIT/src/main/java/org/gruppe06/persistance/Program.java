package org.gruppe06.persistance;

import org.gruppe06.interfaces.ICastMember;
import org.gruppe06.interfaces.IProducer;
import org.gruppe06.interfaces.IProgram;

import java.util.ArrayList;

public class Program implements IProgram {

    private String name;
    private ArrayList<ICastMember> cast;
    private ArrayList<IProducer> producers;

    public Program(String name, ArrayList<ICastMember> cast, ArrayList<IProducer> producers) {
        this.name = name;
        this.cast = cast;
        this.producers = producers;
    }

    public String getName() {
        return name;
    }

    public ArrayList<ICastMember> getCast() {
        return cast;
    }

    public ArrayList<IProducer> getProducers() {
        return producers;
    }

    @Override
    public String toString(){
        return "Name: " + name + "\nProducers: " + producers.toString() + "\nCast: " + cast.toString();
    }
}
