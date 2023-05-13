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
import java.util.concurrent.atomic.AtomicReference;

public class Main extends Application {
    static public User user = new User();
    static public Katalog kat = new Katalog(0,null,null,null,null,null,null,null,null,null);
    static public dbloader dbload = new dbloader();

    static public Wypozyczenia wypozyczenia = new Wypozyczenia(null,null,null,null,null,null);

    @Override
    public void start(Stage stage) throws Exception{
        Image icon = new Image("res/logo/Lectorium_logo.png");
        stage.getIcons().add(icon);
        try {
            final double[] x = {0};
            final double[] y = {0};
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.verify/login.fxml"));
            Parent parent = loader.load();
            logincontroller controller = loader.getController();
            controller.font();
            Scene loginScene = new Scene(parent);
            stage.initStyle(StageStyle.TRANSPARENT);
            loginScene.setOnMousePressed(event -> {
                x[0] = event.getSceneX();
                y[0] = event.getSceneY();
            });
            loginScene.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX()- x[0]);
                stage.setY(event.getScreenY()- y[0]);
            });
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