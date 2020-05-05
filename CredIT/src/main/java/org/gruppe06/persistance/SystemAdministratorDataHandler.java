package org.gruppe06.persistance;

import org.gruppe06.interfaces.IPerson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SystemAdministratorDataHandler {

    private Connection connection;

    public SystemAdministratorDataHandler() {
        connection = DatabaseConnection.getDatabaseConnection().getConnection();
    }

    public boolean createSystemAdministrator(String username, String name, String password) {
        try {
            PreparedStatement createAdministrator = connection.prepareStatement("call create_system_administrator(?,?,?)");
            createAdministrator.setString(1, username);
            createAdministrator.setString(2, name);
            createAdministrator.setString(3, password);
            createAdministrator.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSystemAdministrator(String username) {
        try {
            PreparedStatement deleteAdministrator = connection.prepareStatement("call delete_system_administrator(?)");
            deleteAdministrator.setString(1, username);
            deleteAdministrator.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkIfUsernameIsAvailable(String username) {
        try {
            PreparedStatement userNamesPS = connection.prepareStatement("Select user_id from users where user_id = ?");
            userNamesPS.setString(1, username);
            ResultSet userNameSet = userNamesPS.executeQuery();

            return !userNameSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<IPerson> getListOfUsers(String searchString) {
        List<IPerson> personList = new ArrayList<>();

        try {
            PreparedStatement administratorsPS = connection.prepareStatement("SELECT id, name from system_administrators where id ilike ?");
            administratorsPS.setString(1, "%" + searchString + "%");
            ResultSet administratorSet = administratorsPS.executeQuery();

            PreparedStatement producersPS = connection.prepareStatement("SELECT id, name from producers where id ilike ?");
            producersPS.setString(1, "%" + searchString + "%");
            ResultSet producerSet = producersPS.executeQuery();

            while (administratorSet.next()) {
                personList.add(new SystemAdministrator(administratorSet.getString("id"), administratorSet.getString("name")));
            }

            while (producerSet.next()) {
                personList.add(new Producer(producerSet.getString("id"), producerSet.getString("name")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personList;
    }

    public int getUserRole(String username) {
        int userRole = 0;
        try {
            PreparedStatement userRolePS = connection.prepareStatement("Select role from has_user_role where user_id = ?");
            userRolePS.setString(1, username);
            ResultSet userRoleSet = userRolePS.executeQuery();

            if (userRoleSet.next()) {
                userRole = userRoleSet.getInt("role");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userRole;
    }

    public boolean updateUserRole(String username, int role) {
        try {
            PreparedStatement updateRolePS = connection.prepareStatement("call update_user_role(?,?)");
            updateRolePS.setString(1, username);
            updateRolePS.setInt(2, role);
            updateRolePS.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUsersName(String username, String newName){
        try {
            PreparedStatement updateName = connection.prepareStatement("call update_users_name(?,?)");
            updateName.setString(1,username);
            updateName.setString(2,newName);
            updateName.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePassword(String username, String password){
        try {
            PreparedStatement updatePasswordPS = connection.prepareStatement("update users set password = ? where user_id = ?");
            updatePasswordPS.setString(1,password);
            updatePasswordPS.setString(2, username);
            updatePasswordPS.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
