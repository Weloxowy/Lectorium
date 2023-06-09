package org.example.app.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.example.Users;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import org.example.User;
import org.example.app.appParent;

import static org.example.Main.dbload;


public class userManagerController extends appParent {
    @FXML
    private ImageView avatar = new ImageView();

    @FXML
    private Label labelglowna;


    @FXML
    private TextField searchbar;

    @FXML
    private Label nametag;

    @FXML
    private AnchorPane anchortable;

    @FXML
    private Label labelbiblioteka;

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
    private ImageView search_button;

    @FXML
    private Pane pane_id_masked;

    @FXML
    private AnchorPane root_anchor;

    @FXML
    private GridPane grid;

    @FXML
    final TableView<Users> lista = new TableView<>();

    public void init(String imie, String nazwisko) {
        nametag.setText(imie + " " + nazwisko);
        avatar.setImage(User.getInstance().getImage());
        avatar_view();
        Katalog_lista_adminUser();
        labelrezerwacje.setStyle("-fx-text-fill:#808080");
        pane_id_masked.setVisible(false);
        root_anchor.setTopAnchor(grid,0.0);
        root_anchor.setLeftAnchor(grid,0.0);
        root_anchor.setRightAnchor(grid,0.0);
        root_anchor.setBottomAnchor(grid,0.0);
        root_anchor.setTopAnchor(pane_id_masked,0.0);
        root_anchor.setLeftAnchor(pane_id_masked,0.0);
        root_anchor.setRightAnchor(pane_id_masked,0.0);
        root_anchor.setBottomAnchor(pane_id_masked,0.0);
    }

    @FXML
    public void font(Scene scene) {
        super.font(scene);

    }

    void avatar_view() {
        int radius = 28;
        double centerX = avatar.getBoundsInLocal().getWidth() / 2.0;
        double centerY = avatar.getBoundsInLocal().getHeight() / 2.0;
        Circle clipCircle = new Circle(centerX, centerY, radius);
        avatar.setClip(clipCircle);
    }

    @FXML
    void search_init(MouseEvent event) {
        String query = searchbar.getText();
        katalog_clicked(event, query);
    }

    public void Katalog_lista_adminUser() {
        dbload.print_users();
        ObservableList<Users> items = FXCollections.observableArrayList();

        TableColumn<Users, ?> name = new TableColumn<>("Imie");
        name.setMinWidth(anchortable.getPrefWidth()*0.25);
        name.setCellValueFactory(
                new PropertyValueFactory<>("imie_katalog"));


        TableColumn<Users, ?> surname = new TableColumn<>("Nazwisko");
        surname.setMinWidth(anchortable.getPrefWidth()*0.25);
        surname.setCellValueFactory(
                new PropertyValueFactory<>("nazwisko_katalog"));

        TableColumn<Users, ?> id_user = new TableColumn<>("Id_użytkownika");
        id_user.setMinWidth(anchortable.getPrefWidth()*0.25);
        id_user.setCellValueFactory(
                new PropertyValueFactory<>("id_katalog"));

        TableColumn<Users, ?> check_admin = new TableColumn<>("Czy administrator?");
        check_admin.setMinWidth(anchortable.getPrefWidth()*0.25);
        check_admin.setCellValueFactory(
                new PropertyValueFactory<>("czy_admin_katalog"));


        for(String[] tab: dbload.lista) {
            Integer id = Integer.valueOf(tab[2]);
            String imie = tab[0];
            String nazwisko = tab[1];
            String czy_dostepne = tab[3];
            items.add(new Users(imie,nazwisko,id,czy_dostepne));

        }
        //Dodaj wartości do kolumn
        lista.setItems(items);
        //Ustaw wysokosc wierszy na 30px
        lista.setFixedCellSize(30);
        //Dodaj kolumny do tabeli
        lista.getColumns().addAll(name, surname, id_user, check_admin);
        // Ustaw preferowaną wielkość TableView na zgodną z AnchorPane
        lista.setPrefWidth(anchortable.getPrefWidth());
        lista.setPrefHeight(anchortable.getPrefHeight());
        // Powiąż preferowane wielkości TableView i AnchorPane
        lista.prefWidthProperty().bind(anchortable.widthProperty());
        lista.prefHeightProperty().bind(anchortable.heightProperty());
        // Dodaj TableView do AnchorPane
        anchortable.getChildren().addAll(lista);
        // Ustaw parametry kotwiczenia TableView na wartość 0
        AnchorPane.setLeftAnchor(anchortable, 0.0);
        AnchorPane.setBottomAnchor(anchortable, 0.0);
        AnchorPane.setRightAnchor(anchortable, 0.0);


        lista.getStylesheets().add("/fxml.home/home.css");
        //filtrowanie rekordów
        //tworzenie nowej listy obiektow katalog
        /*
        FilteredList<Users> filteredList = new FilteredList<>(items, b -> true);
        //tworzenie lambdy z 3 wartosciami do obserowania zmian dla rekordow

        searchbar.textProperty().addListener((observable,newValue, oldValue) -> filteredList.setPredicate(Users -> {
            if(newValue.isEmpty() || newValue.isBlank()){ return true;}

            String searchword = newValue.toLowerCase();
            //jezeli dla nazwy, autora lub isbn bedzie zgodnosc, wtedy zwracamy
            if(Users.getImie_katalog().toLowerCase().contains(searchword)){
                return true;
            }
            if(Users.getNazwisko_katalog().toLowerCase().contains(searchword)){
                return true;
            }
            return Users.getCzy_admin_katalog().toLowerCase().contains(searchword);

        }));


        //tworzenie listy posortowanych elementow dla tych ktore sa poprawne
        SortedList<Users> sortedList = new SortedList<>(filteredList);
        //zamien elementy na te, ktore zgadzaja sie z tekstem w polu wyszukiwania
        sortedList.comparatorProperty().bind(lista.comparatorProperty());
        //umiesc elementy
        lista.setItems(sortedList);
/*
        lista.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
                TablePosition<Katalog, ?> tablePosition = lista.getSelectionModel().getSelectedCells().get(0);
                Integer data = (Integer) idCol.getCellObservableValue(tablePosition.getRow()).getValue();
                katalog_item(event,data);//get data
            }
        }); */
    }

    @FXML
    public void hide_pane(MouseEvent event){ //funkcja chowajaca pola; dla wszystkich guzikow; wywolywana jak klikniemy x
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
