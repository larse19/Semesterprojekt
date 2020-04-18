package org.gruppe06.domain;

import org.gruppe06.interfaces.IProgram;
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
  
  private boolean checkIfProgramExists(String name) {
        IProgram checkProgram = programDataHandler.getPrograms(name);
        return checkProgram != null;
    }

    public boolean createNewProgram(String programName, String releaseYear) {
        if(checkIfProgramExists(programName)){
            System.out.println("Program already exists!");
            return false;
        } else {
            programDataHandler.createProgram(programName, releaseYear);
            return true;
        }
    }

    public boolean deleteProgram(String name) {
        if(checkIfProgramExists(name)){
            programDataHandler.deleteProgram(name);
            return true;
        } else {
            System.out.println("Program does not exist!");
            return false;
        }
    }

    public boolean updateProgram(String oldName, String newName, String newReleaseYear) {
        if (checkIfProgramExists(oldName)){
            programDataHandler.updateProgram(oldName, newName, newReleaseYear);
            return true;
        } else {
            return false;
        }
    }

    public List<String> getListOfProgramNames(){
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

    public IProgram getProgram(String programName) throws NullPointerException {
        IProgram program;
        try {
            program = programDataHandler.getProgram(programName);
        }catch (NullPointerException e){
            throw new NullPointerException();
        }
        return program;
    }
}
