package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerImp {
    // TODO = Static bool act; ?? What is this?
    // TODO = Test
    // TODO Java Doc
    public static Customer getCustomer(int customerID) throws SQLException {
        Customer customer;
        ResultSet resultSet;

        Connection connection = DBConnection.getConnection();
        String SQLStatement = "SELECT customers.*, first_level_divisions.COUNTRY_ID FROM customers " +
                "INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID " +
                "WHERE Customer_ID = '" + customerID + "';";
        Query.setPreparedStatement(connection, SQLStatement);
        PreparedStatement preparedStatement = Query.getPreparedStatement();
        preparedStatement.execute();
        resultSet = preparedStatement.getResultSet();

        try {
            // TODO - int customerID = resultSet.getInt(Customer_ID);
            while (resultSet.next()) {
                String customerName = resultSet.getString("Customer_Name");
                String address = resultSet.getString("Address");
                String postalCode = resultSet.getString("Postal_Code");
                String phone = resultSet.getString("Phone");
                String createdBy = resultSet.getString("Created_By");
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                int divisionID = resultSet.getInt("Division_ID");
                int countryID = resultSet.getInt("COUNTRY_ID");

                customer = new Customer(customerID, customerName, address, postalCode, phone, createdBy,
                        lastUpdatedBy, divisionID, countryID);
                return customer;
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        Customer customer;
        ResultSet resultSet;

        Connection connection = DBConnection.getConnection();
        String SQLStatement = "SELECT customers.*, first_level_divisions.COUNTRY_ID FROM customers " +
                "INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID;";
        Query.setPreparedStatement(connection, SQLStatement);
        PreparedStatement preparedStatement = Query.getPreparedStatement();
        preparedStatement.execute();
        resultSet = preparedStatement.getResultSet();
        try {
            while (resultSet.next()) {
                int customerID = resultSet.getInt("Customer_ID");
                String customerName = resultSet.getString("Customer_Name");
                String address = resultSet.getString("Address");
                String postalCode = resultSet.getString("Postal_Code");
                String phone = resultSet.getString("Phone");
                String createdBy = resultSet.getString("Created_By");
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                int divisionID = resultSet.getInt("Division_ID");
                int countryID = resultSet.getInt("COUNTRY_ID");

                customer = new Customer(customerID, customerName, address, postalCode, phone, createdBy,
                        lastUpdatedBy, divisionID, countryID);

                allCustomers.add(customer);
            }
            return allCustomers;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    // TODO = Add Customer
    // TODO = Make this a boolean?
    // TODO = Add user parameter for "created by"
    public static void addCustomer(Customer customer) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String SQLStatement = "INSERT INTO customers " +
                "(Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID)" +
                "VALUES" +
                "(?, ?, ?, ?, ?, ?, ?);";
        Query.setPreparedStatement(connection, SQLStatement);
        PreparedStatement preparedStatement = Query.getPreparedStatement();
        preparedStatement.setString(1, customer.getCustomerName());
        preparedStatement.setString(2, customer.getAddress());
        preparedStatement.setString(3, customer.getPostalCode());
        preparedStatement.setString(4, customer.getPhoneNumber());
        preparedStatement.setString(5, customer.getCreatedBy());
        preparedStatement.setString(6, customer.getLastUpdatedBy());
        preparedStatement.setString(7, Integer.toString(customer.getDivisionID()));
        preparedStatement.execute();

    }
    // TODO = Update Customer
    public static void updateCustomer(Customer customer) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String SQLStatement = "UPDATE customers SET " +
                "Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = CURRENT_TIMESTAMP, " +
                "Last_Updated_By = ?, Division_ID = ? " +
                "WHERE Customer_ID = ?;";
        Query.setPreparedStatement(connection, SQLStatement);
        PreparedStatement preparedStatement = Query.getPreparedStatement();
        preparedStatement.setString(1, customer.getCustomerName());
        preparedStatement.setString(2, customer.getAddress());
        preparedStatement.setString(3, customer.getPostalCode());
        preparedStatement.setString(4, customer.getPhoneNumber());
        preparedStatement.setString(5, customer.getLastUpdatedBy());
        preparedStatement.setString(6, Integer.toString(customer.getDivisionID()));
        preparedStatement.setString(7, Integer.toString(customer.getCustomerID()));
        preparedStatement.execute();


    }


    /**
     *
     * @param customer
     * @throws SQLException
     */
    // TODO = Make this a boolean?
    public static void deleteCustomer(Customer customer) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String SQLStatement = "Delete FROM customers WHERE Customer_ID = '" + customer.getCustomerID() + "';";
        Query.setPreparedStatement(connection, SQLStatement);
        PreparedStatement preparedStatement = Query.getPreparedStatement();
        preparedStatement.execute();
    }
}
