package org.gruppe06.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

}
