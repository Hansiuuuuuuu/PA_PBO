import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LihatDataPesanan extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Data Pesanan");

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

        // Create a BorderPane to hold the GridPane
        BorderPane bPesananPane = new BorderPane();
        bPesananPane.setCenter(dataGrid);

        // Setting the scene
        Scene scene = new Scene(bPesananPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addHeaderLabels(GridPane gridPane) {
        String[] headers = {"NO", "NAMA PEMESAN", "NO TELP", "EMAIL", "JUMLAH TIKET", "ID TIKET"};
        for (int i = 0; i < headers.length; i++) {
            Label headerLabel = new Label(headers[i]);
            headerLabel.setFont(new Font("Times New Roman", 14));
            headerLabel.setTextFill(Color.BLACK);
            gridPane.add(headerLabel, i, 0);  // Add header label to the first row
        }
    }

    private void addDataToGrid(GridPane gridPane) {
        ObservableList<Pesanan> dataPesanan = getDataPesanan();
        for (int row = 0; row < dataPesanan.size(); row++) {
            Pesanan pesanan = dataPesanan.get(row);
            addPesananToGrid(gridPane, pesanan, row + 1, row + 1);
        }
    }

    private void addPesananToGrid(GridPane gridPane, Pesanan pesanan, int row, int no) {
        Label noLabel = new Label(String.valueOf(no));
        noLabel.setFont(new Font("Times New Roman", 16));
        noLabel.setTextFill(Color.BLACK);

        Label namaPemesanLabel = new Label(pesanan.getNamaPemesan());
        namaPemesanLabel.setFont(new Font("Times New Roman", 16));
        namaPemesanLabel.setTextFill(Color.BLACK);

        Label noTelpLabel = new Label(pesanan.getNoTelp());
        noTelpLabel.setFont(new Font("Times New Roman", 16));
        noTelpLabel.setTextFill(Color.BLACK);

        Label emailLabel = new Label(pesanan.getEmail());
        emailLabel.setFont(new Font("Times New Roman", 16));
        emailLabel.setTextFill(Color.BLACK);

        Label jumlahTiketLabel = new Label(String.valueOf(pesanan.getJumlahTiket()));
        jumlahTiketLabel.setFont(new Font("Times New Roman", 16));
        jumlahTiketLabel.setTextFill(Color.BLACK);

        Label idTiketLabel = new Label(String.valueOf(pesanan.getIdTiket()));
        idTiketLabel.setFont(new Font("Times New Roman", 16));
        idTiketLabel.setTextFill(Color.BLACK);

        gridPane.add(noLabel, 0, row);
        gridPane.add(namaPemesanLabel, 1, row);
        gridPane.add(noTelpLabel, 2, row);
        gridPane.add(emailLabel, 3, row);
        gridPane.add(jumlahTiketLabel, 4, row);
        gridPane.add(idTiketLabel, 5, row);
    }

    private ObservableList<Pesanan> getDataPesanan() {
        ObservableList<Pesanan> dataPesanan = FXCollections.observableArrayList();
        int id_user = Session.getCurrentUserId(); // Mendapatkan id_user dari sesi yang aktif

        try {
            Connection connection = Database.getConnection();
            String sql = "SELECT * FROM orders WHERE id_user = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_user);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String namaPemesan = resultSet.getString("nama_pemesan");
                String noTelp = resultSet.getString("no_telp");
                String email = resultSet.getString("email");
                int jumlahTiket = resultSet.getInt("jumlah_tiket");
                int idTiket = resultSet.getInt("id_tiket");

                dataPesanan.add(new Pesanan(namaPemesan, noTelp, email, jumlahTiket, idTiket));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Error", "Terjadi kesalahan saat mengambil data pesanan!");
        }

        return dataPesanan;
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