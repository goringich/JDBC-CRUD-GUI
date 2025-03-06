package application;

import java.sql.*;

public class TestDB {
  public static void main(String[] args) {
    String url = "jdbc:postgresql://localhost:5432/sql_laba";
    String username = "admin_user";
    String password = "admin123";

    try (Connection conn = DriverManager.getConnection(url, username, password)) {
      System.out.println("Connected as " + username);
      Statement st = conn.createStatement();
      // 1) Show current user and DB:
      ResultSet r1 = st.executeQuery("SELECT current_user, current_database()");
      while (r1.next()) {
        System.out.println("User: " + r1.getString(1) + ", DB: " + r1.getString(2));
      }
      // 2) Try selecting from frameworks:
      ResultSet r2 = st.executeQuery("SELECT * FROM frameworks");
      int count = 0;
      while (r2.next()) {
        count++;
        System.out.println("Row: id=" + r2.getInt("id") + ", name=" + r2.getString("name"));
      }
      ResultSet r3 = st.executeQuery("SELECT * FROM pg_tables WHERE schemaname = 'public'");
      while (r3.next()) {
        System.out.println("Found table: " + r3.getString("tablename"));
      }
      System.out.println("Total rows in frameworks: " + count);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
