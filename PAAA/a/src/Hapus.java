import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Hapus extends Application {

    

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Admin Dashboard");

        // Create a MenuBar
        MenuBar menuBar = new MenuBar();

        // Create Menus
        Menu berandaMenu = new Menu("Beranda");
        Menu dataMenu = new Menu("Data");
        Menu accountMenu = new Menu("Account");

        MenuItem berandaItem = new MenuItem("Beranda");

        berandaMenu.getItems().add(berandaItem);

        // Create MenuItems for Data menu
        MenuItem tambahDataItem = new MenuItem("Tambah Data");
        MenuItem updeteDataItem = new MenuItem("Updete Data");
        MenuItem lihatDataItem = new MenuItem("Lihat Data");

        // Add MenuItems to the Data menu
        dataMenu.getItems().addAll(tambahDataItem, updeteDataItem, lihatDataItem);

        // Create MenuItems for Account menu
        MenuItem logoutItem = new MenuItem("Logout");

        // Add MenuItems to the Account menu
        accountMenu.getItems().addAll(logoutItem);

        // Add Menus to the MenuBar
        menuBar.getMenus().addAll(berandaMenu, dataMenu, accountMenu);

        // VBox layout with padding and spacing
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(10);
        vbox.setStyle("-fx-background-color: #2c3e50;");

        // Welcome Label
        Label welcomeLabel = new Label("UPDETE DATA");
        welcomeLabel.setFont(new Font("Arial", 24));
        welcomeLabel.setTextFill(Color.WHITESMOKE);

        // Additional Label
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Create input fields

        Label idLabel = new Label("ID:");
        idLabel.setFont(new Font("Times New Roman", 14));
        idLabel.setTextFill(Color.WHITESMOKE);
        TextField idField = new TextField();



        

        // Add input fields to GridPane
        gridPane.add(idLabel, 0, 0);
        gridPane.add(idField, 1, 0);
        
        // Create Save button
        Button deleteButton = new Button("DELETE");
        deleteButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        deleteButton.setOnMouseEntered(e -> deleteButton.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;"));
        deleteButton.setOnMouseExited(e -> deleteButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;"));

        // Add Save button to GridPane
        gridPane.add(deleteButton, 1, 1);

        // Add event handler for Save button
        deleteButton.setOnAction(e -> {
            String id = idField.getText();

            try {
                Connection connection = Database.getConnection();
                String sql = "DELETE FROM tbtiket WHERE id_tiket = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, id);
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    showAlert("Sukses", "Data tiket berhasil dihapus!");
                } else {
                    showAlert("Error", "Data tiket dengan ID tersebut tidak ditemukan!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                showAlert("Error", "Terjadi kesalahan saat menghapus data!");
            }
        });

        // Adding all elements to VBox
        vbox.getChildren().addAll(welcomeLabel, gridPane);

        // Create a BorderPane to hold the MenuBar and VBox
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(vbox);

        // Setting the scene
        Scene scene = new Scene(borderPane, 350, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Add event handling for MenuItems
        
        
        lihatDataItem.setOnAction(e -> showAlert("Lihat Data", "Functionality to view data goes here"));

        berandaItem.setOnAction(e -> {
            primaryStage.close();
            Admin admin = new Admin();
            Stage adminStage = new Stage();
            admin.start(adminStage);
        });

        tambahDataItem.setOnAction(e -> {
            primaryStage.close();
            Tambah tambah = new Tambah();
            Stage tambahStage = new Stage();
            tambah.start(tambahStage);
        });

        updeteDataItem.setOnAction(e -> {
            primaryStage.close();
            Updete updete = new Updete();
            Stage updeteStage = new Stage();
            updete.start(updeteStage);
        });

        logoutItem.setOnAction(e -> {
            primaryStage.close();
            LoginApp loginApp = new LoginApp();
            Stage loginStage = new Stage();
            loginApp.start(loginStage);
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
