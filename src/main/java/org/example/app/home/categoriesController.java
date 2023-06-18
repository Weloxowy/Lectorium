package org.example.app.home;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import org.example.User;
import org.example.app.appParent;

import static org.example.Main.db_getData;

/**
 * Klasa {@code categoriesController} reprezentuje kontroler widoku kategorii.
 * Zarządza interakcjami i logiką związaną z widokiem kategorii.
 * Rozszerza klasę {@link appParent}, aby dziedziczyć pewne funkcjonalności z klasy nadrzędnej.
 *
 */
public class categoriesController extends appParent {

    @FXML
    private ImageView avatar;
    @FXML
    private ImageView image_a1;
    @FXML
    private ImageView image_a2;
    @FXML
    private ImageView image_a3;

    @FXML
    private Label labelkategorie;
    @FXML
    private Label nametag;

    @FXML
    private TextField searchbar;

    @FXML
    private VBox cat_vbox;
    @FXML
    private VBox images_vbox;


    /**
     * Metoda {@code init} inicjalizuje kontroler kategorii.
     * Ustawia imię i nazwisko użytkownika, obrazek awatara, widoki kategorii oraz styl etykiety kategorii.
     *
     * @param imie     imię użytkownika
     * @param nazwisko nazwisko użytkownika
     */
    public void init(String imie, String nazwisko){
        nametag.setText(imie+" "+nazwisko);
        avatar.setImage(User.getInstance().getImage());
        avatar_view();
        images_view();
        categories_init();
        labelkategorie.setStyle("-fx-text-fill:#808080");

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
     * Metoda {@code search_init} inicjalizuje wyszukiwanie.
     * Pobiera z pola tekstowego zapytanie wyszukiwania i wywołuje metodę {@code katalog_clicked} z odpowiednimi parametrami.
     *
     * @param event zdarzenie kliknięcia myszą
     */
    @FXML
    void search_init(MouseEvent event){
        String query = searchbar.getText();
        katalog_clicked(event,query);
    }

    /**
     * Metoda {@code categories_init} inicjalizuje kategorie.
     * Pobiera dane kategorii z bazy danych i tworzy widok kategorii na podstawie tych danych.
     */
    void categories_init(){
        db_getData.getCategories();
        if(db_getData.categories != null) {
            Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-SemiBold.ttf"),18);
            Font pop_r_h2 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"),16);
            Label lab = (Label) images_vbox.lookup("#header");
            lab.setFont(pop_r_h1);
            Label cat = new Label("Kategorie:");
            cat.setFont(pop_r_h1);
            cat_vbox.getChildren().add(cat);
            int i=1;
            for (String[] tab : db_getData.categories) {
                    String input =tab[0]+" ("+tab[1]+" pozycji)";
                    String id = "cat"+i;
                    Label label = new Label(input);
                    label.setFont(pop_r_h2);
                    label.setVisible(true);
                    label.setTextAlignment(TextAlignment.CENTER);
                    label.setOnMouseClicked(event -> {
                    String categoryName = tab[0];
                        katalog_clicked(event,categoryName);
                });
                    label.setOnMouseEntered(event -> label.setUnderline(true));
                label.setOnMouseExited(event -> label.setUnderline(false));
                    cat_vbox.getChildren().add(label);
                    label.setId(id);
                    i++;
            }
        }
    }

    /**
     * Metoda {@code images_view} ustala wygląd pól reklamowych.
     */
    void images_view() {
        double centerXa1 = image_a1.getBoundsInLocal().getWidth();
        double centerYa1 = image_a1.getBoundsInLocal().getHeight();
        Rectangle rectanglea1 = new Rectangle(centerXa1, centerYa1);
        rectanglea1.setArcWidth(20.0);
        rectanglea1.setArcHeight(20.0);
        image_a1.setClip(rectanglea1);

        double centerXa2 = image_a2.getBoundsInLocal().getWidth();
        double centerYa2 = image_a2.getBoundsInLocal().getHeight();
        Rectangle rectanglea2 = new Rectangle(centerXa2, centerYa2);
        rectanglea2.setArcWidth(20.0);
        rectanglea2.setArcHeight(20.0);
        image_a2.setClip(rectanglea2);

        double centerXa3 = image_a3.getBoundsInLocal().getWidth();
        double centerYa3 = image_a3.getBoundsInLocal().getHeight();
        Rectangle rectanglea3 = new Rectangle(centerXa3, centerYa3);
        rectanglea3.setArcWidth(20.0);
        rectanglea3.setArcHeight(20.0);
        image_a3.setClip(rectanglea3);
    }

    /**
     * Metoda {@code menu_panels} obsługuje kliknięcie na panele reklamowe.
     * W zależności od klikniętego panelu, wywołuje odpowiednie metody.
     *
     * @param event zdarzenie kliknięcia myszą
     */
    @FXML
    void menu_panels(MouseEvent event){
        if(event.getSource().equals(image_a1)){
            katalog_item(event,130,false);
        }
        if(event.getSource().equals(image_a2)){
            String query = "Stephen King";
            katalog_clicked(event,query);
        }
        if(event.getSource().equals(image_a3)){
            String query = "Atwood";
            katalog_clicked(event,query);
        }

    }

}
