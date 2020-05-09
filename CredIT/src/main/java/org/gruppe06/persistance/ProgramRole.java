package org.gruppe06.persistance;

public class ProgramRole {
    private ProgramInfo programInfo;
    private Role role;

    public ProgramRole(ProgramInfo programInfo, Role role) {
        this.programInfo = programInfo;
        this.role = role;
    }

    public ProgramInfo getProgramInfo() {
        return programInfo;
    }

    public void setProgramInfo(ProgramInfo programInfo) {
        this.programInfo = programInfo;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Program: " + programInfo.getName() + " Role: " + role.getRole();
    }
}
