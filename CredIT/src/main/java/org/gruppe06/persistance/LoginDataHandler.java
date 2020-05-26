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

    //Pulls a users hashed password from the database
    public String getLogin(String username){

        try {
            PreparedStatement loginPS = connection.prepareStatement("select password from users where user_id = ?");
            loginPS.setString(1,username);
            ResultSet password = loginPS.executeQuery();
            if(password.next()){
                return password.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";

    }

    //Pulls a users name, based on their username (Works for both producers and administrators)
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
