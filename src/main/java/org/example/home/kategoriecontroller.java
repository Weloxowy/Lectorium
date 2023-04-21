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
import javafx.scene.input.TouchEvent;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.Main;
import org.example.verify.logincontroller;

import java.io.IOException;

public class kategoriecontroller {

    @FXML
    public void font() {
        Font ssp_sb_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/SourceSerifPro-SemiBold.ttf"),25);
        Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"),18);
        Font pop_r_h2 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"),14);
        Font pop_b_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-SemiBold.ttf"),14);
        nametag.setFont(ssp_sb_h1);
        labelbiblioteka.setFont(pop_b_h1);
        labelglowna.setFont(pop_b_h1);
        labelkatalog.setFont(pop_b_h1);
        labelkontakt.setFont(pop_b_h1);
        labelkategorie.setFont(pop_b_h1);
        labelnowosci.setFont(pop_b_h1);
        labelrezerwacje.setFont(pop_b_h1);
        labelwypozyczenia.setFont(pop_b_h1);
        searchbar.setFont(pop_r_h1);
    }

    public void init(String imie, String nazwisko, MouseEvent event, Image image){ //zamienic na image
        nametag.setText(imie+" "+nazwisko);
        searchbar_exited(event);
        avatar.setImage(Main.user.getImage());
        avatar_view();
    }

    void avatar_view() {
        int radius = 28;
        double centerX = avatar.getBoundsInLocal().getWidth() / 2.0;
        double centerY = avatar.getBoundsInLocal().getHeight() / 2.0;
        Circle clipCircle = new Circle(centerX, centerY, radius);
        avatar.setClip(clipCircle);
    }
    @FXML
    private ImageView avatar;

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
    private ImageView logout;

    @FXML
    private Label nametag;

    @FXML
    private TextField searchbar;

    @FXML
    void katalog_clicked(MouseEvent event) {
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
        if (parent == null)
            return;
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void searchbar_exited(MouseEvent event) {

    }

    @FXML
    void glowna_clicked(MouseEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent parent = null;
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
