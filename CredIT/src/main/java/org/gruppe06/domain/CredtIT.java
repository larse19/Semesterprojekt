package org.gruppe06.domain;

import org.gruppe06.interfaces.IProgram;
import org.gruppe06.persistance.DatabaseConnection;
import org.gruppe06.persistance.ProgramDataHandler;

import java.util.ArrayList;

public class CredtIT {

    private DatabaseConnection databaseConnection;
    private static ProgramDataHandler programDataHandler;

    public CredtIT(){
        databaseConnection = DatabaseConnection.getDatabaseConnection();
        programDataHandler = new ProgramDataHandler();
    }

    public static IProgram getProgram(String programName){
        return programDataHandler.getProgram(programName);
    }





}
