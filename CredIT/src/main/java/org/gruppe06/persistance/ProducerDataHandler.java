package org.gruppe06.persistance;

import org.gruppe06.interfaces.IProducer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProducerDataHandler {

    private Connection connection;

    public ProducerDataHandler(){
        connection = DatabaseConnection.getDatabaseConnection().getConnection();
    }

    public boolean createProducer(String username, String name, String password){

        try {
            PreparedStatement createProducer = connection.prepareStatement("call create_producer(?,?,?)");
            createProducer.setString(1,username);
            createProducer.setString(2,name);
            createProducer.setString(3,password);
            createProducer.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProducer(String username){
        try {
            PreparedStatement deleteProducerPS = connection.prepareStatement("call delete_producer(?)");
            deleteProducerPS.setString(1, username);
            deleteProducerPS.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //Method to get a producer from the database, based on name. Throws NullPointerException, if the producer doesn't exist
    public IProducer getProducer(String producerName) throws NullPointerException{
        IProducer producer = new Producer();
        try {
            PreparedStatement getProducerPS = connection.prepareStatement("SELECT * FROM producers WHERE name iLIKE ?");
            getProducerPS.setString(1, "%"+producerName+"%");
            ResultSet getProducerRS = getProducerPS.executeQuery();

            while(getProducerRS.next()) {
                producer = new Producer(getProducerRS.getString("ID"), getProducerRS.getString("name"));
                if(getProducerRS.getString("name").equals(" ")){
                    throw new NullPointerException();
                }
            }
            //Gets programs and roles where producer is involved
            PreparedStatement programRolesPS = connection.prepareStatement(
                    "SELECT * FROM programs INNER JOIN produces_program ON produces_program.program_ID = programs.ID WHERE produces_program.producer_ID = ?");
            programRolesPS.setString(1,producer.getID());
            ResultSet programRolesRS = programRolesPS.executeQuery();

            ArrayList<ProgramRole> programRoles = new ArrayList<>();
            while(programRolesRS.next()){
                ProgramInfo programInfo = new ProgramInfo(programRolesRS.getInt("ID"),programRolesRS.getString("name"),programRolesRS.getString("release_year"));
                Role role = new Role(programRolesRS.getString("role"));
                ProgramRole programRole = new ProgramRole(programInfo,role);
                programRoles.add(programRole);
            }
            producer.setProgramRoles(programRoles);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producer;

    }

}
