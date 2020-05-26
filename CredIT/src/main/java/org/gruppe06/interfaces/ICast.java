package org.gruppe06.interfaces;

import org.gruppe06.persistance.ProgramRole;
import java.util.ArrayList;

public interface ICast extends IPerson{

    //getProgramRoles method gets an ArrayList of roles which a person has for a specific program.
    ArrayList<ProgramRole> getProgramRoles();

    //setProgramRoles is a method to give a cast, a role for a program. The values are set in an ArrayList.
    void setProgramRoles(ArrayList<ProgramRole> programRoles);

}
