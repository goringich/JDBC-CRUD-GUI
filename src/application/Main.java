package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Main.fxml"));
    Parent root = loader.load();
    
    // Создаём объект базы данных
    DBService dbService = new DBService("admin_user", "admin123");

    // Получаем контроллер и устанавливаем DBService до вызова refreshTable()
    MainController controller = loader.getController();
    controller.setDBService(dbService); // ← ВАЖНО!

    primaryStage.setScene(new Scene(root));
    primaryStage.setTitle("Система управления БД");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
