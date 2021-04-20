package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactImp {
    public static Contact getContact(int contactID) throws SQLException {
        ResultSet resultSet;
        Contact contact;
        Connection connection = DBConnection.getConnection();
        String SQLStatement = "SELECT * FROM contacts WHERE Contact_ID = '" + contactID + "';";
        Query.setPreparedStatement(connection, SQLStatement);
        PreparedStatement preparedStatement = Query.getPreparedStatement();
        preparedStatement.execute();

        resultSet = preparedStatement.getResultSet();
        // Read in data and create user
        try {
            // TODO = Why the While() loop?
            while(resultSet.next()) {
                String contactName = resultSet.getString("Contact_Name");
                String email = resultSet.getString("Email");

                contact = new Contact(contactID, contactName, email);

                return contact;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static ObservableList<Contact> getAllContacts() throws SQLException {
        ResultSet resultSet;
        Contact contact;
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        Connection connection = DBConnection.getConnection();
        String SQLStatement = "SELECT * FROM contacts;";
        Query.setPreparedStatement(connection, SQLStatement);
        PreparedStatement preparedStatement = Query.getPreparedStatement();
        preparedStatement.execute();

        resultSet = preparedStatement.getResultSet();
        // Read in data and create user
        try {
            // TODO = Why the While() loop?
            while(resultSet.next()) {
                int contactID = resultSet.getInt("Contact_ID");
                String contactName = resultSet.getString("Contact_Name");
                String email = resultSet.getString("Email");

                contact = new Contact(contactID, contactName, email);
                allContacts.add(contact);
            }
            return allContacts;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
