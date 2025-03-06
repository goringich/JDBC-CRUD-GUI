package application;

import java.sql.Timestamp;

public class Framework {
  private int id;
  private String name;
  private String type;
  private String description;
  private Timestamp createdAt;

  public Framework(int id, String name, String type, String description, Timestamp createdAt) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.description = description;
    this.createdAt = createdAt;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public String getDescription() {
    return description;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }
}
