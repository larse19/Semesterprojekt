package org.gruppe06.domain;

import org.gruppe06.interfaces.IRole;
import org.gruppe06.persistance.ProgramDataHandler;
import org.gruppe06.persistance.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProgramSystem {

    private ProgramDataHandler programDataHandler;

    public ProgramSystem(){
        programDataHandler = new ProgramDataHandler();
    }

    public List<String> getListOfPrograms(){
        return programDataHandler.getAllProgramNames();
    }

    public List<ProgramInfo> getAllProgramsInfo(){
        List<ProgramInfo> programInfoList = new ArrayList<>();
        Map<Integer, String> programData = programDataHandler.getAllProgramIdAndNames();

        for(Integer program : programData.keySet()){
            programInfoList.add(new ProgramInfo(program, programData.get(program)));
        }
        return programInfoList;
    }

    public boolean addCastMemberToProgram(ProgramInfo programInfo, String castName, String role){
        IRole Irole = new Role(role);
        return programDataHandler.addCastMemberToProgram(programInfo.getID(),castName,Irole);
    }

}
