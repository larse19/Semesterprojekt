package org.gruppe06.domain;

import org.gruppe06.interfaces.*;
import org.gruppe06.persistance.Actor;
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
        IProgramInfo checkProgram = programDataHandler.getProgramInfo(name);
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
    public boolean deleteProgram(IProgramInfo programInfo) {
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
    public List<IProgramInfo> getAllProgramsInfo(){
        return programDataHandler.getAllProgramInfo();
    }

    public List<IProgramInfo> getAllProgramsInfo(String searchString){
        return programDataHandler.getAllProgramInfo(searchString);
    }

    public List<IProgramInfo> getAllProducersProgramInfo(String userName, String searchString){
        return programDataHandler.getAllProducerProgramInfo(userName, searchString);
    }

    //Adds a cast member to a program
    public boolean addCastMemberToProgram(IProgramInfo programInfo, ICastMember castMember){
        return programDataHandler.addCastMemberToProgram(programInfo.getID(), castMember);
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

    public boolean updateCastMembersRoleOnProgram(int programID, ICastMember castMember, IRole newRole){
        return programDataHandler.updateCastMembersRoleOnProgram(programID, castMember, newRole);
    }

    public boolean removeCastMemberFromProgram(int programID, ICastMember castMember){
        return programDataHandler.removeCastMemberFromProgram(programID, castMember);
    }
}
