package application;

import java.sql.*;

public class TestDB {
  public static void main(String[] args) {
    String url = "jdbc:postgresql://localhost:5432/sql-laba5";
    String username = "guest_user";
    String password = "guest123";

    try (Connection conn = DriverManager.getConnection(url, username, password)) {
      System.out.println("✅ Подключение успешно!");
    } catch (SQLException e) {
      System.out.println("❌ Ошибка подключения!");
      e.printStackTrace();
    }
  }
}
