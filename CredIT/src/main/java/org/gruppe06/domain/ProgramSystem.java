package org.gruppe06.domain;

import org.gruppe06.interfaces.IProgram;
import org.gruppe06.persistance.ProgramDataHandler;

public class ProgramSystem {
    private ProgramDataHandler programDataHandler;

    public ProgramSystem() {
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

    public IProgram getProgram(String name) {
        return programDataHandler.getProgram(name);
    }

}
