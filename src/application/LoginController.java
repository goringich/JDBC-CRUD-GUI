import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.sql.*;


public class LoginController {
  @FXML
  private TextField usernameField;
  @FXML
  private PasswordField passwordField;

  @FXML
  private void handleLogin() {
    String username = usernameField.getText().trim();
    String password = passwordField.getText().trim();
    System.out.println("Введён логин: " + username + " | Пароль: " + password); // DEBUG

    DBService dbService = new DBService(username, password);

    try (Connection conn = dbService.getConnection()) {
      System.out.println("Успешное подключение!");

      FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
      Parent root = loader.load();
      MainController controller = loader.getController();
      controller.setDBService(dbService);

      Stage stage = (Stage) usernameField.getScene().getWindow();
      stage.setScene(new Scene(root));
    } catch (Exception e) {
      e.printStackTrace();
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Ошибка входа");
      alert.setHeaderText("Неверные учётные данные или ошибка подключения");
      alert.setContentText("Проверьте логин и пароль.");
      alert.showAndWait();
    }
  }

}
