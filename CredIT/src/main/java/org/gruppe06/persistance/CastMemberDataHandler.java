package org.gruppe06.persistance;

import org.gruppe06.domain.CastMember;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;


public class CastMemberDataHandler {

    private DatabaseConnection databaseConnection;
    private Connection connection;

    private CastMember castMember;

    public CastMemberDataHandler(){
        databaseConnection = DatabaseConnection.getDatabaseConnection();
        connection = databaseConnection.getConnection();
    }

    //This method creates a cast member into the cast_member table.
    public void createCastMember(String castMemberName){
        try {
            PreparedStatement createCastMemberPS = connection.prepareStatement("INSERT INTO cast_members(id, name) VALUES (?, ?);");
            createCastMemberPS.setString(1 , castMemberName);
            createCastMemberPS.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //This method deletes a cast member from the cast_members table.
    public void deleteCastMember(String castID){
        try {
            PreparedStatement createCastMemberPS = connection.prepareStatement("DELETE FROM TABLE cast_members WHERE id = ?;");
            createCastMemberPS.setString(1, castID);
            createCastMemberPS.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //This method updates cast member names, not id.
    public void updateCastMember(String castMemberName){
        try {
            PreparedStatement createCastMemberPS = connection.prepareStatement("ALTER TABLE cast_members SET name = ? WHERE id = castID;");
            createCastMemberPS.setString(1, castMemberName);
            createCastMemberPS.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //Method to get a cast member from the database
    public CastMember getCastMember(String castMemberName) {
        try {
            PreparedStatement getCastMemberPS = connection.prepareStatement("SELECT * FROM cast_members WHERE name like ?;");
            getCastMemberPS.setString(1, castMemberName);
            ResultSet getCastMemberRS = getCastMemberPS.executeQuery();

            while(getCastMemberRS.next()) {
                System.out.println(getCastMemberRS.getString("name"));
            }
            if(castMemberName == " "){
                System.out.println("No such existing cast member");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return castMember;

    }

}
