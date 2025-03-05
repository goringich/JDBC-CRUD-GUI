package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;

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
      // Загрузка Main.fxml
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
      Parent root = loader.load();
      MainController controller = loader.getController();
      controller.setDBService(dbService);

      Stage stage = (Stage) usernameField.getScene().getWindow();
      stage.setScene(new Scene(root));
      stage.setTitle("Frontend DB - Admin/Guest");
    } catch (Exception e) {
      e.printStackTrace();
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Ошибка авторизации");
      alert.setHeaderText("Неверные учётные данные или ошибка подключения");
      alert.setContentText(e.getMessage());
      alert.showAndWait();
    }
  }

  @FXML
  private void handleBypassLogin() {
    // Например, логин guest
    DBService dbService = new DBService("guest_user", "guest123");

    try (Connection conn = dbService.getConnection()) {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
      Parent root = loader.load();
      MainController controller = loader.getController();
      controller.setDBService(dbService);

      Stage stage = (Stage) usernameField.getScene().getWindow();
      stage.setScene(new Scene(root));
      stage.setTitle("Frontend DB - Guest Bypass");
    } catch (Exception e) {
      e.printStackTrace();
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Ошибка гостевого входа");
      alert.setHeaderText("Невозможно войти как гость");
      alert.setContentText(e.getMessage());
      alert.showAndWait();
    }
  }
}
