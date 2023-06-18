package org.example.app.home;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import org.example.Main;
import org.example.User;
import org.example.app.appParent;

import java.util.HashMap;
import java.util.Map;

import static org.example.Main.db_getData;

/**
 * Klasa {@code latestBooksController} reprezentuje kontroler widoku najnowszych książek.
 * Odpowiada za logikę interakcji użytkownika z widokiem, taką jak wyszukiwanie, wyświetlanie informacji o książkach itp.
 *
 * @see appParent
 */
public class latestBooksController extends appParent {

    @FXML
    private ImageView avatar;
    @FXML
    private Label nametag;

    @FXML
    private TextField searchbar;

    @FXML
    private VBox vbox1;
    @FXML
    private VBox vbox2;
    @FXML
    private VBox vbox3;
    @FXML
    private VBox vbox4;
    @FXML
    private VBox vbox5;
    @FXML
    private VBox vbox6;
    @FXML
    private VBox vbox7;
    @FXML
    private VBox vbox8;

    @FXML
    private Label labelnowosci;

    @FXML
    private GridPane grid_nowosci;

    /**
     * Metoda {@code search_init} inicjalizuje wyszukiwanie.
     * Pobiera z pola tekstowego zapytanie wyszukiwania i wywołuje metodę {@code katalog_clicked} z odpowiednimi parametrami.
     *
     * @param event zdarzenie kliknięcia myszą
     */
    @FXML
    void search_init(MouseEvent event) {
        String query = searchbar.getText();
        katalog_clicked(event, query);
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
        Font ssp_sb_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/SourceSerifPro-SemiBold.ttf"), 25);
        Label header = (Label) grid_nowosci.lookup("#header");
        header.setFont(ssp_sb_h1);
    }

    /**
     * Metoda inicjalizująca kontroler widoku najnowszych książek.
     * Ustawia informacje o użytkowniku, inicjalizuje dane dotyczące najnowszych książek,
     * a także wyświetla odpowiednie elementy w interfejsie użytkownika.
     *
     * @param imie     imię użytkownika
     * @param nazwisko nazwisko użytkownika
     */
    public void init(String imie, String nazwisko) {
        db_getData.getTop();
        nametag.setText(imie + " " + nazwisko);
        avatar.setImage(User.getInstance().getImage());
        avatar_view();
        int i = 1;
        grid_nowosci.setPrefWidth(Region.USE_COMPUTED_SIZE);
        grid_nowosci.setPrefHeight(Region.USE_COMPUTED_SIZE);

        AnchorPane.setTopAnchor(grid_nowosci, 10.0);
        AnchorPane.setLeftAnchor(grid_nowosci, 10.0);
        AnchorPane.setRightAnchor(grid_nowosci, 10.0);
        AnchorPane.setBottomAnchor(grid_nowosci, 10.0);

        grid_nowosci.setHgap(55.0);
        grid_nowosci.setVgap(30.0);
        Map<Integer, VBox> vboxMap = new HashMap<>();
        vboxMap.put(1, vbox1);
        vboxMap.put(2, vbox2);
        vboxMap.put(3, vbox3);
        vboxMap.put(4, vbox4);
        vboxMap.put(5, vbox5);
        vboxMap.put(6, vbox6);
        vboxMap.put(7, vbox7);
        vboxMap.put(8, vbox8);

        for (String[] tab : db_getData.top) {
            int id_katalog = Integer.parseInt(tab[0]);
            String nazwa = tab[1];
            String nazwa_autora = tab[2];
            VBox vbox = vboxMap.get(i);
            setLabelText(vbox, nazwa, nazwa_autora, id_katalog, i);
            i++;
        }
        labelnowosci.setStyle("-fx-text-fill:#808080");
    }

    /**
     * Metoda ustawiająca teksty etykiety  i obraz dla danego VBoxa.
     *
     * @param vb         obiekt VBox, dla którego ustawiany jest tekst etykiety
     * @param nazwa      nazwa książki
     * @param autor      nazwa autora
     * @param id_katalog identyfikator katalogowy książki
     * @param i          numer indeksu VBoxa
     */
    public void setLabelText(VBox vb, String nazwa, String autor, int id_katalog, int i) {
        Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"), 14);
        Label label_a = (Label) vb.lookup("#author" + i);
        if (label_a != null) {
            label_a.setText(autor);
            label_a.setFont(pop_r_h1);
        }
        Label label_n = (Label) vb.lookup("#title" + i);
        if (label_n != null) {
            label_n.setText(nazwa);
            if (label_a != null) {
                label_a.setFont(pop_r_h1);
            }
        }
        db_getData.getCover(id_katalog);
        ImageView image = (ImageView) vb.lookup("#cover" + i);
        if (image != null) {
            image.setImage(Main.kat.getOkladka());
            double centerX = image.getBoundsInLocal().getWidth();
            double centerY = image.getBoundsInLocal().getHeight();
            Rectangle rectangle = new Rectangle(centerX, centerY);
            rectangle.setArcWidth(10.0);
            rectangle.setArcHeight(10.0);
            image.setClip(rectangle);
        }
    }

    /**
     * Metoda {@code avatar_view} ustala widok awatara.
     * Tworzy okrągłe przycięcie dla awatara.
     */
    void avatar_view() {
        int radius = 28;
        double centerX = avatar.getBoundsInLocal().getWidth() / 2.0;
        double centerY = avatar.getBoundsInLocal().getHeight() / 2.0;
        Circle clipCircle = new Circle(centerX, centerY, radius);
        avatar.setClip(clipCircle);
    }


    /**
     * Metoda ustawiająca przejście do danej książki po kliknięciu jej okładki.
     */
    @FXML
    void refer_to_book(MouseEvent event) {
        Map<Integer, VBox> vboxMap = new HashMap<>();
        vboxMap.put(1, vbox1);
        vboxMap.put(2, vbox2);
        vboxMap.put(3, vbox3);
        vboxMap.put(4, vbox4);
        vboxMap.put(5, vbox5);
        vboxMap.put(6, vbox6);
        vboxMap.put(7, vbox7);
        vboxMap.put(8, vbox8);
        if (db_getData.books.size() == 0) {
            db_getData.getBooks();
        }
        for (Map.Entry<Integer, VBox> act : vboxMap.entrySet()) {
            if (event.getSource().equals(act.getValue())) {
                String[] st = db_getData.top.get(act.getKey() - 1);
                katalog_item(event, Integer.parseInt(st[0]), false);
            }
        }
    }
}
