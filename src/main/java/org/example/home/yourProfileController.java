package org.example.home;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import org.example.Main;
import org.example.verify.logincontroller;

import javax.imageio.ImageIO;
import javax.naming.Name;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;

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

    @FXML
    private GridPane grid;

    @FXML
    private Label Rank;


    void avatar_view() {
        int radius = 28;
        double centerX = avatar.getBoundsInLocal().getWidth() / 2.0;
        double centerY = avatar.getBoundsInLocal().getHeight() / 2.0;
        Circle clipCircle = new Circle(centerX, centerY, radius);
        avatar.setClip(clipCircle);
    }

    void avatar_view_profile() {
        int radius = 101;
        double centerX = avatar2115.getBoundsInLocal().getWidth() / 2.0;
        double centerY = avatar2115.getBoundsInLocal().getHeight() / 2.0;
        Circle clipCircle = new Circle(centerX, centerY, radius);
        avatar2115.setClip(clipCircle);
    }

    @FXML
    void font() {
        Font ssp_sb_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/SourceSerifPro-SemiBold.ttf"), 25);
        Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"), 18);
        Font pop_r_h2 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"), 14);
        Font pop_b_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-SemiBold.ttf"), 22);
        Font pop_b_h2 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-SemiBold.ttf"), 18);
        Label name = (Label) grid.lookup("#Name");
        name.setFont(pop_b_h1);
        Label sur = (Label) grid.lookup("#Surname");
        sur.setFont(pop_b_h1);
        Label rank = (Label) grid.lookup("#Rank");
        rank.setFont(pop_b_h1);
        Button login_change = (Button) grid.lookup("#login_change");
        login_change.setFont(pop_r_h1);
        Button password_change = (Button) grid.lookup("#password_change");
        password_change.setFont(pop_r_h1);
        Button profile_delete = (Button) grid.lookup("#profile_delete");
        profile_delete.setFont(pop_r_h1);
        Button avatar_change = (Button) grid.lookup("#avatar_change");
        avatar_change.setFont(pop_r_h1);
        Label warning = (Label) grid.lookup("#text_delete");
        warning.setFont(pop_r_h2);
        PasswordField delete_password = (PasswordField) grid.lookup("#delete_password");
        delete_password.setFont(pop_r_h1);
        Button commit_delete = (Button) grid.lookup("#commit_delete");
        commit_delete.setFont(pop_b_h2);
        TextField curr_login = (TextField) grid.lookup("#curr_login");
        curr_login.setFont(pop_r_h1);
        TextField new_login = (TextField) grid.lookup("#new_login");
        new_login.setFont(pop_r_h1);
        Button commit_login = (Button) grid.lookup("#commit_login");
        commit_login.setFont(pop_b_h2);
        PasswordField curr_password = (PasswordField) grid.lookup("#curr_password");
        curr_password.setFont(pop_r_h1);
        PasswordField new_password = (PasswordField) grid.lookup("#new_password");
        new_password.setFont(pop_r_h1);
        Button commit_password = (Button) grid.lookup("#commit_password");
        commit_password.setFont(pop_b_h2);
        Label info = (Label) grid.lookup("#info");
        info.setFont(pop_b_h1);
    }

    public void init(String imie, String nazwisko, MouseEvent event, Image image) {
        nametag.setText(imie + " " + nazwisko);
        avatar.setImage(Main.user.getImage());
        avatar2115.setImage(Main.user.getImage());
        Name.setText("Imie: " + imie);
        Surname.setText("Nazwisko: " + nazwisko);
        Rank.setText(Rank.getText()+": czytelnik");
        avatar_view();
        avatar_view_profile();
        wash_effects();
    }

    @FXML
    void search_init(MouseEvent event) {
        String query = searchbar.getText();
        katalog_clicked(event, query);
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
        commit_login.setStyle("-fx-border-width: 1");
        commit_login.setStyle("-fx-border-color: #004aad");
        String old_l = curr_login.getText();
        String new_l = new_login.getText();
        Label info = (Label) grid.lookup("#info");
        info.setOpacity(0.0);
        if (dbload.login_update(new_l, Main.user.getId(), old_l)) {
            info.setText("Zmiana wykonana pomyślnie.");
            info.setOpacity(1.0);
        } else {
            commit_login.setStyle("-fx-border-width: 2");
            commit_login.setStyle("-fx-border-color: red");
            info.setText("Wystapil błąd. Spróbuj ponownie.");
            info.setOpacity(1.0);
        }
    }


    @FXML
    void password_change(MouseEvent event) {
        commit_login.setStyle("-fx-border-width: 1");
        commit_login.setStyle("-fx-border-color: #004aad");
        String old_p = curr_password.getText();
        String new_p = new_password.getText();
        Label info = (Label) grid.lookup("#info");
        info.setOpacity(0.0);
        if (dbload.password_update(new_p, Main.user.getId(), old_p)) {
            info.setText("Zmiana wykonana pomyślnie.");
            info.setOpacity(1.0);
        } else {
            commit_password.setStyle("-fx-border-width: 2");
            commit_password.setStyle("-fx-border-color: red");
            info.setText("Wystapil błąd. Spróbuj ponownie.");
            info.setOpacity(1.0);
        }
    }

    @FXML
    void profile_d(MouseEvent event) {
        wash_effects();
        usun.setVisible(true);
        profile_delete.setStyle("-fx-border-width: 2");
    }

    @FXML
    void delete_profile(MouseEvent event) {
        commit_delete.setStyle("-fx-border-width: 1");
        commit_delete.setStyle("-fx-border-color: #004aad");
        String password = delete_password.getText();
        Label info = (Label) grid.lookup("#info");
        info.setOpacity(0.0);
        if (dbload.profile_delete(password, Main.user.getId())) {
            info.setText("Zmiana wykonana pomyślnie.");
            info.setOpacity(1.0);
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(3),
                    event2 -> logout_perform(event) //TODO sprawdzić skuteczność
            ));
            timeline.play();
        } else {
            commit_delete.setStyle("-fx-border-width: 2");
            commit_delete.setStyle("-fx-border-color: red");
            info.setText("Wystapil błąd. Spróbuj ponownie.");
            info.setOpacity(1.0);
        }
    }

    void wash_effects() {
        password.setVisible(false);
        usun.setVisible(false);
        login.setVisible(false);
        login_change.setStyle("-fx-border-width: 1");
        password_change.setStyle("-fx-border-width: 1");
        profile_delete.setStyle("-fx-border-width: 1");
    }

    @FXML
    void avatar_swap(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Obrazy (*.png, *.jpg)", "*.png", "*.jpg");
        fileChooser.setTitle("Wybierz avatar");
        fileChooser.getExtensionFilters().add(imageFilter);
        File file = fileChooser.showOpenDialog(avatar2115.getScene().getWindow());
        if (file != null) {
            try (InputStream stream = new FileInputStream(file)) {
                Image image = new Image(stream);
                Main.user.setImage(image);
                BufferedImage bimage = SwingFXUtils.fromFXImage(image, null);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bimage, "jpg", bos);
                byte[] imageData = bos.toByteArray();
                int id = Main.user.getId();
                dbload.avatar_change(imageData, id);
                avatar.fireEvent(event);
            } catch (IOException ex) {
                ex.printStackTrace();
                Label info = (Label) grid.lookup("#info");
                info.setText("Avatar nie został zmieniony. Spróbuj później.");
                info.setOpacity(1.0);
            }
        }
    }

}
