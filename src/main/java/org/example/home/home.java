package org.example.home;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.Main;
import org.example.verify.logincontroller;

import java.io.IOException;

public class home {

    @FXML
    void logout_perform(MouseEvent event) {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Image icon = new Image("res/logo/Lectorium_logo.png");
        stage.getIcons().add(icon);
        final Stage oldstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        oldstage.close();
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.verify/login.fxml"));
            parent = loader.load();
            logincontroller controller = loader.getController();
            controller.font();
            stage.setResizable(false);
            stage.setResizable(true);
            stage.isMaximized();
            stage.setFullScreen(false);
            stage.setTitle("Lectorium alpha");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(parent == null)
            return;
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void katalog_clicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/katalog.fxml"));
            parent = loader.load();
            katalogcontroller kat = loader.getController();
            kat.init(Main.user.getImie(),Main.user.getNazwisko(),event,Main.user.getImage());
            kat.init_lista();
            kat.font();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (parent == null)
            return;
        Scene scene = new Scene(parent);
        stage.setScene(scene);
    }

    @FXML
    void kategorie_clicked(MouseEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/kategorie.fxml"));
            parent = loader.load();
            kategoriecontroller controller = loader.getController();
            controller.font();
            controller.init(Main.user.getImie(),Main.user.getNazwisko(),event,Main.user.getImage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (parent == null)
            return;
        Scene scene = new Scene(parent);
        stage.setScene(scene);
    }

    @FXML
    void contact_clicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/contact.fxml"));
            parent = loader.load();
            contactcontroller contact = loader.getController();
            contact.init(Main.user.getImie(),Main.user.getNazwisko(),event,Main.user.getImage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (parent == null)
            return;
        Scene scene = new Scene(parent);
        stage.setScene(scene);
    }

    @FXML
    void glowna_clicked(MouseEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/home.fxml"));
            parent = loader.load();
            homecontroller controller = loader.getController();
            controller.font();
            controller.init(Main.user.getImie(),Main.user.getNazwisko(),event,Main.user.getImage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (parent == null)
            return;
        Scene scene = new Scene(parent);
        stage.setScene(scene);
    }
}
