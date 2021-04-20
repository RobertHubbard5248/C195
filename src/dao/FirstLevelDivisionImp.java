package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.FirstLevelDivision;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class FirstLevelDivisionImp {
    public static ObservableList<FirstLevelDivision> getDivisionsByCountry(Country country) throws SQLException {
        int countryID = country.getCountryID();
        ObservableList<FirstLevelDivision> filteredDivisions = FXCollections.observableArrayList();
        FirstLevelDivision firstLevelDivision;
        ResultSet resultSet;

        Connection connection = DBConnection.getConnection();
        String SQLStatement = "SELECT * FROM first_level_divisions " +
                "WHERE COUNTRY_ID = " + countryID + ";";
        Query.setPreparedStatement(connection, SQLStatement);
        PreparedStatement preparedStatement = Query.getPreparedStatement();
        preparedStatement.execute();
        resultSet = preparedStatement.getResultSet();

        try {
            while (resultSet.next()) {
                int divisionID = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");
                LocalDateTime createDate = resultSet.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = resultSet.getString("Created_By");
                LocalDateTime lastUpdate = resultSet.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");

                firstLevelDivision = new FirstLevelDivision(divisionID, divisionName, createDate, createdBy,
                        lastUpdate, lastUpdatedBy, countryID);

                filteredDivisions.add(firstLevelDivision);
            }
            return filteredDivisions;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
