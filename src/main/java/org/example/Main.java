package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent loginParent = FXMLLoader.load(getClass().getResource("/fxml/test.fxml"));
        Scene loginScene = new Scene(loginParent);
        stage.setTitle("GBS");
        stage.setScene(loginScene);
        stage.show();
    }

    public static void main(String[] args) {
        //database.initializeDatabase();
        launch();
    }
}