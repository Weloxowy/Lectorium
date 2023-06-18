package org.example.app.home;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.example.User;
import org.example.app.PasswordSkin;
import org.example.app.appParent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

import static org.example.Main.*;

/**
 * Klasa {@code yourProfileController} jest kontrolerem widoku informacji o profilu aktualnie zalogowanego użytkownika.
 * Odpowiada za obsługę interakcji użytkownika, wyświetlanie informacji o jego profilu, udostępnia funkcje modyfikacji
 * danych profilu oraz inicjalizację widoku.
 * Dziedziczy po klasie {@link appParent}, aby działać w kontekście głównego okna aplikacji.
 *
 */
public class yourProfileController extends appParent {
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

    /**
     * Metoda {@code avatar_view} ustawia clipping dla obrazka avatara, aby uzyskać efekt zaokrąglonych rogów.
     */
    void avatar_view() {
        int radius = 28;
        double centerX = avatar.getBoundsInLocal().getWidth() / 2.0;
        double centerY = avatar.getBoundsInLocal().getHeight() / 2.0;
        Circle clipCircle = new Circle(centerX, centerY, radius);
        avatar.setClip(clipCircle);
    }
    /**
     * Metoda {@code avatar_view} ustawia clipping dla większego obrazka avatara, aby uzyskać efekt zaokrąglonych rogów.
     */
    void avatar_view_profile() {
        int radius = 101;
        double centerX = avatar2115.getBoundsInLocal().getWidth() / 2.0;
        double centerY = avatar2115.getBoundsInLocal().getHeight() / 2.0;
        Circle clipCircle = new Circle(centerX, centerY, radius);
        avatar2115.setClip(clipCircle);
    }

    /**
     * Metoda inicjalizująca styl czcionki dla elementów w scenie.
     * Wywołuje również metodę inicjalizującą styl czcionki dla klasy nadrzędnej.
     *
     * @param scene obiekt {@link Scene} reprezentujący scenę JavaFX
     * @see appParent#font(Scene)
     */
    @FXML
    public void font(Scene scene) {
        super.font(scene);
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
        Label delete_account = (Label) grid.lookup("#delete_account");
        delete_account.setFont(pop_b_h2);
        Label change_login = (Label) grid.lookup("#change_login");
        change_login.setFont(pop_b_h2);
        Label change_password = (Label) grid.lookup("#change_password");
        change_password.setFont(pop_b_h2);
    }
    /**
     * Metoda {@code init} inicjalizuje widok ekranu domowego.
     * Ustawia tekst w polu nametag, wczytuje obrazek awatara użytkownika oraz wywołuje metody odpowiedzialne za wyświetlanie obrazków i ustawienie stylu labelglowna.
     *
     * @param imie     imię użytkownika
     * @param nazwisko nazwisko użytkownika
     */
    public void init(String imie, String nazwisko) {
        nametag.setText(imie + " " + nazwisko);
        avatar.setImage(User.getInstance().getImage());
        avatar2115.setImage(User.getInstance().getImage());
        Name.setText("Imie: " + imie);
        Surname.setText("Nazwisko: " + nazwisko);
        if(User.getInstance().getCzy_admin().contentEquals("T")){
            Rank.setText(Rank.getText()+": administrator");
        }
        else{
            Rank.setText(Rank.getText()+": czytelnik");
        }
        curr_password.setSkin(new PasswordSkin(curr_password));
        new_password.setSkin(new PasswordSkin(new_password));
        delete_password.setSkin(new PasswordSkin(delete_password));
        avatar_view();
        avatar_view_profile();
        wash_effects();
    }

    /**
     * Metoda {@code search_init} obsługuje inicjalizację wyszukiwania po wciśnięciu przycisku lub klawisza Enter w polu searchbar.
     * Pobiera zapytanie z pola searchbar i przekazuje je do metody katalog_clicked w celu obsługi wyszukiwania.
     *
     * @param event zdarzenie kliknięcia myszy
     */
    @FXML
    void search_init(MouseEvent event) {
        String query = searchbar.getText();
        katalog_clicked(event, query);
    }

    /**
     * Obsługuje zdarzenie kliknięcia przycisku "Hasło".
     * Wywołuje metodę `wash_effects()` i ustawia widoczność pola "password" na true.
     *
     * @param event obiekt reprezentujący zdarzenie myszy
     */
    @FXML
    void haslo_c(MouseEvent event) {
        wash_effects();
        password.setVisible(true);

    }

    /**
     * Obsługuje zdarzenie kliknięcia przycisku "Login".
     * Wywołuje metodę `wash_effects()` i ustawia widoczność pola "login" na true.
     *
     * @param event obiekt reprezentujący zdarzenie myszy
     */
    @FXML
    void login_c(MouseEvent event) {
        wash_effects();
        login.setVisible(true);

    }

    /**
     * Obsługuje zdarzenie kliknięcia przycisku "Zmień login".
     * Wywołuje metodę `wash_effects()` i wykonuje operacje zmiany loginu w bazie danych.
     * Wyświetla odpowiednie komunikaty na podstawie rezultatu operacji.
     *
     * @param event obiekt reprezentujący zdarzenie myszy
     */
    @FXML
    void login_change(MouseEvent event) {
        commit_login.setStyle("-fx-border-width: 1");
        commit_login.setStyle("-fx-border-color: #004aad");
        String old_l = curr_login.getText();
        String new_l = new_login.getText();
        Label info = (Label) grid.lookup("#info");
        info.setOpacity(0.0);
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(2),
                event2 -> info.setOpacity(0.0)
        ));
        if (db_updateData.loginUpdate(new_l, User.getInstance().getId(), old_l)) {
            info.setText("Zmiana wykonana pomyślnie.");
            info.setOpacity(1.0);
        } else {
            commit_login.setStyle("-fx-border-width: 2");
            commit_login.setStyle("-fx-border-color: red");
            info.setText("Wystapil błąd. Spróbuj ponownie.");
            info.setOpacity(1.0);
        }
        timeline.play();
    }

    /**
     * Obsługuje zdarzenie kliknięcia przycisku "Zmień hasło".
     * Wywołuje metodę `wash_effects()` i wykonuje operacje zmiany hasła w bazie danych.
     * Wyświetla odpowiednie komunikaty na podstawie rezultatu operacji.
     *
     * @param event obiekt reprezentujący zdarzenie myszy
     */
    @FXML
    void password_change(MouseEvent event) {
        commit_login.setStyle("-fx-border-width: 1");
        commit_login.setStyle("-fx-border-color: #004aad");
        String old_p = curr_password.getText();
        String new_p = new_password.getText();
        Label info = (Label) grid.lookup("#info");
        info.setOpacity(0.0);
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(2),
                event2 -> info.setOpacity(0.0)
        ));
        if (db_updateData.updatePassword(new_p, User.getInstance().getId(), old_p)) {
            info.setText("Zmiana wykonana pomyślnie.");
            info.setOpacity(1.0);
        } else {
            commit_password.setStyle("-fx-border-width: 2");
            commit_password.setStyle("-fx-border-color: red");
            info.setText("Wystapil błąd. Spróbuj ponownie.");
            info.setOpacity(1.0);
        }
        timeline.play();
    }

    /**
     * Obsługuje zdarzenie kliknięcia przycisku "Profil".
     * Wywołuje metodę `wash_effects()` i ustawia widoczność pola "usun" na true.
     * Ustawia styl ramki przycisku "profile_delete".
     *
     * @param event obiekt reprezentujący zdarzenie myszy
     */
    @FXML
    void profile_d(MouseEvent event) {
        wash_effects();
        usun.setVisible(true);
        profile_delete.setStyle("-fx-border-width: 2");
    }

    /**
     * Obsługuje zdarzenie kliknięcia przycisku "Usuń profil".
     * Wywołuje metodę `wash_effects()` i wykonuje operacje usunięcia profilu z bazy danych.
     * Wyświetla odpowiednie komunikaty na podstawie rezultatu operacji.
     *
     * @param event obiekt reprezentujący zdarzenie myszy
     */
    @FXML
    void delete_profile(MouseEvent event) {
        commit_delete.setStyle("-fx-border-width: 1");
        commit_delete.setStyle("-fx-border-color: #004aad");
        String password = delete_password.getText();
        Label info = (Label) grid.lookup("#info");
        info.setOpacity(0.0);
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(2),
                event2 -> info.setOpacity(0.0)
        ));
        if (db_deleteData.deleteProfile(password, User.getInstance().getId())) {
            info.setText("Zmiana wykonana pomyślnie.");
            info.setOpacity(1.0);
        } else {
            commit_delete.setStyle("-fx-border-width: 2");
            commit_delete.setStyle("-fx-border-color: red");
            info.setText("Wystapil błąd. Spróbuj ponownie.");
            info.setOpacity(1.0);
        }
        timeline.play();
    }

    /**
     * Resetuje efekty wizualne ustawione na elementach interfejsu.
     * Ukrywa pola "password", "usun" i "login".
     */
    void wash_effects() {
        password.setVisible(false);
        usun.setVisible(false);
        login.setVisible(false);
    }

    /**
     * Obsługuje zdarzenie zamiany avatara.
     * Otwiera okno dialogowe umożliwiające wybór pliku z obrazem.
     * Po wybraniu pliku, przekształca go na obiekt Image, zmieniając aktualny avatar użytkownika.
     * Następnie przekształca obraz na format BufferedImage, a następnie na tablicę bajtów.
     * Wywołuje operację zmiany avatara w bazie danych, a następnie ponownie inicjuje zdarzenie dla elementu "avatar".
     * W przypadku wystąpienia błędu podczas operacji, wyświetla odpowiedni komunikat.
     *
     * @param event obiekt reprezentujący zdarzenie myszy
     */
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
                User.getInstance().setImage(image);
                BufferedImage bimage = SwingFXUtils.fromFXImage(image, null);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bimage, "jpg", bos);
                byte[] imageData = bos.toByteArray();
                int id = User.getInstance().getId();
                db_updateData.changeAvatar(imageData, id);
                avatar.fireEvent(event);
            } catch (IOException ex) {
                ex.printStackTrace();
                Label info = (Label) grid.lookup("#info");
                info.setText("Avatar nie został zmieniony. Spróbuj później.");
                Timeline timeline = new Timeline(new KeyFrame(
                        Duration.seconds(2),
                        event2 -> info.setOpacity(0.0)
                ));
                info.setOpacity(1.0);
                timeline.play();
            }
        }
    }

}
