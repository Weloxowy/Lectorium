package org.example.home;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.Katalog;
import org.example.Main;
import org.example.verify.logincontroller;

import java.awt.*;
import java.io.IOException;

import static org.example.Main.dbload;

public class katalogcontroller extends home{
    @FXML
    public void font() {
        Font ssp_sb_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/SourceSerifPro-SemiBold.ttf"),25);
        Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"),18);
        Font pop_r_h2 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"),14);
        Font pop_b_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-SemiBold.ttf"),14);
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
    }

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
    private TableView<Katalog> lista = new TableView<Katalog>();

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

    private int lastLoadedIndex = 0; // zmienna przechowująca numer ostatnio pobranego rekordu


    public void Katalog_lista() {
        dbload.print_book();
        ObservableList<Katalog> items = FXCollections.observableArrayList();

        TableColumn idCol = new TableColumn("Id");
        idCol.setMinWidth(anchortable.getPrefWidth()*0.15);
        idCol.setCellValueFactory(
                new PropertyValueFactory<>("id_katalog"));
            idCol.setVisible(false);

        TableColumn autorCol = new TableColumn("Autor");
        autorCol.setMinWidth(anchortable.getPrefWidth()*0.15);
        autorCol.setCellValueFactory(
                new PropertyValueFactory<>("nazwa_autora"));

        TableColumn nazwaCol = new TableColumn("Nazwa");
        nazwaCol.setMinWidth(anchortable.getPrefWidth()*0.25);
        nazwaCol.setCellValueFactory(
                new PropertyValueFactory<>("nazwa"));

        TableColumn rokCol = new TableColumn("Rok wydania");
        rokCol.setMinWidth(anchortable.getPrefWidth()*0.1);
        rokCol.setCellValueFactory(
                new PropertyValueFactory<>("rok_wydania"));

        TableColumn wydanieCol = new TableColumn("Wydanie");
        wydanieCol.setMinWidth(anchortable.getPrefWidth()*0.1);
        wydanieCol.setCellValueFactory(
                new PropertyValueFactory<>("wydanie"));

        TableColumn isbnCol = new TableColumn("ISBN");
        isbnCol.setMinWidth(anchortable.getPrefWidth()*0.1);
        isbnCol.setCellValueFactory(
                new PropertyValueFactory<>("isbn"));

        TableColumn jezykCol = new TableColumn("Język");
        jezykCol.setMinWidth(anchortable.getPrefWidth()*0.1);
        jezykCol.setCellValueFactory(
                new PropertyValueFactory<>("jezyk"));

        TableColumn uwagiCol = new TableColumn("Uwagi");
        uwagiCol.setMinWidth(anchortable.getWidth()*0.4);
        uwagiCol.setCellValueFactory(
                new PropertyValueFactory<>("uwagi"));

        /*lista.addEventFilter(ScrollEvent.SCROLL, event -> {
            ScrollBar verticalScrollBar = getVerticalScrollBar(lista);
            double verticalValue = verticalScrollBar.getValue();
            double verticalMax = verticalScrollBar.getMax();

            if (verticalValue > 0.8 * verticalMax) {
                // Załaduj kolejne rekordy
                loadNextRecords(items);
                lista.setItems(items);
            }
        });
        loadNextRecords(items);*/
        for (String[] tab: dbload.array) {
            items.add(new Katalog(Integer.parseInt(tab[0]), tab[1], tab[2], tab[3], tab[4], tab[5], tab[6], tab[7], tab[8], tab[9]));
        }
        //Dodaj wartości do kolumn
        lista.setItems(items);
        //Dodaj kolumny do tabeli
        lista.getColumns().addAll(idCol,nazwaCol,autorCol,rokCol,wydanieCol,isbnCol,jezykCol,uwagiCol);
        // Ustaw preferowaną wielkość TableView na zgodną z AnchorPane
        lista.setPrefWidth(anchortable.getPrefWidth());
        lista.setPrefHeight(anchortable.getPrefHeight());
        // Powiąż preferowane wielkości TableView i AnchorPane
        lista.prefWidthProperty().bind(anchortable.widthProperty());
        lista.prefHeightProperty().bind(anchortable.heightProperty());
        // Dodaj TableView do AnchorPane
        anchortable.getChildren().addAll(lista);
        // Ustaw parametry kotwiczenia TableView na wartość 0
        AnchorPane.setTopAnchor(lista, 0.0);
        AnchorPane.setLeftAnchor(lista, 0.0);
        AnchorPane.setBottomAnchor(lista, 0.0);
        AnchorPane.setRightAnchor(lista, 0.0);

        lista.getStylesheets().add("/fxml.home/home.css");
        //filtrowanie rekordów
        //tworzenie nowej listy obiektow katalog
        FilteredList<Katalog> filteredList = new FilteredList<Katalog>(items, b-> true);
        //tworzenie lambdy z 3 wartosciami do obserowania zmian dla rekordow
        searchbar.textProperty().addListener((observable,newValue, oldValue) ->{
            filteredList.setPredicate(Katalog -> {
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null ){ return true;}

                String searchword = newValue.toLowerCase();
                //jezeli dla nazwy, autora lub isbn bedzie zgodnosc, wtedy zwracamy
                if(Katalog.getNazwa().toLowerCase().indexOf(searchword) > -1){
                    return true;
                }
                if(Katalog.getNazwa_autora().toLowerCase().indexOf(searchword) > -1){
                    return true;
                }
                if(Katalog.getIsbn().toLowerCase().indexOf(searchword) > -1){
                    return true;
                }
                else return false;

            });
        });
        //tworzenie listy posortowanych elementow dla tych ktore sa poprawne
        SortedList<Katalog> sortedList = new SortedList<Katalog>(filteredList);
        //zamien elementy na te, ktore zgadzaja sie z tekstem w polu wyszukiwania
        sortedList.comparatorProperty().bind(lista.comparatorProperty());
        //umiesc elementy
        lista.setItems(sortedList);

        lista.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
                TablePosition tablePosition = lista.getSelectionModel().getSelectedCells().get(0);
                Integer data = (Integer) idCol.getCellObservableValue(tablePosition.getRow()).getValue();
                System.out.println("DDD:"+data);
                katalog_item(event,data);//get data
            }
        });
    }

    void katalog_item(MouseEvent event, int row) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent parent = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/katalog_item.fxml"));
            parent = loader.load();
            dbload.get_cover(row);
            katalog_itemcontroller kat = loader.getController();
            kat.init(Main.user.getImie(),Main.user.getNazwisko(),event,Main.user.getImage());
            kat.font();
            kat.load(row-1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (parent == null)
            return;
        Scene scene = new Scene(parent,stage.getWidth()-15,stage.getHeight()-38);
        stage.setScene(scene);
    }

    public void Katalog_lista(String query) {
        dbload.print_book();
        ObservableList<Katalog> items = FXCollections.observableArrayList();

        TableColumn idCol = new TableColumn("Id");
        idCol.setMinWidth(anchortable.getPrefWidth()*0.15);
        idCol.setCellValueFactory(
                new PropertyValueFactory<>("id_katalog"));
        idCol.setVisible(false);

        TableColumn autorCol = new TableColumn("Autor");
        autorCol.setMinWidth(anchortable.getPrefWidth()*0.15);
        autorCol.setCellValueFactory(
                new PropertyValueFactory<>("nazwa_autora"));

        TableColumn nazwaCol = new TableColumn("Nazwa");
        nazwaCol.setMinWidth(anchortable.getPrefWidth()*0.25);
        nazwaCol.setCellValueFactory(
                new PropertyValueFactory<>("nazwa"));

        TableColumn rokCol = new TableColumn("Rok wydania");
        rokCol.setMinWidth(anchortable.getPrefWidth()*0.1);
        rokCol.setCellValueFactory(
                new PropertyValueFactory<>("rok_wydania"));

        TableColumn wydanieCol = new TableColumn("Wydanie");
        wydanieCol.setMinWidth(anchortable.getPrefWidth()*0.1);
        wydanieCol.setCellValueFactory(
                new PropertyValueFactory<>("wydanie"));

        TableColumn isbnCol = new TableColumn("ISBN");
        isbnCol.setMinWidth(anchortable.getPrefWidth()*0.1);
        isbnCol.setCellValueFactory(
                new PropertyValueFactory<>("isbn"));

        TableColumn jezykCol = new TableColumn("Język");
        jezykCol.setMinWidth(anchortable.getPrefWidth()*0.1);
        jezykCol.setCellValueFactory(
                new PropertyValueFactory<>("jezyk"));

        TableColumn uwagiCol = new TableColumn("Uwagi");
        uwagiCol.setMinWidth(anchortable.getPrefWidth()*0.2);
        uwagiCol.setCellValueFactory(
                new PropertyValueFactory<>("uwagi"));

        for (String[] tab: dbload.array) {
            items.add(new Katalog(Integer.parseInt(tab[0]), tab[1], tab[2], tab[3], tab[4], tab[5], tab[6], tab[7], tab[8], tab[9]));
        }
        //Dodaj wartości do kolumn
        lista.setItems(items);
        //Dodaj kolumny do tabeli
        lista.getColumns().addAll(idCol,nazwaCol,autorCol,rokCol,wydanieCol,isbnCol,jezykCol,uwagiCol);
        // Ustaw preferowaną wielkość TableView na zgodną z AnchorPane
        lista.setPrefWidth(anchortable.getPrefWidth());
        lista.setPrefHeight(anchortable.getPrefHeight());
        // Powiąż preferowane wielkości TableView i AnchorPane
        lista.prefWidthProperty().bind(anchortable.widthProperty());
        lista.prefHeightProperty().bind(anchortable.heightProperty());
        // Dodaj TableView do AnchorPane
        anchortable.getChildren().addAll(lista);
        // Ustaw parametry kotwiczenia TableView na wartość 0
        AnchorPane.setTopAnchor(lista, 0.0);
        AnchorPane.setLeftAnchor(lista, 0.0);
        AnchorPane.setBottomAnchor(lista, 0.0);
        AnchorPane.setRightAnchor(lista, 0.0);

        lista.getStylesheets().add("/fxml.home/home.css");
        //filtrowanie rekordów
        //tworzenie nowej listy obiektow katalog
        FilteredList<Katalog> filteredList = new FilteredList<Katalog>(items, b-> true);
        //tworzenie lambdy z 3 wartosciami do obserowania zmian dla rekordow

            filteredList.setPredicate(Katalog -> {
                if(query.isEmpty() || query.isBlank() || query == null ){ return true;}

                String searchword = query.toLowerCase();
                //jezeli dla nazwy, autora lub isbn bedzie zgodnosc, wtedy zwracamy
                if(Katalog.getNazwa().toLowerCase().indexOf(searchword) > -1){
                    return true;
                }
                if(Katalog.getNazwa_autora().toLowerCase().indexOf(searchword) > -1){
                    return true;
                }
                if(Katalog.getIsbn().toLowerCase().indexOf(searchword) > -1){
                    return true;
                }
                if(Katalog.getNazwa_gatunku().toLowerCase().indexOf(searchword)> -1){
                    return true;
                }
                else return false;

            });
        //tworzenie listy posortowanych elementow dla tych ktore sa poprawne
        SortedList<Katalog> sortedList = new SortedList<Katalog>(filteredList);
        //zamien elementy na te, ktore zgadzaja sie z tekstem w polu wyszukiwania
        sortedList.comparatorProperty().bind(lista.comparatorProperty());
        //umiesc elementy
        lista.setItems(sortedList);

        lista.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
                TablePosition tablePosition = lista.getSelectionModel().getSelectedCells().get(0);
                Integer data = (Integer) idCol.getCellObservableValue(tablePosition.getRow()).getValue();
                katalog_item(event,data);//get data
            }
        });
    }

    public void init(String imie, String nazwisko, MouseEvent event, Image image) {
        nametag.setText(imie + " " + nazwisko);
        avatar.setImage(Main.user.getImage());
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
            if (node instanceof ScrollBar) {
                ScrollBar scrollBar = (ScrollBar) node;
                if (scrollBar.getOrientation() == Orientation.VERTICAL) {
                    return scrollBar;
                }
            }
        }
        return null;
    }

    void loadNextRecords(ObservableList<Katalog> items){
        int recordsToLoad = 50;
        int endIndex = Math.min(lastLoadedIndex + recordsToLoad, dbload.array.size());

        for (int i = lastLoadedIndex; i < endIndex; i++) {
            String[] tab = dbload.array.get(i);

            items.add(new Katalog(Integer.parseInt(tab[0]), tab[1], tab[2], tab[3], tab[4], tab[5], tab[6], tab[7], tab[8], tab[9]));
        }
        lastLoadedIndex = endIndex;
    }
}
