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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.example.Egzemplarze;
import org.example.Main;
import org.example.User;
import org.example.app.appParent;

import java.util.Objects;

import static org.example.Main.dbload;

public class katalog_itemcontroller extends appParent {
    public boolean if_adm = false;
    @FXML
    private AnchorPane anchortable;

    @FXML
    private Label author_book;

    @FXML
    private ImageView avatar;
    @FXML
    private Label background_tytul;
    @FXML
    private ImageView cover_book;

    @FXML
    private Label isbn_book;

    @FXML
    private Label labelkatalog;

    @FXML
    private Label nametag;

    @FXML
    private Label publisher_book;

    @FXML
    private TextField searchbar;

    @FXML
    private Label title_book;

    @FXML
    private Label year_book;

    @FXML
    private AnchorPane anchor;

    @FXML
    private final TableView<Egzemplarze> lista = new TableView<>();

    @FXML
    public void font(Scene scene) {
        super.font(scene);
        Font ssp_sb_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/SourceSerifPro-SemiBold.ttf"), 25);
        Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"), 18);
        title_book.setFont(ssp_sb_h1);
        author_book.setFont(pop_r_h1);
        isbn_book.setFont(pop_r_h1);
        publisher_book.setFont(pop_r_h1);
        year_book.setFont(pop_r_h1);
    }

    public void load(int id) { //ladujemy po lp. wiersza z tabeli
        String[] tab = dbload.array.get(id);
        if (tab[0] != null) {
            author_book.setText("Autor: " + tab[2]);
            title_book.setText("\"" + tab[1] + "\"");
            year_book.setText("Rok wydania: " + tab[3]);
            publisher_book.setText("Wydawnictwo: " + tab[8]);
            isbn_book.setText("ISBN: " + tab[5]);
            String text;
            if (tab[1].length() > 8) {
                text = tab[1].substring(0, 8);
            } else {
                text = tab[1];
            }
            background_tytul.setText(text);
            egzemplarz_lista(id + 1);
        }
    }

    public void init(String imie, String nazwisko) {
        nametag.setText(imie + " " + nazwisko);
        avatar.setImage(User.getInstance().getImage());
        cover_book.setImage(Main.kat.getOkladka());
        avatar_view();
        lista.setPlaceholder(new Label("Jesteśmy zaskoczeni, że niczego nie znaleźliśmy! Czyżbyśmy mieli dzień wolny?"));
        cover_view();
        AnchorPane.setTopAnchor(anchor, 0.0);
        AnchorPane.setLeftAnchor(anchor, 0.0);
        AnchorPane.setBottomAnchor(anchor, 0.0);
        AnchorPane.setRightAnchor(anchor, 0.0);
        labelkatalog.setStyle("-fx-text-fill:#808080");
    }

    void avatar_view() {
        int radius = 28;
        double centerX = avatar.getBoundsInLocal().getWidth() / 2.0;
        double centerY = avatar.getBoundsInLocal().getHeight() / 2.0;
        Circle clipCircle = new Circle(centerX, centerY, radius);
        avatar.setClip(clipCircle);
    }

    public void egzemplarz_lista(int id) { //TODO do poprawy
        dbload.nowe_item(id,User.getInstance().getId());
        ObservableList<Egzemplarze> items = FXCollections.observableArrayList();

        TableColumn<Egzemplarze, ?> nazwaCol = new TableColumn<>("Nazwa");
        nazwaCol.setMinWidth(anchortable.getPrefWidth() * 0.25);
        nazwaCol.setCellValueFactory(new PropertyValueFactory<>("nazwa"));

        TableColumn<Egzemplarze, ?> nrCol = new TableColumn<>("Numer egzemplarza");
        nrCol.setMinWidth(anchortable.getPrefWidth() * 0.15);
        nrCol.setCellValueFactory(new PropertyValueFactory<>("id_egzemplarze"));

        TableColumn<Egzemplarze, ?>  lokalizacjaCol = new TableColumn<>("Lokalizacja");
        lokalizacjaCol.setMinWidth(anchortable.getPrefWidth() * 0.25);
        lokalizacjaCol.setCellValueFactory(new PropertyValueFactory<>("lokalizacja"));

        TableColumn<Egzemplarze, ?>  dostepneCol = new TableColumn<>("Czy dostępne");
        dostepneCol.setMinWidth(anchortable.getPrefWidth() * 0.1);
        dostepneCol.setCellValueFactory(new PropertyValueFactory<>("czy_dostepne"));
        dostepneCol.setVisible(false);

        TableColumn<Egzemplarze, ?>  zwrotCol = new TableColumn<>("Data zwrotu");
        zwrotCol.setMinWidth(anchortable.getPrefWidth() * 0.15);
        zwrotCol.setCellValueFactory(new PropertyValueFactory<>("data_zwrotu"));

        TableColumn<Egzemplarze,String> wypozyczCol = new TableColumn<>("Zarezerwuj");
        wypozyczCol.setMinWidth(anchortable.getPrefWidth() * 0.1);
        wypozyczCol.setCellValueFactory(new PropertyValueFactory<>("czy_dostepne"));
        wypozyczCol.setCellFactory(col -> {
            TableCell<Egzemplarze, String> cell = new TableCell<>();
            cell.itemProperty().addListener((obs, old, newVal) -> {
                if (newVal != null && newVal.contentEquals("T")) { //wolne
                    Node centreBox = createAddGraphic();
                    cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((Node) null).otherwise(centreBox));
                }
                else if(newVal != null && newVal.contentEquals("W")) { //wypozyczone przez tego uzytkownika
                    Node centreBox = createConfirmGraphic();
                    cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((Node) null).otherwise(centreBox));
                }
                else if(newVal != null && newVal.contentEquals("R")) { //rezerwacja dla tego uzytkownika
                    Node centreBox = createDeleteGraphic();
                    cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((Node) null).otherwise(centreBox));
                }
                cell.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                        if(cell.itemProperty().getValue().contentEquals("T")) {
                            if (dbload.rentlimit(User.getInstance().getId()) < 5) {
                                TablePosition<Egzemplarze, ?> tablePosition = lista.getSelectionModel().getSelectedCells().get(0);
                                int row = tablePosition.getRow();
                                int data = (int) nrCol.getCellObservableValue(row).getValue();
                                dbload.rent(data, User.getInstance().getId());
                                Label notificationLabel = new Label("Zarezerwowano pomyślnie.");
                                Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"), 18);
                                notificationLabel.setFont(pop_r_h1);
                                notificationLabel.setAlignment(Pos.CENTER);
                                notificationLabel.setPrefSize(300, 50);
                                notificationLabel.setLayoutX(700);
                                notificationLabel.setLayoutY(320);
                                notificationLabel.setStyle("""
                                        -fx-border-radius: 10;
                                            -fx-border-color: #004aad;
                                            -fx-background-radius: 10;
                                            -fx-background-color: NULL;
                                            -fx-border-width: 1;
                                            -fx-text-fill: #004aad;""");
                                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event2 -> {
                                    notificationLabel.setVisible(false);
                                    katalog_item(event, id,if_adm);
                                }));
                                timeline.play();
                                anchor.getChildren().add(notificationLabel);
                            } else {
                                Label notificationLabel = new Label("Przekroczono limit rezerwacji.");
                                Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"), 18);
                                notificationLabel.setFont(pop_r_h1);
                                notificationLabel.setAlignment(Pos.CENTER);
                                notificationLabel.setPrefSize(300, 50);
                                notificationLabel.setLayoutX(700);
                                notificationLabel.setLayoutY(320);
                                notificationLabel.setStyle("""
                                        -fx-border-radius: 10;
                                            -fx-border-color: #004aad;
                                            -fx-background-radius: 10;
                                            -fx-background-color: NULL;
                                            -fx-border-width: 1;
                                            -fx-text-fill: #004aad;""");
                                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event2 -> notificationLabel.setVisible(false)));
                                timeline.play();
                                anchor.getChildren().add(notificationLabel);
                            }
                        }
                        else if(cell.itemProperty().getValue().contentEquals("R")){
                            TablePosition<Egzemplarze, ?> tablePosition = lista.getSelectionModel().getSelectedCells().get(0);
                            int row = tablePosition.getRow();
                            int data = (int) nrCol.getCellObservableValue(row).getValue();
                            dbload.delete(data);
                            Label notificationLabel = new Label("Anulowano rezerwację.");
                            Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"), 18);
                            notificationLabel.setFont(pop_r_h1);
                            notificationLabel.setAlignment(Pos.CENTER);
                            notificationLabel.setPrefSize(300, 50);
                            notificationLabel.setLayoutX(700);
                            notificationLabel.setLayoutY(320);
                            notificationLabel.setStyle("""
                                        -fx-border-radius: 10;
                                            -fx-border-color: #004aad;
                                            -fx-background-radius: 10;
                                            -fx-background-color: NULL;
                                            -fx-border-width: 1;
                                            -fx-text-fill: #004aad;""");
                            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event2 -> {
                                notificationLabel.setVisible(false);
                                katalog_item(event, id,if_adm);
                            }));
                            timeline.play();
                            anchor.getChildren().add(notificationLabel);
                        }
                    }
                });
            });
            return cell;

        });


        for (String[] tab : dbload.results) {
            Integer id_egzemplarze = Integer.valueOf(tab[0]);
            String nazwa = tab[1];
            String lokalizacja = tab[2];
            String czy_dostepne = tab[3];
            String data_zwrotu;
            if (tab[4].contentEquals("null")){
                data_zwrotu = "";
            }
            else{
                data_zwrotu = tab[4];
            }
            items.add(new Egzemplarze(nazwa, id_egzemplarze, lokalizacja, czy_dostepne, data_zwrotu));
        }
        //Dodaj wartości do kolumn
        lista.setItems(items);
        //Ustaw wysokosc wierszy na 30px
        lista.setFixedCellSize(30);
        //Dodaj kolumny do tabeli
        lista.getColumns().addAll(nazwaCol, nrCol, lokalizacjaCol, dostepneCol, zwrotCol, wypozyczCol);
        // Ustaw preferowaną wielkość TableView na zgodną z AnchorPane
        lista.setPrefWidth(anchortable.getPrefWidth());
        lista.setPrefHeight(anchortable.getPrefHeight());
        // Powiąż preferowane wielkości TableView i AnchorPane
        lista.prefWidthProperty().bind(anchortable.widthProperty());
        lista.prefHeightProperty().bind(anchortable.heightProperty());
        // Dodaj TableView do AnchorPane
        anchortable.getChildren().addAll(lista);
        // Ustaw parametry kotwiczenia TableView na wartość 0
        AnchorPane.setTopAnchor(anchor, 0.0);
        AnchorPane.setLeftAnchor(anchortable, 0.0);
        AnchorPane.setBottomAnchor(anchortable, 0.0);
        AnchorPane.setRightAnchor(anchortable, 0.0);
        //dodaj css
        lista.getStylesheets().add("/fxml.home/home.css");
    }

    private Node createAddGraphic() {//dodaj rezerwacje
        HBox graphicContainer = new HBox();
        graphicContainer.setAlignment(Pos.CENTER);
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/res/icons/dark/add.png"))));
        imageView.setFitHeight(25);
        imageView.setPreserveRatio(true);
        graphicContainer.getChildren().add(imageView);
        return graphicContainer;
    }

    private Node createDeleteGraphic() { // usuwanie rezerwacje
        HBox graphicContainer = new HBox();
        graphicContainer.setAlignment(Pos.CENTER);
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/res/icons/dark/remove.png"))));
        imageView.setFitHeight(25);
        imageView.setPreserveRatio(true);
        graphicContainer.getChildren().add(imageView);
        return graphicContainer;
    }

    private Node createConfirmGraphic() {
        HBox graphicContainer = new HBox();
        graphicContainer.setAlignment(Pos.CENTER);
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/res/icons/dark/check.png"))));
        imageView.setFitHeight(25);
        imageView.setPreserveRatio(true);
        graphicContainer.getChildren().add(imageView);
        return graphicContainer;
    }

    void cover_view() {
        double centerX = cover_book.getBoundsInLocal().getWidth();
        double centerY = cover_book.getBoundsInLocal().getHeight();
        Rectangle rectangle = new Rectangle(centerX, centerY);
        rectangle.setArcWidth(10.0);
        rectangle.setArcHeight(10.0);
        cover_book.setClip(rectangle);
    }

    @FXML
    void search_init(MouseEvent event) {
        String query = searchbar.getText();
        katalog_clicked(event, query);
    }

    @FXML
    void back_to_prv(MouseEvent event){
        if(if_adm){
            yourHire_clicked(event);
        }
        else{
            katalog_clicked(event);
        }
    }

}
