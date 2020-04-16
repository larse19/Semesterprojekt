package org.gruppe06.persistance;

import org.gruppe06.interfaces.IRole;

public class Role implements IRole {

    private String role;

    public Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
