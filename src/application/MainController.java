package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MainController {
  private DBService dbService;

  @FXML
  private TabPane tabPane;
  @FXML
  private TableView<Framework> frameworksTable;
  @FXML
  private TableColumn<Framework, Integer> colId;
  @FXML
  private TableColumn<Framework, String> colName;
  @FXML
  private TableColumn<Framework, String> colType;
  @FXML
  private TableColumn<Framework, String> colDescription;
  @FXML
  private TableColumn<Framework, String> colCreatedAt;

  @FXML
  private TextField searchField;
  @FXML
  private Button btnSearch;
  @FXML
  private Button btnRefresh;

  @FXML
  private TextField addNameField;
  @FXML
  private TextField addTypeField;
  @FXML
  private TextArea addDescField;
  @FXML
  private Button btnAdd;

  @FXML
  private TextField updateOldNameField;
  @FXML
  private TextField updateNewNameField;
  @FXML
  private TextField updateNewTypeField;
  @FXML
  private TextArea updateNewDescField;
  @FXML
  private Button btnUpdate;

  @FXML
  private TextField deleteNameField;
  @FXML
  private Button btnDelete;

  @FXML
  private Button btnCreateDB;
  @FXML
  private Button btnClearTable;

  @FXML
  private TextField newUserField;
  @FXML
  private PasswordField newUserPassField;
  @FXML
  private ChoiceBox<String> roleChoiceBox;
  @FXML
  private Button btnCreateUser;

  @FXML
  private TextArea logArea;

  public void setDBService(DBService dbService) {
    this.dbService = dbService;
    roleChoiceBox.setItems(FXCollections.observableArrayList("admin", "guest"));
    roleChoiceBox.setValue("guest");
    refreshTable();
  }

  @FXML
  public void initialize() {
    System.out.println("Initializing TableView columns...");

    // Привязка колонок
    colId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
    colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
    colType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
    colDescription.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
    colCreatedAt.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreatedAt().toString()));

    System.out.println("TableView columns initialized.");
  }


  
  @FXML
  private void refreshTable() {
    if (dbService == null) {
      System.err.println("Error: DBService is null!");
      return;
    }

    System.out.println("Refreshing table...");
    List<Framework> list = dbService.getAllFrameworks();

    if (list.isEmpty()) {
      System.out.println("No data to display in TableView.");
    } else {
      System.out.println("Updating TableView with " + list.size() + " items.");
    }

    ObservableList<Framework> data = FXCollections.observableArrayList(list);
    frameworksTable.setItems(data);
  }
  

  @FXML
  private void handleSearch() {
    String key = searchField.getText();
    List<Framework> list = dbService.searchFramework(key);
    ObservableList<Framework> data = FXCollections.observableArrayList(list);
    frameworksTable.setItems(data);
  }

  @FXML
  private void handleAdd() {
    String name = addNameField.getText();
    String type = addTypeField.getText();
    String desc = addDescField.getText();
    dbService.addFramework(name, type, desc);
    logArea.appendText("Record added: " + name + "\n");
    refreshTable();
  }

  @FXML
  private void handleUpdate() {
    String oldName = updateOldNameField.getText();
    String newName = updateNewNameField.getText();
    String newType = updateNewTypeField.getText();
    String newDesc = updateNewDescField.getText();
    int count = dbService.updateFramework(oldName, newName, newType, newDesc);
    logArea.appendText("Record updated: " + oldName + " (" + count + " row(s) affected)\n");
    refreshTable();
  }

  @FXML
  private void handleDelete() {
    String name = deleteNameField.getText();
    dbService.deleteFramework(name);
    logArea.appendText("Record deleted: " + name + "\n");
    refreshTable();
  }

  @FXML
  private void handleCreateDB() {
    dbService.createDatabase();
    logArea.appendText("Database created\n");
  }

  @FXML
  private void handleClearTable() {
    dbService.clearTable();
    logArea.appendText("Table cleared\n");
    refreshTable();
  }

  @FXML
  private void handleRefresh() {
    refreshTable();
    logArea.appendText("Data refreshed\n");
  }

  @FXML
  private void handleCreateUser() {
    String newUsername = newUserField.getText();
    String newPassword = newUserPassField.getText();
    String role = roleChoiceBox.getValue();
    dbService.createUser(newUsername, newPassword, role);
    logArea.appendText("User created: " + newUsername + " with role: " + role + "\n");
  }
}
