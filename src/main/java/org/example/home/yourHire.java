
package org.example.home;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
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

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.example.Main.dbload;

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
    private Label Name;
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

    public void init(String imie, String nazwisko, MouseEvent event, Image image) {
        nametag.setText(imie + " " + nazwisko);
        avatar.setImage(Main.user.getImage());
        avatar_view();
        font();
        Lista_Hire(Main.user.getId());
        Name.setText(imie + " " + nazwisko);
        labelwypozyczenia.setStyle("-fx-text-fill:#808080");

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

        TableColumn przedluzCol = new TableColumn("Przedłuż");
        przedluzCol.setMinWidth(anchortable.getPrefWidth()*0.1);
        przedluzCol.setCellValueFactory(
                new PropertyValueFactory<>("ilosc_przedluzen"));
        przedluzCol.setCellFactory(col -> {

            TableCell<Katalog, String> cell = new TableCell<>();
            cell.itemProperty().addListener((obs, old, newVal) -> {
                if (newVal != null && Integer.parseInt(newVal) < 3) { //tak
                    Node centreBox = createPriorityGraphic();
                    cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((Node) null).otherwise(centreBox));

                    cell.setOnMouseClicked(event -> {
                        if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1){
                            TablePosition tablePosition = lista.getSelectionModel().getSelectedCells().get(0);
                            int row = tablePosition.getRow();
                            int data = Integer.parseInt((String) egzemplarzeCol.getCellObservableValue(row).getValue());
                            String start_date = (String) data_wypozyczeniaCol.getCellObservableValue(row).getValue();
                            if(dbload.rent_date_update(data,Main.user.getId(), start_date)) {
                                Label notificationLabel = new Label("Zarezerwowano pomyślnie.");
                                Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"),18);
                                notificationLabel.setFont(pop_r_h1);
                                notificationLabel.setAlignment(Pos.CENTER); //TODO: zmienić wygląd?!
                                notificationLabel.setPrefSize(300, 50);
                                notificationLabel.setLayoutX(700);
                                notificationLabel.setLayoutY(320);
                                notificationLabel.setStyle("-fx-border-radius: 10;\n" +
                                        "    -fx-border-color: #004aad;\n" +
                                        "    -fx-background-radius: 10;\n" +
                                        "    -fx-background-color: NULL;\n" +
                                        "    -fx-border-width: 1;\n" +
                                        "    -fx-text-fill: #004aad;");
                                Timeline timeline = new Timeline(new KeyFrame(
                                        Duration.seconds(3),
                                        event2 -> {
                                            notificationLabel.setVisible(false);
                                            labelwypozyczenia.fireEvent(event);
                                        }
                                ));
                                timeline.play();
                                anchor_hire.getChildren().add(notificationLabel);
                            }
                            else{
                                Label notificationLabel = new Label("Przekroczono limit rezerwacji.");
                                Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"),18);
                                notificationLabel.setFont(pop_r_h1);
                                notificationLabel.setAlignment(Pos.CENTER); //TODO: zmienić wygląd?!
                                notificationLabel.setPrefSize(300, 50);
                                notificationLabel.setLayoutX(700);
                                notificationLabel.setLayoutY(320);
                                notificationLabel.setStyle("-fx-border-radius: 10;\n" +
                                        "    -fx-border-color: #004aad;\n" +
                                        "    -fx-background-radius: 10;\n" +
                                        "    -fx-background-color: NULL;\n" +
                                        "    -fx-border-width: 1;\n" +
                                        "    -fx-text-fill: #004aad;");
                                Timeline timeline = new Timeline(new KeyFrame(
                                        Duration.seconds(3),
                                        event2 -> notificationLabel.setVisible(false)
                                ));
                                timeline.play();
                                anchor_hire.getChildren().add(notificationLabel);
                            }
                        }
                    });
                }
            });
            return cell;

        });

        TableColumn grzywnaCol = new TableColumn("Grzywna");
        data_zwrotuCol.setMinWidth(anchor_hire.getPrefWidth()*0.2);
        data_zwrotuCol.setCellValueFactory(
                new PropertyValueFactory<>("data_zwrotu"));

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
        lista.getColumns().addAll (nazwaCol, egzemplarzeCol,autorCol, data_wypozyczeniaCol,data_zwrotuCol,przedluzCol);
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
        //tworzenie listy posortowanych elementow dla tych ktore sa poprawne
/*
        lista.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
                TablePosition tablePosition = lista.getSelectionModel().getSelectedCells().get(0);
                int row = tablePosition.getRow();
                Integer data = (Integer) idCol.getCellObservableValue(row).getValue();
                System.out.println(data);
            }
        });

 */
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
        Name.setFont(pop_b_h2);
    }

    @FXML
    void check_hire_information(MouseEvent event)
    {
        ObservableList<Wypozyczenia> items = FXCollections.observableArrayList();
        //dbload.yourHireInformation(Main.user.getId());
        items.clear();
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
}
