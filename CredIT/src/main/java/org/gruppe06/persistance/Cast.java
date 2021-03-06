package org.gruppe06.persistance;

import org.gruppe06.interfaces.ICast;
import org.gruppe06.interfaces.IPerson;

import java.util.ArrayList;

public class Cast extends Person implements ICast, IPerson {

    private ArrayList<ProgramRole> programRoles;

    public Cast(String ID, String name, ArrayList<ProgramRole> programRoles) {
        super(ID, name);
        this.programRoles = programRoles;
    }

    public Cast(String ID, String name) {
        super(ID, name);
    }

    public Cast() {
    }

    @Override
    public ArrayList<ProgramRole> getProgramRoles() {
        return programRoles;
    }

    @Override
    public void setProgramRoles(ArrayList<ProgramRole> programRole) {
        this.programRoles = programRole;

    }

    //Returns a string with all programRoles (with toString()), separated by a new line
    public String printProgramRoles(){
        StringBuilder result = new StringBuilder();
        for (ProgramRole programRole:this.getProgramRoles()) {
            result.append("\n").append(programRole.toString());
        }
        return result.toString();
    }


}
