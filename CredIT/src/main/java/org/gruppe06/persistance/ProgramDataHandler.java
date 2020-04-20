package org.gruppe06.persistance;

import org.gruppe06.interfaces.ICastMember;
import org.gruppe06.interfaces.IProducer;
import org.gruppe06.interfaces.IProgram;
import org.gruppe06.interfaces.IRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgramDataHandler {

    private DatabaseConnection databaseConnection;
    private Connection connection;

    public ProgramDataHandler() {
        databaseConnection = DatabaseConnection.getDatabaseConnection();
        connection = databaseConnection.getConnection();
    }

    //Method to create programs and add them to the DB
    public void createProgram(String programName, String releaseYear) {
        try {
            PreparedStatement createProgramPS = connection.prepareStatement("INSERT INTO programs(name, release_year) VALUES (?, ?);");
            createProgramPS.setString(1, programName);
            createProgramPS.setString(2, releaseYear);
            createProgramPS.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Method to delete a program from it's program ID
    public void deleteProgram(String programName) {
        try {
            PreparedStatement deleteProgramPS = connection.prepareStatement("DELETE FROM programs WHERE name = ?;");
            deleteProgramPS.setString(1, programName);
            deleteProgramPS.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Method to update an already existing programs information from it's ID
    public void updateProgram(String oldName, String newProgramName, String releaseYear) {
        try {
            PreparedStatement updateProgramPS = connection.prepareStatement("UPDATE programs SET name = ? SET release_year = ? WHERE name = oldName;");
            updateProgramPS.setString(1, newProgramName);
            updateProgramPS.setString(2, releaseYear);
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
    public Map<Integer, String> getAllProgramIdAndNames() {

        Map<Integer, String> res = new HashMap<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM programs");
            ResultSet set = ps.executeQuery();

            while (set.next()) {
                res.put(Integer.parseInt(set.getString("ID")), set.getString("name"));
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
            PreparedStatement producersPS = connection.prepareStatement("select producers.ID as ID, producers.name as producer_name from producers " +
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
                Producer producer = new Producer(producerSet.getString("ID"), producerSet.getString("producer_name"));
                producers.add(producer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Program(name, getCastMembers(programID), producers);

    }

    public IProgram getPrograms(String programName) throws NullPointerException {
        IProgram program = null;

        try {
            PreparedStatement getProgramsPS = connection.prepareStatement("SELECT * FROM programs WHERE name like ?;");
            getProgramsPS.setString(1, "%" + programName + "%");
            ResultSet getProgramRS = getProgramsPS.executeQuery();

            while (getProgramRS.next()) {
                program = new Program(getProgramRS.getString("name"), getProgramRS.getString("release_year"));
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
