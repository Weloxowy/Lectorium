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
 * Klasa {@code homeController} jest kontrolerem widoku domowego ekranu aplikacji.
 * Odpowiada za obsługę interakcji użytkownika, wyświetlanie informacji oraz inicjalizację widoku.
 * Dziedziczy po klasie {@link appParent}, aby działać w kontekście głównego okna aplikacji.
 */
public class homeController extends appParent {
    @FXML
    private ImageView avatar = new ImageView();

    @FXML
    private Label labelglowna;


    @FXML
    private TextField searchbar;

    @FXML
    private Text biblioteka_desc;

    @FXML
    private ImageView biblioteka_photo;

    @FXML
    private Label biblioteka_title;

    @FXML
    private ImageView image_a1;

    @FXML
    private ImageView image_b1;

    @FXML
    private ImageView image_b2;
    @FXML
    private Label nametag;

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
        labelglowna.setStyle("-fx-text-fill:#808080");
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
        Font pop_r_h2 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"), 14);
        Font pop_b_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-SemiBold.ttf"), 14);
        biblioteka_title.setFont(pop_b_h1);
        biblioteka_desc.setFont(pop_r_h2);
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
    void images_view() {
        double centerXa1 = image_a1.getBoundsInLocal().getWidth();
        double centerYa1 = image_a1.getBoundsInLocal().getHeight();
        Rectangle rectanglea1 = new Rectangle(centerXa1, centerYa1);
        rectanglea1.setArcWidth(20.0);
        rectanglea1.setArcHeight(20.0);
        image_a1.setClip(rectanglea1);

        double centerXb1 = image_b1.getBoundsInLocal().getWidth();
        double centerYb1 = image_b1.getBoundsInLocal().getHeight();
        Rectangle rectangleb1 = new Rectangle(centerXb1, centerYb1);
        rectangleb1.setArcWidth(20.0);
        rectangleb1.setArcHeight(20.0);
        image_b1.setClip(rectangleb1);

        double centerXb2 = image_b2.getBoundsInLocal().getWidth();
        double centerYb2 = image_b2.getBoundsInLocal().getHeight();
        Rectangle rectangleb2 = new Rectangle(centerXb2, centerYb2);
        rectangleb2.setArcWidth(20.0);
        rectangleb2.setArcHeight(20.0);
        image_b2.setClip(rectangleb2);

        double centerXbib = biblioteka_photo.getBoundsInLocal().getWidth();
        double centerYbib = biblioteka_photo.getBoundsInLocal().getHeight();
        Rectangle rectanglebib = new Rectangle(centerXbib, centerYbib);
        rectanglebib.setArcWidth(20.0);
        rectanglebib.setArcHeight(20.0);
        biblioteka_photo.setClip(rectanglebib);
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
     * Metoda {@code menu_panels} obsługuje kliknięcia na panele menu.
     * W zależności od klikniętego obrazka, wywołuje odpowiednią funkcję.
     *
     * @param event zdarzenie kliknięcia myszy
     */
    @FXML
    void menu_panels(MouseEvent event) {
        if (event.getSource().equals(image_a1)) {
            String query = "Houellebecq";
            katalog_clicked(event, query);
        }
        if (event.getSource().equals(image_b1)) {
            katalog_item(event, 130, false);
        }
        if (event.getSource().equals(image_b2)) {
            String query = "King";
            katalog_clicked(event, query);
        }
    }
}
