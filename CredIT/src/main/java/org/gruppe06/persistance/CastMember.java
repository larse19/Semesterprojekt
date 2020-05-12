package org.gruppe06.persistance;

import org.gruppe06.interfaces.ICastMember;
import org.gruppe06.interfaces.IRole;

import java.util.ArrayList;

public class CastMember extends Cast implements ICastMember {

    private IRole roleOnProgram;

    public CastMember(String ID, String name, ArrayList<ProgramRole> programRoles) {
        super(ID, name, programRoles);
    }

    public CastMember(String ID, String name, IRole role) {
        super(ID, name);
        this.roleOnProgram = role;
    }

    public CastMember(String ID, String name) {
        super(ID, name);
    }

    public CastMember() {
    }

    @Override
    public IRole getRole() {
        return roleOnProgram;
    }

    @Override
    public void setRole(IRole role) {
        this.roleOnProgram = role;
    }

    @Override
    public String toString(){
        try {
            return "Name: " + super.getName() + "\nCastMember ID: " + super.getID() + "\n\nPrograms: " + super.printProgramRoles();
        }catch (NullPointerException e){
            if(roleOnProgram != null){
                return "Name: " + super.getName() + "   Role: " + roleOnProgram.toString();
            }else{
                return super.getName();
            }
        }
    }
}
