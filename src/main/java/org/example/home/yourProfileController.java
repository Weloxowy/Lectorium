package org.example.home;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
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

import static org.example.Main.dbload;

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
        private Button commit_delete;

        @FXML
        private Button commit_login;

        @FXML
        private Button commit_password;

        @FXML
        private TextField curr_login;

        @FXML
        private TextField curr_password;

        @FXML
        private TextField delete_password;

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
        private VBox login;

        @FXML
        private Button login_change;

        @FXML
        private ImageView logout;

        @FXML
        private Label nametag;

        @FXML
        private TextField new_login;

        @FXML
        private TextField new_password;

        @FXML
        private VBox password;

        @FXML
        private Button password_change;

        @FXML
        private Button profile_delete;

        @FXML
        private TextField searchbar;

        @FXML
        private Label text_delete;

        @FXML
        private VBox usun;


        void avatar_view() {
            int radius = 28;
            double centerX = avatar.getBoundsInLocal().getWidth() / 2.0;
            double centerY = avatar.getBoundsInLocal().getHeight() / 2.0;
            Circle clipCircle = new Circle(centerX, centerY, radius);
            avatar.setClip(clipCircle);
        }

        void avatar_view_profile() {
            int radius = 107;
            double centerX = avatar2115.getBoundsInLocal().getWidth() / 2.0;
            double centerY = avatar2115.getBoundsInLocal().getHeight() / 2.0;
            Circle clipCircle = new Circle(centerX, centerY, radius);
            avatar2115.setClip(clipCircle);
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
            avatar_view_profile();
            wash_effects();
        }

        @FXML
        void search_init(MouseEvent event){
            String query = searchbar.getText();
            katalog_clicked(event,query);
        }

        @FXML
        void haslo_c(MouseEvent event) {
            wash_effects();
            password.setVisible(true);
            password_change.setStyle("-fx-border-width: 2");
        }

        @FXML
        void login_c(MouseEvent event) {
            wash_effects();
            login.setVisible(true);
            login_change.setStyle("-fx-border-width: 2");
        }

        @FXML
        void login_change(MouseEvent event) {
            String old_l = curr_login.getText();
            String new_l = new_login.getText();
            if(dbload.login_update(new_l,Main.user.getId(),old_l)){
                //tutaj dac potwierdzenie ze zmieniono, ewentualnie wylogowac
            }
            else{
                commit_login.setStyle("-fx-border-width: 2");
                commit_login.setStyle("-fx-border-color: red");
                //dac tekst ze blad
            }
        }


        @FXML
        void password_change(MouseEvent event) {

        }

        @FXML
        void profile_d(MouseEvent event) {
            wash_effects();
            usun.setVisible(true);
            profile_delete.setStyle("-fx-border-width: 2");
        }

        @FXML
        void delete_profile(MouseEvent event) {

        }

        void wash_effects(){
            password.setVisible(false);
            usun.setVisible(false);
            login.setVisible(false);
            login_change.setStyle("-fx-border-width: 1");
            password_change.setStyle("-fx-border-width: 1");
            profile_delete.setStyle("-fx-border-width: 1");
        }
}
