package org.gruppe06.persistance;

import org.gruppe06.interfaces.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgramDataHandler {

    private Connection connection;

    public ProgramDataHandler() {
        DatabaseConnection databaseConnection = DatabaseConnection.getDatabaseConnection();
        connection = databaseConnection.getConnection();
    }

    //Method to create programs and add them to the DB
    public void createProgram(String programName, String releaseYear, String producerID, String producerRole) {
        try {
            PreparedStatement createProgramPS = connection.prepareStatement("INSERT INTO programs(name, release_year) VALUES (?, ?);");
            createProgramPS.setString(1, programName);
            createProgramPS.setString(2, releaseYear);
            createProgramPS.execute();
            PreparedStatement associateProducerPS = connection.prepareStatement("INSERT into produces_program (producer_id, program_id, role) values (?,?,?)");
            associateProducerPS.setString(1,producerID);
            associateProducerPS.setInt(2, getProgramID(programName));
            associateProducerPS.setString(3, producerRole);
            associateProducerPS.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Method to delete a program from it's program ID
    public void deleteProgram(int id) {
        try {
            PreparedStatement deleteProducesProgram = connection.prepareStatement("DELETE FROM produces_program WHERE program_id = ?");
            deleteProducesProgram.setInt(1, id);

            PreparedStatement deleteWorkedOn = connection.prepareStatement("DELETE FROM worked_on WHERE program_id = ?");
            deleteWorkedOn.setInt(1,id);

            PreparedStatement deleteProgramPS = connection.prepareStatement("DELETE FROM programs WHERE id = ?;");
            deleteProgramPS.setInt(1, id);

            deleteProducesProgram.execute();
            deleteWorkedOn.execute();
            deleteProgramPS.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Method to update an already existing programs information from it's ID
    public void updateProgram(String oldName, String newProgramName, String releaseYear) {
        try {
            PreparedStatement updateProgramPS = connection.prepareStatement("UPDATE programs SET name = ?, release_year = ? WHERE name = ?");
            updateProgramPS.setString(1, newProgramName);
            updateProgramPS.setString(2, releaseYear);
            updateProgramPS.setString(3, oldName);
            updateProgramPS.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Private method, for getting cast members from a specific program
    private ArrayList<ICastMember> getCastMembers(int programID) {

        ArrayList<ICastMember> castMembers = new ArrayList<>();

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
    public List<IProgramInfo> getAllProgramInfo() {

        List<IProgramInfo> res = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM programs");
            ResultSet set = ps.executeQuery();

            while (set.next()) {
                res.add(new ProgramInfo(set.getInt("id"),set.getString("name"), set.getString("release_year")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public List<IProgramInfo> getAllProgramInfo(String searchString) {

        List<IProgramInfo> res = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM programs where name ilike ?");
            ps.setString(1,"%" + searchString + "%");
            ResultSet set = ps.executeQuery();

            while (set.next()) {
                res.add(new ProgramInfo(set.getInt("id"),set.getString("name"), set.getString("release_year")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    //Get program based on ID
    private IProgram getProgram(int programID) {

        String name = "";
        ArrayList<IProducer> producers = new ArrayList<>();

        try {
            PreparedStatement producersPS = connection.prepareStatement("select producers.ID as ID, producers.name as producer_name, produces_program.role as role from producers " +
                                                                            "INNER JOIN produces_program on produces_program.producer_id = producers.ID where program_ID = ?");
            producersPS.setInt(1, programID);
            ResultSet producerSet = producersPS.executeQuery();

            PreparedStatement namePS = connection.prepareStatement("SELECT name from programs where id = ?");
            namePS.setInt(1, programID);
            ResultSet nameSet = namePS.executeQuery();

            if (nameSet.next()) {
                name = nameSet.getString("name");
            }

            while (producerSet.next()) {
                Producer producer = new Producer(producerSet.getString("ID"), producerSet.getString("producer_name"), producerSet.getString("role"));
                producers.add(producer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Program(name, getCastMembers(programID), producers);

    }

    private int getProgramID(String name){
        int id = 0;
        try {
            PreparedStatement idPS = connection.prepareStatement("select id from programs where name = ?");
            idPS.setString(1, name);
            ResultSet set = idPS.executeQuery();
            if(set.next()){
                id = set.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    //Get IProgramInfo with year, but without cast
    public IProgramInfo getProgramInfo(String programName) throws NullPointerException {
        IProgramInfo program = null;

        try {
            PreparedStatement getProgramsPS = connection.prepareStatement("SELECT * FROM programs WHERE name like ?;");
            getProgramsPS.setString(1, "%" + programName + "%");
            ResultSet getProgramRS = getProgramsPS.executeQuery();

            if (getProgramRS.next()) {
                program = new ProgramInfo(getProgramRS.getInt("id"),getProgramRS.getString("name"), getProgramRS.getString("release_year"));
                if (getProgramRS.getString("name").equals(" ")) {
                    throw new NullPointerException();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return program;
    }

    //Get program based on name (name doesn't have to be complete, but has to be spelled right)
    public IProgram getProgram(String programName) throws NullPointerException {

        IProgram program = null;

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM programs where name iLIKE ?");
            ps.setString(1, "%" + programName + "%");

            ResultSet set = ps.executeQuery();

            if (set.next()) {
                program = getProgram(set.getInt("ID"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (program == null) {
            System.out.println("Program not found");
            throw new NullPointerException();
        }
        return program;
    }

    //Method for getting af list of all program names
    public List<String> getAllProgramNames() {

        List<String> programNames = new ArrayList<>();

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

    //Adds a cast member to the database
    public boolean addCastMemberToProgram(int programID, String castMemberID, IRole role) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO worked_on (cast_member_ID, program_ID, role) VALUES (?,?,?)");
            ps.setString(1, castMemberID);
            ps.setInt(2, programID);
            if (role instanceof Actor) {
                ps.setString(3, "{actor}" + ((Actor) role).getCharacterName());
            } else {
                ps.setString(3, role.getRole());
            }
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
