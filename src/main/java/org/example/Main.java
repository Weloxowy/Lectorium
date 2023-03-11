package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent loginParent = FXMLLoader.load(getClass().getResource("/FXML/b.fxml"));
        Font.loadFont(getClass().getResourceAsStream("/resources/FONTY/SourceSerifPro-Regular.ttf"), 14);
        Scene loginScene = new Scene(loginParent);
        stage.setTitle("Lectorium");
        stage.setScene(loginScene);
        stage.show();
    }

    public static void main(String[] args) {
        //database.initializeDatabase();
        launch();
    }
}