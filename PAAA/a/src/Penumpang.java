import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Penumpang extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("User Dashboard");

        // Create a MenuBar
        MenuBar menuBar = new MenuBar();

        // Create Menus
        Menu berandaMenu = new Menu("Beranda");
        Menu dataMenu = new Menu("Data");
        Menu accountMenu = new Menu("Account");

        MenuItem berandaItem = new MenuItem("Beranda");

        berandaMenu.getItems().add(berandaItem);

        // Create MenuItems for Data menu
        MenuItem lihatDataItem = new MenuItem("Lihat Data");

        // Add MenuItems to the Data menu
        dataMenu.getItems().addAll(lihatDataItem);

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

        // Adding Order Button
        Button orderButton = new Button("Pesan Tiket");
        orderButton.setOnAction(e -> handleOrder(primaryStage));
        vbox.getChildren().add(orderButton);

        // Setting the scene
        Scene scene = new Scene(borderPane, 1100, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Add event handling for MenuItems
        lihatDataItem.setOnAction(e -> showAlert("Lihat Data", "Functionality to view data goes here"));

        berandaItem.setOnAction(e -> {
            primaryStage.close();
            User user = new User();
            Stage userStage = new Stage();
            user.start(userStage);
        });

        logoutItem.setOnAction(e -> {
            primaryStage.close();
            LoginApp loginApp = new LoginApp();
            Stage loginStage = new Stage();
            loginApp.start(loginStage);
        });

        lihatDataItem.setOnAction(e -> {
            primaryStage.close();
            LihatDataPesanan lihat = new LihatDataPesanan();
            Stage lihatStage = new Stage();
            lihat.start(lihatStage);
        });

    }

    private void handleOrder(Stage primaryStage) {
        Stage orderStage = new Stage();
        orderStage.setTitle("Pesan Tiket");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        Label nameLabel = new Label("Nama Pemesan:");
        GridPane.setConstraints(nameLabel, 0, 0);
        TextField nameField = new TextField();
        GridPane.setConstraints(nameField, 1, 0);

        Label phoneLabel = new Label("No Telp:");
        GridPane.setConstraints(phoneLabel, 0, 1);
        TextField phoneField = new TextField();
        GridPane.setConstraints(phoneField, 1, 1);

        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 2);
        TextField emailField = new TextField();
        GridPane.setConstraints(emailField, 1, 2);

        Label ticketsLabel = new Label("Jumlah Tiket:");
        GridPane.setConstraints(ticketsLabel, 0, 3);
        TextField ticketsField = new TextField();
        GridPane.setConstraints(ticketsField, 1, 3);

        Label ticketIdLabel = new Label("ID Tiket:");
        GridPane.setConstraints(ticketIdLabel, 0, 4);
        TextField ticketIdField = new TextField();
        GridPane.setConstraints(ticketIdField, 1, 4);

        Button submitButton = new Button("Submit");
        GridPane.setConstraints(submitButton, 1, 5);
        submitButton.setOnAction(e -> handleSubmitOrder(nameField.getText(), phoneField.getText(), emailField.getText(), ticketsField.getText(), ticketIdField.getText()));

        grid.getChildren().addAll(nameLabel, nameField, phoneLabel, phoneField, emailLabel, emailField, ticketsLabel, ticketsField, ticketIdLabel, ticketIdField, submitButton);

        Scene scene = new Scene(grid, 300, 250);
        orderStage.setScene(scene);
        orderStage.show();
    }

    private void handleSubmitOrder(String name, String phone, String email, String tickets, String id_tiket) {
        int id_user = Session.getCurrentUserId(); // Mendapatkan id_user dari sesi yang aktif

        try {
            Connection connection = Database.getConnection();
            String sql = "INSERT INTO orders (id_user, nama_pemesan, no_telp, email, jumlah_tiket, id_tiket) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_user); // Menyimpan id_user ke database
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, email);
            preparedStatement.setInt(5, Integer.parseInt(tickets));
            preparedStatement.setString(6, id_tiket);
            preparedStatement.executeUpdate();

            showAlert("Order Success", "Pesanan berhasil dibuat!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Error", "Terjadi kesalahan saat membuat pesanan!");
        }
    }

    private void addHeaderLabels(GridPane gridPane) {
        String[] headers = {"ID TIKET", "ASAL", "TUJUAN", "JAM KEBERANGKATAN", "JUMLAH TIKET", "HARGA TIKET"};
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
        Label idTiketLabel = new Label(String.valueOf(tiket.getIdTiket()));
        idTiketLabel.setFont(new Font("Times New Roman", 16));
        idTiketLabel.setTextFill(Color.BLACK);
    
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
    
        gridPane.add(idTiketLabel, 0, row);
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
