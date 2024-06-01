import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
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
import java.time.LocalDate;

public class Updete extends Application {

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
        MenuItem hapusDataItem = new MenuItem("Hapus Data");
        MenuItem lihatDataItem = new MenuItem("Lihat Data");

        // Add MenuItems to the Data menu
        dataMenu.getItems().addAll(tambahDataItem, hapusDataItem, lihatDataItem);

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

        Label asalLabel = new Label("Asal:");
        asalLabel.setFont(new Font("Times New Roman", 14));
        asalLabel.setTextFill(Color.WHITESMOKE);
        TextField asalField = new TextField();

        Label tujuanLabel = new Label("Tujuan:");
        tujuanLabel.setFont(new Font("Times New Roman", 14));
        tujuanLabel.setTextFill(Color.WHITESMOKE);
        TextField tujuanField = new TextField();

        Label jamLabel = new Label("Jam:");
        jamLabel.setFont(new Font("Times New Roman", 14));
        jamLabel.setTextFill(Color.WHITESMOKE);
        DatePicker jamPicker = new DatePicker();

        Label jumlahTiketLabel = new Label("Jumlah Tiket:");
        jumlahTiketLabel.setFont(new Font("Times New Roman", 14));
        jumlahTiketLabel.setTextFill(Color.WHITESMOKE);
        TextField jumlahTiketField = new TextField();

        Label hargaTiketLabel = new Label("Harga Tiket:");
        hargaTiketLabel.setFont(new Font("Times New Roman", 14));
        hargaTiketLabel.setTextFill(Color.WHITESMOKE);
        TextField hargaTiketField = new TextField();

        // Add input fields to GridPane
        gridPane.add(idLabel, 0, 0);
        gridPane.add(idField, 1, 0);
        gridPane.add(asalLabel, 0, 1);
        gridPane.add(asalField, 1, 1);
        gridPane.add(tujuanLabel, 0, 2);
        gridPane.add(tujuanField, 1, 2);
        gridPane.add(jamLabel, 0, 3);
        gridPane.add(jamPicker, 1, 3);
        gridPane.add(jumlahTiketLabel, 0, 4);
        gridPane.add(jumlahTiketField, 1, 4);
        gridPane.add(hargaTiketLabel, 0, 5);
        gridPane.add(hargaTiketField, 1, 5);

        // Create Save button
        Button saveButton = new Button("Simpan");
        saveButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        saveButton.setOnMouseEntered(e -> saveButton.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;"));
        saveButton.setOnMouseExited(e -> saveButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;"));

        // Add Save button to GridPane
        gridPane.add(saveButton, 1, 6);

        // Add event handler for Save button
        saveButton.setOnAction(e -> {
            String id = idField.getText();
            String asal = asalField.getText();
            String tujuan = tujuanField.getText();
            LocalDate jam = jamPicker.getValue();
            int jumlahTiket = Integer.parseInt(jumlahTiketField.getText());
            int hargaTiket = Integer.parseInt(hargaTiketField.getText());

            try {
                Connection connection = Database.getConnection();
                String sql = "UPDATE tbtiket SET asal = ?, tujuan = ?, jam = ?, jumlah_tiket = ?, harga_tiket = ? WHERE id_tiket = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, asal);
                preparedStatement.setString(2, tujuan);
                preparedStatement.setObject(3, jam);
                preparedStatement.setInt(4, jumlahTiket);
                preparedStatement.setInt(5, hargaTiket);
                preparedStatement.setString(6, id);
                preparedStatement.executeUpdate();
                showAlert("Sukses", "Data tiket berhasil diperbarui!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                showAlert("Error", "Terjadi kesalahan saat memperbarui data!");
            }
        });

        // Adding all elements to VBox
        vbox.getChildren().addAll(welcomeLabel, gridPane);

        // Create a BorderPane to hold the MenuBar and VBox
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(vbox);

        // Setting the scene
        Scene scene = new Scene(borderPane, 350, 350);
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

        hapusDataItem.setOnAction(e -> {
            primaryStage.close();
            Hapus hapus = new Hapus();
            Stage hapusStage = new Stage();
            hapus.start(hapusStage);
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
