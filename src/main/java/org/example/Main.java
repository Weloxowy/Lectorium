package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.db.dbloader;
import org.example.verify.logincontroller;

import java.io.IOException;

public class Main extends Application {
    static public User user = new User();

    @Override
    public void start(Stage stage) throws Exception{
        Image icon = new Image("res/9k.png");
        stage.getIcons().add(icon);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.verify/login.fxml"));
            Parent parent = loader.load();
            logincontroller controller = loader.getController();
            controller.font();
            Scene loginScene = new Scene(parent);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("Lectorium");
            stage.setScene(loginScene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    public static void main(String[] args) {
        launch();
    }
}