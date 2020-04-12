package org.gruppe06.domain;

import org.gruppe06.persistance.DatabaseConnection;
import org.gruppe06.persistance.ProgramDataHandler;

import java.sql.Connection;

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

}
