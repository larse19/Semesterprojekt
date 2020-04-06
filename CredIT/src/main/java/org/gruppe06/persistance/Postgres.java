package org.gruppe06.persistance;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

//This class is purely meant for testing queries with the database
public class Postgres {

    private static DatabaseConnection dbc = new DatabaseConnection();

    public static HashMap<String, String> getPeople() {

        HashMap<String, String> result = new HashMap<>();

        try {

            Connection db = dbc.getConnection();

            PreparedStatement ps = db.prepareStatement("SELECT * FROM people");

            ResultSet set = ps.executeQuery();

            while(set.next()){
                result.put((set.getString("firstname") + " " + set.getString("lastname")), set.getString("role"));
            }

        }
        catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;

    }

}
