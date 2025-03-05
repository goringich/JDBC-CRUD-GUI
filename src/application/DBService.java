import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBService {
  private final String url = "jdbc:postgresql://localhost:5432/sql-laba5";
  private String username;
  private String password;

  public DBService(String username, String password) {
    System.out.println("🔍 Логин: " + username + " | Пароль: " + password); // DEBUG
    this.username = username;
    this.password = password;
  }

  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(url, username, password);
  }

  // Создание таблицы (хранимая процедура)
  public void createTable() {
    try (Connection conn = getConnection();
         CallableStatement stmt = conn.prepareCall("{call create_table()}")) {
      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // Очистка таблицы
  public void clearTable() {
    try (Connection conn = getConnection();
         CallableStatement stmt = conn.prepareCall("{call clear_table()}")) {
      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // Добавление новой записи
  public void addFramework(String name, String type, String description) {
    try (Connection conn = getConnection();
         CallableStatement stmt = conn.prepareCall("{call add_framework(?, ?, ?)}")) {
      stmt.setString(1, name);
      stmt.setString(2, type);
      stmt.setString(3, description);
      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // Поиск записей по name
  public List<Framework> searchFramework(String name) {
    List<Framework> list = new ArrayList<>();
    try (Connection conn = getConnection();
         CallableStatement stmt = conn.prepareCall("{? = call search_framework(?)}")) {
      stmt.registerOutParameter(1, Types.OTHER);
      stmt.setString(2, name);
      stmt.execute();
      ResultSet rs = (ResultSet) stmt.getObject(1);
      while (rs.next()) {
        list.add(new Framework(
          rs.getInt("id"),
          rs.getString("name"),
          rs.getString("type"),
          rs.getString("description"),
          rs.getTimestamp("created_at")
        ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  // Обновление записи
  public void updateFramework(String oldName, String newName, String newType, String newDescription) {
    try (Connection conn = getConnection();
         CallableStatement stmt = conn.prepareCall("{call update_framework(?, ?, ?, ?)}")) {
      stmt.setString(1, oldName);
      stmt.setString(2, newName);
      stmt.setString(3, newType);
      stmt.setString(4, newDescription);
      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // Удаление записи по name
  public void deleteFramework(String name) {
    try (Connection conn = getConnection();
         CallableStatement stmt = conn.prepareCall("{call delete_framework(?)}")) {
      stmt.setString(1, name);
      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // Получение всех записей
  public List<Framework> getAllFrameworks() {
    List<Framework> list = new ArrayList<>();
    try (Connection conn = getConnection();
         CallableStatement stmt = conn.prepareCall("{? = call get_all_frameworks()}")) {
      stmt.registerOutParameter(1, Types.OTHER);
      stmt.execute();
      ResultSet rs = (ResultSet) stmt.getObject(1);
      while (rs.next()) {
        list.add(new Framework(
          rs.getInt("id"),
          rs.getString("name"),
          rs.getString("type"),
          rs.getString("description"),
          rs.getTimestamp("created_at")
        ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  // Создание нового пользователя базы данных
  public void createUser(String newUsername, String newPassword, String role) {
    try (Connection conn = getConnection();
         CallableStatement stmt = conn.prepareCall("{call create_user(?, ?, ?)}")) {
      stmt.setString(1, newUsername);
      stmt.setString(2, newPassword);
      stmt.setString(3, role);
      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // Дополнительные функции для администрирования базы
  public void createDatabase() {
    try (Connection conn = getConnection();
         CallableStatement stmt = conn.prepareCall("{call create_database()}")) {
      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void deleteDatabase() {
    try (Connection conn = getConnection();
         CallableStatement stmt = conn.prepareCall("{call delete_database()}")) {
      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
