import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Admin extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Admin Dashboard");

        // Create a MenuBar
        MenuBar menuBar = new MenuBar();

        // Create Menus
        Menu dataMenu = new Menu("Data");
        Menu accountMenu = new Menu("Account");

        // Create MenuItems for Data menu
        MenuItem tambahDataItem = new MenuItem("Tambah Data");
        MenuItem editDataItem = new MenuItem("Edit Data");
        MenuItem hapusDataItem = new MenuItem("Hapus Data");
        MenuItem lihatDataItem = new MenuItem("Lihat Data");

        // Add MenuItems to the Data menu
        dataMenu.getItems().addAll(tambahDataItem, editDataItem, hapusDataItem, lihatDataItem);

        // Create MenuItems for Account menu
        MenuItem logoutItem = new MenuItem("Logout");

        // Add MenuItems to the Account menu
        accountMenu.getItems().addAll(logoutItem);

        // Add Menus to the MenuBar
        menuBar.getMenus().addAll(dataMenu, accountMenu);

        // VBox layout with padding and spacing
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(10);
        vbox.setStyle("-fx-background-color: #2c3e50;");

        // Welcome Label
        Label welcomeLabel = new Label("Dashboard");
        welcomeLabel.setFont(new Font("Arial", 24));
        welcomeLabel.setTextFill(Color.WHITESMOKE);

        // Additional Label
        Label additionalLabel = new Label("Ini adalah teks tambahan di bawah welcome admin.");
        additionalLabel.setFont(new Font("Arial", 18));
        additionalLabel.setTextFill(Color.WHEAT);

        // Example Buttons
        Button button1 = new Button("Manage Users");
        Button button2 = new Button("View Reports");
        Button button3 = new Button("Settings");

        // Adding styles to buttons
        button1.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        button2.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        button3.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");

        // Adding hover effects to buttons
        button1.setOnMouseEntered(e -> button1.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;"));
        button1.setOnMouseExited(e -> button1.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;"));

        button2.setOnMouseEntered(e -> button2.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;"));
        button2.setOnMouseExited(e -> button2.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;"));

        button3.setOnMouseEntered(e -> button3.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;"));
        button3.setOnMouseExited(e -> button3.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;"));

        // Adding all elements to VBox
        vbox.getChildren().addAll(welcomeLabel, additionalLabel, button1, button2, button3);

        // Create a BorderPane to hold the MenuBar and VBox
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(vbox);

        // Setting the scene
        Scene scene = new Scene(borderPane, 700, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Add event handling for MenuItems

        lihatDataItem.setOnAction(e -> showAlert("Lihat Data", "Functionality to view data goes here"));

        tambahDataItem.setOnAction(e -> {
            primaryStage.close();
            Tambah tambah = new Tambah();
            Stage tambahStage = new Stage();
            tambah.start(tambahStage);
        });

        editDataItem.setOnAction(e -> {
            primaryStage.close();
            Updete updete = new Updete();
            Stage updeteStage = new Stage();
            updete.start(updeteStage);
        });

        hapusDataItem.setOnAction(e -> {
            primaryStage.close();
            Hapus hapus = new Hapus();
            Stage hapusStage = new Stage();
            hapus.start(hapusStage);
        });

        lihatDataItem.setOnAction(e -> {
            primaryStage.close();
            LihatData lihatdata = new LihatData();
            Stage lihatdataStage = new Stage();
            lihatdata.start(lihatdataStage);
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
