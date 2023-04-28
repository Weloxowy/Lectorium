package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.db.dbloader;
import org.example.home.contactcontroller;
import org.example.verify.logincontroller;
import java.io.IOException;

public class Main extends Application {
    static public User user = new User();
    static public Katalog kat = new Katalog(0,null,null,null,null,null,null,null,null);
    static public dbloader dbload = new dbloader();

    static public Wypozyczenia wypozyczenia = new Wypozyczenia(null,null,null,null,null);

    @Override
    public void start(Stage stage) throws Exception{
        Image icon = new Image("res/logo/Lectorium_logo.png");
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