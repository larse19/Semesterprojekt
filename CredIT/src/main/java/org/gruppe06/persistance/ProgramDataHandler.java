package org.gruppe06.persistance;

import org.gruppe06.domain.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProgramDataHandler {

    private DatabaseConnection databaseConnection;
    private Connection connection;

    public ProgramDataHandler() {
        databaseConnection = DatabaseConnection.getDatabaseConnection();
        connection = databaseConnection.getConnection();
        System.out.println("test");
    }

    public ArrayList<CastMember> getCastMembers(int programID) {

        ArrayList<CastMember> castMembers = new ArrayList<>();

        try {
            PreparedStatement castPS = connection.prepareStatement("select cast_members.ID as ID, cast_members.name as cast_name, worked_on.role as role from cast_members INNER JOIN worked_on ON cast_members.ID = worked_on.cast_member_ID INNER JOIN programs on program_ID = worked_on.program_ID where program_ID = ?");
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

    public Program getProgram(int programID) {

        String name = "";
        ArrayList<Producer> producers = new ArrayList<>();

        try {
            PreparedStatement producersPS = connection.prepareStatement("select producers.ID as ID, producers.name as producer_name, programs.name as program_name from producers INNER JOIN produces_program on produces_program.producer_id = producers.ID INNER JOIN programs on programs.ID = produces_program.program_ID where programs.id = ?");
            producersPS.setInt(1, programID);
            ResultSet producerSet = producersPS.executeQuery();

            while (producerSet.next()) {
                Producer producer = new Producer(producerSet.getString("ID"), producerSet.getString("producer_name"));
                producers.add(producer);
                name = producerSet.getString("program_name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Program(name, getCastMembers(programID), producers);

    }


}
