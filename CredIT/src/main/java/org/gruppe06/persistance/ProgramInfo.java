package org.gruppe06.persistance;

import org.gruppe06.interfaces.IProgramInfo;

//Class that contains name and ID of a program
public class ProgramInfo implements IProgramInfo {

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
  
    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getYear() { return year; }

    @Override
    public String toString(){
        return this.name;
    }
}
