package org.gruppe06.domain;

import org.gruppe06.interfaces.*;
import org.gruppe06.persistance.ProgramDataHandler;
import java.util.List;

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
        return programDataHandler.addCastMemberToProgram(programInfo, castMember);
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

    //Updates a cast members role on a program
    public boolean updateCastMembersRoleOnProgram(IProgramInfo programInfo, ICastMember castMember, IRole newRole){
        return programDataHandler.updateCastMembersRoleOnProgram(programInfo, castMember, newRole);
    }

    //Removes a cast member from a program
    public boolean removeCastMemberFromProgram(IProgramInfo programInfo, ICastMember castMember){
        return programDataHandler.removeCastMemberFromProgram(programInfo, castMember);
    }

    //Adds a producer to a program
    public boolean addProducerToProgram(IProgramInfo programInfo, IProducer producer){
        return programDataHandler.addProducerToProgram(programInfo, producer);
    }

    //Updates a producers role on a program
    public boolean updateProducersRoleOnProgram(IProgramInfo programInfo, IProducer producer, String newRole){
        return programDataHandler.updateProducersRoleOnProgram(programInfo, producer, newRole);
    }

    //Removes a producer from a program
    public boolean removeProducerFromProgram(IProgramInfo programInfo, IProducer producer){
        return programDataHandler.removeProducerFromProgram(programInfo, producer);
    }
}
