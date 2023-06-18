package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.app.PasswordSkin;
import org.example.auth.loginController;
import org.example.db.*;

import java.io.IOException;

/**
 * Klasa główna aplikacji, dziedzicząca po klasie Application z JavaFX.
 * Zarządza startem aplikacji, inicjalizacją sceny logowania oraz obsługą przeciągania okna.
 */
public class Main extends Application {
    /**
     * Inicjalizacja statycznego obiektu Katalog
     */
    static public Katalog kat = new Katalog(0, null, null, null, null, null, null, null, null, null);

    /**
     * Obiekty {@code db_parent}, {@code db_getData}, {@code db_setData}, {@code db_updateData} i {@code db_deleteData}
     * reprezentują instancje klas do obsługi bazy danych.
     * Każdy obiekt odpowiada za różne operacje na bazie danych, takie jak pobieranie danych, zapisywanie, aktualizowanie i usuwanie.
     */
    static public DbParent db_parent = new DbParent();
    static public DbGetData db_getData = new DbGetData();
    static public DbSetData db_setData = new DbSetData();
    static public DbUpdateData db_updateData = new DbUpdateData();
    static public DbDeleteData db_deleteData = new DbDeleteData();

    /**
     * Inicjalizacja statycznego obiektu Rezerwacje
     */
    static public Rezerwacje rezerwacje = new Rezerwacje(null, null, null, null, null, null, null);

    /**
     * Metoda startująca aplikację, odpowiedzialna za inicjalizację sceny logowania oraz obsługę przeciągania okna.
     *
     * @param stage Główna scena aplikacji.
     */
    @Override
    public void start(Stage stage) {
        Image icon = new Image("res/logo/Lectorium_logo.png");
        stage.getIcons().add(icon);
        try {
            final double[] x = {0};
            final double[] y = {0};
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.verify/login.fxml"));
            Parent parent = loader.load();
            loginController controller = loader.getController();
            controller.font();
            controller.getHaslo().setSkin(new PasswordSkin(controller.getHaslo()));
            Scene loginScene = new Scene(parent);
            stage.initStyle(StageStyle.TRANSPARENT);
            loginScene.setOnMousePressed((MouseEvent event) -> {
                x[0] = event.getSceneX();
                y[0] = event.getSceneY();
            });
            loginScene.setOnMouseDragged((MouseEvent event) -> {
                stage.setX(event.getScreenX() - x[0]);
                stage.setY(event.getScreenY() - y[0]);
            });
            stage.setTitle("Lectorium");
            stage.setScene(loginScene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metoda uruchamiająca aplikację.
     *
     * @param args Argumenty wiersza poleceń.
     */
    public static void main(String[] args) {
        launch();
    }
}