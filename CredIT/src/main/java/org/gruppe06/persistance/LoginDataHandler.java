package org.gruppe06.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDataHandler {

    private Connection connection;

    public LoginDataHandler(){
        DatabaseConnection databaseConnection = DatabaseConnection.getDatabaseConnection();
        connection = databaseConnection.getConnection();
    }

    public int login(String username, String password){
        int userRole = 0;

        try {
            PreparedStatement loginStatement = connection.prepareStatement("SELECT login(?,?)");
            loginStatement.setString(1,username);
            loginStatement.setString(2, password);
            ResultSet loginResult = loginStatement.executeQuery();

            if(loginResult.next()){
                userRole = loginResult.getInt("login");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userRole;

    }

    public String getUsersName(String username){
        String name = "";

        try {
            PreparedStatement nameStatement = connection.prepareStatement("SELECT get_users_name(?)");
            nameStatement.setString(1,username);
            ResultSet nameSet = nameStatement.executeQuery();

            if(nameSet.next()){
                name = nameSet.getString("get_users_name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return name;

    }

}
