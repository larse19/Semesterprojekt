package org.gruppe06.domain;

import java.util.ArrayList;

public class Program {

    private String name;
    private ArrayList<CastMember> cast;
    private ArrayList<Producer> producers;

    public Program(String name, ArrayList<CastMember> cast, ArrayList<Producer> producers) {
        this.name = name;
        this.cast = cast;
        this.producers = producers;
    }

    public String getName() {
        return name;
    }

    public ArrayList<CastMember> getCast() {
        return cast;
    }

    public ArrayList<Producer> getProducers() {
        return producers;
    }

    @Override
    public String toString(){
        return "Name: " + name + "\nProducers: " + producers.toString() + "\nCast: " + cast.toString();
    }
}
