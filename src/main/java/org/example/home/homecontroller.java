package org.example.home;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.Main;
import org.example.verify.logincontroller;

import java.io.IOException;

public class homecontroller {

    @FXML
    public void font(){
        Font ssp_semibold = Font.loadFont(getClass().getResourceAsStream("/res/SourceSerifPro-SemiBold.ttf"), 25);
        Font ssp_reg = Font.loadFont(getClass().getResourceAsStream("/res/SourceSerifPro-Regular.ttf"), 13);
        Font pop_med = Font.loadFont(getClass().getResourceAsStream("/res/Poppins-Medium.ttf"), 13);
        Font pop_med_bigger = Font.loadFont(getClass().getResourceAsStream("/res/Poppins-Medium.ttf"), 19);
        nametag.setFont(ssp_semibold);
        labelbiblioteka.setFont(pop_med);
        labelglowna.setFont(pop_med);
        labelkatalog.setFont(pop_med);
        labelkontakt.setFont(pop_med);
        labelkategorie.setFont(pop_med);
        labelnowosci.setFont(pop_med);
        labelrezerwacje.setFont(pop_med);
        labelwypozyczenia.setFont(pop_med);
        searchbar.setFont(pop_med_bigger);
    }
    @FXML
    private ImageView avatar = new ImageView();


    @FXML
    private Label labelbiblioteka;

    @FXML
    private Label labelglowna;

    @FXML
    private Label labelkatalog;

    @FXML
    private Label labelkategorie;

    @FXML
    private Label labelkontakt;

    @FXML
    private Label labelnowosci;

    @FXML
    private Label labelrezerwacje;

    @FXML
    private Label labelwypozyczenia;

    @FXML
    private ImageView logo;

    @FXML
    private Label nametag;

    @FXML
    private TextField searchbar;

    @FXML
    private VBox vbox;
    @FXML
    void searchbar_exited(MouseEvent event) {
        searchbar.setFocusTraversable(false);
    }
    public void init(String imie, String nazwisko, MouseEvent event, Image image){ //zamienic na image
        System.out.println(Main.user.getImage());
        nametag.setText(imie+" "+nazwisko);
        searchbar_exited(event);
        avatar.setImage(Main.user.getImage());
    }
    @FXML
    void logout_perform(MouseEvent event) {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Image icon = new Image("res/Lectorium_logo.png");
        stage.getIcons().add(icon);
        final Stage oldstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        oldstage.close();
        Parent parent = null;
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
    void katalog_klik(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent parent = null;
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


}
