package org.example.app.home;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import org.example.User;
import org.example.app.appParent;

/**
 * Klasa {@code contactController} jest kontrolerem widoku kontaktu z biblioteką.
 * Odpowiada za obsługę interakcji użytkownika, wyświetlanie informacji oraz inicjalizację widoku.
 * Dziedziczy po klasie {@link appParent}, aby działać w kontekście głównego okna aplikacji.
 */
public class contactController extends appParent {
    @FXML
    private VBox kontakt_id;
    @FXML
    private ImageView contact_img;
    @FXML
    private ImageView avatar;
    @FXML
    private Label labelkontakt;
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
        images_view();
        labelkontakt.setStyle("-fx-text-fill:#808080");
    }

    /**
     * Metoda {@code images_view} ustawia clipping dla obrazków
     */
    void images_view() {
        double centerXa1 = contact_img.getBoundsInLocal().getWidth();
        double centerYa1 = contact_img.getBoundsInLocal().getHeight();
        Rectangle rectanglea1 = new Rectangle(centerXa1, centerYa1);
        rectanglea1.setArcWidth(40.0);
        rectanglea1.setArcHeight(40.0);
        contact_img.setClip(rectanglea1);
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
     * Metoda inicjalizująca styl czcionki dla elementów w scenie.
     * Wywołuje również metodę inicjalizującą styl czcionki dla klasy nadrzędnej.
     *
     * @param scene obiekt {@link Scene} reprezentujący scenę JavaFX
     * @see appParent#font(Scene)
     */
    @Override
    public void font(Scene scene) {
        super.font(scene);
        Font pop_b_h2 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-SemiBold.ttf"), 30);
        Label contact1 = (Label) kontakt_id.lookup("#contact1");
        contact1.setFont(pop_b_h2);
        Label contact2 = (Label) kontakt_id.lookup("#contact2");
        contact2.setFont(pop_b_h2);
        Label contact3 = (Label) kontakt_id.lookup("#contact3");
        contact3.setFont(pop_b_h2);
        Label contact4 = (Label) kontakt_id.lookup("#contact4");
        contact4.setFont(pop_b_h2);
        Label contact5 = (Label) kontakt_id.lookup("#contact5");
        contact5.setFont(pop_b_h2);
        Label contact6 = (Label) kontakt_id.lookup("#contact6");
        contact6.setFont(pop_b_h2);
        Label contact7 = (Label) kontakt_id.lookup("#contact7");
        contact7.setFont(pop_b_h2);
        Label contact8 = (Label) kontakt_id.lookup("#contact8");
        contact8.setFont(pop_b_h2);
        Label contact9 = (Label) kontakt_id.lookup("#contact9");
        contact9.setFont(pop_b_h2);
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
