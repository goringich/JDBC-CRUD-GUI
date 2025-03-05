package application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBService {
  private final String url = "jdbc:postgresql://localhost:5432/sql_laba";
  private String username;
  private String password;

  public DBService(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(url, username, password);
  }

  public void createTable() {
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement("CALL create_table()")) {
      stmt.execute();
    } catch (SQLException e) {
      System.err.println("Error createTable: " + e.getMessage());
    }
  }

  public void clearTable() {
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement("CALL clear_table()")) {
      stmt.execute();
    } catch (SQLException e) {
      System.err.println("Error clearTable: " + e.getMessage());
    }
  }

  public void addFramework(String name, String type, String description) {
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement("CALL add_framework(?, ?, ?)")) {
      stmt.setString(1, name);
      stmt.setString(2, type);
      stmt.setString(3, description);
      stmt.execute();
    } catch (SQLException e) {
      System.err.println("Error addFramework: " + e.getMessage());
    }
  }

  public List<Framework> searchFramework(String name) {
    List<Framework> list = new ArrayList<>();
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement("SELECT * FROM search_framework(?)")) {
      stmt.setString(1, name);
      ResultSet rs = stmt.executeQuery();
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
      System.err.println("Error searchFramework: " + e.getMessage());
    }
    return list;
  }

  public int updateFramework(String oldName, String newName, String newType, String newDescription) {
    int updated = 0;
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement("SELECT update_framework(?, ?, ?, ?)")) {
      stmt.setString(1, oldName);
      stmt.setString(2, newName);
      stmt.setString(3, newType);
      stmt.setString(4, newDescription);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        updated = rs.getInt(1);
      }
      System.out.println("Update count: " + updated);
    } catch (SQLException e) {
      System.err.println("Error updateFramework: " + e.getMessage());
    }
    return updated;
  }

  public void deleteFramework(String name) {
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement("CALL delete_framework(?)")) {
      stmt.setString(1, name);
      stmt.execute();
    } catch (SQLException e) {
      System.err.println("Error deleteFramework: " + e.getMessage());
    }
  }

  public List<Framework> getAllFrameworks() {
    List<Framework> list = new ArrayList<>();
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement("SELECT * FROM get_all_frameworks()")) {
      ResultSet rs = stmt.executeQuery();
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
      System.err.println("Error getAllFrameworks: " + e.getMessage());
    }
    return list;
  }

  public void createUser(String newUsername, String newPassword, String role) {
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement("CALL create_user(?, ?, ?)")) {
      stmt.setString(1, newUsername);
      stmt.setString(2, newPassword);
      stmt.setString(3, role);
      stmt.execute();
    } catch (SQLException e) {
      System.err.println("Error createUser: " + e.getMessage());
    }
  }

  public void createDatabase() {
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement("CALL create_database()")) {
      stmt.execute();
    } catch (SQLException e) {
      System.err.println("Error createDatabase: " + e.getMessage());
    }
  }

  public void deleteDatabase() {
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement("CALL delete_database()")) {
      stmt.execute();
    } catch (SQLException e) {
      System.err.println("Error deleteDatabase: " + e.getMessage());
    }
  }
}
