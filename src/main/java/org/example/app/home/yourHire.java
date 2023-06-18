
package org.example.app.home;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.example.User;
import org.example.Wypozyczenia;
import org.example.app.appParent;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.example.Main.*;

public class yourHire extends appParent {
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
    private final TableView<Wypozyczenia> lista = new TableView<>();
    @FXML
    private final AnchorPane anchortable = new AnchorPane();

    @FXML
    private AnchorPane anchor_hire;

    @FXML
    private AnchorPane anchor;

    @FXML
    private Label yourHire_name;

    double suma_zadluzenia = 0;

    public void init(String imie, String nazwisko, MouseEvent event) {
        nametag.setText(imie + " " + nazwisko);
        avatar.setImage(User.getInstance().getImage());
        avatar_view();
        actual_button(event,true);
        Lista_Hire(User.getInstance().getId());
        Wyp.setText(Wyp.getText()+" "+imie + " " + nazwisko);
        labelwypozyczenia.setStyle("-fx-text-fill:#808080");
        Wszystkie.setStyle("-fx-border-width: 2");

    }

    public void Lista_Hire(int id) {
        db_getData.getHireInformation(id);
        ObservableList<Wypozyczenia> items = FXCollections.observableArrayList();

        TableColumn<Wypozyczenia, ?> autorCol = new TableColumn<>("Autor");
        autorCol.setMinWidth(anchor_hire.getPrefWidth()*0.2);
        autorCol.setCellValueFactory(
                new PropertyValueFactory<>("nazwa_autora"));

        TableColumn<Wypozyczenia, ?> nazwaCol = new TableColumn<>("Nazwa");
        nazwaCol.setMinWidth(anchor_hire.getPrefWidth()*0.2);
        nazwaCol.setCellValueFactory(
                new PropertyValueFactory<>("nazwa"));

        TableColumn<Wypozyczenia, ?> egzemplarzeCol = new TableColumn<>("Numer egzemplarza");
        egzemplarzeCol.setMinWidth(anchor_hire.getPrefWidth()*0.15);
        egzemplarzeCol.setCellValueFactory(
                new PropertyValueFactory<>("id_egzemplarze"));

        TableColumn<Wypozyczenia, ?> data_wypozyczeniaCol = new TableColumn<>("Data wypozyczenia");
        data_wypozyczeniaCol.setMinWidth(anchor_hire.getPrefWidth()*0.15);
        data_wypozyczeniaCol.setCellValueFactory(
                new PropertyValueFactory<>("data_wypozyczenia"));

        TableColumn<Wypozyczenia, ?> data_zwrotuCol = new TableColumn<>("Termin zwrotu");
        data_zwrotuCol.setMinWidth(anchor_hire.getPrefWidth()*0.1);
        data_zwrotuCol.setCellValueFactory(
                new PropertyValueFactory<>("data_zwrotu"));
        TableColumn<Wypozyczenia, String> grzywnaCol = new TableColumn<>("Grzywna");
        grzywnaCol.setMinWidth(anchor_hire.getPrefWidth()*0.1);
        grzywnaCol.setCellValueFactory(
                new PropertyValueFactory<>("data_zwrotu"));



        grzywnaCol.setCellFactory(col -> {
            TableCell<Wypozyczenia, String> cell = new TableCell<>();
            cell.itemProperty().addListener((obs, old, newVal) -> {
                String debt;
                List<Double> lista = new ArrayList<>();
                int rowIndex = cell.getIndex();
                if (data_zwrotuCol.getCellObservableValue(rowIndex) != null) {
                    String data_zwrotu = data_zwrotuCol.getCellData(rowIndex).toString();
                    LocalDate dataZwrotu = LocalDate.parse(data_zwrotu, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDate currentDate = LocalDate.now();

                    if (dataZwrotu.isAfter(currentDate)) {
                        cell.setText(" - ");
                    } else if (dataZwrotu.isBefore(currentDate)) {
                        DecimalFormat currency = new DecimalFormat("#0.00");
                        int diff = (int) ChronoUnit.DAYS.between(dataZwrotu, currentDate);
                        debt = String.valueOf(currency.format(diff*0.2));
                        cell.setText(debt+" zł");
                            double konwert;
                            try {
                                konwert = currency.parse(debt).doubleValue();
                                lista.add(konwert);
                                for(double pam : lista)
                                {
                                    suma_zadluzenia += pam ;
                                }
                                suma_zadluzenia = currency.parse(String.valueOf(suma_zadluzenia)).doubleValue();
                                yourHire_name.setText("Suma zadłużenia użytkownika wynosi " + suma_zadluzenia + " zł");
                            } catch (ParseException e) {
                                e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    }
                }

            });
            return cell;
        });


        TableColumn<Wypozyczenia, String> przedluzCol = new TableColumn<>("Przedłuż");
        przedluzCol.setMinWidth(anchortable.getPrefWidth()*0.1);
        przedluzCol.setCellValueFactory(
                new PropertyValueFactory<>("ilosc_przedluzen"));

        przedluzCol.setCellFactory(col -> {
            TableCell<Wypozyczenia, String> cell = new TableCell<>();
            cell.itemProperty().addListener((obs, old, newVal) -> {
                int rowIndex = cell.getIndex();
                if (data_zwrotuCol.getCellObservableValue(rowIndex) != null) {
                    String data_zwrotu = data_zwrotuCol.getCellData(rowIndex).toString();
                    LocalDate dataZwrotu = LocalDate.parse(data_zwrotu, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDate currentDate = LocalDate.now();
                    if (newVal != null && Integer.parseInt(newVal) < 3 && dataZwrotu.isAfter(currentDate)) {
                        Node centreBox = createPriorityGraphic();
                        cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((Node) null).otherwise(centreBox));

                        cell.setOnMouseClicked(event -> {
                            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                                TablePosition tablePosition = lista.getSelectionModel().getSelectedCells().get(0);
                                int row = tablePosition.getRow();
                                int data = Integer.parseInt((String) egzemplarzeCol.getCellObservableValue(row).getValue());
                                String start_date = (String) data_wypozyczeniaCol.getCellObservableValue(row).getValue();
                                if (db_updateData.updateRentalDate(data, User.getInstance().getId(), start_date)) {
                                    Label notificationLabel = new Label("Przedłużono pomyślnie.");
                                    Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"), 18);
                                    notificationLabel.setFont(pop_r_h1);
                                    notificationLabel.setAlignment(Pos.CENTER);
                                    notificationLabel.setPrefSize(300, 50);
                                    notificationLabel.setLayoutX(150);
                                    notificationLabel.setLayoutY(70);
                                    notificationLabel.setStyle("""
                                            -fx-border-radius: 10;
                                                -fx-border-color: #004aad;
                                                -fx-background-radius: 10;
                                                -fx-background-color: NULL;
                                                -fx-border-width: 1;
                                                -fx-text-fill: #004aad;""");
                                    Timeline timeline = new Timeline(new KeyFrame(
                                            Duration.seconds(3),
                                            event2 -> {
                                                notificationLabel.setVisible(false);
                                                labelwypozyczenia.fireEvent(event);
                                            }
                                    ));
                                    timeline.play();
                                    anchor.getChildren().add(notificationLabel);
                                } else {
                                    Label notificationLabel = new Label("Przedłużenie się nie powiodło.");
                                    Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"), 18);
                                    notificationLabel.setFont(pop_r_h1);
                                    notificationLabel.setAlignment(Pos.CENTER);
                                    notificationLabel.setPrefSize(300, 50);
                                    notificationLabel.setLayoutX(150);
                                    notificationLabel.setLayoutY(70);
                                    notificationLabel.setStyle("""
                                            -fx-border-radius: 10;
                                                -fx-border-color: #004aad;
                                                -fx-background-radius: 10;
                                                -fx-background-color: NULL;
                                                -fx-border-width: 1;
                                                -fx-text-fill: #004aad;""");
                                    Timeline timeline = new Timeline(new KeyFrame(
                                            Duration.seconds(3),
                                            event2 -> notificationLabel.setVisible(false)
                                    ));
                                    timeline.play();
                                    anchor.getChildren().add(notificationLabel);

                                }
                            }
                        });
                    }
                }
            });
            return cell;
        });

        for (String[] tab : db_getData.rental) {
            String id_egzemplarze = tab[0];
            String nazwa = tab[1];
            String nazwa_autora = tab[2];
            String data_wypozyczenia = tab[3];
            String data_zwrotu = tab[4];
            String ilosc_przedluzen = tab[5];
            items.add(new Wypozyczenia(id_egzemplarze,nazwa, data_wypozyczenia, data_zwrotu,nazwa_autora,ilosc_przedluzen));
        }

        lista.setItems(items);

        lista.getColumns().addAll (nazwaCol, egzemplarzeCol,autorCol, data_wypozyczeniaCol,data_zwrotuCol,grzywnaCol,przedluzCol);

        lista.setPlaceholder(new Label("Jesteśmy zaskoczeni, że niczego nie znaleźliśmy! Czyżbyśmy mieli dzień wolny?"));

        lista.setFixedCellSize(30);

        lista.setPrefWidth(anchor_hire.getPrefWidth());
        lista.setPrefHeight(anchor_hire.getPrefHeight());

        lista.prefWidthProperty().bind(anchor_hire.widthProperty());
        lista.prefHeightProperty().bind(anchor_hire.heightProperty());

        anchor_hire.getChildren().addAll(lista);

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
    public void font(Scene scene){
        super.font(scene);
        Font pop_b_h2 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-SemiBold.ttf"),21);
        Font pop_r_h2 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"), 14);
        Wyp.setFont(pop_b_h2);
        yourHire_name.setFont(pop_r_h2);
    }

    @FXML
    void check_hire_information(MouseEvent event)
    {
        actual_button(event,false);
        ObservableList<Wypozyczenia> items = FXCollections.observableArrayList();

        items.clear();
        lista.refresh();
        db_getData.checkHireInformation(User.getInstance().getId());
        for (String[] tab : db_getData.rental) {
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

    @FXML
    void actual_button(MouseEvent event,boolean type){
        if(type){
            Wszystkie.setStyle("-fx-border-width: 2");
            Aktualne.setStyle("");
            Aktualne.getStyleClass().add("button");
            yourHire_name.setOpacity(1.0);
        }
        else{
            Aktualne.setStyle("-fx-border-width: 2");
            Wszystkie.setStyle("");
            Wszystkie.getStyleClass().add("button");
            yourHire_name.setOpacity(0.0);
        }
    }

}
