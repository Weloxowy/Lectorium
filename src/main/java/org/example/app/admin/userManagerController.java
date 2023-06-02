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
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import org.example.Katalog;
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

    final TableView<User> lista = new TableView<>();

    public void init(String imie, String nazwisko) {
        nametag.setText(imie + " " + nazwisko);
        avatar.setImage(User.getInstance().getImage());
        avatar_view();
        Katalog_lista_adminUser();
        labelglowna.setStyle("-fx-text-fill:#808080");
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
        ObservableList<User> items = FXCollections.observableArrayList();

        TableColumn<User, ?> name = new TableColumn<>("Imie");
        name.setMinWidth(anchortable.getPrefWidth()*0.25);
        name.setCellValueFactory(
                new PropertyValueFactory<>("imie"));


        TableColumn<User, ?> surname = new TableColumn<>("Nazwisko");
        surname.setMinWidth(anchortable.getPrefWidth()*0.25);
        surname.setCellValueFactory(
                new PropertyValueFactory<>("nazwisko"));

        TableColumn<User, ?> id_user = new TableColumn<>("Id_użytkownika");
        id_user.setMinWidth(anchortable.getPrefWidth()*0.25);
        id_user.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn<User, ?> check_admin = new TableColumn<>("Czy administrator?");
        check_admin.setMinWidth(anchortable.getPrefWidth()*0.25);
        check_admin.setCellValueFactory(
                new PropertyValueFactory<>("czy_admin"));

         String imie = User.getInstance().getImie();
         String nazwisko = User.getInstance().getNazwisko();
         int id = User.getInstance().getId();
         String czy_admin = User.getInstance().getCzy_admin();

        for (String[] tab: dbload.lista) {
            System.out.println(tab[0] + " " + tab[1] + " " + tab[2] + " " + tab[3]);
           // items.add(new User(tab[0], tab[1],Integer.parseInt(tab[2]), tab[3]));
            User.getInstance().setImie(tab[0]);
            User.getInstance().setNazwisko(tab[1]);
            User.getInstance().setId(Integer.parseInt(tab[2]));
            User.getInstance().setCzy_admin(tab[3]);
            items.add(User.getInstance());
        }
        User.getInstance().setImie(imie);
        User.getInstance().setNazwisko(nazwisko);
        User.getInstance().setId(id);
        User.getInstance().setCzy_admin(czy_admin);

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
        AnchorPane.setTopAnchor(lista, 0.0);
        AnchorPane.setLeftAnchor(lista, 0.0);
        AnchorPane.setBottomAnchor(lista, 0.0);
        AnchorPane.setRightAnchor(lista, 0.0);


        lista.getStylesheets().add("/fxml.home/home.css");
        //filtrowanie rekordów
        //tworzenie nowej listy obiektow katalog
        FilteredList<User> filteredList = new FilteredList<>(items, b -> true);
        //tworzenie lambdy z 3 wartosciami do obserowania zmian dla rekordow

        searchbar.textProperty().addListener((observable,newValue, oldValue) -> filteredList.setPredicate(User -> {
            if(newValue.isEmpty() || newValue.isBlank()){ return true;}

            String searchword = newValue.toLowerCase();
            //jezeli dla nazwy, autora lub isbn bedzie zgodnosc, wtedy zwracamy
            if(User.getImie().toLowerCase().contains(searchword)){
                return true;
            }
            if(User.getNazwisko().toLowerCase().contains(searchword)){
                return true;
            }
            return User.getCzy_admin().toLowerCase().contains(searchword);

        }));


        //tworzenie listy posortowanych elementow dla tych ktore sa poprawne
        SortedList<User> sortedList = new SortedList<>(filteredList);
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

}
