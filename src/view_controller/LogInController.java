package view_controller;

import dao.UserImp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.User;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * This is the controller for the Login.fxml file.
 */
// TODO = JAVA doc
// TODO = Language
public class LogInController implements Initializable {

    public static User user;
    @FXML
    private TextField userNameTextField;

    @FXML
    private Label zoneID;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    void onActionLogin(ActionEvent event) throws IOException {
        if(userAuthentication()) {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/view_controller/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.centerOnScreen();
            stage.setTitle("Scheduling App");
            stage.show();
        }
    }
    // TODO = Case sensitive.
    private boolean userAuthentication() throws FileNotFoundException {
        
        String userName = userNameTextField.getText();
        String password = passwordTextField.getText();

        if(userName.equals("") & password.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter Username and Password");
            alert.showAndWait();
            return false;
        } else if(userName.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter Username");
            alert.showAndWait();
            return false;
        } else if(password.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter Password");
            alert.showAndWait();
            return false;
        } else {
            try {
                user = UserImp.getUser(userNameTextField.getText());
                if (userName.equals(user.getUserName()) && password.equals(user.getPassword())) {
                    // TODO = Reminders
                    userLog(user);
                    return true;
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect Password");
                    alert.showAndWait();
                    return false;
                }
            } catch (NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Username not found");
                alert.showAndWait();
                return false;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }
    private void userLog(User user) {
        String fileName = "src/files/log.txt";
        try {

            // Create FileWriter and PrintWriter
            FileWriter fileWriter = new FileWriter(fileName, true);
            PrintWriter outFile = new PrintWriter(fileWriter);
            // TODO = Get consistant time
            Instant instant = Instant.now();
            outFile.println(user.getUserName() + ": " + instant + " -UTC");
            // Close PrintWriter
            outFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
// TODO = Zone Identification
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
