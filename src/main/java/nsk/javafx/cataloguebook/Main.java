package nsk.javafx.cataloguebook;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;

    private VBox currentRoot;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createGUI();
    }

    private void createGUI() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));

        try {
            currentRoot = fxmlLoader.load();

            primaryStage.setTitle("Адресная книга");

            Scene scene = new Scene(currentRoot, 300, 275);
            primaryStage.setScene(scene);
            primaryStage.setMinHeight(700);
            primaryStage.setMinWidth(600);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        System.exit(0);
    }
}