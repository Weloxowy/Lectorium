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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.Main;
import org.example.verify.logincontroller;

import java.io.IOException;

public class contactcontroller extends home{
    @FXML
    private Label contact1;

    @FXML
    private Label contact2;

    @FXML
    private Label contact3;

    @FXML
    private Label contact4;

    @FXML
    private Label contact5;

    @FXML
    private Label contact6;

    @FXML
    private Label contact7;

    @FXML
    private Label contact8;

    @FXML
    private Label contact9;

    @FXML
    private VBox kontakt_id;
    @FXML
    private ImageView contact_img;
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



    void images_view(){
        double centerXa1 = contact_img.getBoundsInLocal().getWidth();
        double centerYa1 = contact_img.getBoundsInLocal().getHeight();
        Rectangle rectanglea1 = new Rectangle(centerXa1, centerYa1);
        rectanglea1.setArcWidth(40.0);
        rectanglea1.setArcHeight(40.0);
        //rectangle.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 20, 0, 0, 0)");
        contact_img.setClip(rectanglea1);
    }

    public void init(String imie, String nazwisko, MouseEvent event, Image image) {
        nametag.setText(imie + " " + nazwisko);
        avatar.setImage(Main.user.getImage());
        avatar_view();
        images_view();
        font();
        labelkontakt.setStyle("-fx-text-fill:#808080");

    }

    void avatar_view() {
        int radius = 28;
        double centerX = avatar.getBoundsInLocal().getWidth() / 2.0;
        double centerY = avatar.getBoundsInLocal().getHeight() / 2.0;
        Circle clipCircle = new Circle(centerX, centerY, radius);
        avatar.setClip(clipCircle);
    }

    @FXML
     void font(){
        Font ssp_sb_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/SourceSerifPro-SemiBold.ttf"),25);
        Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"),18);
        Font pop_r_h2 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"),14);
        Font pop_b_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-SemiBold.ttf"),14);
        Font pop_b_h2 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-SemiBold.ttf"),30);
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
        contact1.setFont(pop_b_h2);
        contact2.setFont(pop_b_h2);
        contact3.setFont(pop_b_h2);
        contact4.setFont(pop_b_h2);
        contact5.setFont(pop_b_h2);
        contact6.setFont(pop_b_h2);
        contact7.setFont(pop_b_h2);
        contact8.setFont(pop_b_h2);
        contact9.setFont(pop_b_h2);
    }

    @FXML
    void search_init(MouseEvent event){
        String query = searchbar.getText();
        katalog_clicked(event,query);
    }
}
