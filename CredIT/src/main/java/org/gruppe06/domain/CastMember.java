package org.gruppe06.domain;

import java.util.Map;

public class CastMember extends Person {

    private Role roleOnProgram;

    public CastMember(String ID, String name, Role roleOnProgram) {
        super(ID, name);
        this.roleOnProgram = roleOnProgram;
    }

    public Role getRoleOnProgram() {
        return roleOnProgram;
    }

    @Override
    public String toString(){
        return "Name: " + super.getName() + " Role: " + roleOnProgram.getRole();
    }

}
