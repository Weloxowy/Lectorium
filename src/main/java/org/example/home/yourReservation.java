package org.example.home;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import org.example.Rezerwacje;

import static org.example.Main.dbload;

public class yourReservation extends home{

    @FXML
    private Label Name;

    @FXML
    private Label Wyp;

    @FXML
    private AnchorPane anchor_hire;

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
    @FXML
    private AnchorPane anchor;
    @FXML
    private TableView<Rezerwacje> lista = new TableView<Rezerwacje>();
    @FXML
    private AnchorPane anchortable = new AnchorPane();




    public void init(String imie, String nazwisko, MouseEvent event, Image image) {
        nametag.setText(imie + " " + nazwisko);
        avatar.setImage(Main.user.getImage());
        avatar_view();
        font();
        Lista_Hire(Main.user.getId());
        Name.setText(imie + " " + nazwisko);
        labelrezerwacje.setStyle("-fx-text-fill:#808080");

    }

    public void Lista_Hire(int id) {
        dbload.yourReservationInformation(id);
        ObservableList<Rezerwacje> items = FXCollections.observableArrayList();


        TableColumn autorCol = new TableColumn("Autor");
        autorCol.setMinWidth(anchortable.getPrefWidth()*0.15);
        autorCol.setCellValueFactory(
                new PropertyValueFactory<>("nazwa_autora"));

        TableColumn nazwaCol = new TableColumn("Nazwa");
        nazwaCol.setMinWidth(anchortable.getPrefWidth()*0.2);
        nazwaCol.setCellValueFactory(
                new PropertyValueFactory<>("nazwa"));


        TableColumn egzemplarzeCol = new TableColumn("id_egzemplarze");
        egzemplarzeCol.setMinWidth(anchortable.getPrefWidth()*0.05);
        egzemplarzeCol.setCellValueFactory(
                new PropertyValueFactory<>("id_egzemplarze"));

        TableColumn data_koncaCol = new TableColumn("data_konca");
        data_koncaCol.setMinWidth(anchortable.getPrefWidth()*0.20);
        data_koncaCol.setCellValueFactory(
                new PropertyValueFactory<>("data_konca"));

        TableColumn data_rezerwacjiCol = new TableColumn("data_rezerwacji");
        data_rezerwacjiCol.setMinWidth(anchortable.getPrefWidth()*0.20);
        data_rezerwacjiCol.setCellValueFactory(
                new PropertyValueFactory<>("data_rezerwacji"));

        TableColumn przedluz_rezerwacjeCol = new TableColumn("przedluż_rezerwacje");
        przedluz_rezerwacjeCol.setMinWidth(anchortable.getPrefWidth()*0.10);
        przedluz_rezerwacjeCol.setCellValueFactory(
                new PropertyValueFactory<>("przedluz_rezerwacje"));

        TableColumn anuluj_rezerwacjeCol = new TableColumn("anuluj_rezerwacje");
        anuluj_rezerwacjeCol.setMinWidth(anchortable.getPrefWidth()*0.10);
        anuluj_rezerwacjeCol.setCellValueFactory(
                new PropertyValueFactory<>("anuluj_rezerwacje"));

        przedluz_rezerwacjeCol.setCellFactory(col -> {
            TableCell<Katalog, String> cell = new TableCell<>();
            cell.itemProperty().addListener((obs, old, newVal) -> {
                int rowIndex = cell.getIndex();
                if (przedluz_rezerwacjeCol.getCellObservableValue(rowIndex) != null) {
                    Node centreBox = createPriorityGraphic();
                    cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((Node) null).otherwise(centreBox));

                cell.setOnMouseClicked(event -> {
                    if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1){
                        if(Integer.parseInt(przedluz_rezerwacjeCol.getCellData(rowIndex).toString()) > 3) {
                            Label notificationLabel = new Label("Przekroczono limit przedluzen rezerwacji.");
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
                            anchor.getChildren().add(notificationLabel);
                        }
                        else{
                            TablePosition tablePosition = lista.getSelectionModel().getSelectedCells().get(0);
                            int row = tablePosition.getRow();
                            int data = Integer.parseInt((String) egzemplarzeCol.getCellObservableValue(row).getValue());
                            dbload.update(data);
                            Label notificationLabel = new Label("Przedłużono pomyślnie.");
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
                                        yourReservation_clicked(event);
                                    }
                            ));
                            timeline.play();
                            anchor.getChildren().add(notificationLabel);
                        }
                    }
                });
                }
            });
            return cell;

        });


        anuluj_rezerwacjeCol.setCellFactory(col -> {
            TableCell<Katalog, String> cell = new TableCell<>();
            cell.itemProperty().addListener((obs, old, newVal) -> {
                int rowIndex = cell.getIndex();
                if (anuluj_rezerwacjeCol.getCellObservableValue(rowIndex) != null) {
                    Node centreBox = createDeleteGraphic();
                    cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((Node) null).otherwise(centreBox));

                    cell.setOnMouseClicked(event -> {
                        if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1){
                            if(anuluj_rezerwacjeCol.getCellData(rowIndex).toString().contentEquals("1")) {
                                TablePosition tablePosition = lista.getSelectionModel().getSelectedCells().get(0);
                                int row = tablePosition.getRow();
                                int data = Integer.parseInt((String) egzemplarzeCol.getCellObservableValue(row).getValue());
                                if(dbload.delete(data) > 0)
                                {
                                    Label notificationLabel = new Label("Anulowano rezerwacje.");
                                    Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"),18);
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
                                    Timeline timeline = new Timeline(new KeyFrame(
                                            Duration.seconds(3),
                                            event2 -> {notificationLabel.setVisible(false); labelrezerwacje.fireEvent(event);}
                                    ));
                                    timeline.play();
                                    anchor.getChildren().add(notificationLabel);
                                }
                                else
                                {
                                    Label notificationLabel = new Label("Nie udalo sie anulowac rezerwacji.");
                                    Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"),18);
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
                                    Timeline timeline = new Timeline(new KeyFrame(
                                            Duration.seconds(3),
                                            event2 -> {notificationLabel.setVisible(false); labelrezerwacje.fireEvent(event);}
                                    ));
                                    timeline.play();
                                    anchor.getChildren().add(notificationLabel);
                                }
                            }
                        }
                    });
                }
            });
            return cell;

        });





        for (String[] tab : dbload.ListHire) {
            String id_egzemplarze = tab[0];
            String nazwa = tab[1];
            String nazwa_autora = tab[2];
            String data_konca = tab[3];
            String data_rezerwacji = tab[4];
            String przedluz_rezerwacje = tab[5];
            String anuluj_rezerwacje = tab[6];

            items.add(new Rezerwacje(id_egzemplarze,nazwa, nazwa_autora, data_konca, data_rezerwacji,  przedluz_rezerwacje, anuluj_rezerwacje));
        }
        //Dodaj wartości do kolumn
        lista.setItems(items);
        //Dodaj kolumny do tabeli
        lista.getColumns().addAll(egzemplarzeCol, nazwaCol,  autorCol, data_koncaCol,data_rezerwacjiCol,przedluz_rezerwacjeCol, anuluj_rezerwacjeCol);
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
        Name.setFont(pop_b_h2);
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

    private Node createDeleteGraphic(){
        HBox graphicContainer = new HBox();
        graphicContainer.setAlignment(Pos.CENTER);
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/res/icons/dark/remove.png")));
        imageView.setFitHeight(25);
        imageView.setPreserveRatio(true);
        graphicContainer.getChildren().add(imageView);
        return graphicContainer;
    }


}