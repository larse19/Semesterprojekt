package org.gruppe06.interfaces;

import org.gruppe06.persistance.ProgramRole;

import java.util.ArrayList;

public interface ICastMember extends ICast {

    IRole getRole();

    void setRole(IRole role);

}
