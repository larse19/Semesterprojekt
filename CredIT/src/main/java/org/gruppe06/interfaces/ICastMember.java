package org.gruppe06.interfaces;

public interface ICastMember extends ICast {

    //ICastMember has a role, which can be received by getRole method
    IRole getRole();

    //A role can be set with setRole for a cast member
    void setRole(IRole role);

}
