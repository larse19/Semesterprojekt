package org.gruppe06.domain;

import org.gruppe06.interfaces.IProgram;
import org.gruppe06.persistance.CastMemberDataHandler;
import org.gruppe06.persistance.DatabaseConnection;
import org.gruppe06.persistance.ProgramDataHandler;

import java.util.ArrayList;

public class CredtIT {

    private DatabaseConnection databaseConnection;
    private static ProgramDataHandler programDataHandler;
    private static CastMemberDataHandler castMemberDataHandler;

    public CredtIT(){
        databaseConnection = DatabaseConnection.getDatabaseConnection();
        programDataHandler = new ProgramDataHandler();
        castMemberDataHandler = new CastMemberDataHandler();
    }

    public static IProgram getProgram(int programID){
        return programDataHandler.getProgram(programID);
    }

    public static IProgram getProgram(String programName){
        return programDataHandler.getProgram(programName);
    }

    public static ArrayList<String> getAllProgramNames(){
        return programDataHandler.getAllProgramNames();
    }

}
