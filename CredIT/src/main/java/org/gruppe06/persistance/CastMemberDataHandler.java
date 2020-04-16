package org.gruppe06.persistance;

import org.gruppe06.interfaces.ICastMember;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;


public class CastMemberDataHandler {

    private DatabaseConnection databaseConnection;
    private Connection connection;

    public CastMemberDataHandler(){
        databaseConnection = DatabaseConnection.getDatabaseConnection();
        connection = databaseConnection.getConnection();
    }

    //This method creates a cast member into the cast_member table.
    public void createCastMember(String castMemberID, String castMemberName){
        try {
            PreparedStatement createCastMemberPS = connection.prepareStatement("INSERT INTO cast_members(id, name) VALUES (?, ?);");
            createCastMemberPS.setString(1, castMemberID);
            createCastMemberPS.setString(2, castMemberName);
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
    public void updateCastMember(String castMemberID, String castMemberName){
        try {
            PreparedStatement createCastMemberPS = connection.prepareStatement("UPDATE cast_members SET name = ? WHERE id = ?;");
            createCastMemberPS.setString(1, castMemberName);
            createCastMemberPS.setString(2, castMemberID);
            createCastMemberPS.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Method to get a cast member from the database
    public ICastMember getCastMember(String castMemberName) throws NullPointerException{
        ICastMember castMember = null;
        try {
            PreparedStatement getCastMemberPS = connection.prepareStatement("SELECT * FROM cast_members WHERE name like ?;");
            getCastMemberPS.setString(1, "%"+castMemberName+"%");
            ResultSet getCastMemberRS = getCastMemberPS.executeQuery();

            while(getCastMemberRS.next()) {
                castMember = new CastMember(getCastMemberRS.getString("ID"), getCastMemberRS.getString("name"));
            }
            if(castMemberName.equals(" ")){
                throw new NullPointerException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return castMember;

    }

}
