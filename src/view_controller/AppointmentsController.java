package view_controller;

import dao.AppointmentImp;
import dao.ContactImp;
import dao.CustomerImp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {

    Appointment appointment;

    Stage stage;
    Parent scene;

    @FXML
    private TextField apptIdLbl;

    @FXML
    private TextField userIdLbl;

    @FXML
    private TextField titleLbl;

    @FXML
    private TextField descriptionLbl;

    @FXML
    private TextField locationLbl;

    @FXML
    private TextField typeLbl;

    @FXML
    private ComboBox<Customer> customerBox;

    @FXML
    private ComboBox<Contact> contactBox;

    @FXML
    private DatePicker startDateBox;

    @FXML
    private ComboBox<LocalTime> startTimeBox;

    @FXML
    private DatePicker endDateBox;

    @FXML
    private ComboBox<LocalTime> endTimeBox;

    @FXML
    private Label startErrLbl;

    @FXML
    private Label custErrorLbl;

    @FXML
    private Label locErrorLbl;

    @FXML
    private Label titleErrorLbl;

    @FXML
    private Label endErrLbl;

    @FXML
    private Label contErrLbl;

    @FXML
    private Label typeErrorLbl;

    @FXML
    private Label descriptionErrorLbl;

    public AppointmentsController() throws SQLException {
    }

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        changeView("MainMenu", event);
    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException, SQLException {
        if(appointmentValidation()) {
            if(appointment == null) {
                addAppointment();
            } else {
                modifyAppointment();
            }
            changeView("MainMenu", event);
        }
    }

    private void addAppointment() throws SQLException {
        String title = titleLbl.getText();
        String description = descriptionLbl.getText();
        String location = locationLbl.getText();
        String type = typeLbl.getText();
        LocalDateTime start = LocalDateTime.of(startDateBox.getValue(), startTimeBox.getValue());
        LocalDateTime end = LocalDateTime.of(endDateBox.getValue(), endTimeBox.getValue());
        String lastUpdatedBy = LogInController.user.getUserName();
        Customer customer = customerBox.getValue();
        User user = LogInController.user;
        Contact contact = contactBox.getValue();
        appointment = new Appointment(0, title, description, location, type, start, end,
        lastUpdatedBy, customer, user, contact);

        AppointmentImp.addAppointment(appointment);
    }

    private void modifyAppointment() throws SQLException {
        String title = titleLbl.getText();
        String description = descriptionLbl.getText();
        String location = locationLbl.getText();
        String type = typeLbl.getText();
        LocalDateTime start = LocalDateTime.of(startDateBox.getValue(), startTimeBox.getValue());
        LocalDateTime end = LocalDateTime.of(endDateBox.getValue(), endTimeBox.getValue());
        String lastUpdatedBy = LogInController.user.getUserName();
        Customer customer = customerBox.getValue();
        User user = LogInController.user;
        Contact contact = contactBox.getValue();

        appointment.setTitle(title);
        appointment.setDescription(description);
        appointment.setLocation(location);
        appointment.setType(type);
        appointment.setStart(start);
        appointment.setEnd(end);
        appointment.setLastUpdatedBy(lastUpdatedBy);
        appointment.setCustomer(customer);
        appointment.setUser(user);
        appointment.setContact(contact);

        AppointmentImp.modifyAppointment(appointment);
    }

    private boolean appointmentValidation() {
        boolean conditionsMet = true;

        apptIdLbl.setStyle(null);
        descriptionLbl.setStyle(null);
        locationLbl.setStyle(null);
        typeLbl.setStyle(null);
        customerBox.setStyle(null);
        contactBox.setStyle(null);
        startDateBox.setStyle(null);
        startTimeBox.setStyle(null);
        endDateBox.setStyle(null);
        endTimeBox.setStyle(null);

        startErrLbl.setVisible(false);
        custErrorLbl.setVisible(false);
        locErrorLbl.setVisible(false);
        titleErrorLbl.setVisible(false);
        endErrLbl.setVisible(false);
        contErrLbl.setVisible(false);
        typeErrorLbl.setVisible(false);
        descriptionErrorLbl.setVisible(false);



        if(titleLbl.getText().equals("")){
            titleLbl.setPromptText("Please Enter a Title");
            titleLbl.setStyle("-fx-border-color: #b22222; -fx-focus-color: #B22222; -fx-background-insets: 0;");
            titleErrorLbl.setVisible(true);
            conditionsMet = false;
        }
        if(descriptionLbl.getText().equals("")){
            descriptionLbl.setPromptText("Please Enter an Description");
            descriptionLbl.setStyle("-fx-border-color: #b22222; -fx-focus-color: #B22222; -fx-background-insets: 0;");
            descriptionErrorLbl.setVisible(true);
            conditionsMet = false;
        }
        if(locationLbl.getText().equals("")){
            locationLbl.setPromptText("Please Enter a Location");
            locationLbl.setStyle("-fx-border-color: #b22222; -fx-focus-color: #B22222; -fx-background-insets: 0;");
            locErrorLbl.setVisible(true);
            conditionsMet = false;
        }
        if(typeLbl.getText().equals("")) {
            typeLbl.setPromptText("Please Enter a Type");
            typeLbl.setStyle("-fx-border-color: #b22222; -fx-focus-color: #B22222; -fx-background-insets: 0;");
            typeErrorLbl.setVisible(true);
            conditionsMet = false;
        }
        if(customerBox.getValue() == null) {
            customerBox.setPromptText("Please Choose a Customer");
            customerBox.setStyle("-fx-border-color: #b22222; -fx-focus-color: #B22222; -fx-background-insets: 0;");
            custErrorLbl.setVisible(true);
            conditionsMet = false;
        }
        if(contactBox.getValue() == null) {
            contactBox.setPromptText("Please Choose a Contact");
            contactBox.setStyle("-fx-border-color: #b22222; -fx-focus-color: #B22222; -fx-background-insets: 0;");
            contErrLbl.setVisible(true);
            conditionsMet = false;
        }
        if(startDateBox.getValue() == null){
            startDateBox.setPromptText("Please Choose a Date");
            startDateBox.setStyle("-fx-border-color: #b22222; -fx-focus-color: #B22222; -fx-background-insets: 0;");
            startErrLbl.setVisible(true);
            conditionsMet = false;
        }
        if(startTimeBox.getValue() == null) {
            startTimeBox.setPromptText("Please Choose a Time");
            startTimeBox.setStyle("-fx-border-color: #b22222; -fx-focus-color: #B22222; -fx-background-insets: 0;");
            startErrLbl.setVisible(true);
            conditionsMet = false;
        }
        if(endDateBox.getValue() == null) {
            endDateBox.setPromptText("Please Choose a Date");
            endDateBox.setStyle("-fx-border-color: #b22222; -fx-focus-color: #B22222; -fx-background-insets: 0;");
            endErrLbl.setVisible(true);
            conditionsMet = false;
        }
        if(endTimeBox.getValue() == null) {
            endTimeBox.setPromptText("Please Choose a Time");
            endTimeBox.setStyle("-fx-border-color: #b22222; -fx-focus-color: #B22222; -fx-background-insets: 0;");
            endErrLbl.setVisible(true);
            conditionsMet = false;
        }

        return conditionsMet;
    }

    public void sendAppointment(Appointment appointment) {
        this.appointment = appointment;
        setModifyAppointment();
    }

    private void setModifyAppointment() {
        apptIdLbl.setText("Appointment ID - " + appointment.getAppointmentID());
        userIdLbl.setText("User ID - " + LogInController.user.getUserID());
        titleLbl.setText(appointment.getTitle());
        descriptionLbl.setText(appointment.getDescription());
        locationLbl.setText(appointment.getLocation());
        typeLbl.setText(appointment.getType());
        customerBox.setValue(appointment.getCustomer());
        contactBox.setValue(appointment.getContact());
        startDateBox.setValue(appointment.getStart().toLocalDate());
        startTimeBox.setValue(appointment.getStart().toLocalTime());
        endDateBox.setValue(appointment.getEnd().toLocalDate());
        endTimeBox.setValue(appointment.getEnd().toLocalTime());

    }
    private void changeView(String view, ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view_controller/" + view + ".fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.setTitle("Scheduling App");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            userIdLbl.setText("User ID - " + LogInController.user.getUserID());
            apptIdLbl.setText("Appointment ID - Auto Generated");
            customerBox.setItems(CustomerImp.getAllCustomers());
            contactBox.setItems(ContactImp.getAllContacts());
            LocalTime start = LocalTime.of(8,0);
            LocalTime end = LocalTime.of(22,0);
            while(start.isBefore(end.plusSeconds(1))) {
                startTimeBox.getItems().add(start);
                endTimeBox.getItems().add(start);
                start = start.plusMinutes(15);
           }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

