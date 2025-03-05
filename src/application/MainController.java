package application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

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
  private Button btnDropDB;
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
    // Инициализируем ChoiceBox для ролей
    roleChoiceBox.setItems(FXCollections.observableArrayList("admin", "guest"));
    roleChoiceBox.setValue("guest");
    // Заполняем таблицу данными
    refreshTable();
  }

  @FXML
  private void refreshTable() {
    List<Framework> list = dbService.getAllFrameworks();
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
    logArea.appendText("Добавлена запись: " + name + "\n");
    refreshTable();
  }

  @FXML
  private void handleUpdate() {
    String oldName = updateOldNameField.getText();
    String newName = updateNewNameField.getText();
    String newType = updateNewTypeField.getText();
    String newDesc = updateNewDescField.getText();
    dbService.updateFramework(oldName, newName, newType, newDesc);
    logArea.appendText("Обновлена запись: " + oldName + "\n");
    refreshTable();
  }

  @FXML
  private void handleDelete() {
    String name = deleteNameField.getText();
    dbService.deleteFramework(name);
    logArea.appendText("Удалена запись: " + name + "\n");
    refreshTable();
  }

  @FXML
  private void handleCreateDB() {
    dbService.createDatabase();
    logArea.appendText("Создана база данных\n");
  }

  @FXML
  private void handleDropDB() {
    dbService.deleteDatabase();
    logArea.appendText("База данных удалена\n");
  }

  @FXML
  private void handleClearTable() {
    dbService.clearTable();
    logArea.appendText("Таблица очищена\n");
    refreshTable();
  }

  @FXML
  private void handleCreateUser() {
    String newUsername = newUserField.getText();
    String newPassword = newUserPassField.getText();
    String role = roleChoiceBox.getValue();
    dbService.createUser(newUsername, newPassword, role);
    logArea.appendText("Создан пользователь: " + newUsername + " с ролью: " + role + "\n");
  }

  @FXML
  private void handleRefresh() {
    refreshTable();
    logArea.appendText("Данные обновлены\n");
  }
}
