package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.sql.*; 

import application.DBService;
import application.MainController;


public class LoginController {
  @FXML
  private TextField usernameField;
  @FXML
  private PasswordField passwordField;

  @FXML
  private void handleLogin() {
    String username = usernameField.getText().trim();
    String password = passwordField.getText().trim();
    DBService dbService = new DBService(username, password);
    try (Connection conn = dbService.getConnection()) {
      // Connection successful, load main window
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
      Parent root = loader.load();
      MainController controller = loader.getController();
      controller.setDBService(dbService);
      Stage stage = (Stage) usernameField.getScene().getWindow();
      stage.setScene(new Scene(root));
    } catch (Exception e) {
      System.err.println("Login error: ");
      e.printStackTrace();
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText("Unable to log in");
      alert.setContentText("Please check your credentials and try again.");
      alert.showAndWait();
    }
  }

  @FXML
  private void handleBypassLogin() {
    // Using preset guest credentials
    DBService dbService = new DBService("guest_user", "guest123");
    try (Connection conn = dbService.getConnection()) {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
      Parent root = loader.load();
      MainController controller = loader.getController();
      controller.setDBService(dbService);
      Stage stage = (Stage) usernameField.getScene().getWindow();
      stage.setScene(new Scene(root));
    } catch (Exception e) {
      System.err.println("Bypass login error: ");
      e.printStackTrace();
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText("Unable to log in");
      alert.setContentText("Please check your credentials and try again.");
      alert.showAndWait();
    }
  }

}
