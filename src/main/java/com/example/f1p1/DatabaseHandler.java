package com.example.f1p1;

import java.sql.*;

public class DatabaseHandler extends Configs {
    Connection dbConnection;
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("com.mysql.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }


    public String sighupUser(User user) throws SQLException, ClassNotFoundException {
        PreparedStatement ps =
                getDbConnection().prepareStatement
                        ("SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USERS_USERNAME + "= ?");
        ps.setString (1, user.getUserName());
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return "est";
        } else {
            String insert = "INSERT INTO " + Const.USER_TABLE + "(" + Const.USERS_NAME + "," + Const.USERS_SURNAME +
                    "," + Const.USERS_USERNAME + "," + Const.USERS_PASSWORD + ")" + "VALUES(?,?,?,? )";

            try {
                PreparedStatement prst = getDbConnection().prepareStatement(insert);
                prst.setString(1, user.getFirstName());
                prst.setString(2, user.getLastName());
                prst.setString(3, user.getUserName());
                prst.setString(4, user.getPassword());
                prst.executeUpdate();
                return "vse norm";
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        }
        return "vse ploxo";
    }

    public ResultSet getUser(User user){
        ResultSet resSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_USERNAME + "=? AND " + Const.USERS_PASSWORD + "=?";
        try {
            PreparedStatement prst = getDbConnection().prepareStatement(select);
            prst.setString(1, user.getUserName());
            prst.setString(2, user.getPassword());
            resSet = prst.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        return resSet;
    }
}
