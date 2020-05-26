package org.gruppe06.persistance;

import org.gruppe06.interfaces.ICastMember;
import org.gruppe06.interfaces.IRole;

public class CastMember extends Cast implements ICastMember {

    private IRole roleOnProgram;

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

    //Returns the information about the cast member. The "try" statement returns if the objekt has an instance of ProgramRole
    //the catch statement is called, if it doesn't
    @Override
    public String toString(){
        try {
            return "Navn: " + super.getName() + "\nCastMember ID: " + super.getID() + "\n\nProgrammer: " + super.printProgramRoles();
        }catch (NullPointerException e){
            if(roleOnProgram != null){
                return "Navn: " + super.getName() + "     Rolle: " + roleOnProgram.toString();
            }else{
                return super.getName();
            }
        }
    }
}
