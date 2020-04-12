package org.gruppe06.domain;

import org.gruppe06.persistance.DatabaseConnection;
import org.gruppe06.persistance.ProgramDataHandler;

import java.sql.Connection;
import java.util.ArrayList;

public class CredtIT {

    private DatabaseConnection databaseConnection;
    private static ProgramDataHandler programDataHandler;

    public CredtIT(){
        databaseConnection = DatabaseConnection.getDatabaseConnection();
        programDataHandler = new ProgramDataHandler();
    }

    public static Program getProgram(int programID){
        return programDataHandler.getProgram(programID);
    }

    public static Program getProgram(String programName){
        return programDataHandler.getProgram(programName);
    }

    public static ArrayList<String> getAllProgramNames(){
        return programDataHandler.getAllProgramNames();
    }

}
