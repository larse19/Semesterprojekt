package org.gruppe06.domain;

//Class that contains name and ID of a program
public class ProgramInfo {

    private int ID;
    private String name;
    private String year;

    public ProgramInfo(int id, String name){
        this.ID = id;
        this.name = name;
    }

    public ProgramInfo(int id, String name, String year){
        this.ID = id;
        this.name = name;
        this.year = year;
    }

    public String getYear() { return year; }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
