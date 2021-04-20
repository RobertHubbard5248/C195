package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CountryImp {
    public static ObservableList<Country> getAllCountries() throws SQLException {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        Country country;
        ResultSet resultSet;

        Connection connection = DBConnection.getConnection();
        String SQLStatement = "SELECT * FROM countries;";
        Query.setPreparedStatement(connection, SQLStatement);
        PreparedStatement preparedStatement = Query.getPreparedStatement();
        preparedStatement.execute();
        resultSet = preparedStatement.getResultSet();
        try {
            while (resultSet.next()) {
                int countryID = resultSet.getInt("Country_ID");
                String countryName = resultSet.getString("Country");
                LocalDateTime createDate = resultSet.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = resultSet.getString("Created_By");
                LocalDateTime lastUpdate = resultSet.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");

                country = new Country(countryID, countryName, createDate, createdBy,
                lastUpdate, lastUpdatedBy);

                allCountries.add(country);
            }
            return allCountries;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    /*
    public static Country getCountryByID() {

    }
    */
}
