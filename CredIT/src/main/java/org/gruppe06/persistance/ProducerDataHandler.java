package org.gruppe06.persistance;

import org.gruppe06.interfaces.IProducer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        IProducer producer = null;
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producer;

    }

}
