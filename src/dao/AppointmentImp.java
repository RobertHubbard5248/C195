package dao;

import com.mysql.cj.exceptions.ConnectionIsClosedException;
import com.mysql.cj.xdevapi.SqlStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AppointmentImp {

    public static void addAppointment(Appointment appointment) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String SQLStatement = "INSERT INTO appointments " +
                "(Title, Description, Location, Type, Start, End, Created_By, " +
                "Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Query.setPreparedStatement(connection, SQLStatement);
        PreparedStatement preparedStatement = Query.getPreparedStatement();
        preparedStatement.setString(1, appointment.getTitle());
        preparedStatement.setString(2, appointment.getDescription());
        preparedStatement.setString(3, appointment.getLocation());
        preparedStatement.setString(4, appointment.getType());
        preparedStatement.setString(5, String.valueOf(appointment.getStart()));
        preparedStatement.setString(6, String.valueOf(appointment.getEnd()));
        preparedStatement.setString(7, String.valueOf(appointment.getUser().getUserName()));
        preparedStatement.setString(8, String.valueOf(appointment.getUser().getUserName()));
        preparedStatement.setString(9, String.valueOf(appointment.getCustomer().getCustomerID()));
        preparedStatement.setString(10, String.valueOf(appointment.getUser().getUserID()));
        preparedStatement.setString(11, String.valueOf(appointment.getContact().getContactID()));
        try {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            e.getMessage();
            System.out.println("Prepared Statement: " + preparedStatement);
        }
    }

    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        Appointment appointment;
        ResultSet resultSet;
        Connection connection = DBConnection.getConnection();
        String SQLStatement = "SELECT * FROM appointments;";
        Query.setPreparedStatement(connection, SQLStatement);
        PreparedStatement preparedStatement = Query.getPreparedStatement();
        preparedStatement.execute();
        resultSet = preparedStatement.getResultSet();

        try {
            while(resultSet.next()) {
                int appointmentID = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                LocalDateTime start = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = resultSet.getTimestamp("End").toLocalDateTime();
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                Customer customer = CustomerImp.getCustomer(resultSet.getInt("Customer_ID"));
                User user = UserImp.getUser(resultSet.getInt("User_ID"));
                Contact contact = ContactImp.getContact(resultSet.getInt("Contact_ID"));

                appointment = new Appointment(appointmentID, title, description, location, type, start,
                        end, lastUpdatedBy, customer, user, contact);
                allAppointments.add(appointment);
            }
            return allAppointments;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Appointment getAppointment(int appointmentID) throws SQLException {
        Appointment appointment;
        ResultSet resultSet;
        Connection connection = DBConnection.getConnection();
        String SQLStatement = "SELECT * FROM appointments WHERE AppointmentID = " + appointmentID + ";";
        Query.setPreparedStatement(connection, SQLStatement);
        PreparedStatement preparedStatement = Query.getPreparedStatement();
        preparedStatement.execute();
        resultSet = preparedStatement.getResultSet();
        try {
            while (resultSet.next()) {
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                LocalDateTime start = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = resultSet.getTimestamp("End").toLocalDateTime();
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                Customer customer = CustomerImp.getCustomer(resultSet.getInt("Customer_ID"));
                User user = UserImp.getUser(resultSet.getInt("User_ID"));
                Contact contact = ContactImp.getContact(resultSet.getInt("Contact_ID"));

                appointment = new Appointment(appointmentID, title, description, location, type, start,
                        end, lastUpdatedBy, customer, user, contact);
                return appointment;
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void modifyAppointment(Appointment appointment) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String SQLStatement = "UPDATE appointments SET " +
                "Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
                "Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? " +
                "WHERE Appointment_ID = ?;";
        Query.setPreparedStatement(connection, SQLStatement);
        PreparedStatement preparedStatement = Query.getPreparedStatement();
        preparedStatement.setString(1, appointment.getTitle());
        preparedStatement.setString(2, appointment.getDescription());
        preparedStatement.setString(3, appointment.getLocation());
        preparedStatement.setString(4, appointment.getType());
        preparedStatement.setString(5, String.valueOf(appointment.getStart()));
        preparedStatement.setString(6, String.valueOf(appointment.getEnd()));
        preparedStatement.setString(7, appointment.getLastUpdatedBy());
        preparedStatement.setString(8, String.valueOf(appointment.getCustomer().getCustomerID()));
        preparedStatement.setString(9, String.valueOf(appointment.getUser().getUserID()));
        preparedStatement.setString(10, String.valueOf(appointment.getContact().getContactID()));
        preparedStatement.setString(11, String.valueOf(appointment.getAppointmentID()));

        System.out.println(preparedStatement);

        preparedStatement.execute();

    }
    public static void deleteAppointment() {

    }

}
