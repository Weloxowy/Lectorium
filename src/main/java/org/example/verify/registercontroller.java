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
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.Main;
import org.example.db.dbloader;
import org.example.home.homecontroller;

import java.io.IOException;

public class registercontroller {

    public void font(){
        Font myFont1 = Font.loadFont(getClass().getResourceAsStream("/res/SourceSerifPro-SemiBold.ttf"), 35);
        Font myFont2 = Font.loadFont(getClass().getResourceAsStream("/res/SourceSerifPro-Regular.ttf"), 13);
        myFont1 = Font.font(myFont1.getFamily(), FontWeight.SEMI_BOLD, myFont1.getSize());
        myFont2 = Font.font(myFont2.getFamily(), FontWeight.NORMAL, myFont2.getSize());
        zarejestrujsietext.setFont(myFont1);
        switchtologin.setFont(myFont2);
    }

    @FXML
    private ImageView closebutton;

    @FXML
    private PasswordField haslo;

    @FXML
    private TextField imie;

    @FXML
    private TextField login;

    @FXML
    private ImageView minimizebutton;

    @FXML
    private TextField nazwisko;

    @FXML
    private ImageView strzalkabutton;

    @FXML
    private Button submit;

    @FXML
    private Text switchtologin;

    @FXML
    private Label zarejestrujsietext;

    @FXML
    private Label error_dane;

    @FXML
    private Label error_haslo;

    @FXML
    private Label error_login;

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.verify/login.fxml"));
            parent = loader.load();
            logincontroller controller = loader.getController();
            controller.font();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(parent == null)
            return;
        Scene scene = new Scene(parent);
        stage.setScene(scene);
    }

    @FXML
    void onsubmit(MouseEvent event){
        hidefailuresign(event);
        org.example.db.dbloader l = new dbloader();
        String name = imie.getText();
        String last_name = nazwisko.getText();
        String log = login.getText();
        String has = haslo.getText();
        if(has.isEmpty() || log.isEmpty() || last_name.isEmpty() || name.isEmpty()){
            error_dane.setOpacity(1.0);
            return;
        }
        if(has.length()<5){
            error_haslo.setOpacity(1.0);
            return;
        }
        boolean res = l.tryRegister(name,last_name,log,has);
        res = l.tryLogin(log,has);
        if (res) {
            onsuccess(event); //przekazywanie wartosci z funkcji tryLogin?
        } else if (!res) {
            onfailure(event);
        }
    }
    void onsuccess(MouseEvent event) {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
        Image icon = new Image("res/Lectorium_logo.png");
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
        error_login.setOpacity(1.0);
    }

    @FXML
    void hidefailuresign(MouseEvent event) {
        if(error_dane.getOpacity() == 1.0){
        error_dane.setOpacity(0.0);
        }
        if(error_haslo.getOpacity() == 1.0) {
            error_haslo.setOpacity(0.0);
        }
        if(error_login.getOpacity() == 1.0) {
            error_login.setOpacity(0.0);
        }
    }

}
