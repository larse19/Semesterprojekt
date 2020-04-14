package org.gruppe06.persistance;

import org.gruppe06.domain.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ProgramDataHandler {

    private DatabaseConnection databaseConnection;
    private Connection connection;

    public ProgramDataHandler() {
        databaseConnection = DatabaseConnection.getDatabaseConnection();
        connection = databaseConnection.getConnection();
    }

    //Private method, for getting cast members from a specific program
    private ArrayList<CastMember> getCastMembers(int programID) {

        ArrayList<CastMember> castMembers = new ArrayList<>();

        try {
            PreparedStatement castPS = connection.prepareStatement("select cast_members.ID as ID, cast_members.name as cast_name, worked_on.role as role from programs INNER JOIN worked_on ON programs.ID = worked_on.program_ID INNER JOIN cast_members on cast_members.ID = worked_on.cast_member_ID where program_ID = ?");
            castPS.setInt(1, programID);
            ResultSet castSet = castPS.executeQuery();

            while (castSet.next()) {
                String roleString = castSet.getString("role");
                Role role;
                if (roleString.contains("{actor}")) {
                    role = new Actor(roleString.split("}")[1]);
                } else {
                    role = new Role(roleString);
                }
                CastMember castMember = new CastMember(castSet.getString("ID"), castSet.getString("cast_name"), role);
                castMembers.add(castMember);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return castMembers;
    }

    //Private method for getting all program names and their ID
    private HashMap<Integer, String> getAllProgramIdAndNames(){

        HashMap<Integer, String> res = new HashMap<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM programs");
            ResultSet set = ps.executeQuery();

            while(set.next()){
                res.put(Integer.parseInt(set.getString("ID")), set.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;

    }

    //Get program based on ID
    public Program getProgram(int programID) {

        String name = "";
        ArrayList<Producer> producers = new ArrayList<>();

        try {
            PreparedStatement producersPS = connection.prepareStatement("select producers.ID as ID, producers.name as producer_name, programs.name as program_name from programs INNER JOIN produces_program on produces_program.program_id = programs.ID INNER JOIN producers on producers.ID = produces_program.producer_id where programs.id = ?");
            producersPS.setInt(1, programID);
            ResultSet producerSet = producersPS.executeQuery();

            //if (producerSet.getFetchSize() != 0) {
                while (producerSet.next()) {
                    Producer producer = new Producer(producerSet.getString("ID"), producerSet.getString("producer_name"));
                    producers.add(producer);
                    name = producerSet.getString("program_name");
                }
            /*}
            else{
                System.out.println("That program does not exist");
            }*/

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Program(name, getCastMembers(programID), producers);

    }

    //Get program based on name (name doesn't have to be complete, but has to be spelled right)
    public Program getProgram(String programName){

        Program program = null;

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM programs where name iLIKE ?");
            ps.setString(1,programName+"%");

            ResultSet set = ps.executeQuery();

            while(set.next()){
                program = getProgram(set.getInt("ID"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //TODO Returning an empty program is not good, need rework
        if(program == null){
            System.out.println("Program not found");
            program = new Program("", new ArrayList<>(), new ArrayList<>());
        }
        return program;
    }

    //Method for getting af list of all program names
    public ArrayList<String> getAllProgramNames() {

        ArrayList<String> programNames = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT name from programs");

            ResultSet set = ps.executeQuery();

            while (set.next()) {
                programNames.add(set.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return programNames;

    }

}
