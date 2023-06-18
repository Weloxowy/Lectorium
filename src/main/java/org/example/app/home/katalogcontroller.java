package org.example.app.home;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import org.example.Katalog;
import org.example.User;
import org.example.app.appParent;

import java.util.Objects;

import static org.example.Main.db_getData;
import static org.example.Main.db_parent;

public class katalogcontroller extends appParent {
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
    private final TableView<Katalog> lista = new TableView<>();

    @FXML
    private ImageView logout;

    @FXML
    private VBox menubox;

    @FXML
    private Label nametag;

    @FXML
    private TextField searchbar;

    @FXML
    private AnchorPane anchortable = new AnchorPane();

    private int lastLoadedIndex = 0;


    public void Katalog_lista() {
        db_getData.getBooks();
        ObservableList<Katalog> items = FXCollections.observableArrayList();

        TableColumn<Katalog, ?> idCol = new TableColumn<>("Id");
        idCol.setMinWidth(anchortable.getPrefWidth()*0.15);
        idCol.setCellValueFactory(
                new PropertyValueFactory<>("id_katalog"));
            idCol.setVisible(false);

        TableColumn<Katalog, ?> autorCol = new TableColumn<>("Autor");
        autorCol.setMinWidth(anchortable.getPrefWidth()*0.15);
        autorCol.setCellValueFactory(
                new PropertyValueFactory<>("nazwa_autora"));

        TableColumn<Katalog, ?> nazwaCol = new TableColumn<>("Nazwa");
        nazwaCol.setMinWidth(anchortable.getPrefWidth()*0.25);
        nazwaCol.setCellValueFactory(
                new PropertyValueFactory<>("nazwa"));

        TableColumn<Katalog, ?> rokCol = new TableColumn<>("Rok wydania");
        rokCol.setMinWidth(anchortable.getPrefWidth()*0.1);
        rokCol.setCellValueFactory(
                new PropertyValueFactory<>("rok_wydania"));

        TableColumn<Katalog, ?> wydanieCol = new TableColumn<>("Wydanie");
        wydanieCol.setMinWidth(anchortable.getPrefWidth()*0.1);
        wydanieCol.setCellValueFactory(
                new PropertyValueFactory<>("wydanie"));

        TableColumn<Katalog, ?> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setMinWidth(anchortable.getPrefWidth()*0.1);
        isbnCol.setCellValueFactory(
                new PropertyValueFactory<>("isbn"));

        TableColumn<Katalog, ?> jezykCol = new TableColumn<>("Język");
        jezykCol.setMinWidth(anchortable.getPrefWidth()*0.1);
        jezykCol.setCellValueFactory(
                new PropertyValueFactory<>("jezyk"));

        TableColumn<Katalog, ?> uwagiCol = new TableColumn<>("Uwagi");
        uwagiCol.setMinWidth(anchortable.getWidth()*0.4);
        uwagiCol.setCellValueFactory(
                new PropertyValueFactory<>("uwagi"));

        lista.addEventFilter(ScrollEvent.SCROLL, event -> {
            ScrollBar verticalScrollBar = getVerticalScrollBar(lista);
            double verticalValue = Objects.requireNonNull(verticalScrollBar).getValue();
            double verticalMax = verticalScrollBar.getMax();

            if (verticalValue > 0.8 * verticalMax) {

                loadNextRecords(items);
                lista.setItems(items);
            }
        });

        loadNextRecords(items);


        lista.setItems(items);

        lista.setFixedCellSize(30);

        lista.getColumns().addAll(idCol,nazwaCol,autorCol,rokCol,wydanieCol,isbnCol,jezykCol,uwagiCol);

        lista.setPrefWidth(anchortable.getPrefWidth());
        lista.setPrefHeight(anchortable.getPrefHeight());

        lista.prefWidthProperty().bind(anchortable.widthProperty());
        lista.prefHeightProperty().bind(anchortable.heightProperty());

        anchortable.getChildren().addAll(lista);

        AnchorPane.setTopAnchor(lista, 0.0);
        AnchorPane.setLeftAnchor(lista, 0.0);
        AnchorPane.setBottomAnchor(lista, 0.0);
        AnchorPane.setRightAnchor(lista, 0.0);

        lista.getStylesheets().add("/fxml.home/home.css");

        lista.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
                TablePosition<Katalog, ?> tablePosition = lista.getSelectionModel().getSelectedCells().get(0);
                Integer data = (Integer) idCol.getCellObservableValue(tablePosition.getRow()).getValue();

                katalog_item(event,data,false);
            }
        });
    }

    public void Katalog_lista(String query) {
        db_getData.getBooks();
        ObservableList<Katalog> items = FXCollections.observableArrayList();

        TableColumn<Katalog, ?> idCol = new TableColumn<>("Id");
        idCol.setMinWidth(anchortable.getPrefWidth()*0.15);
        idCol.setCellValueFactory(
                new PropertyValueFactory<>("id_katalog"));
        idCol.setVisible(false);

        TableColumn<Katalog, ?> autorCol = new TableColumn<>("Autor");
        autorCol.setMinWidth(anchortable.getPrefWidth()*0.15);
        autorCol.setCellValueFactory(
                new PropertyValueFactory<>("nazwa_autora"));

        TableColumn<Katalog, ?> nazwaCol = new TableColumn<>("Nazwa");
        nazwaCol.setMinWidth(anchortable.getPrefWidth()*0.25);
        nazwaCol.setCellValueFactory(
                new PropertyValueFactory<>("nazwa"));

        TableColumn<Katalog, ?> rokCol = new TableColumn<>("Rok wydania");
        rokCol.setMinWidth(anchortable.getPrefWidth()*0.1);
        rokCol.setCellValueFactory(
                new PropertyValueFactory<>("rok_wydania"));

        TableColumn<Katalog, ?> wydanieCol = new TableColumn<>("Wydanie");
        wydanieCol.setMinWidth(anchortable.getPrefWidth()*0.1);
        wydanieCol.setCellValueFactory(
                new PropertyValueFactory<>("wydanie"));

        TableColumn<Katalog, ?> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setMinWidth(anchortable.getPrefWidth()*0.1);
        isbnCol.setCellValueFactory(
                new PropertyValueFactory<>("isbn"));

        TableColumn<Katalog, ?> jezykCol = new TableColumn<>("Język");
        jezykCol.setMinWidth(anchortable.getPrefWidth()*0.1);
        jezykCol.setCellValueFactory(
                new PropertyValueFactory<>("jezyk"));

        TableColumn<Katalog, ?> uwagiCol = new TableColumn<>("Uwagi");
        uwagiCol.setMinWidth(anchortable.getPrefWidth()*0.2);
        uwagiCol.setCellValueFactory(
                new PropertyValueFactory<>("uwagi"));

        for (String[] tab: db_getData.books) {
            items.add(new Katalog(Integer.parseInt(tab[0]), tab[1], tab[2], tab[3], tab[4], tab[5], tab[6], tab[7], tab[8], tab[9]));
        }

        lista.setItems(items);

        lista.setFixedCellSize(30);

        lista.getColumns().addAll(idCol,nazwaCol,autorCol,rokCol,wydanieCol,isbnCol,jezykCol,uwagiCol);

        lista.setPrefWidth(anchortable.getPrefWidth());
        lista.setPrefHeight(anchortable.getPrefHeight());

        lista.prefWidthProperty().bind(anchortable.widthProperty());
        lista.prefHeightProperty().bind(anchortable.heightProperty());

        anchortable.getChildren().addAll(lista);

        AnchorPane.setTopAnchor(lista, 0.0);
        AnchorPane.setLeftAnchor(lista, 0.0);
        AnchorPane.setBottomAnchor(lista, 0.0);
        AnchorPane.setRightAnchor(lista, 0.0);

        lista.getStylesheets().add("/fxml.home/home.css");

        FilteredList<Katalog> filteredList = new FilteredList<>(items, b-> true);

            filteredList.setPredicate(Katalog -> {
                if(query.isEmpty() || query.isBlank()){ return true;}

                String searchword = query.toLowerCase();

                if(Katalog.getNazwa().toLowerCase().contains(searchword)){
                    return true;
                }
                if(Katalog.getNazwa_autora().toLowerCase().contains(searchword)){
                    return true;
                }
                if(Katalog.getIsbn().toLowerCase().contains(searchword)){
                    return true;
                }
                return Katalog.getNazwa_gatunku().toLowerCase().contains(searchword);

            });

        SortedList<Katalog> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(lista.comparatorProperty());

        lista.setItems(sortedList);

        lista.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
                TablePosition<org.example.Katalog, ?> tablePosition = lista.getSelectionModel().getSelectedCells().get(0);

                Integer data = (Integer) idCol.getCellObservableValue(tablePosition.getRow()).getValue();
                katalog_item(event,data,false);
            }
        });
    }

    public void init(String imie, String nazwisko) {
        nametag.setText(imie + " " + nazwisko);
        avatar.setImage(User.getInstance().getImage());
        avatar_view();
        lista.setPlaceholder(new Label("Jesteśmy zaskoczeni, że niczego nie znaleźliśmy! Czyżbyśmy mieli dzień wolny?"));
        labelkatalog.setStyle("-fx-text-fill:#808080");
    }

    void avatar_view() {
        int radius = 28;
        double centerX = avatar.getBoundsInLocal().getWidth() / 2.0;
        double centerY = avatar.getBoundsInLocal().getHeight() / 2.0;
        Circle clipCircle = new Circle(centerX, centerY, radius);
        avatar.setClip(clipCircle);
    }

    @FXML
    void search_init(MouseEvent event){
        String query = searchbar.getText();
        katalog_clicked(event,query);
    }

    private ScrollBar getVerticalScrollBar(TableView<?> tableView) {
        for (Node node : tableView.lookupAll(".scroll-bar")) {
            if (node instanceof ScrollBar scrollBar) {
                if (scrollBar.getOrientation() == Orientation.VERTICAL) {
                    return scrollBar;
                }
            }
        }
        return null;
    }

    void loadNextRecords(ObservableList<Katalog> items){
        int recordsToLoad = 50;
        int endIndex = Math.min(lastLoadedIndex + recordsToLoad, db_getData.books.size());

        for (int i = lastLoadedIndex; i < endIndex; i++) {
            String[] tab = db_getData.books.get(i);

            items.add(new Katalog(Integer.parseInt(tab[0]), tab[1], tab[2], tab[3], tab[4], tab[5], tab[6], tab[7], tab[8], tab[9]));
        }
        lastLoadedIndex = endIndex;
    }
}
