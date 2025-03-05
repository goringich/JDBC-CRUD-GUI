package application;  

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*; 

public class Main extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));

    primaryStage.setScene(new Scene(root));
    primaryStage.setTitle("Система управления БД");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

  public class TestDB {
    public static void main(String[] args) {
      String url = "jdbc:postgresql://localhost:5432/sql_laba";
      String username = "admin_user"; 
      String password = "admin123";  
  
      try (Connection conn = DriverManager.getConnection(url, username, password)) {
        System.out.println("Подключение успешно!");
      } catch (SQLException e) {
        System.out.println("Ошибка подключения!");
        e.printStackTrace();
      }
    }
  }
  
}
