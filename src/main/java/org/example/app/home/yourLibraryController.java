package org.example.app.home;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.example.User;
import org.example.app.appParent;

/**
 * Klasa {@code yourLibraryController} jest kontrolerem widoku informacji o bibliotece.
 * Odpowiada za obsługę interakcji użytkownika, wyświetlanie informacji o blibliotece oraz inicjalizację widoku.
 * Dziedziczy po klasie {@link appParent}, aby działać w kontekście głównego okna aplikacji.
 */
public class yourLibraryController extends appParent {


    @FXML
    private Text gbs1;

    @FXML
    private Text gbs2;

    @FXML
    private Text gbs3;

    @FXML
    private ImageView gbs_dobrze_jest;
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
        avatar_view();
        image_view();
        labelbiblioteka.setStyle("-fx-text-fill:#808080");
    }

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
     * Metoda {@code images_view} ustawia clipping dla obrazków
     */
    void image_view() {
        double centerXa1 = gbs_dobrze_jest.getBoundsInLocal().getWidth();
        double centerYa1 = gbs_dobrze_jest.getBoundsInLocal().getHeight();
        Rectangle rectanglea1 = new Rectangle(centerXa1, centerYa1);
        rectanglea1.setArcWidth(20.0);
        rectanglea1.setArcHeight(20.0);
        gbs_dobrze_jest.setClip(rectanglea1);
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
        Font pop_b_h2 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-SemiBold.ttf"), 20);
        gbs1.setFont(pop_b_h2);
        gbs2.setFont(pop_b_h2);
        gbs3.setFont(pop_b_h2);
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
}
