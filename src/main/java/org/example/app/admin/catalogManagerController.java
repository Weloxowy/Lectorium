package org.example.app.admin;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.example.Katalog;
import org.example.User;
import org.example.app.appParent;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.example.Main.*;


/**
 * Klasa kontrolera zarządzającego katalogiem.
 */
public class catalogManagerController extends appParent {

    @FXML
    private AnchorPane anchortable;

    @FXML
    private ImageView avatar;

    @FXML
    private Button dodaj_egzemplarz;

    @FXML
    private Label labelkatalog;

    @FXML
    private Label labelwypozyczenia;

    @FXML
    private ImageView logout;

    @FXML
    private Label nametag;

    @FXML
    private Pane pane_id_masked;

    @FXML
    private ImageView search_button;

    @FXML
    private ImageView search_button1;

    @FXML
    private TextField searchbar;

    @FXML
    private TextField searchbar1;


    @FXML
    private GridPane grid;

    boolean zapamietaj;


    /**
     * Inicjalizuje kontroler.
     *
     * @param imie     imię użytkownika
     * @param nazwisko nazwisko użytkownika
     */
    public void init(String imie, String nazwisko) {
        zapamietaj = true;
        nametag.setText(imie + " " + nazwisko);
        avatar.setImage(User.getInstance().getImage());
        avatar_view();
        Katalog_lista(anchortable, searchbar1);
        pane_id_masked.setVisible(false);
        labelwypozyczenia.setStyle("-fx-text-fill:#808080");
        AnchorPane.setTopAnchor(grid, 0.0);
        AnchorPane.setLeftAnchor(grid, 0.0);
        AnchorPane.setRightAnchor(grid, 0.0);
        AnchorPane.setBottomAnchor(grid, 0.0);
        AnchorPane.setTopAnchor(pane_id_masked, 0.0);
        AnchorPane.setLeftAnchor(pane_id_masked, 0.0);
        AnchorPane.setRightAnchor(pane_id_masked, 0.0);
        AnchorPane.setBottomAnchor(pane_id_masked, 0.0);
        if (db_getData.books.isEmpty()) {
            db_getData.getBooks();
        }
    }

    final TableView<Katalog> lista = new TableView<>();

    /**
     * Tworzy tabelę z listą książek w określonym panelu.
     *
     * @param anchortable panel, w którym ma zostać wyświetlona tabela
     * @param searchbar   pole tekstowe do filtrowania listy książek
     */
    public void Katalog_lista(AnchorPane anchortable, TextField searchbar) {
        db_getData.getBooks(); //pobieranie danych
        ObservableList<Katalog> items = FXCollections.observableArrayList();
        //tworzenie kolumn tabeli
        TableColumn<Katalog, ?> idCol = new TableColumn<>("Id");
        idCol.setMinWidth(anchortable.getPrefWidth() * 0.15);
        idCol.setCellValueFactory(
                new PropertyValueFactory<>("id_katalog"));
        idCol.setVisible(false);

        TableColumn<Katalog, ?> autorCol = new TableColumn<>("Autor");
        autorCol.setMinWidth(anchortable.getPrefWidth() * 0.15);
        autorCol.setCellValueFactory(
                new PropertyValueFactory<>("nazwa_autora"));

        TableColumn<Katalog, ?> nazwaCol = new TableColumn<>("Nazwa");
        nazwaCol.setMinWidth(anchortable.getPrefWidth() * 0.25);
        nazwaCol.setCellValueFactory(
                new PropertyValueFactory<>("nazwa"));

        TableColumn<Katalog, ?> rokCol = new TableColumn<>("Rok wydania");
        rokCol.setMinWidth(anchortable.getPrefWidth() * 0.1);
        rokCol.setCellValueFactory(
                new PropertyValueFactory<>("rok_wydania"));

        TableColumn<Katalog, ?> wydanieCol = new TableColumn<>("Wydanie");
        wydanieCol.setMinWidth(anchortable.getPrefWidth() * 0.1);
        wydanieCol.setCellValueFactory(
                new PropertyValueFactory<>("wydanie"));

        TableColumn<Katalog, ?> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setMinWidth(anchortable.getPrefWidth() * 0.1);
        isbnCol.setCellValueFactory(
                new PropertyValueFactory<>("isbn"));

        TableColumn<Katalog, ?> jezykCol = new TableColumn<>("Język");
        jezykCol.setMinWidth(anchortable.getPrefWidth() * 0.1);
        jezykCol.setCellValueFactory(
                new PropertyValueFactory<>("jezyk"));

        TableColumn<Katalog, ?> uwagiCol = new TableColumn<>("Uwagi");
        uwagiCol.setMinWidth(anchortable.getWidth() * 0.4);
        uwagiCol.setCellValueFactory(
                new PropertyValueFactory<>("uwagi"));
        for (String[] tab : db_getData.books) {
            items.add(new Katalog(Integer.parseInt(tab[0]), tab[1], tab[2], tab[3], tab[4], tab[5], tab[6], tab[7], tab[8], tab[9]));
        }
        //dodawanie elementów do tabeli
        lista.setItems(items);
        //ustawianie stałej wysokości wierszy
        lista.setFixedCellSize(30);
        //dodawanie kolumn do tabeli
        lista.getColumns().addAll(idCol, nazwaCol, autorCol, rokCol, wydanieCol, isbnCol, jezykCol, uwagiCol);
        //ustawianie bindów, żeby tabela się zwiększała wraz z oknem
        lista.prefWidthProperty().bind(anchortable.widthProperty());
        lista.prefHeightProperty().bind(anchortable.heightProperty());
        //zakotwiczenie listy w anchortable
        anchortable.getChildren().addAll(lista);
        AnchorPane.setLeftAnchor(anchortable, 0.0);
        AnchorPane.setBottomAnchor(anchortable, 0.0);
        AnchorPane.setRightAnchor(anchortable, 0.0);
        //dodawanie wyglądu
        lista.getStylesheets().add("/fxml.home/home.css");

        //dodawanie aktywnego filtra ksiazek po tytule, autorze i gatunku
        FilteredList<Katalog> filteredList = new FilteredList<>(items, b -> true);

        searchbar.textProperty().addListener((observable, newValue, oldValue) -> filteredList.setPredicate(Katalog -> {
            if (newValue.isEmpty() || newValue.isBlank()) {
                return true;
            }

            String searchword = newValue.toLowerCase();

            if (Katalog.getNazwa().toLowerCase().contains(searchword)) {
                return true;
            }
            if (Katalog.getNazwa_autora().toLowerCase().contains(searchword)) {
                return true;
            }
            return Katalog.getIsbn().toLowerCase().contains(searchword);

        }));

        SortedList<Katalog> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(lista.comparatorProperty());

        lista.setItems(sortedList);

        //gdy klikniemy 2 razy, przechodzimy do sceny wyłącznie dla danej książki i jej egzemplarzy.
        lista.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                TablePosition<Katalog, ?> tablePosition = lista.getSelectionModel().getSelectedCells().get(0);
                Integer data = (Integer) idCol.getCellObservableValue(tablePosition.getRow()).getValue();
                zapamietaj = false;
                katalog_item(event, data, true);
            }
        });
    }

    /**
     * Ustawia styl czcionki dla elementów unikalnych dla tej sceny.
     *
     * @param scene scena, dla której ma zostać ustawiony styl czcionki
     */
    @FXML
    public void font(Scene scene) {
        super.font(scene);
        Font pop_r_h2 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"), 14);
        Font pop_b_h2 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-SemiBold.ttf"), 14);
        Label pane_tytul = (Label) scene.lookup("#pane_tytul");
        pane_tytul.setFont(pop_b_h2);
        TextField pane_txt_1 = (TextField) scene.lookup("#pane_txt_1");
        pane_txt_1.setFont(pop_r_h2);
        TextField pane_txt_2 = (TextField) scene.lookup("#pane_txt_2");
        pane_txt_2.setFont(pop_r_h2);
        TextField pane_txt_3 = (TextField) scene.lookup("#pane_txt_3");
        pane_txt_3.setFont(pop_r_h2);
        TextField pane_txt_4 = (TextField) scene.lookup("#pane_txt_4");
        pane_txt_4.setFont(pop_r_h2);
        TextField pane_txt_5 = (TextField) scene.lookup("#pane_txt_5");
        pane_txt_5.setFont(pop_r_h2);
        TextField pane_txt_6 = (TextField) scene.lookup("#pane_txt_6");
        pane_txt_6.setFont(pop_r_h2);
        TextField pane_txt_7 = (TextField) scene.lookup("#pane_txt_7");
        pane_txt_7.setFont(pop_r_h2);
        TextField pane_txt_8 = (TextField) scene.lookup("#pane_txt_8");
        pane_txt_8.setFont(pop_r_h2);
        TextField pane_txt_9 = (TextField) scene.lookup("#pane_txt_9");
        pane_txt_9.setFont(pop_r_h2);
        TextField pane_txt_10 = (TextField) scene.lookup("#pane_txt_10");
        pane_txt_10.setFont(pop_r_h2);
        Button pane_button = (Button) scene.lookup("#pane_button");
        pane_button.setFont(pop_b_h2);
        Label pane_result_msg = (Label) pane_id_masked.lookup("#pane_result_msg");
        pane_result_msg.setFont(pop_b_h2);

    }

    /**
     * Ustawia sposób wyświetlania się awatara użytkownika.
     */
    void avatar_view() {
        int radius = 28;
        double centerX = avatar.getBoundsInLocal().getWidth() / 2.0;
        double centerY = avatar.getBoundsInLocal().getHeight() / 2.0;
        Circle clipCircle = new Circle(centerX, centerY, radius);
        avatar.setClip(clipCircle);
    }

    /**
     * Inicjuje wyszukiwanie na podstawie podanego zapytania.
     *
     * @param event zdarzenie kliknięcia przycisku
     */
    @FXML
    void search_init(MouseEvent event) {
        String query = searchbar.getText();
        katalog_clicked(event, query);
    }

    /**
     * Obsługuje dodawanie nowego egzemplarza książki. Metoda ustawia nazwy pól.
     * Przy kliknięciu w guzik wywołuje funkcje z bazy i zwraca odpowiedni wynik.
     */
    @FXML
    public void dodaj_egz_button() {
        Label pane_tytul = (Label) pane_id_masked.lookup("#pane_tytul");
        pane_tytul.setText("Dodaj egzemplarz książki");
        TextField pane_txt_1 = (TextField) pane_id_masked.lookup("#pane_txt_1");
        pane_txt_1.setOpacity(1.0);
        pane_txt_1.setPromptText("Podaj nazwę książki");
        TextField pane_txt_2 = (TextField) pane_id_masked.lookup("#pane_txt_2");
        pane_txt_2.setOpacity(1.0);
        pane_txt_2.setPromptText("Podaj lokalizację egzemplarza");
        Button pane_button = (Button) pane_id_masked.lookup("#pane_button");
        pane_button.setText("Dodaj egzemplarz");
        pane_button.setOnMouseClicked(event -> { //lambda wykonywana gdy klikniemy w guzik
            Label pane_result_msg = (Label) pane_id_masked.lookup("#pane_result_msg");
            int ret = db_setData.setNewCopy("T", pane_txt_2.getText(), pane_txt_1.getText());
            if (ret > 0) {
                pane_result_msg.setText("Egzemplarz został dodany");
            } else {
                pane_result_msg.setText("Egzemplarz nie został dodany");
            }
            Timeline timeline = new Timeline(new KeyFrame( //timeline uruchamia się i wyświetla komunikat przez 3 sekundy
                    Duration.seconds(3),
                    event2 -> {
                        pane_result_msg.setOpacity(0.0);
                        pane_button.setDisable(false);
                    }
            ));
            pane_result_msg.setOpacity(1.0);
            pane_button.setDisable(true);
            System.out.println("TT");
            timeline.play();
        });
        pane_id_masked.setVisible(true);
    }

    /**
     * Obsługuje usunięcie egzemplarza książki. Metoda ustawia nazwy pól.
     * Przy kliknięciu w guzik wywołuje funkcje z bazy i zwraca odpowiedni wynik.
     */
    @FXML
    public void usun_egz_button() {
        Label pane_tytul = (Label) pane_id_masked.lookup("#pane_tytul");
        pane_tytul.setText("Usuń egzemplarz książki");
        TextField pane_txt_1 = (TextField) pane_id_masked.lookup("#pane_txt_1");
        pane_txt_1.setOpacity(1.0);
        pane_txt_1.setPromptText("Podaj nazwę książki którą chcesz usunąć");
        TextField pane_txt_2 = (TextField) pane_id_masked.lookup("#pane_txt_2");
        pane_txt_2.setOpacity(1.0);
        pane_txt_2.setPromptText("Podaj id egzemplarza");
        Button pane_button = (Button) pane_id_masked.lookup("#pane_button");
        pane_button.setText("Usuń egzemplarz");
        pane_button.setOnMouseClicked(event -> {
            Label pane_result_msg = (Label) pane_id_masked.lookup("#pane_result_msg");
            int ret = db_deleteData.deleteCopyFromDatabase(pane_txt_1.getText(), pane_txt_2.getText());
            if (ret > 0) {
                pane_result_msg.setText("Egzemplarz został usunięty");
            } else {
                pane_result_msg.setText("Egzemplarz nie został usunięty");
            }
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(3),
                    event2 -> pane_result_msg.setOpacity(0.0)
            ));
            pane_result_msg.setOpacity(1.0);

            timeline.play();
        });
        pane_id_masked.setVisible(true);
    }

    /**
     * Obsługuje usunięcie książki z katalogu. Metoda ustawia nazwy pól.
     * Przy kliknięciu w guzik wywołuje funkcje z bazy i zwraca odpowiedni wynik.
     */
    @FXML
    public void usun_pozycje_button() {
        Label pane_tytul = (Label) pane_id_masked.lookup("#pane_tytul");
        pane_tytul.setText("Usuń pozycje książki");
        TextField pane_txt_1 = (TextField) pane_id_masked.lookup("#pane_txt_1");
        pane_txt_1.setOpacity(1.0);
        pane_txt_1.setPromptText("Podaj nazwę książki którą chcesz usunąć");

        TextField pane_txt_2 = (TextField) pane_id_masked.lookup("#pane_txt_2");
        pane_txt_2.setOpacity(1.0);
        pane_txt_2.setPromptText("Podaj isbn książki");

        TextField pane_txt_3 = (TextField) pane_id_masked.lookup("#pane_txt_3");
        pane_txt_3.setOpacity(1.0);
        pane_txt_3.setPromptText("Podaj nazwę gatunku książki");

        TextField pane_txt_4 = (TextField) pane_id_masked.lookup("#pane_txt_4");
        pane_txt_4.setOpacity(1.0);
        pane_txt_4.setPromptText("Podaj nazwę wydawnictwa książki");

        Button pane_button = (Button) pane_id_masked.lookup("#pane_button");
        pane_button.setText("Usuń pozycje");
        pane_button.setOnMouseClicked(event -> {
            Label pane_result_msg = (Label) pane_id_masked.lookup("#pane_result_msg");
            int ret = db_deleteData.deleteBookFromDatabase(pane_txt_1.getText(), pane_txt_2.getText(), pane_txt_3.getText(), pane_txt_4.getText());
            if (ret > 0) {
                pane_result_msg.setText("Pozycja usunięta");
            } else {
                pane_result_msg.setText("Pozycja nie została usunięta");
            }
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(3),
                    event2 -> pane_result_msg.setOpacity(0.0)
            ));
            pane_result_msg.setOpacity(1.0);

            timeline.play();
        });
        pane_id_masked.setVisible(true);
    }

    /**
     * Obsługuje modyfikacje egzemplarza książki. Metoda ustawia nazwy pól.
     * Przy kliknięciu w guzik wywołuje funkcje z bazy i zwraca odpowiedni wynik.
     */
    @FXML
    public void zmodyfikuj_egzemplarze() {
        Label pane_tytul = (Label) pane_id_masked.lookup("#pane_tytul");
        pane_tytul.setText("Zmodyfikuj egzemplarze książek");
        TextField pane_txt_1 = (TextField) pane_id_masked.lookup("#pane_txt_1");
        pane_txt_1.setOpacity(1.0);
        pane_txt_1.setPromptText("Podaj id egzemplarza");

        TextField pane_txt_2 = (TextField) pane_id_masked.lookup("#pane_txt_2");
        pane_txt_2.setOpacity(1.0);
        pane_txt_2.setPromptText("Podaj czy książka jest dostępna");

        TextField pane_txt_3 = (TextField) pane_id_masked.lookup("#pane_txt_3");
        pane_txt_3.setOpacity(1.0);
        pane_txt_3.setPromptText("Podaj lokalizacje książki");

        TextField pane_txt_4 = (TextField) pane_id_masked.lookup("#pane_txt_4");
        pane_txt_4.setOpacity(1.0);
        pane_txt_4.setPromptText("Podaj nazwę książki");

        Button pane_button = (Button) pane_id_masked.lookup("#pane_button");
        pane_button.setText("Modyfikuj egzemplarz");
        pane_button.setOnMouseClicked(event -> {
            Label pane_result_msg = (Label) pane_id_masked.lookup("#pane_result_msg");
            int ret = db_updateData.modifyCopy(pane_txt_2.getText(), pane_txt_3.getText(), pane_txt_4.getText(), pane_txt_1.getText());
            if (ret > 0) {
                pane_result_msg.setText("Modyfikacja udana");
            } else {
                pane_result_msg.setText("Modyfikacja się nie powiodła");
            }
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(3),
                    event2 -> pane_result_msg.setOpacity(0.0)
            ));
            pane_result_msg.setOpacity(1.0);

            timeline.play();
        });
        pane_id_masked.setVisible(true);
    }

    /**
     * Obsługuje dodanie książki do katalogu. Metoda ustawia nazwy pól.
     * Przy kliknięciu w guzik wywołuje funkcje z bazy i zwraca odpowiedni wynik.
     * Wynik jest zwracany w postaci 3-elementowej tablicy typu boolean.
     * Odpowiadają one odpowiednio istnienie danego rekordu dla tabel: Autor,Gatunek,Wydawnictwo.
     * Wartość true oznacza, że wprowadzony rodzaj elementu nie istnieje w bazie, false - przeciwnie.
     * Następnie ustawiamy pole sec na true i wywołujemy komunikat.
     * Jeżeli użytkownik kliknie znowu w guzik, wchodzimy w drugi obieg tej samej funkcji i dodajemy
     * nieistniejące dotychczas parametry do docelowych tabel.
     * Na sam koniec przeprowadzamy dodanie książki do katalogu.
     */
    @FXML
    public void add_position() {
        Label pane_tytul = (Label) pane_id_masked.lookup("#pane_tytul");
        pane_tytul.setText("Dodaj pozycje książki");
        TextField pane_txt_1 = (TextField) pane_id_masked.lookup("#pane_txt_1");
        pane_txt_1.setOpacity(1.0);
        pane_txt_1.setPromptText("Podaj nazwę książki którą chcesz dodac");

        TextField pane_txt_2 = (TextField) pane_id_masked.lookup("#pane_txt_2");
        pane_txt_2.setOpacity(1.0);
        pane_txt_2.setPromptText("Podaj rok wydania książki");

        TextField pane_txt_3 = (TextField) pane_id_masked.lookup("#pane_txt_3");
        pane_txt_3.setOpacity(1.0);
        pane_txt_3.setPromptText("Podaj wydanie książki");

        TextField pane_txt_4 = (TextField) pane_id_masked.lookup("#pane_txt_4");
        pane_txt_4.setOpacity(1.0);
        pane_txt_4.setPromptText("Podaj isbn książki");

        TextField pane_txt_5 = (TextField) pane_id_masked.lookup("#pane_txt_5");
        pane_txt_5.setOpacity(1.0);
        pane_txt_5.setPromptText("Podaj język książki");

        TextField pane_txt_6 = (TextField) pane_id_masked.lookup("#pane_txt_6");
        pane_txt_6.setOpacity(1.0);
        pane_txt_6.setPromptText("Uwagi");

        TextField pane_txt_7 = (TextField) pane_id_masked.lookup("#pane_txt_7");
        pane_txt_7.setOpacity(1.0);
        pane_txt_7.setPromptText("Podaj imię autora");

        TextField pane_txt_8 = (TextField) pane_id_masked.lookup("#pane_txt_8");
        pane_txt_8.setOpacity(1.0);
        pane_txt_8.setPromptText("Podaj nazwisko autora");

        TextField pane_txt_9 = (TextField) pane_id_masked.lookup("#pane_txt_9");
        pane_txt_9.setOpacity(1.0);
        pane_txt_9.setPromptText("Podaj gatunek książki");

        TextField pane_txt_10 = (TextField) pane_id_masked.lookup("#pane_txt_10");
        pane_txt_10.setOpacity(1.0);
        pane_txt_10.setPromptText("Podaj wydawnictwo książki");

        Button pane_button = (Button) pane_id_masked.lookup("#pane_button");
        pane_button.setText("Dodaj pozycje");

        boolean[][] process_data = {{false, false, false}};
        AtomicBoolean[] sec = {new AtomicBoolean(false)}; //sec - secound, drugie podejscie czyli dodawanie gatunku, autora i wydawnictwa

        pane_button.setOnMouseClicked(event -> {
            Label pane_result_msg = (Label) pane_id_masked.lookup("#pane_result_msg");

            if (!sec[0].get()) {
                boolean[] ret = db_setData.add_one_record_from_catalog(pane_txt_1.getText(), pane_txt_2.getText(), pane_txt_3.getText(), pane_txt_4.getText(), pane_txt_5.getText(), pane_txt_6.getText(), pane_txt_7.getText(), pane_txt_8.getText(), pane_txt_9.getText(), pane_txt_10.getText(), process_data[0]);

                if (Arrays.equals(ret, new boolean[]{false, false, false})) {
                    pane_result_msg.setText("Pozycja dodana");
                } else {
                    if (Arrays.equals(ret, new boolean[]{true, false, false})) {
                        pane_result_msg.setText("Autor nie istnieje w bazie. Kliknij guzik ponownie aby dodać je wraz z książką.");
                    } else if (Arrays.equals(ret, new boolean[]{false, true, false})) {
                        pane_result_msg.setText("Gatunek nie istnieje w bazie. Kliknij guzik ponownie aby dodać je wraz z książką.");
                    } else if (Arrays.equals(ret, new boolean[]{false, false, true})) {
                        pane_result_msg.setText("Wydawnictwo nie istnieje w bazie. Kliknij guzik ponownie aby dodać je wraz z książką.");
                    } else {
                        pane_result_msg.setText("Przynajmniej jeden z parametrów agregujących nie istnieje w bazie. Kliknij guzik ponownie aby dodać je wraz z książką.");
                    }
                    process_data[0] = ret;
                    sec[0].set(true);
                }
            } else {
                if (process_data[0][0]) {
                    boolean res = db_setData.addAuthor(pane_txt_7.getText(), pane_txt_8.getText());
                    if (res)
                        process_data[0][0] = false;
                }
                if (process_data[0][1]) {
                    boolean res = db_setData.addGenre(pane_txt_9.getText());
                    if (res)
                        process_data[0][1] = false;
                }
                if (process_data[0][2]) {
                    boolean res = db_setData.addPublisher(pane_txt_10.getText());
                    if (res)
                        process_data[0][2] = false;
                }
                boolean[] ret = db_setData.add_one_record_from_catalog(pane_txt_1.getText(), pane_txt_2.getText(), pane_txt_3.getText(), pane_txt_4.getText(), pane_txt_5.getText(), pane_txt_6.getText(), pane_txt_7.getText(), pane_txt_8.getText(), pane_txt_9.getText(), pane_txt_10.getText(), process_data[0]);
                if (Arrays.equals(ret, process_data[0])) {
                    pane_result_msg.setText("Pozycja dodana");
                } else {
                    pane_result_msg.setText("Pozycja nie została dodana. Spróbuj ponownie.");
                    sec[0].set(false);
                }
            }

            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(3),
                    event2 -> pane_result_msg.setOpacity(0.0)
            ));
            pane_result_msg.setOpacity(1.0);

            timeline.play();
        });
        pane_id_masked.setVisible(true);
    }

    /**
     * Obsługuje modyfikacje książki z katalogu. Metoda ustawia nazwy pól.
     * Przy kliknięciu w guzik wywołuje funkcje z bazy i zwraca odpowiedni wynik.
     */
    @FXML
    public void zmodyfikuj_pozycje() {
        Label pane_tytul = (Label) pane_id_masked.lookup("#pane_tytul");
        pane_tytul.setText("Zmodyfikuj pozycje książek");
        TextField pane_txt_1 = (TextField) pane_id_masked.lookup("#pane_txt_1");
        pane_txt_1.setOpacity(1.0);
        pane_txt_1.setPromptText("Podaj id katalogu");

        TextField pane_txt_2 = (TextField) pane_id_masked.lookup("#pane_txt_2");
        pane_txt_2.setOpacity(1.0);
        pane_txt_2.setPromptText("Podaj rok wydania książki");

        TextField pane_txt_3 = (TextField) pane_id_masked.lookup("#pane_txt_3");
        pane_txt_3.setOpacity(1.0);
        pane_txt_3.setPromptText("Podaj wydanie książki");

        TextField pane_txt_4 = (TextField) pane_id_masked.lookup("#pane_txt_4");
        pane_txt_4.setOpacity(1.0);
        pane_txt_4.setPromptText("Podaj isbn książki");

        TextField pane_txt_5 = (TextField) pane_id_masked.lookup("#pane_txt_5");
        pane_txt_5.setOpacity(1.0);
        pane_txt_5.setPromptText("Podaj język książki");

        TextField pane_txt_6 = (TextField) pane_id_masked.lookup("#pane_txt_6");
        pane_txt_6.setOpacity(1.0);
        pane_txt_6.setPromptText("Uwagi");

        Button pane_button = (Button) pane_id_masked.lookup("#pane_button");
        pane_button.setText("Modyfikuj pozycje");
        pane_button.setOnMouseClicked(event -> {
            Label pane_result_msg = (Label) pane_id_masked.lookup("#pane_result_msg");
            int ret = db_updateData.modifyBook(pane_txt_2.getText(), pane_txt_3.getText(), pane_txt_4.getText(), pane_txt_5.getText(), pane_txt_6.getText(), pane_txt_1.getText());
            if (ret > 0) {
                pane_result_msg.setText("Modyfikacja udana");
            } else {
                pane_result_msg.setText("Modyfikacja się nie powiodła");
            }
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(3),
                    event2 -> pane_result_msg.setOpacity(0.0)
            ));
            pane_result_msg.setOpacity(1.0);

            timeline.play();
        });
        pane_id_masked.setVisible(true);
    }

    /**
     * Metoda wywoływana jest po kliknięciu guzika zamknięcia panelu pop-up.
     * Przy kliknięciu w guzik chowane są wszystkie przymioty funkcji
     */
    @FXML
    public void hide_pane(MouseEvent event) {
        TextField pane_txt_1 = (TextField) pane_id_masked.lookup("#pane_txt_1");
        pane_txt_1.setOpacity(0.0);
        pane_txt_1.clear();
        TextField pane_txt_2 = (TextField) pane_id_masked.lookup("#pane_txt_2");
        pane_txt_2.setOpacity(0.0);
        pane_txt_2.clear();
        TextField pane_txt_3 = (TextField) pane_id_masked.lookup("#pane_txt_3");
        pane_txt_3.setOpacity(0.0);
        pane_txt_3.clear();
        TextField pane_txt_4 = (TextField) pane_id_masked.lookup("#pane_txt_4");
        pane_txt_4.setOpacity(0.0);
        pane_txt_4.clear();
        TextField pane_txt_5 = (TextField) pane_id_masked.lookup("#pane_txt_5");
        pane_txt_5.setOpacity(0.0);
        pane_txt_5.clear();

        TextField pane_txt_6 = (TextField) pane_id_masked.lookup("#pane_txt_6");
        pane_txt_6.setOpacity(0.0);
        pane_txt_6.clear();

        TextField pane_txt_7 = (TextField) pane_id_masked.lookup("#pane_txt_7");
        pane_txt_7.setOpacity(0.0);
        pane_txt_7.clear();

        TextField pane_txt_8 = (TextField) pane_id_masked.lookup("#pane_txt_8");
        pane_txt_8.setOpacity(0.0);
        pane_txt_8.clear();

        TextField pane_txt_9 = (TextField) pane_id_masked.lookup("#pane_txt_9");
        pane_txt_9.setOpacity(0.0);
        pane_txt_9.clear();

        TextField pane_txt_10 = (TextField) pane_id_masked.lookup("#pane_txt_10");
        pane_txt_10.setOpacity(0.0);
        pane_txt_10.clear();
        Label pane_result_msg = (Label) pane_id_masked.lookup("#pane_result_msg");
        pane_result_msg.setOpacity(0.0);
        ImageView pane_add_cover = (ImageView) pane_id_masked.lookup("#pane_add_cover");
        pane_add_cover.setOpacity(0.0);
        pane_id_masked.setVisible(false);
    }


}
