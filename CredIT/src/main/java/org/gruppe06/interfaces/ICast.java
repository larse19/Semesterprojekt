package org.gruppe06.interfaces;

import org.gruppe06.persistance.Program;
import org.gruppe06.persistance.ProgramRole;
import java.util.ArrayList;

public interface ICast extends IPerson{

    ArrayList<ProgramRole> getProgramRoles();

    void setProgramRoles(ArrayList<ProgramRole> programRoles);

}
