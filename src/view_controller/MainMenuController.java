package view_controller;

import dao.AppointmentImp;
import dao.CustomerImp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableView<Appointment> appointmentsTableView;

    @FXML
    private TableColumn<Appointment, Integer> apptID;

    @FXML
    private TableColumn<Appointment, String> apptTitle;

    @FXML
    private TableColumn<Appointment, String> apptDesc;

    @FXML
    private TableColumn<Appointment, String> apptLoc;

    @FXML
    private TableColumn<Appointment, LocalDateTime> apptStrt;

    @FXML
    private TableColumn<Appointment, LocalDateTime> apptEnd;

    @FXML
    private TableColumn<Appointment, String> apptCust;

    @FXML
    private TableColumn<Appointment, String> apptUser;

    @FXML
    private TableColumn<Appointment, String> apptCont;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, Integer> customerID;

    @FXML
    private TableColumn<Customer, String> customerName;

    @FXML
    private TableColumn<Customer, String> customerAddress;

    @FXML
    private TableColumn<Customer, String> customerPhone;

    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {
        changeView("Appointments", event);
    }

    @FXML
    void onActionDeleteAppointment(ActionEvent event) {
        try {
            AppointmentImp.deleteAppointment(appointmentsTableView.getSelectionModel().getSelectedItem());
            setAppointmentTableView();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select an appointment");
            alert.showAndWait();
        }
    }

    @FXML
    void onActionModifyAppointment(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view_controller/Appointments.fxml"));
        loader.load();
        AppointmentsController appointmentsController = loader.getController();
        try {
            appointmentsController.sendAppointment(appointmentsTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.centerOnScreen();
            stage.setTitle("Scheduling App");
            stage.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select an appointment.");
            alert.showAndWait();
        }
    }

    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        changeView("Customers", event);
    }

    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws SQLException {
        try {
            Customer customer = customerTableView.getSelectionModel().getSelectedItem();
            if(AppointmentImp.customerHasAppointments(customer)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(customer.getCustomerName() + " is assigned to at least one appointment.  " +
                        "Please delete any associated appointments before deleting this customer.");
                alert.showAndWait();
            } else {
                CustomerImp.deleteCustomer(customer);
                setCustomerTableView();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Customer: " + customer.getCustomerName() + " has been deleted.");
                alert.showAndWait();
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a customer");
            alert.showAndWait();
        }
    }

    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException, SQLException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view_controller/Customers.fxml"));
            loader.load();
            CustomerController customerController = loader.getController();
            customerController.sendCustomer(customerTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.centerOnScreen();
            stage.setTitle("Scheduling App");
            stage.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a customer");
            alert.showAndWait();
        }
    }

    @FXML
    void onActionLogOut(ActionEvent event) throws IOException {
        changeView("LogIn", event);
    }

    private void setCustomerTableView() {
        try {
            customerTableView.setItems(CustomerImp.getAllCustomers());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    }
// TODO = Change time format
    private void setAppointmentTableView() {
        try {
            appointmentsTableView.setItems(AppointmentImp.getAllAppointments());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        apptID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLoc.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptStrt.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptCust.setCellValueFactory(new PropertyValueFactory<>("customer"));
        apptUser.setCellValueFactory(new PropertyValueFactory<>("user"));
        apptCont.setCellValueFactory(new PropertyValueFactory<>("contact"));
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
        setAppointmentTableView();
        setCustomerTableView();
    }
}
