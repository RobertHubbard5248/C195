package view_controller;

import dao.CountryImp;
import dao.CustomerImp;
import dao.FirstLevelDivisionImp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static view_controller.LogInController.user;

// TODO = Create method for loader
public class CustomerController implements Initializable {

    Stage stage;
    Parent scene;

    Customer customer;
    ObservableList<Country> allCountries = CountryImp.getAllCountries();
    ObservableList<FirstLevelDivision> divisionsByCountry = FXCollections.observableArrayList();

    @FXML
    private TextField idTextField;

    @FXML
    private TextField userIDTextField;

    @FXML
    private TextField name;

    @FXML
    private TextField address;

    @FXML
    private TextField postalCode;

    @FXML
    private ComboBox<Country> country;

    @FXML
    private ComboBox<FirstLevelDivision> firstLevelDiv;

    @FXML
    private TextField phoneNumber;

    @FXML
    private Label nameErrorLbl;

    @FXML
    private Label addressErrorLbl;

    @FXML
    private Label countryErrorLbl;

    @FXML
    private Label phoneErrorLbl;

    @FXML
    private Label postalErrorLbl;

    @FXML
    private Label divisionErrorLbl;

    public CustomerController() throws SQLException {

    }

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        changeView("MainMenu", event);
    }

    @FXML
    void onActionCountryCombo(ActionEvent event) throws SQLException {
        firstLevelDiv.setItems(FirstLevelDivisionImp.getDivisionsByCountry(country.getValue()));
    }
    // TODO = Error handling
    @FXML
    void onActionSave(ActionEvent event) throws SQLException, IOException {
        if(customerValidation()) {
            if (customer == null) {
                addCustomer();
            } else {
                updateCustomer();
            }

            changeView("MainMenu", event);
        }
    }

    public void sendCustomer(Customer customer) throws SQLException {
        this.customer = customer;
        setCustomerForm();
    }

    private void addCustomer() throws SQLException {
        Customer customer = new Customer(0, name.getText(), address.getText(), postalCode.getText(),
                phoneNumber.getText(), user.getUserName(), user.getUserName(),
                firstLevelDiv.getValue().getDivisionID(), country.getValue().getCountryID());

        CustomerImp.addCustomer(customer);
    }

    private void updateCustomer() throws SQLException {
        customer.setCustomerName(name.getText());
        customer.setAddress(address.getText());
        customer.setPostalCode(postalCode.getText());
        customer.setPhoneNumber(phoneNumber.getText());
        customer.setLastUpdatedBy(user.getUserName());
        customer.setDivisionID(firstLevelDiv.getValue().getDivisionID());
        customer.setCountryID(country.getValue().getCountryID());
        CustomerImp.updateCustomer(customer);
    }

    // Search method(s)
    private FirstLevelDivision searchFirstLevelDivision (int id, ObservableList<FirstLevelDivision> allDivisions) {
        for(FirstLevelDivision d : allDivisions) {
            if(d.getDivisionID() == id)
                return d;
        }
        return null;
    }

    private Country searchCountryByID (int id) {
        for(Country c : allCountries) {
            if (c.getCountryID() == id)
                return c;
        }
        return null;
    }

    private boolean customerValidation() {
        boolean conditionMet = true;

        name.setStyle("");
        phoneNumber.setStyle("");
        address.setStyle("");
        postalCode.setStyle("");
        country.setStyle("");
        firstLevelDiv.setStyle("");

        nameErrorLbl.setVisible(false);
        phoneErrorLbl.setVisible(false);
        addressErrorLbl.setVisible(false);
        postalErrorLbl.setVisible(false);
        countryErrorLbl.setVisible(false);
        divisionErrorLbl.setVisible(false);

        if(name.getText().equals("")) {
            name.setPromptText("Please Enter a Name");
            name.setStyle("-fx-border-color: #b22222; -fx-focus-color: #B22222; -fx-background-insets: 0;");
            nameErrorLbl.setVisible(true);
            conditionMet = false;
        }
        if(phoneNumber.getText().equals("")) {
            phoneNumber.setPromptText("Please Enter a Phone Number");
            phoneNumber.setStyle("-fx-border-color: #b22222; -fx-focus-color: #B22222; -fx-background-insets: 0;");
            phoneErrorLbl.setVisible(true);
            conditionMet = false;
        }
        if(address.getText().equals("")) {
            address.setPromptText("Please Enter an Address");
            address.setStyle("-fx-border-color: #b22222; -fx-focus-color: #B22222; -fx-background-insets: 0;");
            addressErrorLbl.setVisible(true);
            conditionMet = false;
        }
        if(postalCode.getText().equals("")) {
            postalCode.setPromptText("Please Enter a Postal Code");
            postalCode.setStyle("-fx-border-color: #b22222; -fx-focus-color: #B22222; -fx-background-insets: 0;");
            postalErrorLbl.setVisible(true);
            conditionMet = false;
        }
        if(country.getValue() == null) {
            country.setPromptText("Please Pick a Country");
            country.setStyle("-fx-border-color: #b22222; -fx-focus-color: #B22222; -fx-background-insets: 0;");
            countryErrorLbl.setVisible(true);
            conditionMet = false;
        }
        if(firstLevelDiv.getValue() == null) {
            firstLevelDiv.setPromptText("Please Pick a First Level Division");
            firstLevelDiv.setStyle("-fx-border-color: #b22222; -fx-focus-color: #B22222; -fx-background-insets: 0;");
            divisionErrorLbl.setVisible(true);
            conditionMet = false;
        }

        return conditionMet;
    }

    private void changeView(String view, ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view_controller/" + view + ".fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.setTitle("Scheduling App");
        stage.show();
    }

    private void setCustomerForm() throws SQLException {
        name.setText(customer.getCustomerName());
        address.setText(customer.getAddress());
        postalCode.setText(customer.getPostalCode());
        phoneNumber.setText(customer.getPhoneNumber());
        country.setValue(searchCountryByID((customer.getCountryID())));
        firstLevelDiv.setItems(FirstLevelDivisionImp.getDivisionsByCountry(country.getValue()));
        firstLevelDiv.setValue(searchFirstLevelDivision(customer.getDivisionID(), firstLevelDiv.getItems()));
        if(customer != null) {
            idTextField.setText("Customer ID: " + Integer.toString(customer.getCustomerID()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userIDTextField.setText("User ID - " + String.valueOf(LogInController.user.getUserID()));
        country.setItems(allCountries);
    }
}
