import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login");

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        Label userLabel = new Label("Username:");
        TextField userField = new TextField();
        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();
        Button loginButton = new Button("Login");
        Button regisButton = new Button("Register");

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(loginButton, regisButton);

        grid.add(userLabel, 0, 0);
        grid.add(userField, 1, 0);
        grid.add(passLabel, 0, 1);
        grid.add(passField, 1, 1);
        grid.add(buttonBox, 1, 2);

        loginButton.setOnAction(e -> {
            String username = userField.getText();
            String password = passField.getText();
            try {
                Connection connection = Database.getConnection();
                String sql = "SELECT id_user, role FROM user WHERE username = ? AND password = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int id_user = resultSet.getInt("id_user");
                    String role = resultSet.getString("role");
                    Session.setCurrentUserId(id_user);
                    Session.setCurrentUserRole(role);

                    if (role.equals("admin")) {
                        Admin admin = new Admin();
                        admin.start(new Stage());
                        primaryStage.close();
                    } else if (role.equals("user")) {
                        User user = new User();
                        user.start(new Stage());
                        primaryStage.close();
                    }
                } else {
                    showAlert("Login Failed", "Invalid username or password");
                }

                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Error", "An error occurred");
            }
        });

        regisButton.setOnAction(e -> {
            String username = userField.getText();
            String password = passField.getText();
            try {
                Connection connection = Database.getConnection();
                String checkSql = "SELECT COUNT(*) FROM user WHERE username = ?";
                PreparedStatement checkStatement = connection.prepareStatement(checkSql);
                checkStatement.setString(1, username);
                ResultSet checkResult = checkStatement.executeQuery();
                checkResult.next();
                if (checkResult.getInt(1) > 0) {
                    showAlert("Registrasi Gagal", "Username sudah ada");
                } else {
                    String insertSql = "INSERT INTO user (username, password, role) VALUES (?, ?, 'user')";
                    PreparedStatement insertStatement = connection.prepareStatement(insertSql);
                    insertStatement.setString(1, username);
                    insertStatement.setString(2, password);
                    insertStatement.executeUpdate();
                    showAlert("Registrasi sukses", "Kamu sudah berhasil daftar sebagai user");
                }

                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Error", "An error occurred");
            }
        });

        loginButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;"));

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
