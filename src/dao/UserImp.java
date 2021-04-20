package dao;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

// TODO = JAVA Doc
// TODO = Test
public class UserImp {
    public static User getUser(String userName) throws SQLException {
        User user;
        ResultSet resultSet;
        Connection connection = DBConnection.getConnection();
        String SQLStatement = "SELECT * FROM users WHERE User_Name = '" + userName + "';";
        Query.setPreparedStatement(connection, SQLStatement);
        PreparedStatement preparedStatement = Query.getPreparedStatement();
        preparedStatement.execute();

        resultSet = preparedStatement.getResultSet();
        // Read in data and create user
        try {
        // TODO = Why the While() loop?
        while(resultSet.next()) {
            int userID = resultSet.getInt("User_ID");
            //String userName = resultSet.getString("User_Name");
            String password = resultSet.getString("Password");

            user = new User(userID, userName, password);

            return user;
        }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static User getUser(int userID) throws SQLException {
        User user;
        ResultSet resultSet;
        Connection connection = DBConnection.getConnection();
        String SQLStatement = "SELECT * FROM users WHERE User_ID = '" + userID + "';";
        Query.setPreparedStatement(connection, SQLStatement);
        PreparedStatement preparedStatement = Query.getPreparedStatement();
        preparedStatement.execute();

        resultSet = preparedStatement.getResultSet();
        // Read in data and create user
        try {
            // TODO = Why the While() loop?
            while(resultSet.next()) {
                String userName = resultSet.getString("User_Name");
                String password = resultSet.getString("Password");

                user = new User(userID, userName, password);

                return user;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
