import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class LoginController {
  @FXML
  private TextField usernameField;
  @FXML
  private PasswordField passwordField;

  @FXML
  private void handleLogin() {
    String username = usernameField.getText();
    String password = passwordField.getText();
    DBService dbService = new DBService(username, password);
    // Попытка установить соединение (connection (соединение))
    try (java.sql.Connection conn = dbService.getConnection()) {
      // Если соединение успешно, открываем главное окно
      FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
      Parent root = loader.load();
      MainController controller = loader.getController();
      controller.setDBService(dbService);
      Stage stage = (Stage) usernameField.getScene().getWindow();
      stage.setScene(new Scene(root));
    } catch (Exception e) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Ошибка");
      alert.setHeaderText("Неверные учётные данные");
      alert.setContentText("Проверьте имя пользователя и пароль");
      alert.showAndWait();
    }
  }
}
