import dao.CustomerImp;
import dao.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalTime;

// TODO = JAVA Docs
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
    //TODO
        // Implement ZoneID initialization in Login
        Parent root = FXMLLoader.load(getClass().getResource("/view_controller/LogIn.fxml"));
        primaryStage.setTitle("Log In");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) throws SQLException, FileNotFoundException {

        DBConnection.startConnection();


        //System.out.println(CustomerImp.getAllCustomers());
/*        String insertStatement = "Insert INTO countries (Country, Create_Date, Created_By, Last_Updated_By) " +
                "Values (?, ?, ?, ?)";
        Query.setPreparedStatement(connection, insertStatement);
        PreparedStatement preparedStatement = Query.getPreparedStatement();

        String countryName;
        String createDate = "2021-04-14 00:00:00";
        String createdBy = "admin";
        String lastUpdatedBy = "admin";

        Scanner scanner = new Scanner(System.in);
        countryName = scanner.nextLine();

        // Key-Value Mapping
        preparedStatement.setString(1, countryName);
        preparedStatement.setString(2, createDate);
        preparedStatement.setString(3, createdBy);
        preparedStatement.setString(4, lastUpdatedBy);

        preparedStatement.execute();*/

        // Time Combo Box
        /*LocalTime start = LocalTime.of(6, 0);
        LocalTime end = LocalTime.of(12, 0);
        while(start.isBefore(end.plusSeconds(1))) {
            combo.getItems().add(start);
            start = start.plusMinutes(15);
        }*/
        launch(args);
        DBConnection.closeConnection();
    }
}
