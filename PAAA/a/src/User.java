import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class User extends Application {

    private int imageIndex = 0;
    private final Image[] images = new Image[3];  // Array untuk menyimpan gambar
    private ImageView imageView;

    @Override
    public void start(Stage primaryStage) {
        int idUser = Session.getCurrentUserId();
        

        primaryStage.setTitle("User Dashboard");
        MenuBar menuBar = new MenuBar();

        Menu berandaMenu = new Menu("Beranda");
        Menu dataMenu = new Menu("Layanan");
        Menu accountMenu = new Menu("Account");

        // Create MenuItems for Data menu
        MenuItem penumpangItem = new MenuItem("Angkutan Penumpang");
        MenuItem barangItem = new MenuItem("Angkutan Barang");

        // Add MenuItems to the Data menu
        dataMenu.getItems().addAll(penumpangItem, barangItem);

        // Create MenuItems for Account menu
        MenuItem logoutItem = new MenuItem("Logout");

        // Add MenuItems to the Account menu
        accountMenu.getItems().addAll(logoutItem);

        // Add Menus to the MenuBar
        menuBar.getMenus().addAll(berandaMenu, dataMenu, accountMenu);

        VBox vbox = new VBox(menuBar);
        Label label = new Label("Welcome, User ID: " + idUser);

        // Buttons for slideshow control
        Button prevButton = new Button("Prev");
        Button nextButton = new Button("Next");

        prevButton.setStyle("-fx-background-radius: 50;");
        nextButton.setStyle("-fx-background-radius: 50;");

        prevButton.setPrefWidth(50);
        prevButton.setPrefHeight(50);

        nextButton.setPrefWidth(50);
        nextButton.setPrefHeight(50);

        HBox buttonsBox = new HBox(700);
        buttonsBox.getChildren().addAll(prevButton, nextButton);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setCenterShape(true);

        images[0] = new Image(getClass().getResourceAsStream("kereta1.jpg"));
        images[1] = new Image(getClass().getResourceAsStream("kereta2.jpg"));
        images[2] = new Image(getClass().getResourceAsStream("kereta3.jpg"));

        imageView = new ImageView(images[0]);
        imageView.setFitWidth(800);
        imageView.setFitHeight(800);
        imageView.setPreserveRatio(true);

        // StackPane to center image and label
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(imageView, label, buttonsBox);

        prevButton.setOnAction(e -> {
            imageIndex = (imageIndex - 1 + images.length) % images.length;
            imageView.setImage(images[imageIndex]);
        });

        nextButton.setOnAction(e -> {
            imageIndex = (imageIndex + 1) % images.length;
            imageView.setImage(images[imageIndex]);
        });

        vbox.getChildren().addAll(stackPane);

        Scene scene = new Scene(vbox, 900, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        barangItem.setOnAction(e -> showAlert("Edit Data", "Functionality to edit data goes here"));

        penumpangItem.setOnAction(e -> {
            primaryStage.close();
            Penumpang penumpang = new Penumpang();
            Stage penumpangStage = new Stage();
            penumpang.start(penumpangStage);
        });

        logoutItem.setOnAction(e -> {
            Session.clearSession();
            primaryStage.close();
            LoginApp loginApp = new LoginApp();
            Stage loginStage = new Stage();
            loginApp.start(loginStage);
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> nextImage()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void nextImage() {
        imageIndex = (imageIndex + 1) % images.length;
        imageView.setImage(images[imageIndex]);
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
