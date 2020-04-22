package org.gruppe06.domain;

//Class that contains name and ID of a program
public class ProgramInfo {

    private int ID;
    private String name;

    public ProgramInfo(int id, String name){
        this.ID = id;
        this.name = name;
    }

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
