package demo.demo;

import demo.demo.DB.DatabaseSingleton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Dashboard extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Dashboard.class.getResource("dashboard-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1160, 650);
        boolean isSet = scene.getStylesheets().setAll("/style/newStyle.css", "/style/common.css", "/style/style.css");
        if (isSet) {
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        } else {
            System.out.println("Cannot open css file");
        }
        DatabaseSingleton db = DatabaseSingleton.getInstance();
        if (db.isConnected()) {
            System.out.println("DB is connected successfully");
        }

    }

    public static void main(String[] args) {
        launch();
    }
}