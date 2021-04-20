package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Query {

    private static PreparedStatement preparedStatement;

    // Create a Statement Object
    public static void setPreparedStatement(Connection connection, String SQLStatement) throws SQLException {
        preparedStatement = connection.prepareStatement(SQLStatement);
    }
    
    // Returns a Statement Object
    public static PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

}
