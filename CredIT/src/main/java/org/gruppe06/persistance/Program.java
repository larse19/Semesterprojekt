package org.gruppe06.persistance;

import org.gruppe06.interfaces.ICastMember;
import org.gruppe06.interfaces.IProducer;
import org.gruppe06.interfaces.IProgram;

import java.awt.print.PrinterGraphics;
import java.util.ArrayList;

public class Program implements IProgram {

    private String name;
    private ArrayList<ICastMember> cast;
    private ArrayList<IProducer> producers;
    private String releaseYear;

    public Program(String name, ArrayList<ICastMember> cast, ArrayList<IProducer> producers, String releaseYear) {
        this.name = name;
        this.cast = cast;
        this.producers = producers;
        this.releaseYear = releaseYear;
    }

    public Program(String name, ArrayList<ICastMember> cast, ArrayList<IProducer> producers){
        this(name, cast, producers,"");
    }

    public Program(String name, String releaseYear) {
        this.name = name;
        this.releaseYear = releaseYear;
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

    public String getReleaseYear() {
        return releaseYear;
    }

    @Override
    public String toString(){
        return "Name: " + name + "\nProducers: " + producers.toString() + "\nCast: " + cast.toString();
    }
}
