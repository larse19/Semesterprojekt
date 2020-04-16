package org.gruppe06.persistance;

import org.gruppe06.interfaces.ICastMember;
import org.gruppe06.interfaces.IRole;

public class CastMember extends Person implements ICastMember {

    private IRole roleOnProgram;

    public CastMember(String ID, String name, Role roleOnProgram) {
        super(ID, name);
        this.roleOnProgram = roleOnProgram;
    }

    public CastMember(String ID, String name){
        super(ID, name);
        this.roleOnProgram = null;
    }

    public IRole getRoleOnProgram() {
        return roleOnProgram;
    }

    @Override
    public String toString(){
        return "Name: " + super.getName() + " Role: " + roleOnProgram.getRole();
    }

}
