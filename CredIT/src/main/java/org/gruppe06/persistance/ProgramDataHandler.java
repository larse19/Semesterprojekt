package org.gruppe06.persistance;

import org.gruppe06.interfaces.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    //Returns a list of all programs, a producer has worked on, as IProgramInfo
    public List<IProgramInfo> getAllProducerProgramInfo(String userName, String searchString){
        List<IProgramInfo> res = new ArrayList<>();

        try {
            PreparedStatement programsPS = connection.prepareStatement("select * from programs inner join produces_program on programs.ID = produces_program.program_ID where producer_id = ? and programs.name ilike ?");
            programsPS.setString(1, userName);
            programsPS.setString(2, "%" + searchString + "%");

            ResultSet programsRS = programsPS.executeQuery();

            while(programsRS.next()){
                res.add(new ProgramInfo(programsRS.getInt("id"), programsRS.getString("name"), programsRS.getString("release_year")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;

    }

    //Returns a list of all programs, as programInfo
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
        String year = "";
        ArrayList<IProducer> producers = new ArrayList<>();

        try {
            PreparedStatement producersPS = connection.prepareStatement("select producers.ID as ID, producers.name as producer_name, produces_program.role as role from producers " +
                                                                            "INNER JOIN produces_program on produces_program.producer_id = producers.ID where program_ID = ?");
            producersPS.setInt(1, programID);
            ResultSet producerSet = producersPS.executeQuery();

            PreparedStatement namePS = connection.prepareStatement("SELECT * from programs where id = ?");
            namePS.setInt(1, programID);
            ResultSet nameSet = namePS.executeQuery();

            if (nameSet.next()) {
                name = nameSet.getString("name");
                year = nameSet.getString("release_year");
            }

            while (producerSet.next()) {
                Producer producer = new Producer(producerSet.getString("ID"), producerSet.getString("producer_name"), producerSet.getString("role"));
                producers.add(producer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Program(name, getCastMembers(programID), producers, year);

    }

    //Get program based on name
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
            if (program == null) {
                throw new NullPointerException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
    public boolean addCastMemberToProgram(IProgramInfo programInfo, ICastMember castMember) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO worked_on (cast_member_ID, program_ID, role) VALUES (?,?,?)");
            ps.setString(1, castMember.getID());
            ps.setInt(2, programInfo.getID());
            if(castMember.getRole() instanceof Actor){
                ps.setString(3, "{actor}" + castMember.getRole().toString());
            }else{
                ps.setString(3, castMember.getRole().toString());
            }
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCastMembersRoleOnProgram(IProgramInfo programInfo, ICastMember castMember, IRole newRole){
        try {

            PreparedStatement updateRolePS = connection.prepareStatement("update worked_on set role = ? where cast_member_id = ? and program_id = ? and role = ?");

            if(newRole instanceof Actor){
                updateRolePS.setString(1, "{actor}" + newRole.toString());
            }else{
                updateRolePS.setString(1, newRole.toString());
            }

            updateRolePS.setString(2, castMember.getID());
            updateRolePS.setInt(3, programInfo.getID());

            if(castMember.getRole() instanceof Actor){
                updateRolePS.setString(4, "{actor}"+castMember.getRole().toString());
            }else{
                updateRolePS.setString(4, castMember.getRole().toString());
            }

            updateRolePS.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeCastMemberFromProgram(IProgramInfo programInfo, ICastMember castMember){
        try {
            PreparedStatement removeCastMemberPS = connection.prepareStatement("delete from worked_on where program_id = ? and cast_member_id = ? and role = ?");

            removeCastMemberPS.setInt(1, programInfo.getID());
            removeCastMemberPS.setString(2, castMember.getID());

            if(castMember.getRole() instanceof Actor){
                removeCastMemberPS.setString(3, "{actor}" + castMember.getRole().toString());
            }else {
                removeCastMemberPS.setString(3, castMember.getRole().toString());
            }

            removeCastMemberPS.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addProducerToProgram(IProgramInfo programInfo, IProducer producer){
        try {
            PreparedStatement addProducerPS = connection.prepareStatement("INSERT INTO produces_program (producer_id, program_id,role) values (?, ?,?)");
            addProducerPS.setString(1,producer.getID());
            addProducerPS.setInt(2, programInfo.getID());
            addProducerPS.setString(3, producer.getRole());
            addProducerPS.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProducersRoleOnProgram(IProgramInfo programInfo, IProducer producer, String newRole){
        try {
            PreparedStatement updateProducerRolePS = connection.prepareStatement("update produces_program set role = ? where program_id = ? and producer_id = ? and role = ?");
            updateProducerRolePS.setString(1, newRole);
            updateProducerRolePS.setInt(2, programInfo.getID());
            updateProducerRolePS.setString(3, producer.getID());
            updateProducerRolePS.setString(4, producer.getRole());
            updateProducerRolePS.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeProducerFromProgram(IProgramInfo programInfo, IProducer producer){
        try {
            PreparedStatement removeProducerPS = connection.prepareStatement("delete from produces_program where program_id = ? and producer_id = ? and role = ?");
            removeProducerPS.setInt(1, programInfo.getID());
            removeProducerPS.setString(2, producer.getID());
            removeProducerPS.setString(3, producer.getRole());
            removeProducerPS.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
