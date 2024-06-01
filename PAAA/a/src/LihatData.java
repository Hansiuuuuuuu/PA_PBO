import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LihatData extends Application {

    private VBox dataContainer;

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
        MenuItem editDataItem = new MenuItem("Edit Data");
        MenuItem hapusDataItem = new MenuItem("Hapus Data");
        MenuItem lihatDataItem = new MenuItem("Lihat Data");

        // Add MenuItems to the Data menu
        dataMenu.getItems().addAll(editDataItem, hapusDataItem, lihatDataItem);

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
        Label welcomeLabel = new Label("LIHAT DATA");
        welcomeLabel.setFont(new Font("Arial", 24));
        welcomeLabel.setTextFill(Color.WHITESMOKE);

        // GridPane to display data
        GridPane dataGrid = new GridPane();
        dataGrid.setPadding(new Insets(20));
        dataGrid.setHgap(15);  // Horizontal gap between columns
        dataGrid.setVgap(10);  // Vertical gap between rows
        dataGrid.setStyle("-fx-background-color: #ecf0f1;");

        // Add header labels to GridPane
        addHeaderLabels(dataGrid);

        // Add data to GridPane
        addDataToGrid(dataGrid);

        // Adding all elements to VBox
        vbox.getChildren().addAll(welcomeLabel, dataGrid);

        // Create a BorderPane to hold the MenuBar and VBox
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(vbox);

        // Setting the scene
        Scene scene = new Scene(borderPane, 1100, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Add event handling for MenuItems
        editDataItem.setOnAction(e -> showAlert("Edit Data", "Functionality to edit data goes here"));
        hapusDataItem.setOnAction(e -> showAlert("Hapus Data", "Functionality to delete data goes here"));
        lihatDataItem.setOnAction(e -> showAlert("Lihat Data", "Functionality to view data goes here"));

        berandaItem.setOnAction(e -> {
            primaryStage.close();
            Admin admin = new Admin();
            Stage adminStage = new Stage();
            admin.start(adminStage);
        });

        logoutItem.setOnAction(e -> {
            primaryStage.close();
            LoginApp loginApp = new LoginApp();
            Stage loginStage = new Stage();
            loginApp.start(loginStage);
        });
    }

    private void addHeaderLabels(GridPane gridPane) {
        String[] headers = {"ID", "ASAL", "TUJUAN", "JAM KEBERANGKATAN", "JUMLAH TIKET", "HARGA TIKET"};
        for (int i = 0; i < headers.length; i++) {
            Label headerLabel = new Label(headers[i]);
            headerLabel.setFont(new Font("Times New Roman", 14));
            headerLabel.setTextFill(Color.BLACK);
            gridPane.add(headerLabel, i, 0);  // Add header label to the first row
        }
    }

    private void addDataToGrid(GridPane gridPane) {
        ObservableList<Tiket> dataTiket = getDataTiket();
        for (int row = 0; row < dataTiket.size(); row++) {
            Tiket tiket = dataTiket.get(row);
            addTiketToGrid(gridPane, tiket, row + 1);
        }
    }

    private void addTiketToGrid(GridPane gridPane, Tiket tiket, int row) {
        Label idLabel = new Label(String.valueOf(tiket.getIdTiket()));
        idLabel.setFont(new Font("Times New Roman", 16));
        idLabel.setTextFill(Color.BLACK);

        Label asalLabel = new Label(tiket.getAsal());
        asalLabel.setFont(new Font("Times New Roman", 16));
        asalLabel.setTextFill(Color.BLACK);

        Label tujuanLabel = new Label(tiket.getTujuan());
        tujuanLabel.setFont(new Font("Times New Roman", 16));
        tujuanLabel.setTextFill(Color.BLACK);

        Label jamLabel = new Label(tiket.getJam());
        jamLabel.setFont(new Font("Times New Roman", 16));
        jamLabel.setTextFill(Color.BLACK);

        Label jumlahTiketLabel = new Label(String.valueOf(tiket.getJumlahTiket()));
        jumlahTiketLabel.setFont(new Font("Times New Roman", 16));
        jumlahTiketLabel.setTextFill(Color.BLACK);

        Label hargaTiketLabel = new Label(String.valueOf(tiket.getHargaTiket()));
        hargaTiketLabel.setFont(new Font("Times New Roman", 16));
        hargaTiketLabel.setTextFill(Color.BLACK);

        gridPane.add(idLabel, 0, row);
        gridPane.add(asalLabel, 1, row);
        gridPane.add(tujuanLabel, 2, row);
        gridPane.add(jamLabel, 3, row);
        gridPane.add(jumlahTiketLabel, 4, row);
        gridPane.add(hargaTiketLabel, 5, row);
    }

    private ObservableList<Tiket> getDataTiket() {
        ObservableList<Tiket> dataTiket = FXCollections.observableArrayList();
    
        try {
            Connection connection = Database.getConnection();
            String sql = "SELECT * FROM tbtiket";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {
                int idTiket = resultSet.getInt("id_tiket");
                String asal = resultSet.getString("asal");
                String tujuan = resultSet.getString("tujuan");
                String jam = resultSet.getString("jam");
                int jumlahTiket = resultSet.getInt("jumlah_tiket");
                int hargaTiket = resultSet.getInt("harga_tiket");
    
                dataTiket.add(new Tiket(idTiket, hargaTiket, asal, tujuan, jam, jumlahTiket));
            }
    
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Error", "Terjadi kesalahan saat mengambil data!");
        }
    
        return dataTiket;
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
