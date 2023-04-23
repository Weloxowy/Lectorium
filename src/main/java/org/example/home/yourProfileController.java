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

import javax.naming.Name;
import java.io.IOException;

    public class yourProfileController extends home {
        @FXML
        private Label Login;

        @FXML
        private Label Name;

        @FXML
        private Label Surname;

        @FXML
        private ImageView avatar;

        @FXML
        private ImageView avatar2115;

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

        public void init(String imie, String nazwisko, MouseEvent event, Image image) {
            nametag.setText(imie + " " + nazwisko);
            avatar.setImage(Main.user.getImage());
            avatar2115.setImage(Main.user.getImage());
            Name.setText("Imie: " + imie);
            Surname.setText("Nazwisko: " + nazwisko);
            //Login.setText();
            avatar_view();
            font();

        }

}
