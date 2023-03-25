package org.example.verify;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.Main;
import org.example.db.dbloader;
import org.example.home.homecontroller;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;


public class logincontroller {

    public void font() {
        Font myFont1 = null;
        Font myFont2 = null;
        myFont1 = Font.loadFont(getClass().getResourceAsStream("/res/SourceSerifPro-SemiBold.ttf"), 35);
        zalogujsietext.setFont(myFont1);
        myFont2 = Font.loadFont(getClass().getResourceAsStream("/res/SourceSerifPro-Regular.ttf"), 13);
        switchtoregister.setFont(myFont2);
        /*Font myFont3 = Font.loadFont(getClass().getResourceAsStream("/res/Poppins-Medium.ttf"), 13);
        haslo.setFont(myFont3); // niepoprawne znaki przy wprowadzanie hasla oraz zła proporcja
        login.setFont(myFont3);*/
    }

    @FXML
    private ImageView closebutton;

    @FXML
    private PasswordField haslo;

    @FXML
    private TextField login;

    @FXML
    private ImageView minimizebutton;

    @FXML
    private ImageView strzalkabutton;

    @FXML
    private Button submit;

    @FXML
    private Text switchtoregister;

    @FXML
    private Label zalogujsietext = new Label();

    @FXML
    private Label error;

    @FXML
    void onclosewindow(MouseEvent event) {
        Stage stage = (Stage) closebutton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onminimalizewindow(MouseEvent event) {
        Stage stage = (Stage) minimizebutton.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void onregisterclicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent parent = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.verify/register.fxml"));
            parent = loader.load();
            registercontroller controller = loader.getController();
            controller.font();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (parent == null)
            return;
        Scene scene = new Scene(parent);
        stage.setScene(scene);
    }

    @FXML
    void onsubmit(MouseEvent event) {
        error.setOpacity(0.0);
        org.example.db.dbloader l = new dbloader();
        String log = login.getText();
        String has = haslo.getText();
        boolean res = l.tryLogin(log, has);
        if (res) {
            onsuccess(event); //przekazywanie wartosci z funkcji tryLogin?
        } else if (!res) {
            onfailure(event);
        }
    }

    void onsuccess(MouseEvent event) {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
        Image icon = new Image("res/9k.png");
        stage.getIcons().add(icon);
        final Stage oldstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        oldstage.close();
        Parent parent = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/home.fxml"));
            parent = loader.load();
            homecontroller controller = loader.getController();
            controller.font();
            controller.init(Main.user.getImie(),Main.user.getNazwisko(),event,Main.user.getImage());
            stage.setResizable(false);
            stage.setResizable(true);
            stage.isMaximized();
            stage.setFullScreen(false);
            stage.setTitle("Lectorium alpha");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (parent == null)
            return;
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("/fxml.home/home.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    void onfailure(MouseEvent event) {
        error.setOpacity(1.0);
    }

}

