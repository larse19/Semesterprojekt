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

    //Checks if a program exists
    private boolean checkIfProgramExists(String name) {
        IProgram checkProgram = programDataHandler.getProgramNameAndYear(name);
        return checkProgram != null;
    }

    //Creates new program
    public boolean createNewProgram(String programName, String releaseYear, String producerID, String producerRole) {
        if(checkIfProgramExists(programName)){
            System.out.println("Program already exists!");
            return false;
        } else {
            programDataHandler.createProgram(programName, releaseYear, producerID, producerRole);
            return true;
        }
    }

    //Deletes program
    public boolean deleteProgram(ProgramInfo programInfo) {
        programDataHandler.deleteProgram(programInfo.getID());
        return true;
    }

    //TODO Should maybe update based on ID
    //Updates a programs name, and year, based on it's old name
    public boolean updateProgram(String oldName, String newName, String newReleaseYear) {
        if (checkIfProgramExists(oldName)){
            programDataHandler.updateProgram(oldName, newName, newReleaseYear);
            return true;
        } else {
            return false;
        }
    }

    //Returns a list of all program names
    public List<String> getListOfProgramNames(){
        return programDataHandler.getAllProgramNames();
    }

    //Returns a list of all programs as ProgramInfo
    public List<ProgramInfo> getAllProgramsInfo(){
        List<ProgramInfo> programInfoList = new ArrayList<>();
        Map<Integer, String> programData = programDataHandler.getAllProgramIdAndNames();

        for(Integer program : programData.keySet()){
            programInfoList.add(new ProgramInfo(program, programData.get(program)));
        }
        return programInfoList;
    }

    //Adds a cast member to a program
    public boolean addCastMemberToProgram(ProgramInfo programInfo, String castName, String role){
        IRole Irole = new Role(role);
        return programDataHandler.addCastMemberToProgram(programInfo.getID(),castName,Irole);
    }

    //Returns an IProgram, based on the programs name. Throws NullPointerException, if the program doesn't exist
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
