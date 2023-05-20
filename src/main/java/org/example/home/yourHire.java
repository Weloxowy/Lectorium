
package org.example.home;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.example.Katalog;
import org.example.Main;
import org.example.Wypozyczenia;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.example.Main.*;

public class yourHire extends home{
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
    private Label Wyp;
    @FXML
    private Button Wszystkie;
    @FXML
    private Button Aktualne;
    @FXML
    private TextField searchbar;
    @FXML
    private TableView<Wypozyczenia> lista = new TableView<Wypozyczenia>();
    @FXML
    private AnchorPane anchortable = new AnchorPane();

    @FXML
    private AnchorPane anchor_hire;

    @FXML
    private AnchorPane anchor;

    public void init(String imie, String nazwisko, MouseEvent event, Image image) {
        nametag.setText(imie + " " + nazwisko);
        avatar.setImage(Main.user.getImage());
        avatar_view();
        font();
        actual_button(event,true);
        Lista_Hire(Main.user.getId());
        Wyp.setText(Wyp.getText()+" "+imie + " " + nazwisko);
        labelwypozyczenia.setStyle("-fx-text-fill:#808080");
        Wszystkie.setStyle("-fx-border-width: 2");
    }
    public void Lista_Hire(int id) {
        dbload.yourHireInformation(id);
        ObservableList<Wypozyczenia> items = FXCollections.observableArrayList();

        TableColumn autorCol = new TableColumn("Autor");
        autorCol.setMinWidth(anchor_hire.getPrefWidth()*0.2);
        autorCol.setCellValueFactory(
                new PropertyValueFactory<>("nazwa_autora"));

        TableColumn nazwaCol = new TableColumn("Nazwa");
        nazwaCol.setMinWidth(anchor_hire.getPrefWidth()*0.2);
        nazwaCol.setCellValueFactory(
                new PropertyValueFactory<>("nazwa"));

        TableColumn egzemplarzeCol = new TableColumn("Numer egzemplarza");
        egzemplarzeCol.setMinWidth(anchor_hire.getPrefWidth()*0.15);
        egzemplarzeCol.setCellValueFactory(
                new PropertyValueFactory<>("id_egzemplarze"));

        TableColumn data_wypozyczeniaCol = new TableColumn("Data wypozyczenia");
        data_wypozyczeniaCol.setMinWidth(anchor_hire.getPrefWidth()*0.15);
        data_wypozyczeniaCol.setCellValueFactory(
                new PropertyValueFactory<>("data_wypozyczenia"));

        TableColumn data_zwrotuCol = new TableColumn("Termin zwrotu");
        data_zwrotuCol.setMinWidth(anchor_hire.getPrefWidth()*0.1);
        data_zwrotuCol.setCellValueFactory(
                new PropertyValueFactory<>("data_zwrotu"));
        TableColumn grzywnaCol = new TableColumn("Grzywna");
        grzywnaCol.setMinWidth(anchor_hire.getPrefWidth()*0.1);
        grzywnaCol.setCellValueFactory(
                new PropertyValueFactory<>("data_zwrotu"));

        grzywnaCol.setCellFactory(col -> {  //Ustawiamy wartość pola dla kolumny przedluzCol
            TableCell<Katalog, String> cell = new TableCell<>(); //deklarujemy pojedyncze pole jako obiekt klasy TableCell

            cell.itemProperty().addListener((obs, old, newVal) -> {
                int rowIndex = cell.getIndex();
                if (data_zwrotuCol.getCellObservableValue(rowIndex) != null) {
                    String data_zwrotu = data_zwrotuCol.getCellData(rowIndex).toString();
                    LocalDate dataZwrotu = LocalDate.parse(data_zwrotu, DateTimeFormatter.ofPattern("yyyy-MM-dd")); //konwersja daty z patternu, jaki mamy w bazie
                    LocalDate currentDate = LocalDate.now(); //aktualna data
                    String debt = null;
                    if (dataZwrotu.isAfter(currentDate)) {
                        cell.setText(" - ");
                    } else if (dataZwrotu.isBefore(currentDate)) {
                        DecimalFormat currency = new DecimalFormat("#0.00");
                        int diff = (int) ChronoUnit.DAYS.between(dataZwrotu, currentDate);
                        debt = String.valueOf(currency.format(diff*0.2));
                        cell.setText(debt+" zł");
                    }
                }
            });
            return cell;
        });


        TableColumn przedluzCol = new TableColumn("Przedłuż");
        przedluzCol.setMinWidth(anchortable.getPrefWidth()*0.1);
        przedluzCol.setCellValueFactory(
                new PropertyValueFactory<>("ilosc_przedluzen"));
/*
                            |\|\
                            ..    \       .
        [Spaghetti code   o--     \\    / @)
        jest super!]        v__///\\\\__/ @
                             {           }
                             {  } \\\{  }
                             <_|      <_|
*/
        przedluzCol.setCellFactory(col -> {  //Ustawiamy wartość pola dla kolumny przedluzCol
            TableCell<Katalog, String> cell = new TableCell<>(); //deklarujemy pojedyncze pole jako obiekt klasy TableCell
            cell.itemProperty().addListener((obs, old, newVal) -> { //pierwsza lambda nasłuchuje zmiany wartości elementów w kolumnie
                int rowIndex = cell.getIndex(); //rowIndex to jest numer wiersza w nowej tabeli
                if (data_zwrotuCol.getCellObservableValue(rowIndex) != null) { /* sprawdzamy czy data zwrotu jest ustawiona,
                 w przeciwnym wypadku nie podejmie się stworzenia guzika ponieważ "data_zwrotu > aktualna_data"
                 jest jednym z warunków dodania guzika */
                    String data_zwrotu = data_zwrotuCol.getCellData(rowIndex).toString();
                    LocalDate dataZwrotu = LocalDate.parse(data_zwrotu, DateTimeFormatter.ofPattern("yyyy-MM-dd")); //konwersja daty z patternu, jaki mamy w bazie
                    LocalDate currentDate = LocalDate.now(); //aktualna data
                    if (newVal != null && Integer.parseInt(newVal) < 3 && dataZwrotu.isAfter(currentDate)) { //jezeli nie przedluzylismy wiecej niz 2 razy i data_zwrotu nie mineła to tworzymy grafike
                        Node centreBox = createPriorityGraphic(); //tworzenie grafiki -> funkcja na koncu klasy
                        cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((Node) null).otherwise(centreBox)); //ustawianie granic wielkosci

                        cell.setOnMouseClicked(event -> { //jezeli klikniemy na guzik dokonujemy przedluzenia o 30 dni
                            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) { //klikniecie nastapilo dwa razy PPM
                                TablePosition tablePosition = lista.getSelectionModel().getSelectedCells().get(0);  //ustawiamy pozycje tabeli na klikniety wiersz
                                int row = tablePosition.getRow(); //pobieramy numer w tabeli wiersza
                                int data = Integer.parseInt((String) egzemplarzeCol.getCellObservableValue(row).getValue()); //pobieramy id egzemplarza
                                String start_date = (String) data_wypozyczeniaCol.getCellObservableValue(row).getValue(); //pobieramy date wypozyczenia
                                if (dbload.rent_date_update(data, Main.user.getId(), start_date)) { //wykonujemy zapytanie przedłuzajace wypozyczenie: przedluzy i da komunikat o wykonaniu ? da komunikat o braku mozliwosci przedluzenia
                                    Label notificationLabel = new Label("Zarezerwowano pomyślnie."); //wygląd
                                    Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"), 18);
                                    notificationLabel.setFont(pop_r_h1);
                                    notificationLabel.setAlignment(Pos.CENTER); //TODO: zmienić wygląd?!
                                    notificationLabel.setPrefSize(300, 50);
                                    notificationLabel.setLayoutX(150);
                                    notificationLabel.setLayoutY(70);
                                    notificationLabel.setStyle("-fx-border-radius: 10;\n" +
                                            "    -fx-border-color: #004aad;\n" +
                                            "    -fx-background-radius: 10;\n" +
                                            "    -fx-background-color: NULL;\n" +
                                            "    -fx-border-width: 1;\n" +
                                            "    -fx-text-fill: #004aad;");
                                    Timeline timeline = new Timeline(new KeyFrame( //ustawiamy ramke czasową na 3 sekundy; po 3 sekundach wykona sie to co jest w funkcji
                                            Duration.seconds(3),
                                            event2 -> {
                                                notificationLabel.setVisible(false); //chowamy komunikat
                                                labelwypozyczenia.fireEvent(event); //ponownie uruchamiamy tabele z wypozyczeniami aby odswiezyc stan
                                            }
                                    ));
                                    timeline.play(); //uruchamiamy ramke
                                    anchor.getChildren().add(notificationLabel); //wyswietlamy komunikat
                                } else {
                                    Label notificationLabel = new Label("Przekroczono limit rezerwacji.");
                                    Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"), 18);
                                    notificationLabel.setFont(pop_r_h1);
                                    notificationLabel.setAlignment(Pos.CENTER); //TODO: zmienić wygląd?!
                                    notificationLabel.setPrefSize(300, 50);
                                    notificationLabel.setLayoutX(150);
                                    notificationLabel.setLayoutY(70);
                                    notificationLabel.setStyle("-fx-border-radius: 10;\n" +
                                            "    -fx-border-color: #004aad;\n" +
                                            "    -fx-background-radius: 10;\n" +
                                            "    -fx-background-color: NULL;\n" +
                                            "    -fx-border-width: 1;\n" +
                                            "    -fx-text-fill: #004aad;");
                                    Timeline timeline = new Timeline(new KeyFrame( //ustawiamy ramke czasową na 3 sekundy; po 3 sekundach wykona sie to co jest w funkcji
                                            Duration.seconds(3),
                                            event2 -> notificationLabel.setVisible(false) //chowamy komunikat
                                    ));
                                    timeline.play(); //uruchamiamy ramke
                                    anchor.getChildren().add(notificationLabel); //pokazujemy komunikat

                                }
                            }
                        });
                    }
                }
            });
            return cell;
        });

        for (String[] tab : dbload.ListHire) {
            String id_egzemplarze = tab[0];
            String nazwa = tab[1];
            String nazwa_autora = tab[2];
            String data_wypozyczenia = tab[3];
            String data_zwrotu = tab[4];
            String ilosc_przedluzen = tab[5];
            items.add(new Wypozyczenia(id_egzemplarze,nazwa, data_wypozyczenia, data_zwrotu,nazwa_autora,ilosc_przedluzen));
        }
        //Dodaj wartości do kolumn
        lista.setItems(items);
        //Dodaj kolumny do tabeli
        lista.getColumns().addAll (nazwaCol, egzemplarzeCol,autorCol, data_wypozyczeniaCol,data_zwrotuCol,grzywnaCol,przedluzCol);
        //Ustaw wysokosc wierszy na 30px
        lista.setFixedCellSize(30);
        // Ustaw preferowaną wielkość TableView na zgodną z AnchorPane
        lista.setPrefWidth(anchor_hire.getPrefWidth());
        lista.setPrefHeight(anchor_hire.getPrefHeight());
        // Powiąż preferowane wielkości TableView i AnchorPane
        lista.prefWidthProperty().bind(anchor_hire.widthProperty());
        lista.prefHeightProperty().bind(anchor_hire.heightProperty());
        // Dodaj TableView do AnchorPane
        anchor_hire.getChildren().addAll(lista);
        // Ustaw parametry kotwiczenia TableView na wartość 0
        AnchorPane.setTopAnchor(lista, 0.0);
        AnchorPane.setLeftAnchor(lista, 0.0);
        AnchorPane.setBottomAnchor(lista, 0.0);
        AnchorPane.setRightAnchor(lista, 0.0);

        lista.getStylesheets().add("/fxml.home/home.css");

    }
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
        Font pop_b_h2 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-SemiBold.ttf"),21);
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
        Wyp.setFont(pop_b_h2);
    }

    @FXML
    void check_hire_information(MouseEvent event)
    {
        actual_button(event,false);
        ObservableList<Wypozyczenia> items = FXCollections.observableArrayList();
        //dbload.yourHireInformation(Main.user.getId());
        items.clear();
        lista.refresh();
        dbload.check_hire_information(Main.user.getId());
        for (String[] tab : dbload.ListHire) {
            String id_egzemplarze = tab[0];
            String nazwa = tab[1];
            String nazwa_autora = tab[2];
            String data_wypozyczenia = tab[3];
            String data_zwrotu = tab[4];
            String ilosc_przedluzen = tab[5];
            items.add(new Wypozyczenia(id_egzemplarze,nazwa, data_wypozyczenia, data_zwrotu,nazwa_autora,ilosc_przedluzen));
        }
        lista.setItems(items);
    }

    private Node createPriorityGraphic(){
        HBox graphicContainer = new HBox();
        graphicContainer.setAlignment(Pos.CENTER);
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/res/icons/dark/add.png")));
        imageView.setFitHeight(25);
        imageView.setPreserveRatio(true);
        graphicContainer.getChildren().add(imageView);
        return graphicContainer;
    }

    @FXML
    void actual_button(MouseEvent event,boolean type){
        if(type){ //dla wszystkich
            Wszystkie.setStyle("-fx-border-width: 2");
            Aktualne.setStyle("");
            Aktualne.getStyleClass().add("button");
        }
        else{ //dla aktualnych
            Aktualne.setStyle("-fx-border-width: 2");
            Wszystkie.setStyle("");
            Wszystkie.getStyleClass().add("button");
        }
    }

}
