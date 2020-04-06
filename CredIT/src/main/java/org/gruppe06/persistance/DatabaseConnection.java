package org.gruppe06.persistance;

import java.sql.*;

//Class that connects to database
public class DatabaseConnection {

    private Connection connection;

    public DatabaseConnection() {
        //Gets the required drivers
        try {
            Class.forName("org.postgresql.Driver");
        } catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        //Database parameters
        String url = "jdbc:postgresql://balarama.db.elephantsql.com:5432/ztmynldp";
        String username = "ztmynldp";
        String password = "eh5DkkX9YcLO5tcLlD5y_MTHq-dcnnb9";

        //Tries to connect to the database
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        return connection;
    }

}
